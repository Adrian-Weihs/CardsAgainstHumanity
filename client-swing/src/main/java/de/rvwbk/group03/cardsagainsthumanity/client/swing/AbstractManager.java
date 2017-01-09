package de.rvwbk.group03.cardsagainsthumanity.client.swing;

import de.rvwbk.group03.cardsagainsthumanity.client.ClientManager;
import de.rvwbk.group03.cardsagainsthumanity.client.GameManager;
import de.rvwbk.group03.cardsagainsthumanity.client.LobbyManager;

public class AbstractManager {
	
	private final ClientManager clientManager;
	private final LobbyManager lobbyManager;
	private final GameManager gameManager;
	
	
	public AbstractManager() {
		this.clientManager = new ClientManager();
		this.lobbyManager = new LobbyManager();
		this.gameManager = new GameManager();
	}
	
	
	public ClientManager getClientManager() {
		return this.clientManager;
	}
	
	public LobbyManager getLobbyManager() {
		return this.lobbyManager;
	}
	
	public GameManager getGameManager() {
		return this.gameManager;
	}
}
