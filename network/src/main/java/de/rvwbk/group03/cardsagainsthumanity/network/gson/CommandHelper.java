package de.rvwbk.group03.cardsagainsthumanity.network.gson;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * This is a helper class for a command.
 * 
 * @author Adrian
 */
public final class CommandHelper {
	
	/**
	 * Returns a {@link GsonBuilder} with the type adapter {@link CommandAdapter}.
	 * 
	 * @return A {@link GsonBuilder} with the type adapter {@link CommandAdapter}.
	 */
	public static GsonBuilder createGsonCommandBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Command.class, new CommandAdapter());
		
		return gsonBuilder;
	}
	
	/**
	 * Returns the json string of the given {@code command}.
	 * 
	 * @param command The command to convert to a json string.
	 * @return The json string of the given {@code command}.
	 */
	public static String commandToJson(final Command command) {
		return createGsonCommandBuilder().create().toJson(command);
	}
	
	/**
	 * Returns the {@link Command} of the given {@code json}.
	 * 
	 * @param json The json string to convert to a {@link Command}.
	 * @return The {@link Command} of the given {@code json}.
	 * @throws JsonSyntaxException If the json string is not a {@link Command}.
	 */
	public static Command jsonToCommand(final String json) throws JsonSyntaxException {
		return createGsonCommandBuilder().create().fromJson(json, Command.class);
	}
	
	
	private CommandHelper() {}
}
