package de.rvwbk.group03.cardsagainsthumanity.client.debug;

import java.io.IOException;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.ui.Presenter;

public class DebugPresenter implements Presenter, RecievedMessageListener {
	
	private final DebugView view;
	
	public DebugPresenter(final DebugView view) {
		this.view = Objects.requireNonNull(view, "view must not be null");
	}
	
	@Override
	public void init() {
		DebugManager.getRecievedMessageManager().addRecievedMessageListener(this);
	}
	
	public void connectButtonClicked() throws IOException {
		DebugManager.connect();
	}
	
	public void disconnectButtonClicked() throws IOException {
		DebugManager.disconnect();
	}
	
	public void sendJsonButtonClicked(final String message) throws NullPointerException {
		DebugManager.getServerCommunication().getWriteCommunication().writeMessage(Objects.requireNonNull(message, "message must not be null"));
	}
	
	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleRecievedMessageEvent(final RecievedMessageEvent event) {
		this.view.addRecievedMessage(event.getRecievedMessage());
	}
}
