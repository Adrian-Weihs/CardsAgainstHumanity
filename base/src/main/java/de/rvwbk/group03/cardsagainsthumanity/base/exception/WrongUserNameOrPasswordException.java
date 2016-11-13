package de.rvwbk.group03.cardsagainsthumanity.base.exception;

/**
 * This exception should be thrown if a login failed.
 * 
 * @author Adrian
 */
public class WrongUserNameOrPasswordException extends RvwbkException {
	private static final long serialVersionUID = 8691532469797157323L;
	
	
	/**
	 * @param message The message of this exception. Must not be {@code null}
	 * @throws NullPointerException If {@code message} or {@code cause} is {@code null}.
	 */
	public WrongUserNameOrPasswordException(final String message) throws NullPointerException {
		super(message);
	}
	
	/**
	 * @param message The message of this exception. Must not be {@code null}
	 * @param cause The cause of this exception. Must not be {@code null}.
	 * @throws NullPointerException If {@code message} or {@code cause} is {@code null}.
	 */
	public WrongUserNameOrPasswordException(final String message, final Throwable cause) throws NullPointerException {
		super(message, cause);
	}
}
