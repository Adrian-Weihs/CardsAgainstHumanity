package de.rvwbk.group03.cardsagainsthumanity.network.command.client;

import java.util.Objects;

public class StartGameCommand implements LoggedInClientCommand {
	
	private int id;
	
	
	public StartGameCommand(final int id) throws NullPointerException {
		setId(id);
	}
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) throws NullPointerException {
		this.id = Objects.requireNonNull(id, "id must not be null");
	}
}
