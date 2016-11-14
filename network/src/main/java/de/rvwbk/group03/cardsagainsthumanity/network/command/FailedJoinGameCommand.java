package de.rvwbk.group03.cardsagainsthumanity.network.command;

import de.rvwbk.group03.cardsagainsthumanity.network.gson.ServerCommand;

public class FailedJoinGameCommand implements ServerCommand {
	private String errorMessage;
	
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public void setErrorMessage(final String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
