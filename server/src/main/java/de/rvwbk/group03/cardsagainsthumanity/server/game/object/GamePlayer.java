package de.rvwbk.group03.cardsagainsthumanity.server.game.object;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.network.PlayerState;
import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;

/**
 * The player class to play a game.
 * 
 * @author Adrian Weihs
 */
public class GamePlayer {
	
	private Client client;
	private Competition game;
	
	private PlayerState playerState = PlayerState.WAITING;
	
	private List<WhiteCard> whiteCards = new ArrayList<>();
	private int score = 0;
	private boolean zar = false;
	
	
	/**
	 * @param client The client represented by this player. Must not be {@code null}.
	 * @param game The game this player belongs to. Must not be {@code null}.
	 * @throws NullPointerException If {@code client} or {@code game} is {@code null}.
	 */
	public GamePlayer(final Client client, final Competition game) throws NullPointerException {
		this.client = Objects.requireNonNull(client, "client must not be null");
		this.game = Objects.requireNonNull(game, "game must not be null");
	}
	
	/**
	 * Adds the points to this player score.
	 * 
	 * @param points The points to add to this player score.
	 */
	public void addScore(final int points) {
		this.score += points;
	}
	
	/**
	 * Returns the score of this player.
	 * 
	 * @return The score of this player.
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Sets the Score.
	 * 
	 * @param score the score.
	 */
	public void setScore(final int score) {
		this.score = score;
	}
	
	/**
	 * Returns the user id of this player.
	 * 
	 * @return The user id of this player.
	 */
	public int getUserId() {
		return this.client.getUser().getId();
	}
	
	/**
	 * Returns the name of this player.
	 * 
	 * @return The name of this player.
	 */
	public String getName() {
		return this.client.getUser().getName();
	}
	
	/**
	 * Returns the white cards of this player.
	 * 
	 * @return The white cards of this player.
	 */
	public List<WhiteCard> getWhiteCards() {
		return this.whiteCards;
	}
	
	/**
	 * Returns the client of this player.
	 * 
	 * @return The client of this player.
	 */
	public Client getClient() {
		return this.client;
	}
	
	public PlayerState getPlayerState() {
		return this.playerState;
	}
	
	public boolean isZar() {
		return this.zar;
	}
	
	
	@Override
	public String toString() {
		return String.format("%s (Id:%d)", getName(), getUserId());
		
	}
}
