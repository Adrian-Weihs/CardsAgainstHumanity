package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.FailedStartGameReason;

public class FailedStartGameCommand {
	
	private FailedStartGameReason failedStartGameReason;
	
	
	public FailedStartGameReason getFailedStartGameReason() {
		return this.failedStartGameReason;
	}
	
	public void setFailedStartGameReason(final FailedStartGameReason failedStartGameReason) {
		this.failedStartGameReason = failedStartGameReason;
	}
}
