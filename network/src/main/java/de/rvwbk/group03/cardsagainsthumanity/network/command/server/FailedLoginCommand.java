package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.FailedLoginReason;

public class FailedLoginCommand implements ServerCommand {
	private FailedLoginReason failedLoginReason;
	
	
	public FailedLoginReason getFailedLoginReason() {
		return this.failedLoginReason;
	}
	
	public void setFailedLoginReason(final FailedLoginReason failedLoginReason) {
		this.failedLoginReason = failedLoginReason;
	}
}
