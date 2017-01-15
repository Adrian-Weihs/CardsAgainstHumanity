package de.rvwbk.group03.cardsagainsthumanity.util;

import java.awt.Window;

/**
 * This class handles logic called by the views.
 * 
 * @author Alex
 */
public class UiManager {
	
	/**
	 * Disposes all views created by this VM.
	 */
	public static void closeAllViews() {
		for (Window window : Window.getWindows()) {
			window.dispose();
		}
	}
	
	
	private UiManager() {
		// prevent instantiation
	}
}
