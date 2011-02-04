package org.gemsjax.client.model.language;

import java.util.HashMap;

import org.gemsjax.client.exception.XmlException;

import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

/**
 * This is the default interface for a language. It provides all method's to get the word/name in a language.
 * @author Hannes Dorfmann
 *
 */
public class Language
{
	
	private HashMap<String, String> wordMap;
	private String name;
	private String iconUrl;
	
	
	public Language(String xml) throws XmlException
	{
		wordMap = new HashMap<String, String>();
		parseXML(xml);
	}
	
	/**
	 * parse the xml to this class
	 * @param xml
	 * @throws XmlException 
	 */
	private void parseXML(String xml) throws XmlException
	{
		Document doc = XMLParser.parse(xml);
		Element root = doc.getDocumentElement();
		// get the root Elemet <language>
		if (root==null)
			throw new XmlException("The single root element <language> was not found "+doc.getChildNodes().getLength());
		
		
		if (!root.getTagName().equals("language"))
			throw new XmlException("The root element <language> was not found. The root element was: "+root.getTagName());
		
		
		String name = root.getAttribute("name");
		String url = root.getAttribute("icon");
		
		if (name == null || name.equals(""))
			throw new XmlException("Name not set for <language>");
		
		if (url == null || url.equals(""))
			throw new XmlException("Icon url not set for <language>");
		
		// set the basics: name and icon url
		this.name = name;
		this.iconUrl = url;
		
		
		NodeList wordNodes = root.getElementsByTagName("word");
		int l = wordNodes.getLength();
		for (int i =0; i<l; i++)
		{
			Element w = (Element)wordNodes.item(i);
			String wordName = w.getAttribute("name");
			
			String value = wordNodes.item(i).getFirstChild().getNodeValue();
			if (wordName == null || wordName.equals("")) 
				throw new XmlException("Name for a word is null");
			
			if (value == null || value.equals(""))
				throw new XmlException("Word has no value");
			
			// its ok, the word can be inserted
			wordMap.put(wordName, value);
			
		}
		
		
	}
	
	
	
	/**
	 * Get the name of this language
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Get the url for the icon
	 * @return
	 */
	public String getIconUrl()
	{
		return iconUrl;
	}
	
	
	/**
	 * Get a word or sentence  by his unique identifier 
	 * @param identifier See a .xml language file to for the list with all identifiers
	 * @return
	 */
	public String get(String identifier)
	{
		return wordMap.get(identifier);
	}
	
	
	
	
	
	/**
	 * Get the language specific name for the main menu entry to create a new meta-model
	 * @return
	 */
	public String getMenuItemNewMetaModel()
	{
		return wordMap.get("menu-new-meta-model");
	}
	
	/**
	 * Get the language specific name for the main menu entry to show the user own meta-models
	 * @return
	 */
	public String getMenuItemMyMetaModels()
	{
		return wordMap.get("menu-my-meta-models");
	}
	
	/**
	 * Get the language specific name for the main menu entry to create a new experiment
	 * @return
	 */
	public String getMenuItemNewExperiment()
	{
		return wordMap.get("menu-new-experiment");
	}
	
	/**
	 * Get the language specific name for the main menu entry to show the experiments, administrated by the user
	 * @return
	 */
	public String getMenuItemMyExperiments()
	{
		return wordMap.get("menu-my-experiments");
	}
	
	
	/**
	 * Get the language specific word for "logout"
	 * @return
	 */
	public String getWordLogout()
	{
		return wordMap.get("logout");
	}
	
	/**
	 * Get the language specific word for "login"
	 */
	public String getWordLogin()
	{
		return wordMap.get("login");
	}
	
	/**
	 * Get the language specific word for "username"
	 * @return
	 */
	public String getWordUsername()
	{
		return wordMap.get("username");
	}
	
	/**
	 * Get the language specific word for "password"
	 * @return
	 */
	public String getWordPassword()
	{
		return wordMap.get("password");
	}
	
	/**
	 * Get the language specific "welcome"-sentence for the login view 
	 * @return
	 */
	public String getSentenceLoginWelcome()
	{
		return wordMap.get("login-welcome");
	}
	
	
	
	

}
