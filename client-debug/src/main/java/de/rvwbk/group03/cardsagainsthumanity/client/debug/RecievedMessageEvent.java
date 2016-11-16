package de.rvwbk.group03.cardsagainsthumanity.client.debug;

import java.util.EventObject;
import java.util.Objects;

public class RecievedMessageEvent extends EventObject {
	
	private final String recievedMessage;
	
	
	public RecievedMessageEvent(final Object source, final String recievedMessage) throws NullPointerException {
		super(source);
		this.recievedMessage = Objects.requireNonNull(recievedMessage, "recievedMessage must not be null");
	}
	
	
	public String getRecievedMessage() {
		return this.recievedMessage;
	}
}
