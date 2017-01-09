package de.rvwbk.group03.cardsagainsthumanity.client.swing;

import java.io.IOException;

import de.rvwbk.group03.cardsagainsthumanity.view.LoginView;

public class SwingClientManager {
	
	private static AbstractManager manager = new AbstractManager();
	
	
	public static void main(final String[] args) {
		try {
			manager.getClientManager().connect();
			new LoginView(manager).init(false, null);
		} catch (IOException e) {
			new LoginView(manager).init(true, "Konnte keine Verbindung zum Server aufbauen.");
		}
	}
	
	private SwingClientManager() {
		// prevent instantiation
	}
}
