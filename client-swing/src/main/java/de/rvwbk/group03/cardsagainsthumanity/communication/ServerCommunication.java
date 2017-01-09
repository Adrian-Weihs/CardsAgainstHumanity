package de.rvwbk.group03.cardsagainsthumanity.communication;

import java.io.IOException;
import java.net.Socket;

import de.rvwbk.group03.cardsagainsthumanity.base.network.AbstractCommunication;

public class ServerCommunication extends AbstractCommunication {
	
	private final ClientToServerCommunication clientToServerCommunication;
	private final ServerToClientCommunication serverToClientCommunication;
	
	public ServerCommunication(final Socket socket) throws IOException {
		super(socket);
		this.clientToServerCommunication = new ClientToServerCommunication(socket.getOutputStream());
		this.serverToClientCommunication = new ServerToClientCommunication(socket.getInputStream());
	}
	
	@Override
	public ServerToClientCommunication getReadCommunication() {
		return this.serverToClientCommunication;
	}
	
	@Override
	public ClientToServerCommunication getWriteCommunication() {
		return this.clientToServerCommunication;
	}
}
