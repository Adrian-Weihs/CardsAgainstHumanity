package de.rvwbk.group03.cardsagainsthumanity.server.game.create.ui;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.rvwbk.group03.cardsagainsthumanity.server.game.create.GameCreatePresenter;
import de.rvwbk.group03.cardsagainsthumanity.server.game.create.GameCreateView;

public class GameCreateViewImpl extends JFrame implements GameCreateView {
	
	private final GameCreatePresenter presenter;
	
	private JButton createButton = new JButton("Create");
	
	public GameCreateViewImpl() {
		this.presenter = new GameCreatePresenter(this);
		
		init();
		this.presenter.init();
	}
	
	private void init() {
		setTitle("Create Game");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(900, 450);
		
		this.createButton.addActionListener(event -> createButtonClicked(event));
		getContentPane().add(this.createButton);
	}
	
	private void createButtonClicked(final ActionEvent event) {
		this.presenter.handleCreateButtonClicked();
	}
	
	@Override
	public void release() {
		this.presenter.release();
	}
}
