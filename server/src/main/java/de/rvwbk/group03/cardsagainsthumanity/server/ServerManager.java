package de.rvwbk.group03.cardsagainsthumanity.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.rvwbk.group03.cardsagainsthumanity.server.engine.GameEngineManager;


public class ServerManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerManager.class);
	
	private static GameEngineManager MANAGER;
	
	public static void main(final String[] args) {
		try {
			LOGGER.info("Starting Server.");
			MANAGER = new GameEngineManager();
			
			MANAGER.showUI();
		} catch (Exception e) {
			LOGGER.error("Unhandled exception", e);
		}
	}
	
	public static GameEngineManager getManager() {
		return MANAGER;
	}
}
