package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import de.rvwbk.group03.cardsagainsthumanity.network.GameState;
import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.game.create.ui.GameCreateViewImpl;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameAction;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEventHandler;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.server.game.manage.ui.GameManageViewImpl;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.GamePlayer;

public class GameManager implements GameActionEventListener, GameActionEventHandler {
	
	private final Collection<GameActionEventListener> gameActionListeners = new CopyOnWriteArrayList<>();
	private final Map<Integer, Competition> games = new HashMap<>();
	
	
	public Competition getGame(final int id) {
		return this.games.get(id);
	}
	
	public Collection<Competition> getGames() {
		return Collections.unmodifiableCollection(this.games.values());
	}
	
	
	public void showCreateGameUI() {
		GameCreateViewImpl view = new GameCreateViewImpl();
		view.setVisible(true);
	}
	
	public void showManageGameUI(final int id) {
		GameManageViewImpl view = new GameManageViewImpl();
		view.manageGame(id);
		view.setVisible(true);
	}
	
	public void addGame(final Competition game) throws NullPointerException {
		Objects.requireNonNull(game, "game must not be null");
		
		this.games.put(game.getId(), game);
		
		fireGameActionEvent(new GameActionEvent(this, game, GameAction.GAME_CREATED));
	}
	
	public void joinGame(final int id, final String joinPassword, final Client client) throws IllegalArgumentException, NullPointerException {
		Objects.requireNonNull(client, "client must not be null");
		
		Competition game = getGame(id);
		
		if (game == null) {
			throw new IllegalArgumentException("Game with the given id not found.");
		}
		
		if (!GameState.LOBBY.equals(game.getGameState())) {
			throw new IllegalArgumentException("Game with the ginven id already started or finished.");
		}
		
		// TODO: (AW 18.11.2016) game anzahl prüfen
		
		game.join(client, joinPassword);
	}
	
	public void startGame(final int id, final Client client) throws NullPointerException {
		Objects.requireNonNull(client, "client must not be null");
		
		Competition game = getGame(id);
		
		if (game == null) {
			throw new IllegalArgumentException("Game with the given id not found.");
		}
		
		if (!GameState.LOBBY.equals(game.getGameState())) {
			throw new IllegalArgumentException("Game with the ginven id already started or finished.");
		}
		
		GamePlayer player = game.getPlayerManager().getPlayer(client);
		
		if(player == null) {
			throw new IllegalArgumentException("You are not a Player of this game.");
		}
		
		if(!player.equals(game.getCreator())) {
			throw new IllegalArgumentException("You are not the creator of this game.");
		}
		
		game.start();
	}
	
	@Override
	public void handleGameActionEvent(final GameActionEvent event) throws NullPointerException {
		fireGameActionEvent(Objects.requireNonNull(event, "event must not be null"));
	}
	
	private void fireGameActionEvent(final GameActionEvent event) throws NullPointerException {
		this.gameActionListeners.forEach(listener -> listener.handleGameActionEvent(Objects.requireNonNull(event, "event must not be null")));
	}
	
	@Override
	public void addGameActionEventListener(final GameActionEventListener listener) throws NullPointerException {
		this.gameActionListeners.add(Objects.requireNonNull(listener, "listener must not be null"));
	}
	
	@Override
	public boolean removeGameActionEventListener(final GameActionEventListener listener) throws NullPointerException {
		return this.gameActionListeners.remove(Objects.requireNonNull(listener, "listener must not be null"));
	}
}
