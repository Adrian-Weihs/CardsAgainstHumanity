package de.rvwbk.group03.cardsagainsthumanity.network.command;

import de.rvwbk.group03.cardsagainsthumanity.network.gson.Command;

public class ConfirmLoginCommand implements Command {
	private int id;
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
}
