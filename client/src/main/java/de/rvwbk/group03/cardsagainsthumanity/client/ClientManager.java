package de.rvwbk.group03.cardsagainsthumanity.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.communication.ServerCommunication;
import de.rvwbk.group03.cardsagainsthumanity.network.DisconnectReason;
import de.rvwbk.group03.cardsagainsthumanity.network.command.Command;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.DisconnectCommand;

public class ClientManager implements ClientManagerEventHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientManager.class);
	
	private List<ClientManagerEventListener> clientListeners = new ArrayList<>();
	
	private static ServerCommunication serverCommunication;
	private static RecievedMessageManager recievedMessageManager;
	
	
	public void connect() throws IOException {
		if(serverCommunication == null) {
			serverCommunication = new ServerCommunication(new Socket("localhost", 33100));
			Thread thread = new Thread(serverCommunication.getReadCommunication());
			thread.start();
			
			this.clientListeners.forEach(listener -> listener.handleClientManagerActionEvent(new ClientManagerEvent(this, ClientManagerAction.CONNECT)));
		}
	}
	
	public void disconnect() throws IOException {
		
		String disconnectReason = "You lost the connection to the server";
		
		if(serverCommunication != null) {
			
			Command command = CommandHelper.jsonToCommand(serverCommunication.getReadCommunication().getLastMessage());
			
			if (command instanceof DisconnectCommand) {
				DisconnectReason reason = ((DisconnectCommand) command).getDisconnectReason();
				
				switch (reason) {
					case CONNECTION_LOST:
						disconnectReason = "You've lost connection to the server.";
						break;
					case DISAGREEMENT:
						disconnectReason = "You've been kicked from the server.";
						break;
					case NOT_LOGGED_IN:
						disconnectReason = "You're not logged in anymore.";
						break;
					case SHUTDOWN:
						disconnectReason = "The server has shut down";
						break;
					default:
						break;
				}
			}
			
			this.clientListeners.forEach(listener -> listener.handleClientManagerActionEvent(new ClientManagerEvent(this, ClientManagerAction.DISCONNECT)));
			
			serverCommunication.getSocket().close();
			serverCommunication = null;
		}
	}
	
	public static ServerCommunication getServerCommunication() {
		return serverCommunication;
	}
	
	public static RecievedMessageManager getRecievedMessageManager() {
		if(recievedMessageManager == null) {
			recievedMessageManager = new RecievedMessageManager();
		}
		
		return recievedMessageManager;
	}
	
	@Override
	public void addClientManagerActionEventListener(final ClientManagerEventListener listener) throws NullPointerException {
		this.clientListeners.add(Objects.requireNonNull(listener, "listener must not be null."));
		
	}
	
	@Override
	public boolean removeClientManagerActionEventListener(final ClientManagerEventListener listener) throws NullPointerException {
		return this.clientListeners.remove(Objects.requireNonNull(listener, "listener must not be null."));
	}
}
