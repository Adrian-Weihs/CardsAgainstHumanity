package de.rvwbk.group03.cardsagainsthumanity.server.communication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.server.game.Game;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.Player;

public class ServerCommandHelper {
	public static de.rvwbk.group03.cardsagainsthumanity.network.Game toNetworkGame(final Game game, final boolean sendPlayers) throws NullPointerException {
		Objects.requireNonNull(game, "game must not be null");
		
		de.rvwbk.group03.cardsagainsthumanity.network.Game result = new de.rvwbk.group03.cardsagainsthumanity.network.Game();
		
		result.setConfiguration(game.getConfiguration());
		
		if(game.getCreator() == null) {
			result.setCreator(de.rvwbk.group03.cardsagainsthumanity.network.Player.SERVER);
		} else {
			result.setCreator(toNetworkPlayer(game.getCreator()));
		}
		
		result.setGameState(game.getGameState());
		result.setId(game.getId());
		result.setName(game.getConfiguration().getName());
		result.setNumberOfPlayers(game.getPlayerManager().getPlayers().size());
		if(sendPlayers) {
			result.setPlayers(toNetworkPlayers(game.getPlayerManager().getPlayers()));
		}
		
		return result;
	}
	
	public static de.rvwbk.group03.cardsagainsthumanity.network.Player toNetworkPlayer(final Player player) throws NullPointerException {
		Objects.requireNonNull(player, "player must not be null");
		
		de.rvwbk.group03.cardsagainsthumanity.network.Player result = new de.rvwbk.group03.cardsagainsthumanity.network.Player();
		
		result.setId(player.getUserId());
		result.setName(player.getName());
		result.setPlayerState(player.getPlayerState());
		result.setScore(player.getScore());
		result.setZar(player.isZar());
		
		return result;
	}
	
	public static List<de.rvwbk.group03.cardsagainsthumanity.network.Player> toNetworkPlayers(final Collection<Player> players) throws NullPointerException {
		Objects.requireNonNull(players, "players must not be null");
		
		List<de.rvwbk.group03.cardsagainsthumanity.network.Player>  result = new ArrayList<>();
		players.forEach(player -> result.add(toNetworkPlayer(player)));
		
		return result;
	}
}
