package de.rvwbk.group03.cardsagainsthumanity.communication;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.base.network.AbstractBufferedWriteCommunication;

public class ClientToServerCommunication extends AbstractBufferedWriteCommunication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientToServerCommunication.class);
	
	public ClientToServerCommunication(final OutputStream outputStream) {
		super(outputStream);
	}
	
	@Override
	public void writeMessage(final String message) {
		BufferedWriter writer = getWriter();
		try {
			writer.write(message);
			writer.write("\n");
			writer.flush();
		} catch (IOException e) {
			LOGGER.error("Could not write a message.", e);
		}
	}
}
