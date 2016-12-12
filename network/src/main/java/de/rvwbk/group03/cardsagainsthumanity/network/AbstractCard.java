package de.rvwbk.group03.cardsagainsthumanity.network;

public abstract class AbstractCard implements de.rvwbk.group03.cardsagainsthumanity.data.Card {
	private int id;
	private String text;
	
	
	@Override
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	@Override
	public String getText() {
		return this.text;
	}
	
	public void setText(final String text) {
		this.text = text;
	}
}
