package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.FailedLeaveGameReason;

public class FailedLeaveGameCommand implements ServerCommand {
	private FailedLeaveGameReason reason;
	
	
	public FailedLeaveGameReason getReason() {
		return this.reason;
	}
	
	public void setReason(final FailedLeaveGameReason reason) {
		this.reason = reason;
	}
}
