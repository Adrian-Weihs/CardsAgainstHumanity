package de.rvwbk.group03.cardsagainsthumanity.base.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;

public abstract class AbstractBufferedWriteCommunication {
	private final BufferedWriter writerStream;
	
	
	public AbstractBufferedWriteCommunication(final OutputStream outputStream) {
		this.writerStream = new BufferedWriter(new OutputStreamWriter(Objects.requireNonNull(outputStream, "outputStream must not be null")));
	}
	
	public BufferedWriter getWriter() {
		return this.writerStream;
	}
	
	public abstract void writeMessage(final String message) throws IOException;
}
