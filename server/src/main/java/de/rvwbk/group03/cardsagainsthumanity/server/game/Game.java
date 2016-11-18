package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventNotifier;
import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;
import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.network.GameState;
import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.Player;

/**
 * 
 * @author Adrian Weihs
 */
public class Game implements ObjectActionEventListener<Game>, ObjectActionEventNotifier<Game> {
	private static AtomicInteger count = new AtomicInteger(0);
	
	private final Collection<ObjectActionEventListener<Game>> listeners = new CopyOnWriteArrayList<>();
	private final ReentrantLock gameLock = new ReentrantLock();
	private final PlayerManager playerManager = new PlayerManager();
	private final int id;
	private String name = Strings.EMPTY;
	private Configuration configuration = new Configuration();
	
	private GameState gameState = GameState.LOBBY;
	
	
	public Game() {
		this.id = count.incrementAndGet();
	}
	
	public int getId() {
		return this.id;
	}
	
	public GameState getGameState() {
		return this.gameState;
	}
	
	public ReentrantLock getGameLock() {
		return this.gameLock;
	}
	
	public PlayerManager getPlayerManager() {
		return this.playerManager;
	}
	
	
	public String getName() {
		return this.name;
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
		
		if(!GameState.LOBBY.equals(this.gameState)) {
			// TODO: (AW 18.11.2016) Exception schmeißen
			return;
		}
		// TODO: (AW 18.11.2016) Kicked player doesnt allowed to join again!
		
		Player player = new Player(client, this);
		this.playerManager.addPlayer(player);
		
		ObjectActionEvent<Game> gameEvent = new ObjectActionEvent<>(this, this, ObjectActionEvent.ACTION_JOIN);
		this.listeners.forEach(listener -> listener.handleObjectActionEvent(gameEvent));
		
		client.addGame(this);
		
		this.gameLock.unlock();
	}
	
	public void leave(final Client client) {
		this.gameLock.lock();
		
		Player player = this.playerManager.getPlayer(client);
		
		if(player != null) {
			this.playerManager.removePlayer(this.playerManager.getPlayer(client));
			client.removeGame(this);
			ObjectActionEvent<Game> gameEvent = new ObjectActionEvent<>(this, this, ObjectActionEvent.ACTION_LEAVE);
			this.listeners.forEach(listener -> listener.handleObjectActionEvent(gameEvent));
		}
		
		this.gameLock.unlock();
	}
	
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	@Override
	public void addObjectActionEventListener(final ObjectActionEventListener<Game> actionEventListener) throws NullPointerException {
		this.listeners.add(Objects.requireNonNull(actionEventListener, "actionEventListener must not be null"));
	}
	
	@Override
	public boolean removeObjectActionEventListener(final ObjectActionEventListener<Game> actionEventListener) throws NullPointerException {
		return this.listeners.remove(Objects.requireNonNull(actionEventListener, "actionEventListener must not be null"));
	}
	
	@Override
	public void handleObjectActionEvent(final ObjectActionEvent<Game> objectActionEvent) throws NullPointerException {
		// TODO Auto-generated method stub
		
	}
}
