package de.rvwbk.group03.cardsagainsthumanity.base.exception;

import java.util.Objects;

/**
 * The class {@link RvwbkRuntimeException} and its subclasses are a form of {@link RuntimeException} of those exceptions
 * that can be thrown during the normal operation of the Java Virtual Machine.
 * 
 * @author Adrian
 */
public class RvwbkRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 4018070327287071336L;
	
	
	/**
	 * @param message The message of this exception. Must not be {@code null}
	 * @throws NullPointerException If {@code message} is {@code null}.
	 */
	public RvwbkRuntimeException(final String message) throws NullPointerException {
		super(Objects.requireNonNull(message, "message must not be null"));
	}
	
	/**
	 * @param message The message of this exception. Must not be {@code null}
	 * @param cause The cause of this exception. Must not be {@code null}.
	 * @throws NullPointerException If {@code message} or {@code cause} is {@code null}.
	 */
	public RvwbkRuntimeException(final String message, final Throwable cause) throws NullPointerException {
		super(Objects.requireNonNull(message, "message must not be null"), Objects.requireNonNull(cause, "cause must not be null"));
	}
}
