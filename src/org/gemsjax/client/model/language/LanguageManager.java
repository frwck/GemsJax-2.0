package org.gemsjax.client.model.language;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.event.LanguageChangedEvent;
import org.gemsjax.client.event.LanguageConfigLoadEvent;
import org.gemsjax.client.event.LanguageConfigLoadEvent.LangugaeConfigLoadEventType;
import org.gemsjax.client.event.LanguageLoadEvent.LanguageLoadEventType;
import org.gemsjax.client.event.LanguageLoadEvent;
import org.gemsjax.client.exception.XmlException;
import org.gemsjax.client.handler.LanguageChangeHandler;
import org.gemsjax.client.handler.LanguageConfigLoadHandler;
import org.gemsjax.client.handler.LanguageLoadHandler;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

/**
 * The LanguageManager manages the languages for the user interface.
 * <b>Notice:</b> This is a singleton, so use the method {@link #getInstance()} 
 * @author Hannes Dorfmann
 *
 */
public class LanguageManager {
	
	private static LanguageManager instance;
	private LanguageConfiguration currentLanguageConfiguration;
	private List<LanguageChangeHandler> languageChangeHandlerList;
	private List<LanguageConfigLoadHandler> languageConfigLoadHandlerList;
	private List<LanguageLoadHandler> languageLoadHandlerList;
	private List<LanguageConfiguration> supportedLanguagesList;

	/**
	 * The url for the language configuration file.
	 * This file contains a list of all languages that were supported and can be loaded dynamically by loading the corresponding xml file.
	 * The path to the language-xml file is declareted in this config-xml file, even like the name and icon url.
	 * @see #loadConfig()
	 * @see LanguageConfiguration
	 */
	private String languageConfigUrl;
	
	
	
	private LanguageManager()
	{
		languageChangeHandlerList = new LinkedList<LanguageChangeHandler>();
		supportedLanguagesList = new LinkedList<LanguageConfiguration>();
		languageConfigLoadHandlerList = new LinkedList<LanguageConfigLoadHandler>();
		languageLoadHandlerList = new LinkedList<LanguageLoadHandler>();
		
		// The url for the config file
		languageConfigUrl =  "/languages/config.xml";
	}
	
	/**
	 * Get the singleton instance 
	 * @return
	 * @throws RequestException 
	 */
	public static LanguageManager getInstance()
	{
		if (instance == null)
			instance = new LanguageManager();
			
		return instance;
	}
	
	/**
	 * Get the current language
	 * @return
	 */
	public Language getCurrentLanguage()
	{
		return currentLanguageConfiguration.getLanguage();
	}
	
	/**
	 * Get the current LanguageConfiguration
	 * @return
	 */
	public LanguageConfiguration getCurrentLanguageConfiguration()
	{
		return currentLanguageConfiguration;
	}
	
	/**
	 * Set the current language that is used by the user and inform all {@link LanguageChangeHandler}
	 *  by calling the {@link LanguageChangeHandler#onLanguageChanged()} method
	 * @param language
	 */
	public void setCurrentLanguageConfiguratoin(LanguageConfiguration languageConfiguration)
	{
		// if the the language was not already loaded
		if (languageConfiguration.getLanguage()==null)
			loadLanguage(languageConfiguration, false); // load the language
		else
		{	// else when the language was already laoded, set it to the current language
			this.currentLanguageConfiguration = languageConfiguration;
			fireEvent(new LanguageChangedEvent(languageConfiguration.getLanguage()));
		}
		
	}
	
	
	
	/**
	 * Add a {@link LanguageChangeHandler}
	 * @param listener
	 */
	public void addLanguageChangeHandler(LanguageChangeHandler listener)
	{
		if (!languageChangeHandlerList.contains(listener))
			languageChangeHandlerList.add(listener);
	}
	
	/**
	 * Remove a {@link LanguageChangeHandler}
	 * @param listener
	 */
	public void removeLanguageChangeHandler(LanguageChangeHandler listener)
	{
		languageChangeHandlerList.remove(listener);
	}
	
	
	/**
	 * Add a {@link LanguageLoadHandler}
	 * @param listener
	 */
	public void addLanguageLoadHandler(LanguageLoadHandler listener)
	{
		if (!languageLoadHandlerList.contains(listener))
			languageLoadHandlerList.add(listener);
	}

	
	/**
	 * Remove a {@link LanguageLoadHandler}
	 * @param listener
	 */
	public void removeLanguageLoadListener(LanguageLoadHandler listener)
	{
		languageLoadHandlerList.remove(listener);
	}
	
	/**
	 * Add a {@link LanguageConfigLoadHandler} 
	 * @param listener
	 */
	public void addLanguageConfigLoadHandler(LanguageConfigLoadHandler listener)
	{
		if (!languageConfigLoadHandlerList.contains(listener))
			this.languageConfigLoadHandlerList.add(listener);
	}
	
	/**
	 * Remove a {@link LanguageConfigLoadHandler}
	 * @param listener
	 */
	public void removeLanguageConfigLoadHandler(LanguageConfigLoadHandler listener)
	{
		this.languageConfigLoadHandlerList.remove(listener);
	}
	
	/**
	 * Notify all {@link LanguageChangeHandler}
	 */
	private void fireEvent(LanguageChangedEvent event)
	{
		for (LanguageChangeHandler cl:languageChangeHandlerList)
			cl.onLanguageChanged(event);
	}
	
	/**
	 * Get a list with all supported Languages ({@link LanguageConfiguration})
	 * @return
	 */
	public List<LanguageConfiguration> getSupportedLanguages()
	{
		return this.supportedLanguagesList;
	}
	
	
	/**
	 * Parse the xml config file, that has been loaded by {@link #loadConfig()};
	 * <br />
	 * The config.xml file looks like this: <br /> 
	 * <code>
	 * <config><br />
	 *
	 * <language name="english" icon="img/languages/english.jpg" url="languages/english.xml" default="true" /><br />
	 * <language name="deutsch" icon="img/languages/deutsch.jpg" url="languages/deutsch.xml" default="false" /><br />
	 * </config></br>
	 * </code>
	 * 
	 * @param xml
	 * @throws XmlException 
	 */
	private void parseConfig(String xml) throws XmlException
	{
		Document doc = XMLParser.parse(xml);
		Element root = doc.getDocumentElement();
			
		// get the root Element <config>
		if (root==null)
			throw new XmlException("The single root element <config> was not found "+doc.getChildNodes().getLength());
		
		
		if (!root.getTagName().equals("config"))
			throw new XmlException("The root element <config> was not found. The root element was: "+root.getTagName());
		
		
		// parse the <language> elements
		NodeList langNodes = root.getElementsByTagName("language");
		int leng = langNodes.getLength();
		for (int i =0; i<leng; i++)
		{
			Element l = (Element)langNodes.item(i);
			String langName = l.getAttribute("name");
			String url = l.getAttribute("url");
			String icon = l.getAttribute("icon");
			boolean def = Boolean.parseBoolean(l.getAttribute("default"));
			
			supportedLanguagesList.add( new LanguageConfiguration(langName, icon, url, def));
			
		}
		
	}
	
	/**
	 * Load the config xml file and parse it (by calling {@link #parseConfig()}.
	 * The config xml file looks like this (Example):
	 * <code>
	 * <config><br />
	 *
	 * <language name="english" icon="img/languages/english.jpg" url="languages/english.xml" default="true" /><br />
	 * <language name="deutsch" icon="img/languages/deutsch.jpg" url="languages/deutsch.xml" default="false" /><br />
	 * </config></br>
	 * </code>
	 * @throws RequestException 
	 */
	public void loadConfig() throws RequestException
	{
		// Start Loading
		fireEvent(new LanguageConfigLoadEvent(LangugaeConfigLoadEventType.START));
		
		// Send a HTTP GET to the server to get the config xml file
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, languageConfigUrl);
	    builder.sendRequest(null, new RequestCallback() {
			
			@Override
			public void onResponseReceived(Request request, Response response) {
				
				if (response.getStatusCode() == 200) // HTTP 200 means OK
				{
					try {
						// Parse the config file
						parseConfig(response.getText());
						
						LanguageConfiguration def = getDefaultLanguage();
						
						// Load the default Language and set it to the current language
						loadLanguage(def, true);
						
						fireEvent(new LanguageConfigLoadEvent(LangugaeConfigLoadEventType.FINISH));
						
					} catch (XmlException e) {
						fireEvent(new LanguageConfigLoadEvent(LangugaeConfigLoadEventType.ERROR, e));
						
					}
				}
				else
					fireEvent(new LanguageConfigLoadEvent(LangugaeConfigLoadEventType.ERROR, new Exception("Language configuraton file not found")));
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				fireEvent(new LanguageConfigLoadEvent(LangugaeConfigLoadEventType.ERROR, exception));
			}
		});
		
		
	}
	
	
	
	
	
	/**
	 * Get the default language
	 * @return
	 */
	public LanguageConfiguration getDefaultLanguage()
	{
		for (LanguageConfiguration lc : this.supportedLanguagesList)
			if (lc.isDefault())
				return lc;
		
		
		return null;
	}
	
	
	/**
	 * Load a language by loading the corresponding XML file via HTTP GET from the server
	 * @param languageConfiguration {@link LanguageConfiguration} 
	 * @param setCurrentLanguageConfiguration If this flag is true, the now loaded language will be set to the current Language 
	 * @see #setCurrentLanguageConfiguratoin(LanguageConfiguration)
	 */
	private void loadLanguage(final LanguageConfiguration languageConfiguration, final boolean setCurrentLanguageConfiguration)
	{
		
		fireEvent(new LanguageLoadEvent(LanguageLoadEventType.START));
		
		try
		{
			// make a HTTP GET request to get the language xml file
			RequestBuilder langBuilder = new RequestBuilder(RequestBuilder.GET, languageConfiguration.getXmlUrl());
			langBuilder.sendRequest(null, new RequestCallback() {
				
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode()==200)
					{
						try {
							Language language = new Language(response.getText());
							
							languageConfiguration.setLanguage(language);
							fireEvent(new LanguageLoadEvent(LanguageLoadEventType.FINISH));
							
							if (setCurrentLanguageConfiguration)
							{
								// Set the new loaded language and LanguageConfiguration as the current
								LanguageManager.this.currentLanguageConfiguration=languageConfiguration;
								fireEvent(new LanguageChangedEvent(language)); // Fire event that language has been changed
							}
						} catch (XmlException e) {
							
							fireEvent(new LanguageLoadEvent(LanguageLoadEventType.ERROR, e));
							e.printStackTrace();
						}
					}
					else
					{
						fireEvent(new LanguageLoadEvent(LanguageLoadEventType.ERROR, new Exception("Language file not found "+response.getStatusCode())));
					}
					
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					fireEvent(new LanguageLoadEvent(LanguageLoadEventType.ERROR, exception));
					
				}
			});
			
		}
		catch (Exception ex)
		{
			fireEvent(new LanguageLoadEvent(LanguageLoadEventType.ERROR, ex));
		}
		
		
	}
	
	
	
	
	/**
	 * The url to the config file
	 * @return
	 */
	public String getConfigUrl()
	{
		return languageConfigUrl;
	}
	
	
	/**
	 * Notify all {@link LanguageConfigLoadHandler} that something happened during the loading process by firing {@link LanguageConfigLoadEvent}
	 */
	private void fireEvent(LanguageConfigLoadEvent event)
	{
		for (LanguageConfigLoadHandler l : languageConfigLoadHandlerList)
			l.onLoadingLanguageConfigAction(event);
	}
	
	/**
	 * Fire a {@link LanguageLoadEvent} to all {@link LanguageLoadHandler}
	 * @param event
	 */
	private void fireEvent(LanguageLoadEvent event)
	{
		for (LanguageLoadHandler l : this.languageLoadHandlerList)
			l.onLanguageLoadAction(event);
	}
	
	
	/**
	 * This class is used by the {@link LanguageManager} to manage the Language instances.
	 * The LanguageConfiguration will be parsed by loading the languages/config.xml file.
	 * 
	 * The config.xml file looks like this:
	 * <config><br />
	 *
	 * <language name="english" icon="img/languages/english.jpg" url="/languages/english.xml" default="true" /><br />
	 * <language name="deutsch" icon="img/languages/deutsch.jpg" url="/languages/deutsch.xml" default="false" /><br />
	 * </config></br>
	 * 
	 * @author Hannes Dorfmann
	 *
	 */
	public class LanguageConfiguration
	{
		private String name;
		private String xmlUrl;
		private boolean isDefault;
		private String iconUrl;
		/**
		 * If the whole language will be loaded (by loading the corresponding xml file, example english.xml), the language is set here for reuse purpose
		 */
		private Language language;
		
		public LanguageConfiguration(String name, String iconUrl, String xmlUrl, boolean isDefault)
		{
			this.name = name;
			this.iconUrl = iconUrl;
			this.xmlUrl = xmlUrl;
			this.isDefault = isDefault;
		}
		
		/**
		 * Set the Language
		 * @param lang
		 */
		private void setLanguage(Language lang)
		{
			this.language = lang;
		}
		
		/**
		 * Get the Language
		 * @return
		 */
		public Language getLanguage()
		{
			return language;
		}
		
		/**
		 * Is this Language the default
		 * @return
		 */
		public boolean isDefault()
		{
			return this.isDefault;
		}
		
		/**
		 * Get the name
		 * @return
		 */
		public String getName()
		{
			return this.name;
		}
		
		/**
		 * Get the icon for this language
		 * @return
		 */
		public String getIconUrl()
		{
			return this.iconUrl;
		}
		
		
		/**
		 * Get the xml url to this language
		 * @return
		 */
		public String getXmlUrl()
		{
			return this.xmlUrl;
		}
	}

	

}
