package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.data.WhiteCard;
import de.rvwbk.group03.cardsagainsthumanity.data.WhiteCardHolder;

public class WhiteCardStack implements WhiteCardHolder {
	
	private List<WhiteCard> cards = new ArrayList<>();
	
	
	public List<WhiteCard> getRandomWhiteCards(int count) throws NullPointerException {
		Objects.requireNonNull(count, "count must not be null");
		if (count > this.cards.size()) {
			count = this.cards.size();
		}
		
		if (count == 0) {
			throw new IllegalArgumentException("No Tiles in Bag to get");
		} else if (count < 0) {
			throw new IllegalArgumentException("Cant give negativ amount of Tiles");
		}
		
		Collections.shuffle(this.cards);
		
		List<WhiteCard> result = new ArrayList<>();
		
		for (int i = 0; i < count; i++) {
			result.add(this.cards.get(i));
		}
		
		return result;
	}
	
	
	@Override
	public void addWhiteCard(final WhiteCard card) throws NullPointerException {
		this.cards.add(Objects.requireNonNull(card, "card must not be null"));
	}
	
	public void addWhiteCards(final List<WhiteCard> cards) throws NullPointerException {
		Objects.requireNonNull(cards, "cards must not be null");
		
		for (WhiteCard card : cards) {
			card.setCardHolder(this);
		}
	}
	
	@Override
	public boolean removeWhiteCard(final WhiteCard card) {
		return this.cards.remove(Objects.requireNonNull(card, "card must not be null"));
	}
	
	public void removeWhiteCards(final List<WhiteCard> cards) throws NullPointerException {
		this.cards.forEach(card -> card.setCardHolder(null));
	}
	
	public int getNumberOfCards() {
		return this.cards.size();
	}
}
