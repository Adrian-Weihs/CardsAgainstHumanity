package de.rvwbk.group03.cardsagainsthumanity.base.network;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;


public abstract class AbstractCommunication {
	private final Socket socket;
	
	
	public AbstractCommunication(final Socket socket) {
		this.socket = Objects.requireNonNull(socket, "socket must not be null");
	}
	
	
	public Socket getSocket() {
		return this.socket;
	}
	
	public abstract AbstractBufferedReadCommunication getReadCommunication() throws IOException;
	
	public abstract AbstractBufferedWriteCommunication getWriteCommunication() throws IOException;
}
