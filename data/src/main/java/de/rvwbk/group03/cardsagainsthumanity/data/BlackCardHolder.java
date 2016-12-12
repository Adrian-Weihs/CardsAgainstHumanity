package de.rvwbk.group03.cardsagainsthumanity.data;

public interface BlackCardHolder {
	
	public void addBlackCard(BlackCard card) throws NullPointerException;
	
	public boolean removeBlackCard(BlackCard card) throws NullPointerException;
}
