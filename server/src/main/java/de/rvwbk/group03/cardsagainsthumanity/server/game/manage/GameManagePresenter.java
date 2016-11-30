package de.rvwbk.group03.cardsagainsthumanity.server.game.manage;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.ui.Presenter;
import de.rvwbk.group03.cardsagainsthumanity.server.ServerManager;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Game;

public class GameManagePresenter implements Presenter {
	
	private final GameManageView view;
	private Game currentGame;
	
	
	public GameManagePresenter(final GameManageView view) {
		this.view = Objects.requireNonNull(view, "view must not be null");
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void release() {
		
	}
	
	public void manageGame(final int id) {
		this.currentGame = ServerManager.getManager().getGameManager().getGame(id);
		
		if (this.currentGame == null) {
			// TODO: (AW 26.11.2016) Fehlerbehandlung einbauen.
			return;
		}
		
		this.view.setGame(this.currentGame);
	}
}
