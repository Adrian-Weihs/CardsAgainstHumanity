package de.rvwbk.group03.cardsagainsthumanity.server.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventListener;
import de.rvwbk.group03.cardsagainsthumanity.base.event.ObjectActionEvent.ObjectActionEventHandler;
import de.rvwbk.group03.cardsagainsthumanity.server.client.ui.ClientViewImpl;
import de.rvwbk.group03.cardsagainsthumanity.server.communication.ClientSocketHandler;

public class ClientManager implements ObjectActionEventListener<Client>, ObjectActionEventHandler<Client> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientManager.class);
	
	private final ClientSocketHandler clientHandler = new ClientSocketHandler();
	private final Collection<ObjectActionEventListener<Client>> listeners = new CopyOnWriteArrayList<>();
	
	private Collection<Client> clients = new ArrayList<>();
	
	
	public ClientManager() throws IOException {
		Thread clientHandlerThread = new Thread(this.clientHandler);
		clientHandlerThread.start();
	}
	
	public Collection<Client> getClients() {
		return this.clients;
	}
	
	public void showUI() {
		ClientViewImpl view = new ClientViewImpl();
		view.setVisible(true);
	}
	
	public void addClient(final Client client) throws NullPointerException {
		this.clients.add(Objects.requireNonNull(client, "client must not be null"));
		ObjectActionEvent<Client> event = new ObjectActionEvent<>(this, client, ObjectActionEvent.ACTION_ADD);
		this.listeners.forEach(listener -> listener.handleObjectActionEvent(event));
	}
	
	public void removeClient(final Client client) throws NullPointerException {
		this.clients.remove(Objects.requireNonNull(client, "client must not be null"));
		ObjectActionEvent<Client> event = new ObjectActionEvent<>(this, client, ObjectActionEvent.ACTION_REMOVE);
		this.listeners.forEach(listener -> listener.handleObjectActionEvent(event));
	}
	
	public void loginClient(final Client client) {
		Objects.requireNonNull(client, "client must not be null");
		
		if (this.clients.contains(client)) {
			ObjectActionEvent<Client> event = new ObjectActionEvent<>(this, client, ObjectActionEvent.ACTION_LOGIN);
			this.listeners.forEach(listener -> listener.handleObjectActionEvent(event));
		}
	}
	
	public void disconnectClient(final Client client) throws NullPointerException {
		Objects.requireNonNull(client, "client must not be null");
		
		try {
			client.getClientCommunication().getSocket().close();
		} catch (IOException e) {
			LOGGER.error("Could not close the client socket.", e);
		}
		
		removeClient(client);
	}
	
	@Override
	public void handleObjectActionEvent(final ObjectActionEvent<Client> objectActionEvent) throws NullPointerException {
		Objects.requireNonNull(objectActionEvent, "objectActionEvent must not be null");
		switch (objectActionEvent.getAction()) {
			case ObjectActionEvent.ACTION_ADD:
				addClient(objectActionEvent.getObject());
				break;
			case ObjectActionEvent.ACTION_REMOVE:
				removeClient(objectActionEvent.getObject());
				break;
			case ObjectActionEvent.ACTION_DISCONNECT:
				disconnectClient(objectActionEvent.getObject());
				break;
			default:
				break;
		}
	}
	
	@Override
	public void addObjectActionEventListener(final ObjectActionEventListener<Client> actionEventListener) throws NullPointerException {
		this.listeners.add(Objects.requireNonNull(actionEventListener, "actionEventListener must not be null"));
	}
	
	@Override
	public boolean removeObjectActionEventListener(final ObjectActionEventListener<Client> actionEventListener) throws NullPointerException {
		return this.listeners.remove(Objects.requireNonNull(actionEventListener, "actionEventListener must not be null"));
	}
}
