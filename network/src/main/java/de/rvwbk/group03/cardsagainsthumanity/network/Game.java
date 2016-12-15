package de.rvwbk.group03.cardsagainsthumanity.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
	
	private int id;
	private GameState gameState;
	private Player creator;
	private Configuration configuration;
	private int numberOfPlayers;
	private List<Player> players = new ArrayList<>();
	
	public Game(final Configuration configuration) throws NullPointerException {
		this.configuration = Objects.requireNonNull(configuration, "configuration must not be null");
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) throws NullPointerException {
		this.id = id;
	}
	
	public GameState getGameState() {
		return this.gameState;
	}
	
	public void setGameState(final GameState gameState) {
		this.gameState = gameState;
	}
	
	public Player getCreator() {
		return this.creator;
	}
	
	public void setCreator(final Player creator) {
		this.creator = creator;
	}
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	public void setConfiguration(final Configuration configuration) throws NullPointerException {
		this.configuration = Objects.requireNonNull(configuration, "configuration must not be null");
	}
	
	public int getNumberOfPlayers() {
		return this.numberOfPlayers;
	}
	
	public void setNumberOfPlayers(final int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}
	
	public List<Player> getPlayers() {
		return this.players;
	}
	
	public void setPlayers(final List<Player> players) {
		this.players = players;
	}
}
