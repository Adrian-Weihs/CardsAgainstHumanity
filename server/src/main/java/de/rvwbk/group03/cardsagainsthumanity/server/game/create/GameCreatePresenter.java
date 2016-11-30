package de.rvwbk.group03.cardsagainsthumanity.server.game.create;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.ui.Presenter;
import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.server.ServerManager;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Game;

public class GameCreatePresenter implements Presenter {
	
	private final GameCreateView view;
	
	
	public GameCreatePresenter(final GameCreateView view) throws NullPointerException {
		this.view = Objects.requireNonNull(view, "view must not be null");
	}
	
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	public void handleCreateButtonClicked(final Configuration configuration) {
		Game game = new Game(configuration);
		
		ServerManager.getManager().getGameManager().addGame(game);
	}
	
	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
}
