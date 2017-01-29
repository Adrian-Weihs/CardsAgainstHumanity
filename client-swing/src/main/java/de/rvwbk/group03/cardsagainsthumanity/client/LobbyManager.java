package de.rvwbk.group03.cardsagainsthumanity.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.CreateGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.GetGameListCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.JoinGameCommand;

public class LobbyManager implements LobbyManagerEventHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(LobbyManager.class);
	
	private static RecievedMessageManager recievedMessageManager;
	
	public static RecievedMessageManager getRecievedMessageManager() {
		if(recievedMessageManager == null) {
			recievedMessageManager = new RecievedMessageManager();
		}
		
		return recievedMessageManager;
	}
	
	private List<ClientEventListener> lobbyListeners = new ArrayList<>();
	private List<Game> allGamesInLobby = new ArrayList<>();
	private Game createdGame;
	
	public List<Game> getGames() {
		
		GetGameListCommand command = new GetGameListCommand();
		
		ClientManager.getServerCommunication().getWriteCommunication().writeMessage(CommandHelper.commandToJson(command));
		
		ClientEventListenerImpl eventImpl= new ClientEventListenerImpl();
		addLobbyManagerActionEventListener(eventImpl);
		this.lobbyListeners.forEach(listener -> listener.handleClientEvent(new LobbyManagerEvent(this, LobbyManagerAction.GET_GAMES)));
		removeLobbyManagerActionEventListener(eventImpl);
		
		return this.allGamesInLobby;
	}
	
	public void setGames(final List<Game> games) {
		this.allGamesInLobby = games;
	}
	
	public void createGame(final Configuration config) {
		CreateGameCommand createGameCommand = new CreateGameCommand();
		createGameCommand.setConfiguration(config);
		
		ClientManager.getServerCommunication().getWriteCommunication().writeMessage(CommandHelper.commandToJson(createGameCommand));
		
		ClientEventListenerImpl eventImpl= new ClientEventListenerImpl();
		addLobbyManagerActionEventListener(eventImpl);
		this.lobbyListeners.forEach(listener -> listener.handleClientEvent(new LobbyManagerEvent(this, LobbyManagerAction.GET_CURRENT_GAME)));
		removeLobbyManagerActionEventListener(eventImpl);
	}
	
	public void joinGame(final Game game, final String password) {
		JoinGameCommand joinGameCommand = new JoinGameCommand(game.getId(), password);
		ClientManager.getServerCommunication().getWriteCommunication().writeMessage(CommandHelper.commandToJson(joinGameCommand));
	}
	
	@Override
	public void addLobbyManagerActionEventListener(final ClientEventListener listener) throws NullPointerException {
		this.lobbyListeners.add(Objects.requireNonNull(listener, "listener must not be null."));
	}
	
	public void setCreatedGame(final Game game) {
		this.createdGame = game;
	}
	
	public Game getCreatedGame() {
		return this.createdGame;
	}
	
	
	@Override
	public boolean removeLobbyManagerActionEventListener(final ClientEventListener listener) throws NullPointerException {
		return this.lobbyListeners.remove(Objects.requireNonNull(listener, "listener must not be null."));
	}
	
}
