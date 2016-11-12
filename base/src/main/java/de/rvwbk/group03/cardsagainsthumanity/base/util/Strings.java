package de.rvwbk.group03.cardsagainsthumanity.base.util;

import java.util.Objects;

/**
 * A helper class for operating on strings.
 * 
 * <p>
 * This class consists of {@code static} utility methods for operating on strings. These utility methods include
 * {@code null}-safe or {@code null}-tolerant methods for checking if a string is empty.
 * </p>
 *
 * @author Adrian Weihs
 */
public class Strings {
	/**
	 * This constant represents the empty string.
	 */
	public static final String EMPTY = "";
	
	
	/**
	 * Returns {@code null} if the given string is {@code null} or empty or the given string if not.
	 * <p>
	 * This method may return {@code null} but it never returns an empty string.
	 * </p>
	 *
	 * @param string The string to check which may be {@code null}.
	 * @return Either {@code null} if the given string is {@code null} or empty or the given string if it is neither
	 *         {@code null} nor empty.
	 * @see #nullToEmpty(String)
	 * @see #isEmpty(String)
	 */
	public static String emptyToNull(final String string) {
		return string == EMPTY || string != null && string.isEmpty() ? null : string;
	}
	
	/**
	 * Returns {@link #EMPTY} if the given string is {@code null} or empty or the given string if not.
	 * <p>
	 * This method never returns {@code null} but it may return the empty string {@link #EMPTY}.
	 * </p>
	 *
	 * @param string The string to check which may be {@code null}.
	 * @return Either {@link #EMPTY} if the given string is {@code null} or empty or the given string if it is neither
	 *         {@code null} nor empty.
	 * @see #emptyToNull(String)
	 * @see #isEmpty(String)
	 */
	public static String nullToEmpty(final String string) {
		return string == null || string.isEmpty() ? EMPTY : string;
	}
	
	/**
	 * Checks if the given string is either {@code null} or empty.
	 *
	 * @param string The string to check which may be {@code null}.
	 * @return {@code true} if the given string is {@code null} or empty, {@code false} otherwise.
	 * @see #emptyToNull(String)
	 * @see #nullToEmpty(String)
	 */
	public static boolean isEmpty(final String string) {
		return string == null || string == EMPTY || string.isEmpty();
	}
	
	/**
	 * Checks that the specified string reference is not {@code null} and not empty throws a customized
	 * {@link NullPointerException} or {@link IllegalArgumentException} if it is.
	 * 
	 * @param string The string reference to check for nullity. Must no be {@code null}.
	 * @param name The variable name of the string reference. Most not be {@code null}.
	 * @return {@code string} if not {@code null} and not empty.
	 * @throws IllegalArgumentException If {@code string} is empty.
	 * @throws NullPointerException If {@code string} or {@code name} is {@code null}.
	 */
	public static String requireNonNullAndNonEmpty(final String string, final String name) throws IllegalArgumentException, NullPointerException {
		Objects.requireNonNull(name, "name must not be null");
		
		if (Objects.requireNonNull(string, name + " must not be null").isEmpty()) {
			throw new IllegalArgumentException(name + "must not be empty");
		}
		
		return string;
	}
	
	
	private Strings() {}
}
