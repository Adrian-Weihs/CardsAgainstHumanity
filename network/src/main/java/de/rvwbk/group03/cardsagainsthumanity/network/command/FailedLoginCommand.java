package de.rvwbk.group03.cardsagainsthumanity.network.command;

import de.rvwbk.group03.cardsagainsthumanity.network.FailedLoginReason;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.ServerCommand;

public class FailedLoginCommand implements ServerCommand {
	private FailedLoginReason failedLoginReason;
	
	
	public FailedLoginReason getFailedLoginReason() {
		return this.failedLoginReason;
	}
	
	public void setFailedLoginReason(final FailedLoginReason failedLoginReason) {
		this.failedLoginReason = failedLoginReason;
	}
}
