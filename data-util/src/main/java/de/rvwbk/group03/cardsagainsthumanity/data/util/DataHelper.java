package de.rvwbk.group03.cardsagainsthumanity.data.util;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.exception.WrongUserNameOrPasswordException;
import de.rvwbk.group03.cardsagainsthumanity.data.Deck;
import de.rvwbk.group03.cardsagainsthumanity.data.memory.Decks;
import de.rvwbk.group03.cardsagainsthumanity.data.memory.User;

public class DataHelper {
	public static final User createUser(final String name, final String password) throws WrongUserNameOrPasswordException {
		try {
			return new User(name);
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new WrongUserNameOrPasswordException("The user name or the password was wrong.", e);
		}
	}
	
	public static final Deck getDeck(final int id) throws IllegalArgumentException, NullPointerException {
		Objects.requireNonNull(id, "id must not be null");
		Deck[] decks = Decks.values();
		
		if (id >= decks.length) {
			throw new IllegalArgumentException("id must be between 0 and " + decks.length);
		}
		return Decks.values()[id];
	}
	
	public static Deck getDeck(final String cardDeckName) throws IllegalArgumentException {
		Deck[] decks = Decks.values();
		for (Deck deck : decks) {
			if (deck.getName().equals(cardDeckName)) {
				return deck;
			}
		}
		throw new IllegalArgumentException("No deck found for name " + cardDeckName);
	}
	
	public static final List<Deck> getDecks() {
		return Arrays.asList(Decks.values());
	}
}
