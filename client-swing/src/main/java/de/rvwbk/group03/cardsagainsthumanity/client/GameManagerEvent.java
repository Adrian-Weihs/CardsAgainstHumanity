package de.rvwbk.group03.cardsagainsthumanity.client;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.event.AbstractEvent;

public class GameManagerEvent extends AbstractEvent {
	
	private GameManagerAction action;
	
	
	public GameManagerEvent(final Object source, final GameManagerAction action) throws NullPointerException {
		super(source);
		this.action = Objects.requireNonNull(action, "action must not be null");
	}
	
	
	public GameManagerAction getAction() {
		return this.action;
	}
}
