package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.DisconnectReason;

public class DisconnectCommand implements ServerCommand {
	private DisconnectReason disconnectReason;
	
	public DisconnectReason getDisconnectReason() {
		return this.disconnectReason;
	}
	
	public void setDisconnectReason(final DisconnectReason disconnectReason) {
		this.disconnectReason = disconnectReason;
	}
}
