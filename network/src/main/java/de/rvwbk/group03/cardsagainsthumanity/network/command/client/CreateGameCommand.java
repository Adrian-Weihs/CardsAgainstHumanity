package de.rvwbk.group03.cardsagainsthumanity.network.command.client;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;

public class CreateGameCommand implements LoggedInClientCommand {
	private String name;
	private Configuration configuration;
	
	
	public String getName() {
		return this.name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	public void setConfiguration(final Configuration configuration) {
		this.configuration = configuration;
	}
}
