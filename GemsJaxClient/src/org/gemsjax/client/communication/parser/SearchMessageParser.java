package org.gemsjax.client.communication.parser;

import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.friend.FriendErrorAnswerMessage;
import org.gemsjax.shared.communication.message.friend.FriendMessage;
import org.gemsjax.shared.communication.message.friend.ReferenceableFriendMessage;
import org.gemsjax.shared.communication.message.search.ReferenceableSearchMessage;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.SearchResultErrorMessage;

import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class SearchMessageParser {
	
	public ReferenceableSearchMessage parseMessage(String messageXml) throws DOMException{
		
		// parse the XML document into a DOM
		    Document messageDom = XMLParser.parse(messageXml);
		    
		    NodeList surroundingElement = messageDom.getElementsByTagName(ReferenceableSearchMessage.TAG);
		    
		    if (surroundingElement == null)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "No tag <"+ReferenceableSearchMessage.TAG+"> found");
		 
		    if (surroundingElement.getLength()!=1)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "No or more than one <"+ReferenceableSearchMessage.TAG+"> tag found");
			
		    
		    

		    // Check for reference Number
		    String referenceId = ((Element)surroundingElement.item(0)).getAttribute(ReferenceableSearchMessage.ATTRIBUTE_REFERENCE_ID);
		    
		    if (referenceId == null)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "The referenceId is null! Thats not allowed");
			
		    Node systemElement = surroundingElement.item(0);
		    
		    NodeList childNodes = systemElement.getChildNodes();
		    
		    if(childNodes== null || childNodes.getLength() == 0)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+ReferenceableSearchMessage.TAG+"> does not contain child tags");
			
		    
		    if (childNodes.getLength()!=1)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+ReferenceableSearchMessage.TAG+"> contains more that one child tags");
			
		    Element childElement = (Element)childNodes.item(0);
	
		    
		    
		    if (childElement.getTagName().equals(SearchResultErrorMessage.TAG))
		    	return parseErrorMessage(referenceId, childElement);
		    
		    
		    return null;
		    
	}
	
	
	
	private ReferenceableSearchMessage parseErrorMessage(String referenceId, Element e) throws DOMException
	{
		String typeAttribute = e.getAttribute(FriendErrorAnswerMessage.ATTRIBUTE_TYPE);
		SearchError type;
	
			type = SearchError.fromConstant(typeAttribute);
			
		if (type==null)
			throw new DOMException(DOMException.SYNTAX_ERR,"SearchError type is null (after parsing)");
			
		String additionalInfo = e.getNodeValue();
		
		return new SearchResultErrorMessage(referenceId,type, additionalInfo);
		
	}


}
