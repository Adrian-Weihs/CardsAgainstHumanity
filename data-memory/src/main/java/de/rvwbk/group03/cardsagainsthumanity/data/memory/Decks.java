package de.rvwbk.group03.cardsagainsthumanity.data.memory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;
import de.rvwbk.group03.cardsagainsthumanity.data.BlackCard;
import de.rvwbk.group03.cardsagainsthumanity.data.Deck;
import de.rvwbk.group03.cardsagainsthumanity.data.WhiteCard;

public enum Decks implements Deck {
	DEFAULT("default", Arrays.asList(DefaultDeckBlackCard.values()), Arrays.asList(DefaultDeckWhiteCard.values()));
	
	private final String name;
	private final List<BlackCard> blackCards;
	private final List<WhiteCard> whiteCards;
	
	private Decks(final String name, final List<BlackCard> blackCards, final List<WhiteCard> whiteCards) {
		this.name = Strings.requireNonNullAndNonEmpty(name, "name");
		this.blackCards = Objects.requireNonNull(blackCards, "blackCards must not be null");
		this.whiteCards = Objects.requireNonNull(whiteCards, "whiteCards must not be null");
	}
	
	@Override
	public int getId() {
		return ordinal();
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
	
	@Override
	public String toString() {
		return getName();
	}
}
