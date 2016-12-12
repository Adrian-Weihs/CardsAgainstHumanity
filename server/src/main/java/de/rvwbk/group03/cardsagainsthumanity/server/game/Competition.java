package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.Collection;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;
import de.rvwbk.group03.cardsagainsthumanity.data.BlackCard;
import de.rvwbk.group03.cardsagainsthumanity.data.BlackCardHolder;
import de.rvwbk.group03.cardsagainsthumanity.network.GameState;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.GameCommand;
import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.communication.ServerCommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.server.game.configuration.GameConfiguration;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameAction;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEventHandler;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.GamePlayer;

/**
 * 
 * @author Adrian Weihs
 */
public class Competition implements BlackCardHolder, GameActionEventHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(Competition.class);
	
	
	private static AtomicInteger count = new AtomicInteger(0);
	
	private static final int MINIMUM_PLAYER = 3;
	private static final int GAME_START_DELAY = 5000;
	private static final int GAME_ACTION_DELAY = 1000;
	
	private final Collection<ObjectActionEventListener<Competition>> objectActionEventlisteners = new CopyOnWriteArrayList<>();
	private final Collection<GameActionEventListener> gameActionEventlisteners = new CopyOnWriteArrayList<>();
	private final ReentrantLock gameLock = new ReentrantLock();
	private final PlayerManager playerManager;
	private final StackManager stackManager;
	private final int id;
	private final long creationTimestamp;
	
	private Timer taskScheduler = new Timer();
	
	private GameConfiguration configuration;
	private GamePlayer creator;
	private int round = 1;
	private BlackCard blackCard;
	
	
	private GameState gameState = GameState.LOBBY;
	
	
	public Competition(final GameConfiguration configuration) throws NullPointerException {
		this(configuration, null);
	}
	
	public Competition(final GameConfiguration configuration, final Client client) throws NullPointerException {
		this.configuration = Objects.requireNonNull(configuration, "configuration must not be null");
		this.id = count.incrementAndGet();
		this.creationTimestamp = System.currentTimeMillis();
		
		this.playerManager = new PlayerManager(this);
		this.stackManager = new StackManager(this);
		
		if (client != null) {
			join(client);
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public GamePlayer getCreator() {
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
	
	public int getRound() {
		return this.round;
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
		
		GamePlayer player = new GamePlayer(client, this);
		this.playerManager.addPlayer(player);
		if (this.creator == null) {
			updateCreator();
		}
		
		fireGameActionEvent(new GameActionEvent(this, this, GameAction.PLAYER_JOIN));
		
		client.addGame(this);
		
		fireGameCommand();
		this.gameLock.unlock();
	}
	
	public void join(final Client client) throws IllegalArgumentException {
		this.gameLock.lock();
		GamePlayer player = this.playerManager.getPlayer(client);
		
		if (player != null) {
			throw new IllegalArgumentException("Client is already in this game");
		}
		
		player = new GamePlayer(client, this);
		this.playerManager.addPlayer(player);
		this.creator = player;
		
		fireGameActionEvent(new GameActionEvent(this, this, GameAction.PLAYER_JOIN));
		
		client.addGame(this);
		
		fireGameCommand();
		this.gameLock.unlock();
	}
	
	public void leave(final Client client) throws IllegalArgumentException {
		this.gameLock.lock();
		
		GamePlayer player = this.playerManager.getPlayer(client);
		
		if (player == null) {
			throw new IllegalArgumentException("Client is not in this game");
		}
		
		if (player != null) {
			this.playerManager.removePlayer(player);
			if (player.equals(this.creator)) {
				updateCreator();
			}
			
			client.removeGame(this);
			fireGameActionEvent(new GameActionEvent(this, this, GameAction.PLAYER_LEFT));
			fireGameCommand();
			
			if (this.playerManager.getPlayers().size() < MINIMUM_PLAYER) {
				setGameState(GameState.CANCELED);
			}
		}
		
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
			setGameState(GameState.STARTING);
			
			this.stackManager.prepareGameStart();
			this.playerManager.prepareGameStart();
			
			this.taskScheduler.schedule(new GameStartTimerTask(this), GAME_START_DELAY);
		} finally {
			this.gameLock.unlock();
		}
	}
	
	protected void startCompleted() {
		this.gameLock.lock();
		
		setGameState(GameState.STARTED);
		firePlayerRelatedGameCommand();
		
		this.gameLock.unlock();
	}
	
	public void gameMove() {
		
	}
	
	public void nextRound() {
		this.stackManager.nextRound();
		this.playerManager.nextRound();
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
		gameCommand.setGame(ServerCommandHelper.toGame(this, true, false, false));
		
		this.playerManager.getPlayers().forEach(player -> player.getClient().getClientCommunication().getWriteCommunication().writeMessage(CommandHelper.commandToJson(gameCommand)));
	}
	
	private void firePlayerRelatedGameCommand() {
		GameCommand gameCommand = new GameCommand();
		gameCommand.setGame(ServerCommandHelper.toGame(this, true, false, false));
		gameCommand.setBlackCard(this.blackCard);
		gameCommand.setRound(this.round);
		
		for(GamePlayer player : this.playerManager.getPlayers()){
			gameCommand.setWhiteCards(player.getHand().getWhiteCards());
			player.getClient().getClientCommunication().getWriteCommunication().writeMessage(CommandHelper.commandToJson(gameCommand));
		}
	}
	
	public GameConfiguration getConfiguration() {
		return this.configuration;
	}
	
	private void setConfiguration(final GameConfiguration configuration) {
		this.configuration = Objects.requireNonNull(configuration, "configuration must not be null");
		fireGameActionEvent(new GameActionEvent(this, this, GameAction.GAME_CONFIGURATION_CHANGED));
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
	
	public StackManager getStackManager() {
		return this.stackManager;
	}
	
	@Override
	public void addBlackCard(final BlackCard card) throws NullPointerException {
		if(this.blackCard != null) {
			this.blackCard.setCardHolder(null);
		}
		
		this.blackCard = Objects.requireNonNull(card, "card must not be null");
	}
	
	@Override
	public boolean removeBlackCard(final BlackCard card) throws NullPointerException {
		if(this.blackCard != null && this.blackCard.equals(card)) {
			this.blackCard = null;
			return true;
		}
		
		return false;
	}
}
