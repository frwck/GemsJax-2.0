package org.gemsjax.client.model.language;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.event.LanguageChangedEvent;
import org.gemsjax.client.event.LanguageManagerEvent;
import org.gemsjax.client.event.LanguageManagerEvent.LanguageManagerEventType;
import org.gemsjax.client.exception.XmlException;
import org.gemsjax.client.handler.LanguageChangeHandler;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

/**
 * The LanguageManager manages the languages for the user interface.
 * <b>Notice:</b> This is a singleton, so use the method {@link #getInstance()} <br />
 * <b>Important:</b> Do not forget to set the global Presenter {@link EventBus} via {@link #setEventBus(EventBus)};
 * @author Hannes Dorfmann
 *
 */
public class LanguageManager {
	
	private static LanguageManager instance;
	private LanguageConfiguration currentLanguageConfiguration;
	private EventBus eventBus;
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
		supportedLanguagesList = new LinkedList<LanguageConfiguration>();
		
		// The url for the config file
		languageConfigUrl =  "/languages/config.xml";
	}
	
	/**
	 * Set the {@link EventBus}. This normally should be the EventBus that uses all the Presenters
	 * @param eventbus
	 */
	public void setEventBus(EventBus eventbus)
	{
		this.eventBus = eventbus;
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
	public void setCurrentLanguageConfiguration(LanguageConfiguration languageConfiguration)
	{
		// if the the language was not already loaded
		if (languageConfiguration.getLanguage()==null)
			loadLanguage(languageConfiguration, false); // load the language
		else
		{	// else when the language was already laoded, set it to the current language
			this.currentLanguageConfiguration = languageConfiguration;
			eventBus.fireEvent(new LanguageChangedEvent(languageConfiguration.getLanguage()));
		}
		
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
		eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.START_LOADING_LANGUAGE));
		
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
						
						LanguageConfiguration def = getDefaultLanguageConfiguration();
						currentLanguageConfiguration = getDefaultLanguageConfiguration();
						// Load the default Language and set it to the current language
						loadLanguage(def, true);
						
						
						eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.LANGUAGE_LOADED));
						
					} catch (XmlException e) {
						eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.ERROR, e));
						
					}
				}
				else
					eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.ERROR, new Exception("Language configuraton file not found")));
			}
			
			@Override
			public void onError(Request request, Throwable exception) {
				eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.ERROR, new Exception(exception)));
			}
		});
		
		
	}
	
	
	
	
	
	/**
	 * Get the default language
	 * @return
	 */
	public LanguageConfiguration getDefaultLanguageConfiguration()
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
	 * @see #setCurrentLanguageConfiguration(LanguageConfiguration)
	 */
	private void loadLanguage(final LanguageConfiguration languageConfiguration, final boolean setCurrentLanguageConfiguration)
	{
		
		eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.START_LOADING_LANGUAGE));
		
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
							eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.LANGUAGE_LOADED));
							
							if (setCurrentLanguageConfiguration)
							{
								// Set the new loaded language and LanguageConfiguration as the current
								LanguageManager.this.currentLanguageConfiguration=languageConfiguration;
								eventBus.fireEvent(new LanguageChangedEvent(language)); // Fire event that language has been changed
							}
						} catch (XmlException e) {
							
							eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.ERROR, e));
							e.printStackTrace();
						}
					}
					else
					{
						eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.ERROR, new Exception("Language file not found "+response.getStatusCode())));
					}
					
				}
				
				@Override
				public void onError(Request request, Throwable exception) {
					eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.ERROR, new Exception(exception)));
					
				}
			});
			
		}
		catch (Exception ex)
		{
			eventBus.fireEvent(new LanguageManagerEvent(LanguageManagerEventType.ERROR, ex));
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
