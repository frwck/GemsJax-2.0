package org.gemsjax.client.event;

import org.gemsjax.client.handler.LanguageLoadHandler;
import org.gemsjax.client.model.language.LanguageManager;

/**
 * {@link LanguageLoadEvent} will be fired by the {@link LanguageManager} to inform all {@link LanguageLoadHandler} 
 * via {@link LanguageLoadHandler#onLanguageLoadAction(LanguageLoadEvent)} that something happened.
 * @see LanguageLoadEventType
 * @author Hannes Dorfmann
 *
 */
public class LanguageLoadEvent extends GemsJaxEvent {
	
	/**
	 * A LanguageLoadEvent can be one of this Type
	 * @author Hannes Dorfmann
	 *
	 */
	public enum LanguageLoadEventType
	{
		/**
		 * Loading the xml language file has been started
		 */
		START,
		
		/**
		 * The xml language file has been loaded completely
		 */
		FINISH,
		/**
		 * An error occurred, the {@link Throwable} can be found via 
		 */
		ERROR
	}
	
	
	private LanguageLoadEventType type;
	private Throwable throwable;
	
	/**
	 * The constructor for {@link LanguageLoadEventType#START} and {@link LanguageLoadEventType#FINISH}
	 * @param type
	 */
	public LanguageLoadEvent(LanguageLoadEventType type)
	{
		this.type = type;
	}
	
	/**
	 * This constructor should be used in combination with {@link LanguageLoadEventType#ERROR}
	 * @param type
	 * @param optionalThrowable
	 */
	public LanguageLoadEvent(LanguageLoadEventType type, Throwable optionalThrowable)
	{
		this.throwable = optionalThrowable;
	}
	
	
	
	/**
	 * Get the event type
	 * @return
	 */
	public LanguageLoadEventType getType()
	{
		return this.type;
	}
	
	/**
	 * Get the Throwable
	 * @return
	 */
	public Throwable getOptionalThrowable()
	{
		return throwable;
	}

	
}
