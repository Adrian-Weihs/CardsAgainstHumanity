package de.rvwbk.group03.cardsagainsthumanity.server.communication;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.base.network.AbstractBufferedWriteCommunication;

/**
 * 
 * @author Adrian
 */
public class ServerToClientCommunication extends AbstractBufferedWriteCommunication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerToClientCommunication.class);
	
	private final ClientCommunication clientCommunication;
	
	
	public ServerToClientCommunication(final ClientCommunication clientCommunication, final OutputStream outputStream) throws NullPointerException {
		super(outputStream);
		
		this.clientCommunication = Objects.requireNonNull(clientCommunication, "clientCommunication must not be null");
	}
	
	@Override
	public void writeMessage(final String message) {
		LOGGER.info("Send a message to client with the ip \"{}\" and message \"{}\".", this.clientCommunication.getSocket().getRemoteSocketAddress(), message);
		try {
			getWriter().write(message);
			getWriter().write("\n");
			getWriter().flush();
		} catch (IOException e) {
			LOGGER.error("Could not send a message.", e);
		}
	}
}
