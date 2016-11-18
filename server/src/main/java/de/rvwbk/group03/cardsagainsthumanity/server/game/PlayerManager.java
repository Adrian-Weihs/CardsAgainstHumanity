package de.rvwbk.group03.cardsagainsthumanity.server.game;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.server.client.Client;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.Player;

public class PlayerManager {
	
	private Map<Client, Player> players = new HashMap<>();
	
	
	public Player getPlayer(final Client client) throws NullPointerException {
		Objects.requireNonNull(client, "client must not be null");
		return this.players.get(client);
	}
	
	public Collection<Player> getPlayers() {
		return this.players.values();
	}
	
	public void addPlayer(final Player player) throws NullPointerException {
		Objects.requireNonNull(player, "player must not be null");
		
		this.players.put(player.getClient(), player);
	}
	
	public void removePlayer(final Player player) throws NullPointerException {
		Objects.requireNonNull(player, "player must not be null");
		
		this.players.remove(player.getClient());
	}
}
