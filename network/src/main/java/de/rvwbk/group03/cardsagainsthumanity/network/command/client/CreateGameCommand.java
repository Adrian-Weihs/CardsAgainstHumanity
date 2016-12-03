package de.rvwbk.group03.cardsagainsthumanity.network.command.client;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;

public class CreateGameCommand implements LoggedInClientCommand {
	private Configuration configuration;
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	public void setConfiguration(final Configuration configuration) {
		this.configuration = configuration;
	}
}
