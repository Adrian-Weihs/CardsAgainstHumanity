package de.rvwbk.group03.cardsagainsthumanity.server.game.object;

import java.util.Objects;

public abstract class Card {
	private final int id;
	private final String text;
	
	public Card(final int id, final String text) throws IllegalArgumentException, NullPointerException {
		this.id = id;
		
		if (Objects.requireNonNull(text).isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		this.text = text;
	}
	
	public int getId() {
		return id;
	}

	public String getText() {
		return text;
	}
}
