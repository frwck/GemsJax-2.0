package org.gemsjax.client.handler;

import org.gemsjax.client.event.LanguageLoadEvent;
import org.gemsjax.client.model.language.LanguageManager;

/**
 * This is a Handler to handle {@link LanguageLoadEvents} while the {@link LanguageManager} is loading a language xml file from the server (HTTP GET). 
 * @author Hannes Dorfmann
 *
 */
public interface LanguageLoadHandler {
	
	/**
	 * The {@link LanguageManager} inform the {@link LanguageLoadHandler} via this method by firing a {@link LanguageLoadEvent} that something happened.
	 * @param event
	 */
	public void onLanguageLoadAction(LanguageLoadEvent event);

}
