package de.rvwbk.group03.cardsagainsthumanity.client;

public interface ClientManagerEventHandler {
	
	public void addClientManagerActionEventListener(ClientManagerEventListener listener) throws NullPointerException;
	
	public boolean removeClientManagerActionEventListener(ClientManagerEventListener listener) throws NullPointerException;
	
}
