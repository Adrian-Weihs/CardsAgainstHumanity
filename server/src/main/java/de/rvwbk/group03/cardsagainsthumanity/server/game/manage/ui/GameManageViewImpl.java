package de.rvwbk.group03.cardsagainsthumanity.server.game.manage.ui;

import javax.swing.JFrame;

import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;
import de.rvwbk.group03.cardsagainsthumanity.server.game.manage.GameManagePresenter;
import de.rvwbk.group03.cardsagainsthumanity.server.game.manage.GameManageView;

public class GameManageViewImpl extends JFrame implements GameManageView {
	
	private final GameManagePresenter presenter;
	
	public GameManageViewImpl() {
		this.presenter = new GameManagePresenter(this);
		
		init();
		this.presenter.init();
	}
	
	private void init() {
		setTitle("Manage Game");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(460, 450);
	}
	
	public void manageGame(final int id) {
		this.presenter.manageGame(id);
	}
	
	@Override
	public void release() {
		this.presenter.release();
	}
	
	@Override
	public void setGame(final Competition game) {
		// TODO Auto-generated method stub
		
	}
}
