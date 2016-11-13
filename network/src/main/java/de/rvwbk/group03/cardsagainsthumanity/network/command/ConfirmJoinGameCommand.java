package de.rvwbk.group03.cardsagainsthumanity.network.command;

import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.Command;

public class ConfirmJoinGameCommand implements Command {
	private Game game;
	
	public Game getGame() {
		return this.game;
	}
	
	public void setGame(final Game game) {
		this.game = game;
	}
}
