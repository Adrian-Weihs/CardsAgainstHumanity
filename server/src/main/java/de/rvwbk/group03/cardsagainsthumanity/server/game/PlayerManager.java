package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.network.PlayerState;
import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.GamePlayer;

public class PlayerManager {
	
	private final Competition game;
	private Map<Client, GamePlayer> players = new LinkedHashMap<>();
	private List<GamePlayer> playerOrder = new ArrayList<>();
	private int currentZar = 0;
	
	public PlayerManager(final Competition game) throws NullPointerException {
		this.game = Objects.requireNonNull(game, "game must not be null");
	}
	
	
	public GamePlayer getPlayer(final Client client) throws NullPointerException {
		Objects.requireNonNull(client, "client must not be null");
		return this.players.get(client);
	}
	
	public Collection<GamePlayer> getPlayers() {
		return this.players.values();
	}
	
	public void addPlayer(final GamePlayer player) throws NullPointerException {
		Objects.requireNonNull(player, "player must not be null");
		
		this.players.put(player.getClient(), player);
	}
	
	public void removePlayer(final GamePlayer player) throws NullPointerException {
		Objects.requireNonNull(player, "player must not be null");
		
		this.players.remove(player.getClient());
	}
	
	protected void prepareGameStart() {
		
		this.playerOrder.addAll(getPlayers());
		Collections.shuffle(this.playerOrder);
		this.playerOrder.get(this.currentZar).setZar(true);
		
		setupPlayerState();
	}
	
	private void setupPlayerState() {
		for(GamePlayer player : this.playerOrder) {
			if(PlayerState.WAITING.equals(player.getPlayerState()) && !player.isZar()) {
				player.setPlayerState(PlayerState.PLAYING);
			}
		}
	}
	
	protected void nextRound() throws IllegalStateException {
		if (this.playerOrder.isEmpty()) {
			throw new IllegalStateException("The player manager was not prepared for a game start so there is no nect round.");
		}
		
		this.playerOrder.get(this.currentZar).setZar(false);
		
		if (this.currentZar < this.playerOrder.size() - 1) {
			this.currentZar++;
		} else {
			this.currentZar = 0;
		}
		
		this.playerOrder.get(this.currentZar).setZar(true);
		
		setupPlayerState();
	}
	
	public GamePlayer getZar() throws IllegalStateException {
		if (this.playerOrder.isEmpty()) {
			throw new IllegalStateException("The player manager was not prepared for a game start so there is no zar.");
		}
		
		return this.playerOrder.get(this.currentZar);
	}
	
	public List<GamePlayer> getPlayingPlayers() throws IllegalStateException {
		if (this.playerOrder.isEmpty()) {
			throw new IllegalStateException("The player manager was not prepared for a game start so there are no playing players.");
		}
		
		List<GamePlayer> result = new ArrayList<>(this.playerOrder);
		result.remove(this.currentZar);
		
		return result;
	}
}
