package de.rvwbk.group03.cardsagainsthumanity.view;

import javax.swing.JFrame;

import de.rvwbk.group03.cardsagainsthumanity.client.swing.AbstractManager;

public class GameView extends JFrame {
	
	private AbstractManager manager;
	
	public GameView(final AbstractManager manager) {
		this.manager = manager;
	}
	
}
