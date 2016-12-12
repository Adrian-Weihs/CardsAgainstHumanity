package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.data.WhiteCard;
import de.rvwbk.group03.cardsagainsthumanity.data.WhiteCardHolder;

public class Hand implements WhiteCardHolder {
	
	public static final int MAX_CARDS_ON_HAND = 6;
	
	private List<WhiteCard> whiteCards = new ArrayList<>();
	
	public List<WhiteCard> getWhiteCards() {
		return this.whiteCards;
	}
	
	public void addWhiteCards(final List<WhiteCard> cards) throws NullPointerException {
		Objects.requireNonNull(cards, "cards must not be null").forEach(card -> card.setCardHolder(this));
	}
	
	@Override
	public void addWhiteCard(final WhiteCard card) throws NullPointerException {
		this.whiteCards.add(Objects.requireNonNull(card, "card must not be null"));
	}
	
	@Override
	public boolean removeWhiteCard(final WhiteCard card) throws NullPointerException {
		return this.whiteCards.remove(Objects.requireNonNull(card, "card must not be null"));
	}
}
