package org.gemsjax.client.admin.presenter;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.admin.event.LoadingAnimationEvent;
import org.gemsjax.client.admin.event.LoadingAnimationEvent.LoadingAnimationEventType;
import org.gemsjax.client.admin.handler.LoadingAnimationEventHandler;
import org.gemsjax.client.admin.view.LoadingView;
import org.gemsjax.client.util.preloader.ResourceLoaderEvent;
import org.gemsjax.client.util.preloader.ResourceLoaderEvent.ResourceLoaderEventType;
import org.gemsjax.client.util.preloader.ResourcePreloader;
import org.gemsjax.client.util.preloader.ResourcePreloaderHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.smartgwt.client.util.SC;

/**
 * This Presenter manages the {@link LoadingView} by calling {@link LoadingView#displayIt()} and 
 * {@link LoadingView#hideIt()}.<br />
 * It also has a {@link ResourcePreloader} Object to preload resources. This will be initially be done  
 * by loading the {@value #resourceFileURL} file ({@link #resourceFileURL}. <br /><br />
 * Since it is a {@link LanguageManagerHandler} it also observer the {@link LanguageManager} for {@link LanguageManagerEvent}s
 * 
 * As this is a {@link Presenter}, other {@link Presenter} can  fire {@link LoadingAnimationEvent}s  
 * (from Type {@link LoadingAnimationEventType#SHOW} or {@link LoadingAnimationEventType#HIDE} via the 
 * {@link Presenter#eventBus} to communicate with this Presenter, because the LoadingPresenter register
 *  an {@link LoadingAnimationEventHandler} to the {@link Presenter#eventBus} to receive {@link LoadingAnimationEvent}s
 * 
 * @see #bind()
 * @author Hannes Dorfmann
 *
 */
public class LoadingPresenter extends Presenter implements LoadingAnimationEventHandler, ResourcePreloaderHandler {
	
	private static final String resourceFileURL = "/resources.xml";

	private LoadingView view;
	private ResourcePreloader resourcePreloader;
	/**
	 * The sources, which has fired an {@link LoadingAnimationEvent}, will be saved in this list.
	 * Whenever a {@link LoadingAnimationEventType#HIDE} is received the source will be removed from this list.
	 * There can be multiple entries for the same source object. 
	 * So the loading overlay will be hidden if and only if this list is empty, so you are sure, that the loading overlay 
	 * can be hidden now, because for every {@link LoadingAnimationEventType#SHOW} Event you received a {@link LoadingAnimationEventType#HIDE} Event.
	 * @see LoadingAnimationEvent#getSource()
	 * @see #hideLoadingOverlay(Object)
	 * @see #showLoadingOverlay(Object)
	 */
	private List <Object> historySourceEventList;
	
	
	public LoadingPresenter(EventBus eventBus, LoadingView view)
	{
		super(eventBus);
		this.view = view;
		resourcePreloader = new ResourcePreloader();
		historySourceEventList = new LinkedList<Object>();
		bind();
		loadResourcesFile();
		
		view.setLoadingMessage("loading languages");
		
		
	}
	
	/**
	 * Register itself as {@link LoadingAnimationEventHandler} to the {@link Presenter#eventBus} to receive {@link LoadingAnimationEvent}s.
	 * It also registers itself to the {@link #resourcePreloader} as {@link ResourcePreloaderHandler}
	 */
	private void bind()
	{
		eventBus.addHandler(LoadingAnimationEvent.TYPE, this);
		resourcePreloader.addResourceLoaderHandler(this);
		
	}
	
	
	/**
	 * Show the Loading overlay.
	 * It also saves the sources, which has fired the Event to show the overlay, in {@link #historySourceEventList}.
	 * @param source The source which has fired the event to show the loading overlay
	 * @see #historySourceEventList
	 * @see #hideLoadingOverlay(Object)
	 */
	private void showLoadingOverlay(Object source)
	{
		historySourceEventList.add(source);
		view.bringToFront();
		
	}
	
	/**
	 * The source which has fired the Event to hide the loading overlay will be saved in  {@link #historySourceEventList}.
	 * So the loading overlay will be hidden if and only if the {@link #historySourceEventList} is empty, so you are sure, that the loading overlay 
	 * can be hidden now, because for every "SHOW" Event you received a "HIDE" Event.
	 * @see LoadingAnimationEvent#getSource()
	 * @see #historySourceEventList
	 * @see #showLoadingOverlay(Object)
	 * @param source  The source which has fired the event to hide the loading overlay
	 */
	private void hideLoadingOverlay(Object source)
	{
		historySourceEventList.remove(source);
		
		if (historySourceEventList.isEmpty())
			view.hideIt();
	}
	
	
	private void loadResourcesFile()
	{
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, resourceFileURL);
	    try {
			builder.sendRequest(null, new RequestCallback() {

				@Override
				public void onResponseReceived(Request request,
						Response response) {
					
					
					Document doc = XMLParser.parse(response.getText());
					Element root = doc.getDocumentElement();
					
					
					NodeList langNodes = root.getElementsByTagName("resource");
					int leng = langNodes.getLength();
					for (int i =0; i<leng; i++)
					{
						Element l = (Element)langNodes.item(i);
						resourcePreloader.addResource(l.getAttribute("url"));
					}
					
					// show the overlay
					showLoadingOverlay(resourcePreloader);
					resourcePreloader.startLoading(false);
					view.setLoadingMessage("preload resources");
					
				}

				@Override
				public void onError(Request request, Throwable exception) {
					hideLoadingOverlay(resourcePreloader);
				}
				});
	    }
	    catch(Exception e)
	    {
	    	
	    }
	}

	@Override
	public void onLoadingAnimationEvent(LoadingAnimationEvent event) {
		
		//TODO maybe a loading message history stack would be a good idea
		if (event.getType() == LoadingAnimationEventType.SHOW)
		{
			showLoadingOverlay(event.getSource());
			view.setLoadingMessage(event.getDisplayingMessage());
		}
		else
		if (event.getType()==LoadingAnimationEventType.HIDE)
			hideLoadingOverlay(event.getSource());
		
	}

	@Override
	public void onResourceLoader(ResourceLoaderEvent event) {
		
		//TODO displaying percent doesnt work correct
		if (event.getType()==ResourceLoaderEventType.SINGLE_RESOURCE_LOADED)
			view.setLoadingMessage(""+resourcePreloader.getLoadedResourcesWithErrorsInPercent()+" % <br /> last loaded: "+event.getResourceURL());
		else
			if (event.getType()==ResourceLoaderEventType.ERROR)
				view.setLoadingMessage(""+resourcePreloader.getLoadedResourcesWithErrorsInPercent()+" % <br /> error while loading "+event.getResourceURL());
		else
		
		if (event.getType()==ResourceLoaderEventType.ALL_RESOURCES_LOADED)
			hideLoadingOverlay(resourcePreloader);
		
		
	}


}
