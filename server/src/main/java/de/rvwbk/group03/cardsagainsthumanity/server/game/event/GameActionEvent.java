package de.rvwbk.group03.cardsagainsthumanity.server.game.event;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.event.AbstractEvent;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;

public class GameActionEvent extends AbstractEvent {
	
	private final Competition game;
	private final GameAction action;
	
	
	public GameActionEvent(final Object source, final Competition game, final GameAction action) throws NullPointerException {
		super(source);
		this.game = Objects.requireNonNull(game, "game must not be null");
		this.action = Objects.requireNonNull(action, "action must not be null");
	}
	
	public Competition getGame() {
		return this.game;
	}
	
	public GameAction getGameAction() {
		return this.action;
	}
}
