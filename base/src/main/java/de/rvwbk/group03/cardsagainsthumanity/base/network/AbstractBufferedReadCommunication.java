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
	private boolean disconnected;
	
	
	/**
	 * @param inputStream The input stream to read from. Must not be {@code null}.
	 * @throws NullPointerException If {@code inputStream} is {@code null}.
	 */
	public AbstractBufferedReadCommunication(final InputStream inputStream) throws NullPointerException {
		this.readerStream = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream, "inputStream must not be null")));
	}
	
	
	public BufferedReader getReader() {
		return this.readerStream;
	}
	
	public boolean isDisconnected() {
		return this.disconnected;
	}
	
	public void disconnect() {
		this.disconnected = true;
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
	protected boolean checkBrackets(final String received) throws IllegalArgumentException, IOException {
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
