package de.rvwbk.group03.cardsagainsthumanity.client;

public interface ClientManagerEventHandler {
	
	public void addClientManagerActionEventListener(ClientEventListener listener) throws NullPointerException;
	
	public boolean removeClientManagerActionEventListener(ClientEventListener listener) throws NullPointerException;
	
}
