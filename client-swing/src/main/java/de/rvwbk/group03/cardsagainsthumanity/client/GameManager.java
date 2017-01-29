package de.rvwbk.group03.cardsagainsthumanity.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.StartGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.UpdateGameConfigurationCommand;

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
	
	public void startGame(final Game currentGame) {
		StartGameCommand startGameCommand = new StartGameCommand(currentGame.getId());
		ClientManager.getServerCommunication().getWriteCommunication().writeMessage(CommandHelper.commandToJson(startGameCommand));
		// TODO: Error Handling
	}
	
	public void updateGame(final Configuration config, final Game game) {
		UpdateGameConfigurationCommand command = new UpdateGameConfigurationCommand(game.getId(), config);
		String jsonMessage = CommandHelper.commandToJson(command);
		ClientManager.getServerCommunication().getWriteCommunication().writeMessage(jsonMessage);
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
