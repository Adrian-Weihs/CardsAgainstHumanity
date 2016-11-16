package de.rvwbk.group03.cardsagainsthumanity.base.event;

import java.util.EventObject;
import java.util.Objects;

/**
 * All event objects should extend this class.
 * 
 * @author Adrian
 */
public abstract class AbstractEvent extends EventObject {
	private static final long serialVersionUID = 1419403578575937003L;
	
	
	/**
	 * @param source The source object on which the event initially occurred. Must not be {@code null}.
	 * @throws NullPointerException If {@code source} is {@code null}.
	 */
	public AbstractEvent(final Object source) throws NullPointerException {
		super(Objects.requireNonNull(source, "source must not be null"));
	}
}
