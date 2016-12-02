package de.rvwbk.group03.cardsagainsthumanity.server.game.configuration;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.GamePlayer;

public class ReachedScoreWinningCondition implements WinningCondition {
	
	private final int score;
	private final String name;
	
	
	public ReachedScoreWinningCondition(final int score) {
		this.score = score;
		StringBuilder stringBuilder = new StringBuilder("Reach ");
		stringBuilder.append(score).append(" points.");
		this.name = stringBuilder.toString();
	}
	
	
	@Override
	public String getConditionName() {
		return this.name;
	}
	
	@Override
	public boolean isConditionReached(final Competition game) throws NullPointerException {
		Objects.requireNonNull(game, "game must not be null");
		
		for (GamePlayer player : game.getPlayerManager().getPlayers()) {
			if (player.getScore() >= this.score) {
				return true;
			}
		}
		
		return false;
	}
	
}
