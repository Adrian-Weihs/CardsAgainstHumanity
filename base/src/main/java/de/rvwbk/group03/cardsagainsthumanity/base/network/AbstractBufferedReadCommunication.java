package de.rvwbk.group03.cardsagainsthumanity.base.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Adrian
 */
public abstract class AbstractBufferedReadCommunication implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractBufferedReadCommunication.class);
	
	private final BufferedReader readerStream;
	
	private int tempOpenBrackets;
	private boolean inString;
	
	
	/**
	 * @param inputStream The input stream to read from. Must not be {@code null}.
	 * @throws NullPointerException If {@code inputStream} is {@code null}.
	 */
	public AbstractBufferedReadCommunication(final InputStream inputStream) throws NullPointerException {
		this.readerStream = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream, "inputStream must not be null")));
	}
	
	
	@Override
	public void run() {
		try {
			String message;
			while ((message = this.readerStream.readLine()) != null) {
				this.tempOpenBrackets = 0;
				this.inString = false;
				
				while (!checkBrackets(message)) {
					// TODO: (AW 12.11.2016) Check better way if message is complete.
					message = this.readerStream.readLine();
				}
				
				handleReceivedMessage(message);
			}
		} catch (IOException e) {
			LOGGER.error("Could not read the message from the client", e);
		}
	}
	
	public abstract void handleReceivedMessage(String message);
	
	/**
	 * Handles a received line by checking the number of open brackets. Lines are added to the complete message stored in
	 * {@link tempReceivedMessage}. If the message is complete (number of open brackets = 0) this method returns true to
	 * indicate that the current transmission ends.
	 * 
	 * @param received the received line
	 * @return {@code true} if the end of the message has been reached
	 * @throws IllegalArgumentException if.
	 * @throws IOException if.
	 * @throws InvalidFormatException if there have been more closing brackets than opening ones
	 */
	private boolean checkBrackets(final String received) throws IllegalArgumentException, IOException {
		if (received == null) { // illegal message
			return false;
		}
		
		for (char c : received.toCharArray()) {
			switch (c) {
				case '"':
					this.inString = !this.inString;
					break;
				case '{':
					if (!this.inString) {
						this.tempOpenBrackets++;
					}
					break;
				case '}':
					if (!this.inString) {
						this.tempOpenBrackets--;
						if (this.tempOpenBrackets < 0) {
							throw new IllegalArgumentException();
						}
					}
					break;
				default:
					break;
			}
		}
		
		if (this.tempOpenBrackets == 0) {
			return true;
		} else {
			return false;
		}
	}
}
