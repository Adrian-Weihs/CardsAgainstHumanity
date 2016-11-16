package de.rvwbk.group03.cardsagainsthumanity.client.debug.communication;

import java.io.IOException;
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
	public void run() {
		try {
			String message;
			while (!isDisconnected() && (message = getReader().readLine()) != null) {
				while (!checkBrackets(message)) {
					LOGGER.info("Brackets worng: \"{}\"", message);
					// TODO: (AW 12.11.2016) Check better way if message is complete.
					message = getReader().readLine();
				}
				
				handleReceivedMessage(message);
			}
		} catch (IOException e) {
			//Ok, server disconnected
			LOGGER.error("Disconnected from Server", e);
		}
	}
	
	@Override
	public void handleReceivedMessage(final String message) {
		
		DebugManager.getRecievedMessageManager().addMessage(message);
	}
}
