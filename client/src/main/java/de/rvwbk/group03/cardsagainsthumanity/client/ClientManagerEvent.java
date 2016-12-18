package de.rvwbk.group03.cardsagainsthumanity.client;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.event.AbstractEvent;

public class ClientManagerEvent extends AbstractEvent {
	
	private ClientManagerAction action;
	
	
	public ClientManagerEvent(final Object source, final ClientManagerAction action) throws NullPointerException {
		super(source);
		this.action = Objects.requireNonNull(action, "action must not be null");
	}
	
}
