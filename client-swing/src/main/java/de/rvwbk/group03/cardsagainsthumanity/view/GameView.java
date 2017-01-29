package de.rvwbk.group03.cardsagainsthumanity.view;

import de.rvwbk.group03.cardsagainsthumanity.client.swing.AbstractManager;

public class GameView {
	
	private AbstractManager manager;
	
	public GameView(final AbstractManager manager) {
		this.manager = manager;
		init();
	}
	
	private void init() {
		// TODO: implementieren
		
		/*
		 * Status holen. Wenn noch nicht gestartet --> PreGameView, mit disablten Buttons
		 * Ansonsten normale View
		 */
	}
	
}
