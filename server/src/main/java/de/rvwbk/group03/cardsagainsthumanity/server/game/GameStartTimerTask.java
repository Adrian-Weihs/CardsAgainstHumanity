package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.Objects;
import java.util.TimerTask;

public class GameStartTimerTask extends TimerTask {
	private final Competition game;
	
	public GameStartTimerTask(final Competition game) throws NullPointerException {
		this.game = Objects.requireNonNull(game, "game must not be null");
	}
	
	/**
	 * The main method of this class.
	 */
	@Override
	public void run() {
		this.game.startCompleted();
	}
}
