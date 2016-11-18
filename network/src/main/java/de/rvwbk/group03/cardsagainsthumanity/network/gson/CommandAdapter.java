package de.rvwbk.group03.cardsagainsthumanity.network.gson;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import de.rvwbk.group03.cardsagainsthumanity.network.command.Command;

/**
 * This {@link CommandAdapter} will be used to serialize and deserialize {@link Command}s.
 * 
 * @author Adrian
 */
public class CommandAdapter implements JsonDeserializer<Command>, JsonSerializer<Command> {
	private static final String PROP_NAME = "myClass";
	
	@Override
	public JsonElement serialize(final Command src, final Type typeOfT, final JsonSerializationContext context) {
		Gson gson = new GsonBuilder().serializeNulls().create();
		JsonElement element = gson.toJsonTree(src);
		JsonObject jsonObject = (JsonObject) element;
		
		String classPath = src.getClass().getName();
		jsonObject.add(PROP_NAME, new JsonPrimitive(classPath));
		
		return jsonObject;
	}
	
	@Override
	public Command deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
		try {
			String classPath = json.getAsJsonObject().getAsJsonPrimitive(PROP_NAME).getAsString();
			Class<Command> clazz = (Class<Command>) Class.forName(classPath);
			
			return (Command) context.deserialize(json, clazz);
		} catch (ClassNotFoundException e) {
			// class not found so could not deserialize
		}
		
		return null;
	}
}
