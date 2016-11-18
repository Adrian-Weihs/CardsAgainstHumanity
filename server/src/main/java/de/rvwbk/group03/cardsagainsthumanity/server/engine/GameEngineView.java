package de.rvwbk.group03.cardsagainsthumanity.server.engine;

import de.rvwbk.group03.cardsagainsthumanity.base.ui.View;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Game;

public interface GameEngineView extends View {
	
	public void addGame(Game game);
	
	public void updateGame(Game game);
	
	public void removeGame(Game game);
	
}
