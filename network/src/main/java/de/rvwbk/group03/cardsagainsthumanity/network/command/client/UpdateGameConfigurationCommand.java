package de.rvwbk.group03.cardsagainsthumanity.network.command.client;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;


public class UpdateGameConfigurationCommand implements LoggedInClientCommand {
	private int id;
	private Configuration configuration;
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	public void setConfiguration(final Configuration configuration) {
		this.configuration = configuration;
	}
}
