package de.rvwbk.group03.cardsagainsthumanity.client;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.ui.Presenter;

public class ClientPresenter implements Presenter, RecievedMessageListener {
	
	private final ClientView view;
	
	public ClientPresenter(final ClientView view) {
		this.view = Objects.requireNonNull(view, "view must not be null");
	}
	
	@Override
	public void init() {
		ClientManager.getRecievedMessageManager().addRecievedMessageListener(this);
	}
	
	@Override
	public void release() {
		
	}
	
	@Override
	public void handleRecievedMessageEvent(final RecievedMessageEvent event) {
		this.view.addRecievedMessage(event.getRecievedMessage());
	}
}
