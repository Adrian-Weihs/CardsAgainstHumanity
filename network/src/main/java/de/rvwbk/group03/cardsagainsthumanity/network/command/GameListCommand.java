package de.rvwbk.group03.cardsagainsthumanity.network.command;

import java.util.ArrayList;
import java.util.List;

import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.ServerCommand;

public class GameListCommand implements ServerCommand {
	private List<Game> games = new ArrayList<>();
	
	
	public List<Game> getGames() {
		return this.games;
	}
	
	public void setGames(final List<Game> games) {
		this.games = games;
	}
}
