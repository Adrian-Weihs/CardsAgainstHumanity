package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.GamePlayer;

public class PlayerManager {
	
	private Map<Client, GamePlayer> players = new HashMap<>();
	
	
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
}
