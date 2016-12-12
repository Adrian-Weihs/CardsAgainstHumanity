package de.rvwbk.group03.cardsagainsthumanity.network.command.client;

import java.util.ArrayList;
import java.util.List;

import de.rvwbk.group03.cardsagainsthumanity.network.GameMoveAction;
import de.rvwbk.group03.cardsagainsthumanity.network.DefaultWhiteCard;

public class GameMoveCommand implements LoggedInClientCommand {
	private GameMoveAction gameMoveAction;
	private List<DefaultWhiteCard> whiteCards = new ArrayList<>();
	
	
	public GameMoveAction getGameMoveAction() {
		return this.gameMoveAction;
	}
	
	public void setGameMoveAction(final GameMoveAction gameMoveAction) {
		this.gameMoveAction = gameMoveAction;
	}
	
	public List<DefaultWhiteCard> getWhiteCards() {
		return this.whiteCards;
	}
	
	public void setWhiteCards(final List<DefaultWhiteCard> whiteCards) {
		this.whiteCards = whiteCards;
	}
}
