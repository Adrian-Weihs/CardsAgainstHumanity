package de.rvwbk.group03.cardsagainsthumanity.data;

public interface WhiteCardHolder {
	
	public void addWhiteCard(WhiteCard card) throws NullPointerException;
	
	public boolean removeWhiteCard(WhiteCard card) throws NullPointerException;
}
