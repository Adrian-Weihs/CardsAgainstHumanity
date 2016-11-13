package de.rvwbk.group03.cardsagainsthumanity.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
	
	private int id;
	private String name;
	private GameState gameState;
	private Player creator;
	private Configuration configuration = new Configuration();
	private int numberOfPlayers;
	private List<Player> players = new ArrayList<>();
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) throws NullPointerException {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) throws IllegalArgumentException,
	NullPointerException {
		
		if (Objects.requireNonNull(name, "name must not be null").isEmpty()) {
			throw new IllegalArgumentException("name must not be empty");
		}
		
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
	
	public void setConfiguration(final Configuration configuration) {
		this.configuration = configuration;
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
