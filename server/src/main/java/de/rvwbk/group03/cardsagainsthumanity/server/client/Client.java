package de.rvwbk.group03.cardsagainsthumanity.server.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.base.exception.WrongUserNameOrPasswordException;
import de.rvwbk.group03.cardsagainsthumanity.data.User;
import de.rvwbk.group03.cardsagainsthumanity.data.util.DataHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.DisconnectReason;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.DisconnectCommand;
import de.rvwbk.group03.cardsagainsthumanity.server.ServerManager;
import de.rvwbk.group03.cardsagainsthumanity.server.communication.ClientCommunication;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;

public class Client {
	private static final Logger LOGGER = LoggerFactory.getLogger(Client.class);
	
	private final ClientCommunication clientCommunication;
	private final List<Competition> games = new ArrayList<>();
	private User user;
	
	public Client(final ClientCommunication clientCommunication) throws NullPointerException {
		this.clientCommunication = Objects.requireNonNull(clientCommunication, "clientCommunication must not be null");
	}
	
	public ClientCommunication getClientCommunication() {
		return this.clientCommunication;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public boolean hasGame() {
		return !this.games.isEmpty();
	}
	
	public void addGame(final Competition game) throws NullPointerException {
		this.games.add(Objects.requireNonNull(game, "game must not be null"));
	}
	
	public boolean removeGame(final Competition game) throws NullPointerException {
		return this.games.remove(Objects.requireNonNull(game, "game must not be null"));
	}
	
	public boolean isLoggedIn() {
		return this.user != null;
	}
	
	public void login(final String name, final String password) throws WrongUserNameOrPasswordException {
		this.user = DataHelper.createUser(name, password);
		
		ServerManager.getManager().getClientManager().loginClient(this);
	}
	
	public void disconnect(final DisconnectReason disconnectReason) throws NullPointerException {
		Objects.requireNonNull(disconnectReason, "disconnectReason must not be null");
		
		DisconnectCommand disconnectCommand = new DisconnectCommand();
		disconnectCommand.setDisconnectReason(disconnectReason);
		
		this.clientCommunication.getWriteCommunication().writeMessage(CommandHelper.commandToJson(disconnectCommand));
		
		disconnect();
	}
	
	public void disconnect() {
		new ArrayList<>(this.games).forEach(game -> game.leave(this));
		this.games.clear();
		
		ServerManager.getManager().getClientManager().disconnectClient(this);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.clientCommunication, this.user);
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Client other = (Client) obj;
		return this.clientCommunication.equals(other.clientCommunication) && this.user.equals(other.user);
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		
		if(isLoggedIn()) {
			stringBuilder.append(this.user).append(' ');
		}
		
		stringBuilder.append("( ").append(this.clientCommunication.getSocket().getRemoteSocketAddress().toString()).append(')');
		return stringBuilder.toString();
	}
}
