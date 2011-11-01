package org.gemsjax.client.admin.presenter;

import com.google.gwt.event.shared.EventBus;


/**
 * A Presenter is the abstract layer that coordinates the view with the model.
 * The {@link EventBus} is used to send Events between Presenters. 
 * So Presenters can "communicate" with each other.
 * @author Hannes Dorfmann
 *
 */
public class Presenter {
	
	/**
	 * The {@link EventBus} is used to send Events between Presenters. 
	 * So Presenters can "communicate" with each other.
	 */
	protected EventBus eventBus;
	
	public Presenter(EventBus eventBus)
	{
		this.eventBus = eventBus;
	}

}
