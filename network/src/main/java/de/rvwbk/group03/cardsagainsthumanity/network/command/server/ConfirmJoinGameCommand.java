package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import de.rvwbk.group03.cardsagainsthumanity.network.Game;

public class ConfirmJoinGameCommand implements ServerCommand {
	private Game game;
	
	public Game getGame() {
		return this.game;
	}
	
	public void setGame(final Game game) {
		this.game = game;
	}
}
