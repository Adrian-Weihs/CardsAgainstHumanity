package de.rvwbk.group03.cardsagainsthumanity.server.game.event;

public interface GameActionEventListener {
	public void handleGameActionEvent(GameActionEvent event) throws NullPointerException;
}
