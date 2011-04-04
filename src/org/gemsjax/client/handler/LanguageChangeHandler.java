package org.gemsjax.client.handler;

import org.gemsjax.client.event.LanguageChangedEvent;
import org.gemsjax.client.model.language.Language;

import com.google.gwt.event.shared.EventHandler;


/**
 * All GUI components, whose language should be changeable dynamically, must implement this interface
 * @author Hannes Dorfmann
 */
public interface LanguageChangeHandler extends EventHandler{
	/**
	 * This method is called when the language has been changed and the component must update the text elements with the new {@link Language} elements.
	 * The new language is packed in the {@link LanguageChangedEvent} by calling {@link LanguageChangedEvent#getLanguage()}
	 *
	 * @param event {@link LanguageChangedEvent}
	 */
	public void onLanguageChanged( LanguageChangedEvent event);

}
