package de.rvwbk.group03.cardsagainsthumanity.client.debug;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.client.debug.communication.ServerCommunication;
import de.rvwbk.group03.cardsagainsthumanity.client.debug.ui.DebugViewImpl;

public class DebugManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(DebugManager.class);
	
	private static ServerCommunication serverCommunication;
	private static RecievedMessageManager recievedMessageManager;
	
	
	public static void main(final String[] args) {
		DebugViewImpl view = new DebugViewImpl();
		view.setVisible(true);
	}
	
	public static void connect() throws IOException {
		if(serverCommunication == null) {
			serverCommunication = new ServerCommunication(new Socket("localhost", 33100));
			Thread thread = new Thread(serverCommunication.getReadCommunication());
			thread.start();
		}
	}
	
	public static void disconnect() throws IOException {
		if(serverCommunication != null) {
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
}
