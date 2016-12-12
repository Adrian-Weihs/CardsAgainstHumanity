package de.rvwbk.group03.cardsagainsthumanity.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.data.WhiteCard;

public class Player {
	public final static Player SERVER = new Player(-1, "<Server>");
	
	private int id;
	private String name;
	private int score;
	private boolean zar;
	private PlayerState playerState = PlayerState.WAITING;
	private List<WhiteCard> whiteCards = new ArrayList<>();
	
	public Player() {
		
	}
	
	private Player(final int id, final String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) {
		if (Objects.requireNonNull(name, "name must not be null").isEmpty()) {
			throw new IllegalArgumentException("name must not be empty");
		}
		
		this.name = name;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public List<WhiteCard> getWhiteCards() {
		return this.whiteCards;
	}
	
	public void setWhiteCards(final List<WhiteCard> cards) throws NullPointerException {
		this.whiteCards = new ArrayList<>(Objects.requireNonNull(cards, "cards must not be null"));
	}
	
	public void setScore(final int score) {
		this.score = score;
	}
	
	public boolean isZar() {
		return this.zar;
	}
	
	public void setZar(final boolean zar) {
		this.zar = zar;
	}
	
	public PlayerState getPlayerState() {
		return this.playerState;
	}
	
	public void setPlayerState(final PlayerState playerState) {
		this.playerState = playerState;
	}
}
