package de.rvwbk.group03.cardsagainsthumanity.server.game.configuration;

import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;

public interface WinningCondition {
	
	public String getConditionName();
	
	public boolean isConditionReached(Competition game) throws NullPointerException;
}
