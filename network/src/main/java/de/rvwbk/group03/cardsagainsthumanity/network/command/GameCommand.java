package de.rvwbk.group03.cardsagainsthumanity.network.command;

import java.util.ArrayList;
import java.util.List;

import de.rvwbk.group03.cardsagainsthumanity.network.BlackCard;
import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.WhiteCard;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.ServerCommand;

public class GameCommand implements ServerCommand {
	private Game game;
	private List<WhiteCard> whiteCards = new ArrayList<>();
	private BlackCard blackCard;
	private int round;
	
	
	public Game getGame() {
		return this.game;
	}
	
	public void setGame(final Game game) {
		this.game = game;
	}
	
	public List<WhiteCard> getWhiteCards() {
		return this.whiteCards;
	}
	
	public void setWhiteCards(final List<WhiteCard> whiteCards) {
		this.whiteCards = whiteCards;
	}
	
	public BlackCard getBlackCard() {
		return this.blackCard;
	}
	
	public void setBlackCard(final BlackCard blackCard) {
		this.blackCard = blackCard;
	}
	
	public int getRound() {
		return this.round;
	}
	
	public void setRound(final int round) {
		this.round = round;
	}
}
