package de.rvwbk.group03.cardsagainsthumanity.communication;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.network.DisconnectReason;
import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.command.Command;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.CreateGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.GetGameListCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.JoinGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.LeaveGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.LoginCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.StartGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.UpdateGameConfigurationCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.ConfirmLoginCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.DisconnectCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.FailedCreateGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.FailedJoinGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.FailedLeaveGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.FailedLoginCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.GameListCommand;

/**
 * This class manages all communication that is done between the server and the client.
 * 
 * @author Alex
 *
 */
public class CommunicationManager {
	
	private static ServerCommunication serverCommunication;
	
	
	/**
	 * Connects the client to the defaultly used server.
	 * 
	 * @throws IOException if there was an error initiating the connection.
	 */
	public static void connect() throws IOException {
		if(serverCommunication == null) {
			serverCommunication = new ServerCommunication(new Socket("localhost", 33100));
			Thread thread = new Thread(serverCommunication.getReadCommunication());
			thread.start();
		}
	}
	
	/**
	 * Disconnects the client from the server.
	 * 
	 * @return the reason for disconnecting
	 * @throws IOException if an I/O error occurs when closing the connection.
	 */
	public static String disconnect() throws IOException {
		
		String disconnectReason = "You've lost connection to the server.";
		
		if(serverCommunication != null) {
			
			Command command = CommandHelper.jsonToCommand(getReadConnection().getLastMessage());
			
			if (command instanceof DisconnectCommand) {
				DisconnectReason reason = ((DisconnectCommand) command).getDisconnectReason();
				
				switch (reason) {
					case DISAGREEMENT:
						disconnectReason = "You've been kicked from the server.";
						break;
					case NOT_LOGGED_IN:
						disconnectReason = "You're not logged in anymore.";
						break;
					case SHUTDOWN:
						disconnectReason = "The server was shut down";
						break;
					default:
						disconnectReason = "You've lost connection to the server.";
						break;
				}
			}
			
			serverCommunication.getSocket().close();
			serverCommunication = null;
		}
		return disconnectReason;
	}
	
	/**
	 * Login using the given name and password.
	 * 
	 * @param name the name of the user that logs in. Must not be {@code null}.
	 * @param password the password needed in order to log in. Must not be {@code null}.
	 * @throws NullPointerException if {@code name} or {@code password} is {@code null}.
	 */
	public static void login (final String name, final String password) throws NullPointerException {
		LoginCommand loginCommand = new LoginCommand(Objects.requireNonNull(name, "name must not be null"), password);
		
		// TODO: throw PasswordWrongException, if Password is wrong as soon corresponding backend logic works
		getWriteConnection().writeMessage(CommandHelper.commandToJson(loginCommand));
		Command command = CommandHelper.jsonToCommand(getReadConnection().getLastMessage());
		
		if (command instanceof ConfirmLoginCommand) {
			// TODO: Something to do?
		} else if (command instanceof FailedLoginCommand) {
			// TODO: Log and throw Exception
		}
	}
	
	/**
	 * Refreshes the lobby.
	 * 
	 * @return all games that are currently played.
	 */
	public static Collection<Game> refreshLobby() {
		getWriteConnection().writeMessage(CommandHelper.commandToJson(new GetGameListCommand()));
		Command command = CommandHelper.jsonToCommand(getReadConnection().getLastMessage());
		if (command instanceof GameListCommand) {
			return ((GameListCommand) command).getGames();
		} else {
			// TODO: throw new Exception indicating there was an error refreshing the lobby
			return null;
		}
	}
	
	/**
	 * Joins the game with the given {@code id}
	 * @param id the id of the game that shall be joined.
	 */
	public static void joinGame(final int id) {
		JoinGameCommand joinGameCommand = new JoinGameCommand(Objects.requireNonNull(id, "id must not be null"));
		getWriteConnection().writeMessage(CommandHelper.commandToJson(joinGameCommand));
		
		Command command = CommandHelper.jsonToCommand(getReadConnection().getLastMessage());
		if (command instanceof FailedJoinGameCommand) {
			// TODO: Log & throw exception
		}
	}
	
	/**
	 * Joins the game with the given {@code id} using the given {@code password}
	 * @param id the id of the game that shall be joined
	 * @param password the password needed in order to join the game
	 */
	public static void joinGame(final int id, final String password) {
		JoinGameCommand joinGameCommand = new JoinGameCommand(Objects.requireNonNull(id, "id must not be null"), password);
		getWriteConnection().writeMessage(CommandHelper.commandToJson(joinGameCommand));
	}
	
	/**
	 * Creates a game with default configurations.
	 */
	public static void createGame() {
		createGame(null);
	}
	
	/**
	 * Creates a game using the given configuration.
	 * 
	 * @param configuration the configuration used for the game
	 */
	public static void createGame(final Configuration configuration) {
		CreateGameCommand createGameCommand = new CreateGameCommand();
		if (configuration != null) {
			createGameCommand.setConfiguration(configuration);
		}
		getWriteConnection().writeMessage(CommandHelper.commandToJson(createGameCommand));
		Command command = CommandHelper.jsonToCommand(getReadConnection().getLastMessage());
		
		if (command instanceof FailedCreateGameCommand) {
			// TODO: Log & throw exception
		}
		
		
	}
	
	/**
	 * Creates a new game giving the opportunity to manually create a {@link Configuration}.
	 * 
	 * @param name the name for the game to be displayed in the lobby. Must not be {@code null}
	 * @param cardDeckName the name of the deck to be used. {@code default} will be used if {@code null}.
	 * @param maxNumberOfPlayer the maximal amount of players
	 * @param passwordProtected whether the created game shall be protected by a password
	 * @param joinPassword the password needed to join, optional if {@code passwordProtected} is {@code null} else must not be {@code null}.
	 * @throws NullPointerException if {@code name} is {@code null} or {@code joinPassword} is {@code null} and {@code passwordProtected}
	 */
	public static void createGame(final String name, final String cardDeckName, final int maxNumberOfPlayer, final boolean passwordProtected, final String joinPassword) throws NullPointerException {
		Configuration configuration = new Configuration(Objects.requireNonNull(name, "name must not be null."));
		configuration.setCardDeckName(cardDeckName);
		configuration.setMaxNumberOfPlayer(maxNumberOfPlayer);
		if (passwordProtected) {
			configuration.setJoinPassword(Objects.requireNonNull(joinPassword, "password must not be null."));
		}
		createGame(configuration);
	}
	
	/**
	 * Updates the given game using the given configuration
	 * 
	 * @param id the id of the game that shall be joined. Must not be {@code null}.
	 * @param configuration the configuration used for the game
	 * @throws NullPointerException if {@code id} is {@code null}.
	 */
	public static void updateGame(final int id, final Configuration configuration) throws NullPointerException {
		UpdateGameConfigurationCommand updateGameCommand = new UpdateGameConfigurationCommand(Objects.requireNonNull(id, "id must not be null."), Objects.requireNonNull(configuration, "configuration must not be null"));
		getWriteConnection().writeMessage(CommandHelper.commandToJson(updateGameCommand));
	}
	
	/**
	 * Updates the given game giving the opportunity to manually update the {@link Configuration}.
	 * 
	 * @param id the id of the game that shall be joined. Must not be {@code null}.
	 * @param name the name for the game to be displayed in the lobby. Must not be {@code null}
	 * @param cardDeckName the name of the deck to be used. {@code default} will be used if {@code null}.
	 * @param maxNumberOfPlayer the maximal amount of players
	 * @param passwordProtected whether the created game shall be protected by a password
	 * @param joinPassword the password needed to join, optional if {@code passwordProtected} is {@code null} else must not be {@code null}.
	 * @throws NullPointerException if {@code id} or {@code name} is {@code null} or {@code joinPassword} is {@code null} and {@code passwordProtected}
	 */
	public static void updateGame(final int id, final String name, final String cardDeckName, final int maxNumberOfPlayer, final boolean passwordProtected, final String joinPassword) throws NullPointerException {
		Configuration configuration = new Configuration(Objects.requireNonNull(name, "name must not be null."));
		configuration.setCardDeckName(cardDeckName);
		configuration.setMaxNumberOfPlayer(maxNumberOfPlayer);
		if (passwordProtected) {
			configuration.setJoinPassword(Objects.requireNonNull(joinPassword, "password must not be null."));
		}
		updateGame(id, configuration);
	}
	
	/**
	 * Starts the game with the given id.
	 * 
	 * @param id the id of the game to be started. Must not be {@code null}.
	 * @throws NullPointerException if {@code id} is {@code null}.
	 */
	public static void startGame(final int id) throws NullPointerException {
		StartGameCommand startGameCommand = new StartGameCommand(Objects.requireNonNull(id, "id must not be null."));
		getWriteConnection().writeMessage(CommandHelper.commandToJson(startGameCommand));
	}
	
	/**
	 * Leaves the game with the given id.
	 * 
	 * @param id the id of the game to be left. Must not be {@code null}.
	 * @throws NullPointerException if {@code id} is {@code null}.
	 */
	public static void leaveGame(final int id) throws NullPointerException {
		LeaveGameCommand leaveGameCommand = new LeaveGameCommand(Objects.requireNonNull(id, "id must not be null."));
		getWriteConnection().writeMessage(CommandHelper.commandToJson(leaveGameCommand));
		
		Command command = CommandHelper.jsonToCommand(getReadConnection().getLastMessage());
		if (command instanceof FailedLeaveGameCommand) {
			// TODO: Log and throw Exception
		}
	}
	
	public static void makeAMove() {
		// TODO: Not yet implemented.
		// TODO: Separate into multiple methods?
	}
	
	private static ClientToServerCommunication getWriteConnection() {
		return serverCommunication.getWriteCommunication();
	}
	
	private static ServerToClientCommunication getReadConnection() {
		return serverCommunication.getReadCommunication();
	}
	
	private CommunicationManager() {
		// prevent instantiation
	}
}
