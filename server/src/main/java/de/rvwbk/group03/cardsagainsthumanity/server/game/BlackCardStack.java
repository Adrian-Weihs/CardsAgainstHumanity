package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.data.BlackCard;
import de.rvwbk.group03.cardsagainsthumanity.data.BlackCardHolder;

public class BlackCardStack implements BlackCardHolder {
	
	private List<BlackCard> cards = new ArrayList<>();
	
	
	public BlackCard getRandomBlackCard() throws IllegalArgumentException {
		if (this.cards.isEmpty()) {
			throw new IllegalArgumentException("No cards in bag to get");
		}
		
		Collections.shuffle(this.cards);
		
		return this.cards.get(0);
	}
	
	public void addBlackCards(final List<BlackCard> cards) throws NullPointerException {
		Objects.requireNonNull(cards, "cards must not be null");
		for (BlackCard card : cards) {
			card.setCardHolder(this);
		}
	}
	
	@Override
	public void addBlackCard(final BlackCard card) throws NullPointerException {
		this.cards.add(Objects.requireNonNull(card, "card must not be null"));
	}
	
	@Override
	public boolean removeBlackCard(final BlackCard card) {
		return this.cards.remove(Objects.requireNonNull(card, "card must not be null"));
	}
	
	public int getNumberOfCards() {
		return this.cards.size();
	}
}
