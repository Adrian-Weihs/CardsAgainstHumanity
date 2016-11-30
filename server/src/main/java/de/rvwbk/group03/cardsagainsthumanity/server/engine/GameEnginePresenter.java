package de.rvwbk.group03.cardsagainsthumanity.server.engine;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.ui.Presenter;
import de.rvwbk.group03.cardsagainsthumanity.server.ServerManager;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.server.game.event.GameActionEventListener;

public class GameEnginePresenter implements GameActionEventListener, Presenter {
	
	private final GameEngineView view;
	
	public GameEnginePresenter(final GameEngineView view) throws NullPointerException {
		this.view = Objects.requireNonNull(view, "view must not be null");
	}
	
	@Override
	public void init() {
		ServerManager.getManager().getGameManager().addGameActionEventListener(this);
	}
	
	@Override
	public void release() {
		ServerManager.getManager().getGameManager().removeGameActionEventListener(this);
	}
	
	public void handleManageClientsButtonClicked() {
		ServerManager.getManager().getClientManager().showUI();
	}
	
	public void handleCreateGameButtonClicked() {
		ServerManager.getManager().getGameManager().showCreateGameUI();
	}
	
	public void handleManageGameButtonClicked(final int id) {
		ServerManager.getManager().getGameManager().showManageGameUI(id);
	}
	
	@Override
	public void handleGameActionEvent(final GameActionEvent event) throws NullPointerException {
		Objects.requireNonNull(event, "event must not be null");
		
		switch(event.getGameAction()) {
			case GAME_CREATED:
				this.view.addGame(event.getGame());
				break;
			case GAME_REMOVED:
				this.view.removeGame(event.getGame());
				break;
			case GAME_CONFIGURATION_CHANGED:
			case GAME_STATUS_CHANGED:
			case PLAYER_JOIN:
			case PLAYER_LEFT:
				this.view.updateGame(event.getGame());
				break;
			default:
				break;
		}
	}
}
