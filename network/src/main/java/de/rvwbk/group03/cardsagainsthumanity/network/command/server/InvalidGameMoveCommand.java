package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.InvalidGameMoveReason;

public class InvalidGameMoveCommand implements ServerCommand {
	private InvalidGameMoveReason invalidGameMoveReason;
	
	
	public InvalidGameMoveReason getInvalidGameMoveReason() {
		return this.invalidGameMoveReason;
	}
	
	public void setInvalidGameMoveReason(final InvalidGameMoveReason invalidGameMoveReason) {
		this.invalidGameMoveReason = invalidGameMoveReason;
	}
}
