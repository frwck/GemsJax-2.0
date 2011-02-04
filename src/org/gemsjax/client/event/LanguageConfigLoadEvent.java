package org.gemsjax.client.event;

import org.gemsjax.client.handler.LanguageConfigLoadHandler;
import org.gemsjax.client.model.language.LanguageManager;

/**
 * This Event will be fired from the {@link LanguageManager} to all {@link LanguageConfigLoadHandler} via the method {@link LanguageConfigLoadHandler#onLoadingLanguageConfigAction()}
 * @author Hannes Dorfmann
 *
 */
public class LanguageConfigLoadEvent extends GemsJaxEvent{
	
	/**
	 * Indicates the type of the {@link LanguageConfigLoadEvent}
	 * @author Hannes Dorfmann
	 *
	 */
	public enum LangugaeConfigLoadEventType
	{
		/**
		 * Loading the language configuration file (xml) has been started
		 */
		START,
		/**
		 * The language configuration file has been loaded completely.
		 */
		FINISH,
		
		/**
		 * An error occurred while loading the the config file
		 */
		ERROR
	}
	
	private Throwable throwable;
	private LangugaeConfigLoadEventType type;
	
	public LanguageConfigLoadEvent(LangugaeConfigLoadEventType type) {
		this.type= type;
	}
	
	/**
	 * This should be used to create an error-event ({@link LangugaeConfigLoadEventType#ERROR})
	 * @param type
	 * @param throwable
	 */
	public LanguageConfigLoadEvent(LangugaeConfigLoadEventType type, Throwable throwable)
	{
		this.throwable = throwable;
		this.type = type;
	}
	
	
	/**
	 * Get the event type - {@link LangugaeConfigLoadEventType}
	 * @return {@link LangugaeConfigLoadEventType}
	 */
	public LangugaeConfigLoadEventType getType()
	{
		return type;
	}

	
	/**
	 * Get the Throwabel.
	 * <b>Notice:</b> The throwable is only be set, if the event type is {@link LangugaeConfigLoadEventType#ERROR}
	 * @return
	 */
	public Throwable getOptionalThrowable()
	{
		return throwable;
	}
	
}
