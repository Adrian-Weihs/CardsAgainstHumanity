package de.rvwbk.group03.cardsagainsthumanity.server.engine.ui;

import java.util.Objects;

public enum GameTableColumns {
	ID("#Id:"),
	NAME("Name:"),
	CREATOR("Creator"),
	CURRENT_TO_MAX_PLAYERS("Players:"),
	STATUS("Status:");
	
	private final String columnName;
	
	private GameTableColumns(final String columnName) throws NullPointerException {
		this.columnName = Objects.requireNonNull(columnName, "columnName must not be null");
	}
	
	public String getColumnName() {
		return this.columnName;
	}
}
