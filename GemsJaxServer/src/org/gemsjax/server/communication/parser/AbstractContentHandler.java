package org.gemsjax.server.communication.parser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public abstract class AbstractContentHandler implements ContentHandler{

	private String currentValue;
	
	/**
	 * Get the current value
	 * @return
	 */
	public String getCurrentValue()
	{
		return currentValue;
	}
	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		this.currentValue = new String(ch, start, length);
	}

	@Override
	public abstract void endDocument() throws SAXException;

	@Override
	public abstract void endElement(String uri, String localName, String qName)	throws SAXException;

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {}

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {}

	@Override
	public void setDocumentLocator(Locator locator) {}

	@Override
	public void skippedEntity(String name) throws SAXException {}

	@Override
	public abstract void startDocument() throws SAXException;

	@Override
	public abstract void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException;

	@Override
	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {}

}
