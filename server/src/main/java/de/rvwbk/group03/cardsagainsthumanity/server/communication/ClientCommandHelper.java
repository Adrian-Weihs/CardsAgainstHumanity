package de.rvwbk.group03.cardsagainsthumanity.server.communication;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;
import de.rvwbk.group03.cardsagainsthumanity.server.game.configuration.GameConfiguration;
import de.rvwbk.group03.cardsagainsthumanity.server.game.configuration.OutOfCardsWinningConndition;

public class ClientCommandHelper {
	
	public static GameConfiguration toGameConfiguration(final Configuration configuration) throws IllegalArgumentException, NullPointerException {
		GameConfiguration result = new GameConfiguration();
		
		result.setCardDeckName(configuration.getCardDeckName());
		result.setJoinPassword(configuration.getJoinPassword());
		result.setMaxNumberOfPlayer(configuration.getMaxNumberOfPlayers());
		result.setName(configuration.getName());
		
		result.addWinningCondition(new OutOfCardsWinningConndition());
		
		return result;
	}
	
}
