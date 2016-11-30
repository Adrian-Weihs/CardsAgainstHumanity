package de.rvwbk.group03.cardsagainsthumanity.server.game.event;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.event.AbstractEvent;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Game;

public class GameActionEvent extends AbstractEvent {
	
	private final Game game;
	private final GameAction action;
	
	
	public GameActionEvent(final Object source, final Game game, final GameAction action) throws NullPointerException {
		super(source);
		this.game = Objects.requireNonNull(game, "game must not be null");
		this.action = Objects.requireNonNull(action, "action must not be null");
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public GameAction getGameAction() {
		return this.action;
	}
}
