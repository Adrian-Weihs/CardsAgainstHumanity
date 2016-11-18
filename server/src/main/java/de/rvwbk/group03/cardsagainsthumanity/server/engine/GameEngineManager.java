package de.rvwbk.group03.cardsagainsthumanity.server.engine;

import java.io.IOException;

import de.rvwbk.group03.cardsagainsthumanity.server.client.ClientManager;
import de.rvwbk.group03.cardsagainsthumanity.server.engine.ui.GameEngineViewImpl;
import de.rvwbk.group03.cardsagainsthumanity.server.game.GameManager;

public class GameEngineManager {
	private final ClientManager clientManager = new ClientManager();
	private final GameManager gameManager = new GameManager();
	
	private GameEngineViewImpl view;
	
	public GameEngineManager() throws IOException {
	}
	
	public ClientManager getClientManager() {
		return this.clientManager;
	}
	
	public void showUI() {
		if (this.view == null) {
			this.view = new GameEngineViewImpl();
		}
		this.view.setVisible(true);
	}
	
	public GameManager getGameManager() {
		return this.gameManager;
	}
}
