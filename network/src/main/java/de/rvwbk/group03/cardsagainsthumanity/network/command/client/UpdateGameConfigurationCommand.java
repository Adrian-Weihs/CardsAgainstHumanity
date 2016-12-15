package de.rvwbk.group03.cardsagainsthumanity.network.command.client;

import java.util.Objects;

import de.rvwbk.group03.cardsagainsthumanity.network.Configuration;


public class UpdateGameConfigurationCommand implements LoggedInClientCommand {
	
	private int id;
	private Configuration configuration;
	
	
	public UpdateGameConfigurationCommand(final int id, final Configuration configuration) throws NullPointerException {
		setId(id);
		setConfiguration(configuration);
	}
	
	
	public int getId() {
		return this.id;
	}
	
	public void setId(final int id) throws NullPointerException {
		this.id = Objects.requireNonNull(id, "id must not be null");
	}
	
	public Configuration getConfiguration() {
		return this.configuration;
	}
	
	public void setConfiguration(final Configuration configuration) throws NullPointerException {
		this.configuration = Objects.requireNonNull(configuration, "configuration must not be null");
	}
}
