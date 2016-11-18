package de.rvwbk.group03.cardsagainsthumanity.base.event;

import java.util.Objects;

public class ObjectActionEvent<T> extends AbstractEvent implements ActionEvent {
	
	public static final String ACTION_ADD = "add";
	public static final String ACTION_DISCONNECT = "disconnect";
	public static final String ACTION_JOIN = "join";
	public static final String ACTION_LEAVE = "leave";
	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_LOGOUT = "logout";
	public static final String ACTION_REMOVE = "remove";
	
	public interface ObjectActionEventListener<T> {
		
		public void handleObjectActionEvent(ObjectActionEvent<T> objectActionEvent) throws NullPointerException;
	}
	
	
	public interface ObjectActionEventNotifier<T> {
		
		public void addObjectActionEventListener(ObjectActionEventListener<T> actionEventListener) throws NullPointerException;
		
		public boolean removeObjectActionEventListener(ObjectActionEventListener<T> actionEventListener) throws NullPointerException;
	}
	
	
	private T object;
	private String action;
	
	
	public ObjectActionEvent(final Object source, final T object, final String action) throws NullPointerException {
		super(source);
		this.object = Objects.requireNonNull(object, "object must not be null");
		this.action = Objects.requireNonNull(action, "action must not be null");
	}
	
	
	public T getObject() {
		return this.object;
	}
	
	
	@Override
	public String getAction() {
		return this.action;
	}
}
