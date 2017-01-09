package de.rvwbk.group03.cardsagainsthumanity.client;

public interface GameManagerEventHandler {
	
	public void addGameManagerActionEventListener(ClientEventListener listener) throws NullPointerException;
	
	public boolean removeGameManagerActionEventListener(ClientEventListener listener) throws NullPointerException;
	
}
