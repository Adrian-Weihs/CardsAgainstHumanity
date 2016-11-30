package de.rvwbk.group03.cardsagainsthumanity.network;

import java.util.Objects;

public class Player {
	public final static Player SERVER = new Player(-1, "<Server>");
	
	private int id;
	private String name;
	private int score;
	private boolean zar;
	private PlayerState playerState = PlayerState.WAITING;
	
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
