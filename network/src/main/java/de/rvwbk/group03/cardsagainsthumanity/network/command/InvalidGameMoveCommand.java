package de.rvwbk.group03.cardsagainsthumanity.network.command;

import de.rvwbk.group03.cardsagainsthumanity.network.InvalidGameMoveReason;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.Command;

public class InvalidGameMoveCommand implements Command {
	private InvalidGameMoveReason invalidGameMoveReason;
	
	
	public InvalidGameMoveReason getInvalidGameMoveReason() {
		return this.invalidGameMoveReason;
	}
	
	public void setInvalidGameMoveReason(final InvalidGameMoveReason invalidGameMoveReason) {
		this.invalidGameMoveReason = invalidGameMoveReason;
	}
}
