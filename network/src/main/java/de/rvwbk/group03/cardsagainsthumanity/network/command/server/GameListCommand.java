package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

import java.util.ArrayList;
import java.util.Collection;

import de.rvwbk.group03.cardsagainsthumanity.network.Game;

public class GameListCommand implements ServerCommand {
	private Collection<Game> games = new ArrayList<>();
	
	
	public Collection<Game> getGames() {
		return this.games;
	}
	
	public void setGames(final Collection<Game> games) {
		this.games = games;
	}
}
