package de.rvwbk.group03.cardsagainsthumanity.network.command.client;

public class LeaveGameCommand implements LoggedInClientCommand {
	private int id;
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
}
