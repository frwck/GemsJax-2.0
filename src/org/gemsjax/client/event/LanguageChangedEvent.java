package org.gemsjax.client.event;

import org.gemsjax.client.handler.LanguageChangeHandler;
import org.gemsjax.client.model.language.Language;

/**
 * This Event inform the {@link LanguageChangeHandler} that the Language has been changed and
 * the current language can be retrieved by calling {@link #getLanguage()}
 * @author Hannes Dorfmann
 *
 */
public class LanguageChangedEvent extends GemsJaxEvent {
	
	private Language language;
	
	public LanguageChangedEvent (Language language){
		
		this.language = language;
		
	}

	/**
	 * Get the current set language
	 * @return
	 */
	public Language getLanguage()
	{
		return this.language;
	}
}
