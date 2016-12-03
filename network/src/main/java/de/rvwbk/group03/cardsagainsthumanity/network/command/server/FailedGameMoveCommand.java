package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.FailedGameMoveReason;

public class FailedGameMoveCommand implements ServerCommand {
	private FailedGameMoveReason invalidGameMoveReason;
	
	
	public FailedGameMoveReason getInvalidGameMoveReason() {
		return this.invalidGameMoveReason;
	}
	
	public void setInvalidGameMoveReason(final FailedGameMoveReason invalidGameMoveReason) {
		this.invalidGameMoveReason = invalidGameMoveReason;
	}
}
