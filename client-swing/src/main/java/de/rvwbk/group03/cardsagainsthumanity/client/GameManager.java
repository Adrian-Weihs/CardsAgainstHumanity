package de.rvwbk.group03.cardsagainsthumanity.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameManager implements GameManagerEventHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
	
	
	private List<ClientEventListener> lobbyListeners = new ArrayList<>();
	private static RecievedMessageManager recievedMessageManager;
	
	
	public static RecievedMessageManager getRecievedMessageManager() {
		if(recievedMessageManager == null) {
			recievedMessageManager = new RecievedMessageManager();
		}
		
		return recievedMessageManager;
	}
	
	@Override
	public void addGameManagerActionEventListener(final ClientEventListener listener) throws NullPointerException {
		this.lobbyListeners.add(Objects.requireNonNull(listener, "listener must not be null."));
	}
	
	@Override
	public boolean removeGameManagerActionEventListener(final ClientEventListener listener) throws NullPointerException {
		return this.lobbyListeners.remove(Objects.requireNonNull(listener, "listener must not be null."));
	}
}
