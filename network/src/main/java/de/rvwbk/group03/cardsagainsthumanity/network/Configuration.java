package de.rvwbk.group03.cardsagainsthumanity.network;

import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;

public class Configuration {
	private String name = Strings.EMPTY;
	private String joinPassword = Strings.EMPTY;
	private String cardDeckName = "default";
	private int maxNumberOfPlayer = 6;
	
	
	public int getMaxNumberOfPlayers() {
		return this.maxNumberOfPlayer;
	}
	
	public void setMaxNumberOfPlayer(final int maxNumberOfPlayer) {
		this.maxNumberOfPlayer = maxNumberOfPlayer;
	}
	
	public String getCardDeckName() {
		return this.cardDeckName;
	}
	
	public void setCardDeckName(final String cardDeckName) throws NullPointerException {
		this.cardDeckName = cardDeckName;
	}
	
	public String getJoinPassword() {
		return this.joinPassword;
	}
	
	public void setJoinPassword(final String joinPassword) {
		this.joinPassword = joinPassword;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
}
