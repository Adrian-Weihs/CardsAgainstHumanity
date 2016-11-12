package de.rvwbk.group03.cardsagainsthumanity.base.exception;

import java.util.Objects;

/**
 * The class {@link RvwbkException} and its subclasses are a form of {@link Exception} that indicates conditions that a
 * reasonable application might want to catch.
 * 
 * @author Adrian
 */
public class RvwbkException extends Exception {
	private static final long serialVersionUID = -83529004621527312L;
	
	
	/**
	 * @param message The message of this exception. Must not be {@code null}
	 * @throws NullPointerException If {@code message} is {@code null}.
	 */
	public RvwbkException(final String message) throws NullPointerException {
		super(Objects.requireNonNull(message, "message must not be null"));
	}
	
	/**
	 * @param message The message of this exception. Must not be {@code null}
	 * @param cause The cause of this exception. Must not be {@code null}.
	 * @throws NullPointerException If {@code message} or {@code cause} is {@code null}.
	 */
	public RvwbkException(final String message, final Throwable cause) throws NullPointerException {
		super(Objects.requireNonNull(message, "message must not be null"), Objects.requireNonNull(cause, "cause must not be null"));
	}
}
