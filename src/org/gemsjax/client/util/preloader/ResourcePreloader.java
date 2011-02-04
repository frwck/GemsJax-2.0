package org.gemsjax.client.util.preloader;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.gemsjax.client.util.preloader.ResourceLoaderEvent.ResourceLoaderEventType;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;

/**
 * This class is used to preload resources from the server via HTTP GET.
 * @author Hannes Dorfmann
 *
 */
public class ResourcePreloader {
	
	/**
	 * This enum is used to set the Status of a resource
	 * @author Hannes Dorfmann
	 *
	 */
	public enum LoadStatus
	{
		/**
		 * The resource is loaded completely and correct
		 */
		LOADED,
		/**
		 * An error has occurred, the resource has not been loaded
		 */
		ERROR,
		/**
		 * Not startet yet to load the resource
		 */
		NOT_STARTED,
		
		/**
		 * The resource is stil loading now, but is not completed yet
		 */
		LOADING
	}
	
	
	
	private Map<String, LoadStatus> resourceMap;
	private List<ResourcePreloaderHandler> handlers;
	
	
	public ResourcePreloader()
	{
		resourceMap = new HashMap<String, LoadStatus>();
		handlers = new LinkedList<ResourcePreloaderHandler>();
	}
	
	
	/**
	 * Creates a new ResourceLoader
	 */
	public ResourcePreloader(List<String> resourceURLs)
	{
		this();
		
		// initialize with NOT_STARTED
		for (String url : resourceURLs)
			resourceMap.put(url, LoadStatus.NOT_STARTED);
		
	}
	
	/**
	 * Add a single resource (the url) that should be loaded by calling {@link #startLoading(boolean)}. If the resource url has already been added before, the resourceurl will no bee added a second time.
	 * @param url The resource url
	 */
	public void addResource(String url)
	{
		if (!resourceMap.containsKey(url))
		{
			resourceMap.put(url, LoadStatus.NOT_STARTED);
		}
	}
	
	
	
	/**
	 * Add an {@link ResourcePreloaderHandler} as observer to receive {@link ResourceLoaderEvent}. 
	 * If the handler is already registered, this method will do nothing, because a {@link ResourcePreloaderHandler} can only registered once.
	 * @param handler
	 */
	public void addResourceLoaderHandler(ResourcePreloaderHandler handler)
	{
		if (!handlers.contains(handler))
			handlers.add(handler);
	}
	
	/**
	 * Remove a {@link ResourcePreloaderHandler}
	 * @param handler
	 */
	public void removeResourceLoaderHandler(ResourcePreloaderHandler handler)
	{
		handlers.remove(handler);
	}
	
	
	/**
	 * Fire a {@link ResourceLoaderEvent} to inform all registered {@link ResourcePreloaderHandler}s that something happened.
	 * @param type
	 * @param resourceUrl
	 */
	private void fireResourceLoaderEvent(ResourceLoaderEventType type, String resourceUrl)
	{
		ResourceLoaderEvent event = new ResourceLoaderEvent(type, resourceUrl);
		
		for (ResourcePreloaderHandler handler: handlers)
			handler.onResourceLoader(event);
	}
	
	/**
	 * Is true if this {@link ResourcePreloader} is current loading or waiting for retrieving data from server
	 * @return 
	 */
	public boolean isLoading()
	{
		for (LoadStatus status : resourceMap.values())
			if (status==LoadStatus.LOADING ) 
				return true;
		
		return false;
	}
	
	/**
	 * Check if errors has occurred while loading resources
	 * @return TRUE, if at least one error has occurred while loading
	 */
	public boolean errorsOccurred()
	{
		for (LoadStatus status : resourceMap.values())
			if (status==LoadStatus.ERROR ) 
				return true;
		
		return false;
	}
	
	
	/**
	 * Check if all resources has been loaded, including errors while loading resources
	 * @see #loadedComplete()
	 * @return TRUE, if all resources has loaded completet or an error occured while loading a resource
	 */
	public boolean loadedCompleteWithErrors()
	{
		for (LoadStatus status : resourceMap.values())
			if (status==LoadStatus.NOT_STARTED || status==LoadStatus.LOADING ) 
				return false;
		
		
		return true;
	}
	
	
	/**
	 * Check if all resources has been loaded
	 * @see #loadedCompleteWithErrors()
	 * @return TRUE, if and only if alle resources has been loaded completely without errors 
	 */
	public boolean loadedComplete()
	{
		for (LoadStatus status : resourceMap.values())
			if (status==LoadStatus.NOT_STARTED || status==LoadStatus.LOADING || status == LoadStatus.ERROR) 
				return false;
		
		
		return true;
	}

	/**
	 * Get the resources count
	 * @return
	 */
	public int getResourcesCount()
	{
		return resourceMap.size();
	}
	
	
	/**
	 * Get the count of Resources, which has been loaded completely or an error has occurred while loading
	 * @return
	 */
	public int getLoadedResourcesWithErrorsCount()
	{
		int l =0;
		for (LoadStatus status : resourceMap.values())
			if (status==LoadStatus.LOADING || status == LoadStatus.ERROR) 
				l++;
		
		return l;
	}
	
	/**
	 * With this method you start to load the given resources
	 * @param reloadAlreadyLoaded If its true then already previous loaded resources will be reloaded
	 */
	public void startLoading(boolean reloadAlreadyLoaded)
	{
			
		for (final Entry<String, LoadStatus> entry: resourceMap.entrySet())
		{
			
			if (reloadAlreadyLoaded && entry.getValue()==LoadStatus.LOADED)
				continue;
			

			final String url = entry.getKey();
						
			resourceMap.put(url, LoadStatus.LOADING);
			RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		    try {
				builder.sendRequest(null, new RequestCallback() {

					@Override
					public void onResponseReceived(Request request,
							Response response) {
						resourceMap.put(url, LoadStatus.LOADED);
						// Fire event
						fireResourceLoaderEvent(ResourceLoaderEventType.SINGLE_RESOURCE_LOADED, url);
						
						if (loadedCompleteWithErrors())
							fireResourceLoaderEvent(ResourceLoaderEventType.ALL_RESOURCES_LOADED, null);
						
					}

					@Override
					public void onError(Request request, Throwable exception) {
						resourceMap.put(url, LoadStatus.ERROR);
						
						// fire Event
						fireResourceLoaderEvent(ResourceLoaderEventType.ERROR, url);
						if (loadedCompleteWithErrors())
							fireResourceLoaderEvent(ResourceLoaderEventType.ALL_RESOURCES_LOADED, null);
						
						
					}});
			} catch (RequestException e) {
				// fire event
				fireResourceLoaderEvent(ResourceLoaderEventType.ERROR, url);
				if (loadedCompleteWithErrors())
					fireResourceLoaderEvent(ResourceLoaderEventType.ALL_RESOURCES_LOADED, null);
			}
		}
	}
	
	/**
	 * Get the count of loaded resources (incl. error while loading) as percent
	 * @return
	 */
	public double getLoadedResourcesWithErrorsInPercent()
	{
		double loadedWithErrors = 0;
		double size=0;
		
		for (LoadStatus status : resourceMap.values())
		{
			if (status==LoadStatus.LOADING || status == LoadStatus.ERROR) 
				loadedWithErrors++;
			
			size++;
		}
		
		
		return (size==0)?0:(loadedWithErrors/size*100);
		
		
	}
	
	
	
	
	
	

}
