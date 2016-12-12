package de.rvwbk.group03.cardsagainsthumanity.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class DefaultDeck implements Deck {
	
	private final int id;
	private final String name;
	private final List<BlackCard> blackCards;
	private final List<WhiteCard> whiteCards;
	
	public DefaultDeck(final int id, final String name, final List<BlackCard> blackCards, final List<WhiteCard> whiteCards) {
		this.id = Objects.requireNonNull(id, "id must not be null");
		this.name = Objects.requireNonNull(name, "name must not be null");
		this.blackCards = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(blackCards, "blackCards must not be null")));
		this.whiteCards = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(whiteCards, "whiteCards must not be null")));
	}
	
	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public List<BlackCard> getBlackCards() {
		return this.blackCards;
	}
	
	@Override
	public List<WhiteCard> getWhiteCards() {
		return this.whiteCards;
	}
}
