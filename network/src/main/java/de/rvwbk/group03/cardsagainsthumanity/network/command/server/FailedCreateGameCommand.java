package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.FailedCreateGameReason;

public class FailedCreateGameCommand implements ServerCommand {
	private FailedCreateGameReason reason;
	
	public FailedCreateGameReason getReason() {
		return this.reason;
	}
	
	public void setReason(final FailedCreateGameReason reason) {
		this.reason = reason;
	}
}
