package de.rvwbk.group03.cardsagainsthumanity.server.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class handles all incoming connection requests for new clients. Each client runs in its own thread, to handle
 * incoming packages individually.
 * 
 * Default server port is set to 33098.
 * 
 * @author Adrian Weihs
 * 
 */
public class ClientSocketHandler implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientSocketHandler.class);
	
	private int PORT = 33100;
	
	private ServerSocket server;
	private ExecutorService clientReaderCommunications = Executors.newCachedThreadPool();
	
	
	/**
	 * Creates a new {@link ClientSocketHandler} and automatically tries to open a server socket on the specified port.
	 * 
	 * @param gameEngine the {@link GameEngine}
	 * @throws IOException in case the socket could not be opened
	 */
	public ClientSocketHandler() throws IOException {
		LOGGER.info("Trying to create server socket on port \"{}\".", this.PORT);
		this.server = new ServerSocket(this.PORT);
		LOGGER.info("Server socket opened. Listen for clients on port \"{}\".", this.PORT);
	}
	
	@Override
	/**
	 * Executes the thread.
	 * 
	 * While the thread is not interrupted, it waits for new clients to connect. If a client connects,
	 * further requests are handled individually in extra client threads.	 *
	 */
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			Socket clientSocket = null;
			try {
				// wait for new clients
				clientSocket = this.server.accept();
				LOGGER.info("New client connection request with ip \"{}\".", clientSocket.getRemoteSocketAddress());
				// start client thread with thread pool
				ClientCommunication clientCommunication = new ClientCommunication(clientSocket);
				this.clientReaderCommunications.execute(clientCommunication.getReadCommunication());
				LOGGER.info("Established client connection with ip \"{}\".", clientSocket.getRemoteSocketAddress());
			} catch (IOException e) {
				LOGGER.warn("Client connection with ip \"{}\" could not be established.", clientSocket.getRemoteSocketAddress());
			}
		}
	}
}
