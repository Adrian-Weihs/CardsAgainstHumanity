package de.rvwbk.group03.cardsagainsthumanity.server.client;

import java.util.Collection;

import de.rvwbk.group03.cardsagainsthumanity.base.ui.View;

public interface ClientView extends View {
	
	public void setClients(Collection<Client> clients) throws NullPointerException;
	
	public void addClient(Client client) throws NullPointerException;
	
	public void updateClient(Client client) throws NullPointerException;
	
	public void removeClient(Client client) throws NullPointerException;
}
