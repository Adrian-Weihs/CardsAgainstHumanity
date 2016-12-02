package de.rvwbk.group03.cardsagainsthumanity.server.engine;

import de.rvwbk.group03.cardsagainsthumanity.base.ui.View;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;

public interface GameEngineView extends View {
	
	public void addGame(Competition game);
	
	public void updateGame(Competition game);
	
	public void removeGame(Competition game);
	
}
