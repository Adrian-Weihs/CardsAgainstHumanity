package de.rvwbk.group03.cardsagainsthumanity.server.engine;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.base.ui.Presenter;
import de.rvwbk.group03.cardsagainsthumanity.server.ServerManager;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Game;

public class GameEnginePresenter implements ObjectActionEventListener<Game>, Presenter {
	
	private final GameEngineView view;
	
	public GameEnginePresenter(final GameEngineView view) throws NullPointerException {
		this.view = Objects.requireNonNull(view, "view must not be null");
	}
	
	@Override
	public void init() {
		ServerManager.getManager().getGameManager().addObjectActionEventListener(this);
	}
	
	@Override
	public void release() {
		ServerManager.getManager().getGameManager().removeObjectActionEventListener(this);
	}
	
	public void handleManageClientsButtonClicked() {
		ServerManager.getManager().getClientManager().showUI();
	}
	
	public void handleCreateGameButtonClicked() {
		ServerManager.getManager().getGameManager().showCreateGameUI();
		
	}
	
	@Override
	public void handleObjectActionEvent(final ObjectActionEvent<Game> objectActionEvent) throws NullPointerException {
		Objects.requireNonNull(objectActionEvent, "objectActionEvent must not be null");
		
		switch(objectActionEvent.getAction()) {
			case ObjectActionEvent.ACTION_ADD:
				this.view.addGame(objectActionEvent.getObject());
				objectActionEvent.getObject().addObjectActionEventListener(this);
				break;
			case ObjectActionEvent.ACTION_REMOVE:
				this.view.removeGame(objectActionEvent.getObject());
				objectActionEvent.getObject().removeObjectActionEventListener(this);
				break;
			case ObjectActionEvent.ACTION_JOIN:
			case ObjectActionEvent.ACTION_LEAVE:
				this.view.updateGame(objectActionEvent.getObject());
				break;
			default:
				break;
		}
	}
}
