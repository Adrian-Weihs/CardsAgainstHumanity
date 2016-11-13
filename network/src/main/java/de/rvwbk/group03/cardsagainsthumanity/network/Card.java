package de.rvwbk.group03.cardsagainsthumanity.network;

public abstract class Card {
	private int id;
	private String text;
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(final String text) {
		this.text = text;
	}
}
