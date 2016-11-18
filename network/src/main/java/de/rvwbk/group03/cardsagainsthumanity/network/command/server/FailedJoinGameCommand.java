package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.FailedJoinGameReason;

public class FailedJoinGameCommand implements ServerCommand {
	private FailedJoinGameReason failedJoinGameReason;
	
	
	public FailedJoinGameReason getFailedJoinGameReason() {
		return this.failedJoinGameReason;
	}
	
	public void setErrorMessage(final FailedJoinGameReason failedJoinGameReason) {
		this.failedJoinGameReason = failedJoinGameReason;
	}
}
