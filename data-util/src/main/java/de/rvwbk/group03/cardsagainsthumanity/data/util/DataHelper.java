package de.rvwbk.group03.cardsagainsthumanity.data.util;

import de.rvwbk.group03.cardsagainsthumanity.base.exception.WrongUserNameOrPasswordException;
import de.rvwbk.group03.cardsagainsthumanity.data.memory.User;

public class DataHelper {
	public static final User createUser(final String name, String password) throws WrongUserNameOrPasswordException {
		try {
			return new User(name);
		} catch(IllegalArgumentException | NullPointerException e) {
			throw new WrongUserNameOrPasswordException("The user name or the password was wrong.", e);
		}
	}
}
