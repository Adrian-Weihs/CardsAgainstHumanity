package de.rvwbk.group03.cardsagainsthumanity.server.communication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonSyntaxException;

import de.rvwbk.group03.cardsagainsthumanity.base.exception.WrongUserNameOrPasswordException;
import de.rvwbk.group03.cardsagainsthumanity.base.network.AbstractBufferedReadCommunication;
import de.rvwbk.group03.cardsagainsthumanity.network.FailedJoinGameReason;
import de.rvwbk.group03.cardsagainsthumanity.network.FailedLoginReason;
import de.rvwbk.group03.cardsagainsthumanity.network.InvalidMessageReason;
import de.rvwbk.group03.cardsagainsthumanity.network.command.Command;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.ClientCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.CreateGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.GetGameListCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.JoinGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.LoggedInClientCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.LoginCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.client.StartGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.ConfirmLoginCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.FailedJoinGameCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.FailedLoginCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.GameListCommand;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.InvalidMessageCommand;
import de.rvwbk.group03.cardsagainsthumanity.server.ServerManager;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;


public class ClientToServerCommunication extends AbstractBufferedReadCommunication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientToServerCommunication.class);
	private final ClientCommunication clientCommunication;
	
	
	/**
	 * @param clientCommunication The client of the communication. Must not be {@code null}.
	 * @param inputStream The client input stream. Must not be {@code null}.
	 * @throws NullPointerException If {@code inputStream} is {@code null}.
	 */
	public ClientToServerCommunication(final ClientCommunication clientCommunication, final InputStream inputStream) throws NullPointerException {
		super(inputStream);
		
		this.clientCommunication = Objects.requireNonNull(clientCommunication, "clientCommunication must not be null");
	}
	
	
	@Override
	public void run() {
		try {
			String message;
			while (!isDisconnected() && (message = getReader().readLine()) != null) {
				handleReceivedMessage(message);
			}
		} catch (IOException e) {
			//Ok, client disconnected
		}
		
		this.clientCommunication.getClient().disconnect();
	}
	
	@Override
	public void handleReceivedMessage(final String message) {
		if (!message.isEmpty()) {
			LOGGER.info("Got a message from client with the ip \"{}\" and message \"{}\".", this.clientCommunication.getSocket().getRemoteSocketAddress(), message);
			
			if (CommandHelper.isJsonString(message)) {
				try {
					handleCommand(CommandHelper.jsonToCommand(message));
				} catch (JsonSyntaxException e) {
					LOGGER.info("The received message was not a client command.");
					this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(new InvalidMessageCommand(InvalidMessageReason.NOT_A_CLIENT_COMMAND)));
				}
			} else {
				this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(new InvalidMessageCommand(InvalidMessageReason.NOT_A_JSON_STRING)));
			}
		}
	}
	
	private void handleCommand(final Command command) {
		if (command instanceof ClientCommand) {
			if (command instanceof LoggedInClientCommand) {
				if (isClientLoggedInForCommand()) {
					if (command instanceof GetGameListCommand) {
						handleGetGameListCommand((GetGameListCommand) command);
					} else if (command instanceof JoinGameCommand) {
						handleJoinGameCommand((JoinGameCommand) command);
					} else if (command instanceof StartGameCommand) {
						handleStartGameCommand((StartGameCommand) command);
					} else if (command instanceof CreateGameCommand) {
						handleCreateGameCommand((CreateGameCommand) command);
					}
				}
			} else if (command instanceof LoginCommand) {
				handleLoginCommand((LoginCommand) command);
			} else {
				LOGGER.info("Got an unhandled command \"{}\"", command.getClass().getSimpleName());
				this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(new InvalidMessageCommand(InvalidMessageReason.COMMAND_NOT_IMPLEMENTED_YET)));
			}
		}
	}
	
	private void handleLoginCommand(final LoginCommand loginCommand) {
		Objects.requireNonNull(loginCommand, "loginCommand must not be null");
		if (this.clientCommunication.getClient().isLoggedIn()) {
			FailedLoginCommand failedLoginCommand = new FailedLoginCommand();
			failedLoginCommand.setFailedLoginReason(FailedLoginReason.ALREADY_LOGGED_IN);
			this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(failedLoginCommand));
			return;
		}
		
		try {
			this.clientCommunication.getClient().login(loginCommand.getName(), loginCommand.getPassword());
			LOGGER.info("The client with the ip \"{}\" logged in successfully as user \"{}\".", this.clientCommunication.getSocket().getRemoteSocketAddress(),
					this.clientCommunication.getClient().getUser());
			
			ConfirmLoginCommand confirmLoginCommand = new ConfirmLoginCommand();
			confirmLoginCommand.setId(this.clientCommunication.getClient().getUser().getId());
			this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(confirmLoginCommand));
		} catch (WrongUserNameOrPasswordException e) {
			FailedLoginCommand failedLoginCommand = new FailedLoginCommand();
			failedLoginCommand.setFailedLoginReason(FailedLoginReason.WONG_USERNAME_OR_PASSWORD);
			this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(failedLoginCommand));
		}
	}
	
	private void handleGetGameListCommand(final GetGameListCommand getGameListCommand) throws NullPointerException {
		Objects.requireNonNull(getGameListCommand, "getGameListCommand must not be null");
		
		GameListCommand gameListCommand = new GameListCommand();
		gameListCommand.setGames(ServerCommandHelper.toGames(ServerManager.getManager().getGameManager().getGames(), false, false));
		
		this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(gameListCommand));
	}
	
	private void handleJoinGameCommand(final JoinGameCommand joinGameCommand) throws NullPointerException {
		Objects.requireNonNull(joinGameCommand, "joinGameCommand must not be null");
		
		try {
			ServerManager.getManager().getGameManager().joinGame(joinGameCommand.getId(), joinGameCommand.getJoinPassword(), this.clientCommunication.getClient());
		} catch (IllegalArgumentException e) {
			// TODO: (AW 18.11.2016) Create new exception and get the failedJoinGame
			FailedJoinGameCommand failedJoinGameCommand = new FailedJoinGameCommand();
			failedJoinGameCommand.setFailedJoinGameReason(FailedJoinGameReason.GAME_NOT_FOUND);
			this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(failedJoinGameCommand));
		}
	}
	
	private void handleCreateGameCommand(final CreateGameCommand createGameCommand) throws NullPointerException {
		Objects.requireNonNull(createGameCommand, "createGameCommand must not be null");
		
		// TODO: (AW 02.12.2016) Check is not in game!
		
		try {
			ServerManager.getManager().getGameManager().addGame(new Competition(ClientCommandHelper.toGameConfiguration(createGameCommand.getConfiguration())));
		} catch (IllegalArgumentException e) {
			// TODO: (AW 02.12.2016) Create and throw a FailedCreateGameCommand
			LOGGER.info("Got create Game Command");
		}
	}
	
	private void handleStartGameCommand(final StartGameCommand startGameCommand) throws NullPointerException {
		Objects.requireNonNull(startGameCommand, "startGameCommand must not be null");
		
		try {
			ServerManager.getManager().getGameManager().startGame(startGameCommand.getId(), this.clientCommunication.getClient());
		} catch (IllegalArgumentException e) {
			LOGGER.info("Could not start the game.", e);
		}
	}
	
	private boolean isClientLoggedInForCommand() {
		if (this.clientCommunication.getClient().isLoggedIn()) {
			return true;
		}
		
		this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(new InvalidMessageCommand(InvalidMessageReason.NOT_LOGGED_IN)));
		return false;
	}
}
