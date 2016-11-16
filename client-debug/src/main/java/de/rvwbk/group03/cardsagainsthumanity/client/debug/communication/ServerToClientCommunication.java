package de.rvwbk.group03.cardsagainsthumanity.client.debug.communication;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.base.network.AbstractBufferedReadCommunication;
import de.rvwbk.group03.cardsagainsthumanity.client.debug.DebugManager;

public class ServerToClientCommunication extends AbstractBufferedReadCommunication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerToClientCommunication.class);
	
	public ServerToClientCommunication(final InputStream inputStream) throws NullPointerException {
		super(inputStream);
	}
	
	@Override
	public void handleReceivedMessage(final String message) {
		
		DebugManager.getRecievedMessageManager().addMessage(message);
	}
}
