package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.KickReason;
import de.rvwbk.group03.cardsagainsthumanity.network.Player;

public class KickCommand implements ServerCommand {
	private Player player;
	private KickReason kickReason;
	
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void setPlayer(final Player player) {
		this.player = player;
	}
	
	public KickReason getKickReason() {
		return this.kickReason;
	}
	
	public void setKickReason(final KickReason kickReason) {
		this.kickReason = kickReason;
	}
}
