package org.gemsjax.client.admin.event;

public class ResourceLoaderEvent {

	/**
	 * The Type for the ResourceLoaderEvents
	 * @author Hannes Dorfmann
	 *
	 */
	public enum ResourceLoaderEventType
	{
		/**
		 * A single resource has been loaded completely
		 */
		SINGLE_RESOURCE_LOADED,
		/**
		 * An error occurred while loading a single resource
		 */
		ERROR,
		
		/**
		 * All resources has been loaded completely
		 */
		ALL_RESOURCES_LOADED
	}
	
	
	private ResourceLoaderEventType type;
	private String resourceURL;
	
	
	public ResourceLoaderEvent(ResourceLoaderEventType type)
	{
		this.type = type;
	}
	
	
	public ResourceLoaderEvent(ResourceLoaderEventType type, String resourceURL)
	{
		this.type = type;
		this.resourceURL = resourceURL;
	}
	
	/**
	 * Get the {@link ResourceLoaderEventType} of this event
	 * @return
	 */
	public ResourceLoaderEventType getType()
	{
		return type;
	}
	
	/**
	 * Get the url of the resource that has been loaded or <code>NULL</code>, if this event-type is {@link ResourceLoaderEventType#ALL_RESOURCES_LOADED}
	 * @return
	 */
	public String getResourceURL()
	{
		return resourceURL;
	}
	
}
