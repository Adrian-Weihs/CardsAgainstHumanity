package de.rvwbk.group03.cardsagainsthumanity.network.command;

import de.rvwbk.group03.cardsagainsthumanity.network.gson.Command;

public class JoinGameCommand implements Command {
	private int id;
	private String joinPassword;
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public String getJoinPassword() {
		return this.joinPassword;
	}
	
	public void setJoinPassword(final String joinPassword) {
		this.joinPassword = joinPassword;
	}
}
