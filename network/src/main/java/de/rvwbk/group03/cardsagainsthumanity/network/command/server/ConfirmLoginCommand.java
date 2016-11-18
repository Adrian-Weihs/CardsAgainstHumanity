package de.rvwbk.group03.cardsagainsthumanity.network.command.server;

public class ConfirmLoginCommand implements ServerCommand {
	private int id;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
}
