package de.rvwbk.group03.cardsagainsthumanity.network.command;

import java.util.ArrayList;
import java.util.List;

import de.rvwbk.group03.cardsagainsthumanity.network.GameMoveAction;
import de.rvwbk.group03.cardsagainsthumanity.network.WhiteCard;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.Command;

public class GameMoveCommand implements Command {
	private GameMoveAction gameMoveAction;
	private List<WhiteCard> whiteCards = new ArrayList<>();
	
	
	public GameMoveAction getGameMoveAction() {
		return this.gameMoveAction;
	}
	
	public void setGameMoveAction(final GameMoveAction gameMoveAction) {
		this.gameMoveAction = gameMoveAction;
	}
	
	public List<WhiteCard> getWhiteCards() {
		return this.whiteCards;
	}
	
	public void setWhiteCards(final List<WhiteCard> whiteCards) {
		this.whiteCards = whiteCards;
	}
}
