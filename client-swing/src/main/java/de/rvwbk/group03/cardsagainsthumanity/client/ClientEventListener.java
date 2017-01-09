package de.rvwbk.group03.cardsagainsthumanity.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.base.event.AbstractEvent;
import de.rvwbk.group03.cardsagainsthumanity.network.Game;
import de.rvwbk.group03.cardsagainsthumanity.network.command.Command;
import de.rvwbk.group03.cardsagainsthumanity.network.command.CommandHelper;
import de.rvwbk.group03.cardsagainsthumanity.network.command.server.GameListCommand;

public interface ClientEventListener {
	public default void handleClientEvent(final AbstractEvent event) throws NullPointerException {
		Objects.requireNonNull(event, "event must not be null.");
		if (event instanceof ClientManagerEvent) {
			ClientManagerAction action = ((ClientManagerEvent)event).getAction();
			if (action == ClientManagerAction.CONNECT) {
				// requires no response
			} else if (action == ClientManagerAction.DISCONNECT) {
				//				new ErrorView().init();
			} else if (action == ClientManagerAction.LOGIN) {
				//				new GameView().init();
			} else {
				// TODO: ErrorHandling
			}
		} else if (event instanceof LobbyManagerEvent) {
			LobbyManagerAction action = ((LobbyManagerEvent)event).getAction();
			if (action == LobbyManagerAction.GET_GAMES) {
				String jsonString = ClientManager.getServerCommunication().getReadCommunication().getLastMessage();
				Command command = CommandHelper.jsonToCommand(jsonString);
				if (command instanceof GameListCommand) {
					List<Game> games= new ArrayList<>();
					
					for (Game game : ((GameListCommand)command).getGames()) {
						games.add(game);
					}
					
					((LobbyManager)event.getSource()).setGames(games);
				} else {
					// TODO: Error Handling
				}
			} else if (action == LobbyManagerAction.JOIN_GAME) {
				// TODO: GameView
			} else {
				// TODO: Error Handling
			}
		} else if (event instanceof GameManagerEvent) {
			GameManagerAction action = ((GameManagerEvent)event).getAction();
			if (action == GameManagerAction.CZAR_CHOOSE) {
				// TODO: Punktevergabe & Zeigen des Gewinners
			} else if (action == GameManagerAction.GET_BLACK_CARD) {
				// TODO: Draw Black Card from pile and show to all clients
			} else if (action == GameManagerAction.PLAY_WHITE_CARD) {
				// TODO: Submit on cards and flip once all clients cards were played
			} else {
				// TODO: ErrorHandling
			}
		} else if (event instanceof GameManagerEvent) {
			
		} else {
			
		}
	}
}
