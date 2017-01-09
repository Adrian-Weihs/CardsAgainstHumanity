package de.rvwbk.group03.cardsagainsthumanity.client;

public interface LobbyManagerEventHandler {
	
	public void addLobbyManagerActionEventListener(ClientEventListener listener) throws NullPointerException;
	
	public boolean removeLobbyManagerActionEventListener(ClientEventListener listener) throws NullPointerException;
	
}
