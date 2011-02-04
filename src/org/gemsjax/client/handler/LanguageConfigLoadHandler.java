package org.gemsjax.client.handler;

import org.gemsjax.client.event.LanguageConfigLoadEvent;
import org.gemsjax.client.model.language.LanguageManager;

/**
 * This Handler handles  {@link LanguageConfigLoadEvent} while loading xml configuration file fired by the {@link LanguageManager}
 * @author Hannes Dorfmann
 *
 */
public interface LanguageConfigLoadHandler {
	
	/**
	 * The @link {@link LanguageManager} fires events to inform the LanguageConfigLoadListeners that something happened, when loading the language configuration file (xml)
	 */
	public void onLoadingLanguageConfigAction(LanguageConfigLoadEvent event);
}
