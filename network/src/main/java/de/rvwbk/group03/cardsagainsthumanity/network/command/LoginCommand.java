package de.rvwbk.group03.cardsagainsthumanity.network.command;

import de.rvwbk.group03.cardsagainsthumanity.base.annotation.InFutureUsed;
import de.rvwbk.group03.cardsagainsthumanity.network.gson.Command;

public class LoginCommand implements Command {
	private String name;
	private String password;
	
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	@InFutureUsed
	public String getPassword() {
		return this.password;
	}
	
	@InFutureUsed
	public void setPassword(final String password) {
		this.password = password;
	}
}
