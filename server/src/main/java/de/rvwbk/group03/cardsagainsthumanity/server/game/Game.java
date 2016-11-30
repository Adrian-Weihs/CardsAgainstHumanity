package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventHandler;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;
import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.network.GameState;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.GameCommand;
import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.communication.ServerCommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameAction;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEventHandler;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.Player;

/**
 * 
 * @author Adrian Weihs
 */
public class Game implements GameActionEventHandler, ObjectActionEventListener<Game>, ObjectActionEventHandler<Game> {
	
	private static AtomicInteger count = new AtomicInteger(0);
	
	private static final int MINIMUM_PLAYER = 3;
	private static final int GAME_START_DELAY = 5000;
	private static final int GAME_ACTION_DELAY = 1000;
	
	private final Collection<ObjectActionEventListener<Game>> objectActionEventlisteners = new CopyOnWriteArrayList<>();
	private final Collection<GameActionEventListener> gameActionEventlisteners = new CopyOnWriteArrayList<>();
	private final ReentrantLock gameLock = new ReentrantLock();
	private final PlayerManager playerManager = new PlayerManager();
	private final int id;
	
	private Configuration configuration;
	private Player creator;
	
	
	private GameState gameState = GameState.LOBBY;
	
	
	public Game(final Configuration configuration) throws NullPointerException {
		this(configuration, null);
	}
	
	public Game(final Configuration configuration, final Player creator) throws NullPointerException {
		this.configuration = Objects.requireNonNull(configuration, "configuration must not be null");
		this.creator = creator;
		this.id = count.incrementAndGet();
	}
	
	public int getId() {
		return this.id;
	}
	
	public Player getCreator() {
		return this.creator;
	}
	
	public GameState getGameState() {
		return this.gameState;
	}
	
	private void setGameState(final GameState gameState) throws NullPointerException {
		this.gameState = Objects.requireNonNull(gameState, "gameState must not be null");
		fireGameActionEvent(new GameActionEvent(this, this, GameAction.GAME_STATUS_CHANGED));
	}
	
	public ReentrantLock getGameLock() {
		return this.gameLock;
	}
	
	public PlayerManager getPlayerManager() {
		return this.playerManager;
	}
	
	public String getName() {
		return this.configuration.getName();
	}
	
	public void join(final Client client, final String joinPassword) {
		this.gameLock.lock();
		if (!Strings.nullToEmpty(joinPassword).equals(this.configuration.getJoinPassword())) {
			// TODO: (AW 18.11.2016) Exception schmeißen
			return;
		}
		
		if (this.playerManager.getPlayers().size() >= this.configuration.getMaxNumberOfPlayers()) {
			// TODO: (AW 18.11.2016)  Exception werfen
			return;
		}
		
		if (!GameState.LOBBY.equals(this.gameState)) {
			// TODO: (AW 18.11.2016) Exception schmeißen
			return;
		}
		// TODO: (AW 18.11.2016) Kicked player doesnt allowed to join again!
		
		Player player = new Player(client, this);
		this.playerManager.addPlayer(player);
		if (this.creator == null) {
			updateCreator();
		}
		
		fireGameActionEvent(new GameActionEvent(this, this, GameAction.PLAYER_JOIN));
		
		client.addGame(this);
		
		fireGameCommand();
		this.gameLock.unlock();
	}
	
	public void leave(final Client client) {
		this.gameLock.lock();
		
		Player player = this.playerManager.getPlayer(client);
		
		if (player != null) {
			this.playerManager.removePlayer(player);
			if (player.equals(this.creator)) {
				updateCreator();
			}
			
			client.removeGame(this);
			fireGameActionEvent(new GameActionEvent(this, this, GameAction.PLAYER_LEFT));
		}
		
		fireGameCommand();
		this.gameLock.unlock();
	}
	
	public void start() {
		this.gameLock.lock();
		
		try {
			if (!GameState.LOBBY.equals(this.gameState)) {
				throw new IllegalArgumentException("Game is not in lobby state.");
			}
			
			if (this.playerManager.getPlayers().size() < MINIMUM_PLAYER) {
				throw new IllegalArgumentException("To less players to start the game.");
			}
			
			setGameState(GameState.STARTED);
		} finally {
			this.gameLock.unlock();
		}
	}
	
	private void updateCreator() {
		if (this.playerManager.getPlayers().isEmpty()) {
			this.creator = null;
		} else {
			this.creator = this.playerManager.getPlayers().iterator().next();
			// TODO: (AW 29.11.2016) inform all player that a new creator
		}
	}
	
	private void fireGameCommand() {
		GameCommand gameCommand = new GameCommand();
		gameCommand.setGame(ServerCommandHelper.toNetworkGame(this, true));
		
		this.playerManager.getPlayers().forEach(player -> player.getClient().getClientCommunication().getWriteCommunication().writeMessage(CommandHelper.commandToJson(gameCommand)));
	}
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	private void setConfiguration(final Configuration configuration) {
		this.configuration = Objects.requireNonNull(configuration, "configuration must not be null");
		fireGameActionEvent(new GameActionEvent(this, this, GameAction.GAME_CONFIGURATION_CHANGED));
	}
	
	private void fireObjectActionEvent(final ObjectActionEvent<Game> event) throws NullPointerException {
		this.objectActionEventlisteners.forEach(listener -> listener.handleObjectActionEvent(Objects.requireNonNull(event, "event must not be null")));
	}
	
	@Override
	public void addObjectActionEventListener(final ObjectActionEventListener<Game> actionEventListener) throws NullPointerException {
		this.objectActionEventlisteners.add(Objects.requireNonNull(actionEventListener, "actionEventListener must not be null"));
	}
	
	@Override
	public boolean removeObjectActionEventListener(final ObjectActionEventListener<Game> actionEventListener) throws NullPointerException {
		return this.objectActionEventlisteners.remove(Objects.requireNonNull(actionEventListener, "actionEventListener must not be null"));
	}
	
	@Override
	public void handleObjectActionEvent(final ObjectActionEvent<Game> objectActionEvent) throws NullPointerException {
		// TODO Auto-generated method stub
		
	}
	
	private void fireGameActionEvent(final GameActionEvent event) throws NullPointerException {
		this.gameActionEventlisteners.forEach(listener -> listener.handleGameActionEvent(Objects.requireNonNull(event, "event must not be null")));
	}
	
	@Override
	public void addGameActionEventListener(final GameActionEventListener listener) throws NullPointerException {
		this.gameActionEventlisteners.add(Objects.requireNonNull(listener, "listener must not be null"));
	}
	
	@Override
	public boolean removeGameActionEventListener(final GameActionEventListener listener) throws NullPointerException {
		return this.gameActionEventlisteners.remove(Objects.requireNonNull(listener, "listener must not be null"));
	}
}
