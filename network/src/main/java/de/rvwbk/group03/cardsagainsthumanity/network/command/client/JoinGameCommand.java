package de.rvwbk.group03.cardsagainsthumanity.network.command.client;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;

public class JoinGameCommand implements LoggedInClientCommand {
	
	private int id;
	private String joinPassword;
	
	
	public JoinGameCommand(final int id) {
		this(id, Strings.EMPTY);
	}
	
	public JoinGameCommand(final int id, final String joinPassword) {
		setId(id);
		setJoinPassword(joinPassword);
	}
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) throws IllegalArgumentException, NullPointerException {
		if (Objects.requireNonNull(id, "id must not be null") < 1) {
			throw new IllegalArgumentException("id must be greater than 0");
		}
		this.id = Objects.requireNonNull(id, "id must not be null");
	}
	
	public String getJoinPassword() {
		return this.joinPassword;
	}
	
	public void setJoinPassword(final String joinPassword) {
		this.joinPassword = Strings.nullToEmpty(joinPassword);
	}
}
