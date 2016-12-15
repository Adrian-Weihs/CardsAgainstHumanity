package de.rvwbk.group03.cardsagainsthumanity.network.command.client;

import de.rvwbk.group03.cardsagainsthumanity.base.annotation.InFutureUsed;
import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;

public class LoginCommand implements ClientCommand {
	
	private String name;
	private String password;
	
	
	public LoginCommand(final String name) throws IllegalArgumentException, NullPointerException {
		this(name, Strings.EMPTY);
	}
	
	public LoginCommand(final String name, final String password) throws IllegalArgumentException, NullPointerException {
		setName(name);
		setPassword(password);
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) throws IllegalArgumentException, NullPointerException {
		this.name = Strings.requireNonNullAndNonEmpty(name, "name");
	}
	
	@InFutureUsed
	public String getPassword() {
		return this.password;
	}
	
	@InFutureUsed
	public void setPassword(final String password) {
		this.password = Strings.nullToEmpty(password);
	}
}
