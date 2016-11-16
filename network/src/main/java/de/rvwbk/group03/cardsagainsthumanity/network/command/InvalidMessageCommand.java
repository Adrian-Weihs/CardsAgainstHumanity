package de.rvwbk.group03.cardsagainsthumanity.network.command;

import de.rvwbk.group03.cardsagainsthumanity.network.InvalidMessageReason;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.ServerCommand;

public class InvalidMessageCommand implements ServerCommand {
	private InvalidMessageReason invalidMessageReason;
	
	
	public InvalidMessageCommand() {
		this(null);
	}
	
	public InvalidMessageCommand(final InvalidMessageReason invalidMessageReason) {
		this.invalidMessageReason = invalidMessageReason;
	}
	
	
	public InvalidMessageReason getInvalidMessageReason() {
		return this.invalidMessageReason;
	}
	
	public void setInvalidMessageReason(final InvalidMessageReason invalidMessageReason) {
		this.invalidMessageReason = invalidMessageReason;
	}
}
