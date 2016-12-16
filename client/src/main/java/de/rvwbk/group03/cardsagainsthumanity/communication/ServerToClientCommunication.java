package de.rvwbk.group03.cardsagainsthumanity.communication;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.base.network.AbstractBufferedReadCommunication;
import de.rvwbk.group03.cardsagainsthumanity.client.ClientManager;
import de.rvwbk.group03.cardsagainsthumanity.ui.UiManager;

public class ServerToClientCommunication extends AbstractBufferedReadCommunication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerToClientCommunication.class);
	
	
	private String lastMessage;
	
	
	public ServerToClientCommunication(final InputStream inputStream) throws NullPointerException {
		super(inputStream);
	}
	
	
	@Override
	public void run() {
		try {
			String message;
			ClientManager.connect();
			while (!isDisconnected() && (message = getReader().readLine()) != null) {
				while (!checkBrackets(message)) {
					LOGGER.info("Brackets worng: \"{}\"", message);
					// TODO: (AW 12.11.2016) Check better way if message is complete.
					message = getReader().readLine();
				}
				
				handleReceivedMessage(message);
				this.lastMessage = message;
			}
		} catch (IOException e) {
			//Ok, server disconnected
		}
		try {
			ClientManager.disconnect();
		} catch (IOException e) {
			UiManager.closeAllViews();
			// TODO: Error View
		}
	}
	
	@Override
	public void handleReceivedMessage(final String message) {
		
		ClientManager.getRecievedMessageManager().addMessage(message);
	}
	
	
	public String getLastMessage() {
		return this.lastMessage;
	}
}
