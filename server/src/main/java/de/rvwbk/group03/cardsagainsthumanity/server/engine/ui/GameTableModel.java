package de.rvwbk.group03.cardsagainsthumanity.server.engine.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.table.AbstractTableModel;

import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;

public class GameTableModel extends AbstractTableModel {
	
	private List<Competition> games = new ArrayList<>();
	
	
	@Override
	public int getRowCount() {
		return this.games.size();
	}
	
	@Override
	public int getColumnCount() {
		return GameTableColumns.values().length;
	}
	
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		Competition game = this.games.get(rowIndex);
		switch (columnIndex) {
			case 0:
				return game.getId();
			case 1:
				return game.getName();
			case 2:
				if (game.getCreator() != null) {
					return game.getCreator().getName();
				}
				return "<Server>";
			case 3:
				return game.getPlayerManager().getPlayers().size() + "/" + game.getConfiguration().getMaxNumberOfPlayers();
			case 4:
				return game.getGameState();
			default:
				return null;
		}
	}
	
	@Override
	public String getColumnName(final int column) {
		GameTableColumns[] columns = GameTableColumns.values();
		
		if (column <= GameTableColumns.values().length) {
			return columns[column].getColumnName();
		}
		
		return "<Unkown>";
	}
	
	public void addGame(final Competition game) throws NullPointerException {
		this.games.add(Objects.requireNonNull(game, "game must not be null"));
		fireTableRowsInserted(this.games.size() - 1, this.games.size() - 1);
	}
	
	public void removeGame(final Competition game) throws NullPointerException {
		this.games.remove(Objects.requireNonNull(game, "game must not be null"));
		fireTableRowsDeleted(this.games.size(), this.games.size());
	}
	
	public void updateGame(final Competition game) throws NullPointerException {
		int row = this.games.indexOf(game);
		
		if (row != -1) {
			fireTableRowsUpdated(row, row);
		}
	}
	
	public Competition getGame(final int row) {
		return this.games.get(row);
	}
}
