package de.rvwbk.group03.cardsagainsthumanity.client;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.event.AbstractEvent;

public class LobbyManagerEvent extends AbstractEvent {
	
	private LobbyManagerAction action;
	
	
	public LobbyManagerEvent(final Object source, final LobbyManagerAction action) throws NullPointerException {
		super(source);
		this.action = Objects.requireNonNull(action, "action must not be null");
	}
	
	public LobbyManagerAction getAction() {
		return this.action;
	}
	
}
