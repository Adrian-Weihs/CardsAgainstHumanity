package de.rvwbk.group03.cardsagainsthumanity.server.game.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;
import de.rvwbk.group03.cardsagainsthumanity.data.Deck;
import de.rvwbk.group03.cardsagainsthumanity.data.util.DataHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;

public class GameConfiguration {
	
	private String name = Strings.EMPTY;
	private String joinPassword = Strings.EMPTY;
	private Deck cardDeck = DataHelper.getDeck(0);
	private int maxNumberOfPlayer = 6;
	private List<WinningCondition> winningConditions = new ArrayList<>();
	
	public GameConfiguration() {
		// TODO Auto-generated constructor stub
	}
	
	public int getMaxNumberOfPlayers() {
		return this.maxNumberOfPlayer;
	}
	
	public void setMaxNumberOfPlayer(final int maxNumberOfPlayer) throws IllegalArgumentException {
		if (maxNumberOfPlayer < Configuration.MIN_PLAYERS) {
			throw new IllegalArgumentException("maxNumberOfPlayer must be greater or equal " + Configuration.MIN_PLAYERS + "but was " + maxNumberOfPlayer);
		}
		
		if (maxNumberOfPlayer > Configuration.MAX_PLAYERS) {
			throw new IllegalArgumentException("maxNumberOfPlayer must be less or equal " + Configuration.MAX_PLAYERS + "but was " + maxNumberOfPlayer);
		}
		
		this.maxNumberOfPlayer = maxNumberOfPlayer;
	}
	
	public String getCardDeckName() {
		return this.cardDeck.getName();
	}
	
	public String getJoinPassword() {
		return this.joinPassword;
	}
	
	public void setJoinPassword(final String joinPassword) {
		this.joinPassword = Strings.nullToEmpty(joinPassword);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) throws IllegalArgumentException, NullPointerException {
		this.name = Strings.requireNonNullAndNonEmpty(name, "name");
	}
	
	public List<WinningCondition> getWinningConditions() {
		return this.winningConditions;
	}
	
	public void addWinningCondition(final WinningCondition winningCondition) throws NullPointerException {
		Objects.requireNonNull(winningCondition, "winningCondition must not be null");
		
		this.winningConditions.add(winningCondition);
	}
	
	public boolean removeWinningCondition(final WinningCondition winningCondition) throws NullPointerException {
		Objects.requireNonNull(winningCondition, "winningCondition must not be null");
		
		return this.winningConditions.remove(winningCondition);
	}
	
	public Deck getCardDeck() {
		return this.cardDeck;
	}
	
	public void setCardDeck(final Deck deck) {
		this.cardDeck = Objects.requireNonNull(deck, "deck must not be null");
	}
}
