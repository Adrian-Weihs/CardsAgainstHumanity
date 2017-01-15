package de.rvwbk.group03.cardsagainsthumanity.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PlayedCardsTableModel extends AbstractTableModel {
	
	private List<String> whiteCardsText = new ArrayList<>();
	
	
	public PlayedCardsTableModel(final List<String> whiteCardsText) {
		this.whiteCardsText = whiteCardsText;
	}
	
	@Override
	public int getColumnCount() {
		return 1;
	}
	
	@Override
	public int getRowCount() {
		return this.whiteCardsText.size();
	}
	
	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		try {
			return this.whiteCardsText.get(rowIndex);
		} catch (IndexOutOfBoundsException e) {
			// TODO: Exception loggen
			return null;
		}
	}
	
	
	public List<String> getWhiteCardsText() {
		return this.whiteCardsText;
	}
	
	public void setWhiteCardsText(final List<String> whiteCardsText) {
		this.whiteCardsText = whiteCardsText;
	}
}
