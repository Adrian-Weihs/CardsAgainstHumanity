package de.rvwbk.group03.cardsagainsthumanity.server.game.configuration;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;

public class OutOfCardsWinningConndition implements WinningCondition {
	private static final String NAME = "Until card deck is empty.";
	
	@Override
	public String getConditionName() {
		return NAME;
	}
	
	@Override
	public boolean isConditionReached(final Competition game) throws NullPointerException {
		Objects.requireNonNull(game, "game must not be null");
		
		return game.getCardManager().getDeck().getNumberOfCard() == 0;
	}
	
}
