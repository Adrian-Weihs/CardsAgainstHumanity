package de.rvwbk.group03.cardsagainsthumanity.components;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.swing.table.AbstractTableModel;

import de.rvwbk.group03.cardsagainsthumanity.network.Game;

public class ClientLobbyModel extends AbstractTableModel {
	
	private List<Game> games = null;
	
	
	public ClientLobbyModel (final Collection<Game> games) {
		if (games instanceof List) {
			this.games = (List<Game>) games;
		} else {
			// TODO: Error Handling
		}
	}
	
	
	@Override
	public int getRowCount() {
		return this.games.size();
	}
	
	@Override
	public int getColumnCount() {
		return 6;
	}
	
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		Game game;
		
		try {
			game = this.games.get(rowIndex);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
		switch (columnIndex) {
			case 0:
				return game.getConfiguration().getName();
			case 1:
				return game.getCreator().getName();
			case 2:
				return game.getNumberOfPlayers() + " / " + game.getConfiguration().getMaxNumberOfPlayers();
			case 3:
				return game.getConfiguration().getCardDeckName();
			case 4:
				return game.getGameState();
			case 5:
				// TODO: Wieder einkommentieren, wenn entsprechende Methode wieder existiert
				return /*game.getConfiguration().isPasswordProtcted() ? "Yes" : */"No";
			default:
				return null;
		}
	}
	
	@Override
	public String getColumnName(final int column) {
		switch (column) {
			case 0:
				return "Name";
			case 1:
				return "Creator";
			case 2:
				return "Players";
			case 3:
				return "Deck";
			case 4:
				return "Game Status";
			case 5:
				return "Password?";
			default:
				return null;
		}
	}
	
	
	public void addGame(final Game game) throws NullPointerException {
		this.games.add(Objects.requireNonNull(game, "game must not be null"));
		fireTableRowsInserted(this.games.size() - 1, this.games.size() - 1);
	}
	
	public void removeGame(final Game game) throws NullPointerException {
		this.games.remove(Objects.requireNonNull(game, "game must not be null"));
		fireTableRowsDeleted(this.games.size(), this.games.size());
	}
	
	public void updateGame(final Game game) throws NullPointerException {
		int row = this.games.indexOf(game);
		
		if (row != -1) {
			fireTableRowsUpdated(row, row);
		}
	}
	
	public Game getGame(final int row) {
		return this.games.get(row);
	}
}

