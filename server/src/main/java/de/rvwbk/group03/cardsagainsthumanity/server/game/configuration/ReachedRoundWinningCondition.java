package de.rvwbk.group03.cardsagainsthumanity.server.game.configuration;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;

public class ReachedRoundWinningCondition implements WinningCondition {
	
	private final int rounds;
	private final String name;
	
	
	public ReachedRoundWinningCondition(final int rounds) {
		this.rounds = rounds;
		StringBuilder stringBuilder = new StringBuilder("Reach the most points after ");
		stringBuilder.append(rounds).append(" rounds.");
		this.name = stringBuilder.toString();
	}
	
	
	@Override
	public String getConditionName() {
		return this.name;
	}
	
	@Override
	public boolean isConditionReached(final Competition game) throws NullPointerException {
		Objects.requireNonNull(game, "game must not be null");
		
		return game.getRound() >= this.rounds;
	}
}
