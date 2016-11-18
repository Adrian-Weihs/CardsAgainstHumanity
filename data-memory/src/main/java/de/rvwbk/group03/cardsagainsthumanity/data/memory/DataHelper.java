package de.rvwbk.group03.cardsagainsthumanity.data.memory;

public class DataHelper {
	
	public static final User createUser(final String name) throws IllegalArgumentException, NullPointerException {
		return new User(name);
	}
	
	
	private DataHelper() {}
}
