package de.rvwbk.group03.cardsagainsthumanity.server.communication;

import java.io.IOException;
import java.net.Socket;

import de.rvwbk.group03.cardsagainsthumanity.base.network.AbstractCommunication;
import de.rvwbk.group03.cardsagainsthumanity.server.ServerManager;
import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;

public class ClientCommunication extends AbstractCommunication {
	
	private final ClientToServerCommunication clientToServerCommunication;
	private final ServerToClientCommunication serverToClientCommunication;
	
	private final Client client;
	
	/**
	 * @param socket The socket.
	 * @throws IOException If.
	 * @throws NullPointerException If {@code socket} is {@code null}.
	 */
	public ClientCommunication(final Socket socket) throws IOException, NullPointerException {
		super(socket);
		this.client = new Client(this);
		this.clientToServerCommunication = new ClientToServerCommunication(this, socket.getInputStream());
		this.serverToClientCommunication = new ServerToClientCommunication(this, socket.getOutputStream());
		ServerManager.getManager().getClientManager().addClient(this.client);
	}
	
	public Client getClient() {
		return this.client;
	}
	
	public void disconnect() throws IOException {
		this.clientToServerCommunication.disconnect();
		getSocket().close();
	}
	
	@Override
	public ClientToServerCommunication getReadCommunication() {
		return this.clientToServerCommunication;
	}
	
	@Override
	public ServerToClientCommunication getWriteCommunication() {
		return this.serverToClientCommunication;
	}
}
