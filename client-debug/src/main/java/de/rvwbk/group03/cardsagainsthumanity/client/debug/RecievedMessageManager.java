package de.rvwbk.group03.cardsagainsthumanity.client.debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecievedMessageManager {
	
	private List<String> recievedMessages = new ArrayList<>();
	private List<RecievedMessageListener> listeners = new ArrayList<>();
	
	public void addMessage(final String message) throws NullPointerException {
		this.recievedMessages.add(Objects.requireNonNull(message, "message must not be null"));
		
		RecievedMessageEvent event = new RecievedMessageEvent(this, message);
		this.listeners.forEach(listener -> listener.handleRecievedMessageEvent(event));
	}
	
	public void addRecievedMessageListener(final RecievedMessageListener listener) {
		this.listeners.add(Objects.requireNonNull(listener, "listener must not be null"));
	}
}
