package de.rvwbk.group03.cardsagainsthumanity.network;

import de.rvwbk.group03.cardsagainsthumanity.data.BlackCardHolder;

public class DefaultBlackCard extends AbstractCard implements de.rvwbk.group03.cardsagainsthumanity.data.BlackCard {
	private int numberOfGaps;
	
	
	@Override
	public int getNumberOfGaps() {
		return this.numberOfGaps;
	}
	
	public void setNumberOfGaps(final int numberOfWhiteCards) {
		this.numberOfGaps = numberOfWhiteCards;
	}
	
	@Override
	public void setCardHolder(final BlackCardHolder cardHolder) {
		// Just a dummy class dont have a holder
	}
}
