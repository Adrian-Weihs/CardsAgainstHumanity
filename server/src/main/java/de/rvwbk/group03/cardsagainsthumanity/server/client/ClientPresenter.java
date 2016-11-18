package de.rvwbk.group03.cardsagainsthumanity.server.client;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.base.ui.Presenter;
import de.rvwbk.group03.cardsagainsthumanity.network.DisconnectReason;
import de.rvwbk.group03.cardsagainsthumanity.server.ServerManager;

public class ClientPresenter implements ObjectActionEventListener<Client>, Presenter {
	
	private final ClientView view;
	private final ClientManager clientManager;
	
	
	public ClientPresenter(final ClientView view) {
		this.view = Objects.requireNonNull(view, "view must not be null");
		this.clientManager = ServerManager.getManager().getClientManager();
	}
	
	@Override
	public void init() {
		this.clientManager.addObjectActionEventListener(this);
		this.view.setClients(this.clientManager.getClients());
	}
	
	public void disconnectButtonClicked(final Client client) {
		Objects.requireNonNull(client, "client must not be null").disconnect(DisconnectReason.DISAGREEMENT);
	}
	
	@Override
	public void release() {
		this.clientManager.removeObjectActionEventListener(this);
	}
	
	@Override
	public void handleObjectActionEvent(final ObjectActionEvent<Client> objectActionEvent) throws NullPointerException {
		Objects.requireNonNull(objectActionEvent, "objectActionEvent must not be null");
		
		switch(objectActionEvent.getAction()) {
			case ObjectActionEvent.ACTION_ADD:
				this.view.addClient(objectActionEvent.getObject());
				break;
			case ObjectActionEvent.ACTION_REMOVE:
				this.view.removeClient(objectActionEvent.getObject());
				break;
			case ObjectActionEvent.ACTION_LOGIN:
				this.view.updateClient(objectActionEvent.getObject());
				break;
			default:
				break;
		}
	}
}
