package de.rvwbk.group03.cardsagainsthumanity.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.Player;

public class PlayerTableModel extends AbstractTableModel {
	
	private List<Player> players = new ArrayList<>();
	private boolean showPoints;
	
	
	public PlayerTableModel(final Game game, final boolean showPoints) {
		this.players = game.getPlayers();
		this.showPoints = showPoints;
	}
	
	
	@Override
	public int getColumnCount() {
		return this.players.size();
	}
	
	@Override
	public int getRowCount() {
		return this.showPoints ? 2 : 1;
	}
	
	@Override
	public Object getValueAt(final int row, final int column) {
		if (column == 0) {
			return this.players.get(row).getName();
		} if (column == 1) {
			return this.players.get(row).getScore();
		}
		return null;
	}
	
	@Override
	public String getColumnName(final int column) {
		switch (column) {
			case 0:
				return "Players";
			case 1:
				return "Score";
			default:
				return null;
		}
	}
}
