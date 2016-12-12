package de.rvwbk.group03.cardsagainsthumanity.server.communication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.Player;
import de.rvwbk.group03.cardsagainsthumanity.server.game.Competition;
import de.rvwbk.group03.cardsagainsthumanity.server.game.configuration.GameConfiguration;
import de.rvwbk.group03.cardsagainsthumanity.server.game.object.GamePlayer;

public class ServerCommandHelper {
	
	public static Game toGame(final Competition game, final boolean sendPlayers, final boolean sendPlayersCard, final boolean sendPassword) throws NullPointerException {
		Objects.requireNonNull(game, "game must not be null");
		
		Game result = new Game();
		
		result.setConfiguration(toConfiguration(game.getConfiguration(), sendPassword));
		
		if (game.getCreator() == null) {
			result.setCreator(Player.SERVER);
		} else {
			result.setCreator(toPlayer(game.getCreator(), sendPlayersCard));
		}
		
		result.setGameState(game.getGameState());
		result.setId(game.getId());
		result.setNumberOfPlayers(game.getPlayerManager().getPlayers().size());
		if (sendPlayers) {
			result.setPlayers(toPlayers(game.getPlayerManager().getPlayers(), sendPlayersCard));
		}
		
		return result;
	}
	
	public static List<Game> toGames(final Collection<Competition> games, final boolean sendPlayers, final boolean sendPlayersCard, final boolean sendPassword) throws NullPointerException {
		Objects.requireNonNull(games, "games must not be null");
		
		List<Game> result = new ArrayList<>();
		games.forEach(game -> result.add(toGame(game, sendPlayers, sendPlayersCard, sendPassword)));
		
		return result;
	}
	
	public static Player toPlayer(final GamePlayer player, final boolean sendCards) throws NullPointerException {
		Objects.requireNonNull(player, "player must not be null");
		
		Player result = new Player();
		
		result.setId(player.getUserId());
		result.setName(player.getName());
		result.setPlayerState(player.getPlayerState());
		if(sendCards) {
			result.setWhiteCards(player.getHand().getWhiteCards());
		}
		result.setScore(player.getScore());
		result.setZar(player.isZar());
		
		return result;
	}
	
	public static List<Player> toPlayers(final Collection<GamePlayer> players, final boolean sendCards) throws NullPointerException {
		Objects.requireNonNull(players, "players must not be null");
		
		List<Player> result = new ArrayList<>();
		players.forEach(player -> result.add(toPlayer(player, sendCards)));
		
		return result;
	}
	
	public static Configuration toConfiguration(final GameConfiguration configuration, final boolean sendPassword) throws NullPointerException {
		Objects.requireNonNull(configuration, "configuration must not be null");
		
		Configuration result = new Configuration();
		
		result.setCardDeckName(configuration.getCardDeckName());
		if (sendPassword) {
			result.setJoinPassword(configuration.getJoinPassword());
		}
		result.setPasswordProtected(!configuration.getJoinPassword().isEmpty());
		result.setMaxNumberOfPlayer(configuration.getMaxNumberOfPlayers());
		result.setName(configuration.getName());
		
		return result;
	}
}
