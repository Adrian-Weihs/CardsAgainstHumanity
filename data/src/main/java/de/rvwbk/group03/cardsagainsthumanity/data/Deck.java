package de.rvwbk.group03.cardsagainsthumanity.data;

import java.util.List;

public interface Deck {
	
	public int getId();
	
	public String getName();
	
	public List<BlackCard> getBlackCards();
	
	public List<WhiteCard> getWhiteCards();
}
