package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventNotifier;
import de.rvwbk.group03.cardsagainsthumanity.network.GameState;
import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.game.create.ui.GameCreateViewImpl;

public class GameManager implements ObjectActionEventNotifier<Game> {
	
	private final Collection<ObjectActionEventListener<Game>> listeners = new CopyOnWriteArrayList<>();
	private final Map<Integer, Game> games = new HashMap<>();
	
	
	public Game getGame(final int id) {
		return this.games.get(id);
	}
	
	public Collection<Game> getGames() {
		return Collections.unmodifiableCollection(this.games.values());
	}
	
	
	public void showCreateGameUI() {
		GameCreateViewImpl view = new GameCreateViewImpl();
		view.setVisible(true);
	}
	
	
	public void addGame(final Game game) throws NullPointerException {
		Objects.requireNonNull(game, "game must not be null");
		
		this.games.put(game.getId(), game);
		
		ObjectActionEvent<Game> gameEvent = new ObjectActionEvent<>(this, game, ObjectActionEvent.ACTION_ADD);
		this.listeners.forEach(listener -> listener.handleObjectActionEvent(gameEvent));
	}
	
	public void joinGame(final int id, final String joinPassword, final Client client) throws IllegalArgumentException {
		Game game = getGame(id);
		
		if (game == null) {
			throw new IllegalArgumentException("Game with the given id not found.");
		}
		
		if (!GameState.LOBBY.equals(game.getGameState())) {
			throw new IllegalArgumentException("Game with the ginven id already started or finished.");
		}
		
		// TODO: (AW 18.11.2016) game anzahl prüfen
		
		game.join(client, joinPassword);
		
		ObjectActionEvent<Game> gameEvent = new ObjectActionEvent<>(this, game, ObjectActionEvent.ACTION_JOIN);
		this.listeners.forEach(listener -> listener.handleObjectActionEvent(gameEvent));
	}
	
	@Override
	public void addObjectActionEventListener(final ObjectActionEventListener<Game> actionEventListener) throws NullPointerException {
		this.listeners.add(Objects.requireNonNull(actionEventListener, "actionEventListener must not be null"));
	}
	
	@Override
	public boolean removeObjectActionEventListener(final ObjectActionEventListener<Game> actionEventListener) throws NullPointerException {
		return this.listeners.remove(Objects.requireNonNull(actionEventListener, "actionEventListener must not be null"));
	}
}
