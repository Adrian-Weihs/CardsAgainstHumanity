package de.rvwbk.group03.cardsagainsthumanity.data.memory;

import java.util.concurrent.atomic.AtomicInteger;

import de.rvwbk.group03.cardsagainsthumanity.base.util.Strings;

public class User implements de.rvwbk.group03.cardsagainsthumanity.data.User {
	private static AtomicInteger count = new AtomicInteger(0);
	
	private final int id;
	private final String name;
	
	
	/**
	 * @param name The name of the user. Must not be {@code null}.
	 * @throws IllegalArgumentException If {@code name} is empty.
	 * @throws NullPointerException If {@code name} is {@code null}.
	 */
	public User(final String name) throws IllegalArgumentException, NullPointerException {
		this.name = Strings.requireNonNullAndNonEmpty(name, "name");
		this.id = count.incrementAndGet();
	}
	
	@Override
	public int getId() {
		return this.id;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return new StringBuilder(this.name).append(" (").append(this.id).append(')').toString();
	}
}
