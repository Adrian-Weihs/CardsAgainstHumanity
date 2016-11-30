package de.rvwbk.group03.cardsagainsthumanity.server.game.event;

public interface GameActionEventHandler {
	public void addGameActionEventListener(GameActionEventListener listener) throws NullPointerException;
	
	public boolean removeGameActionEventListener(GameActionEventListener listener) throws NullPointerException;
}
