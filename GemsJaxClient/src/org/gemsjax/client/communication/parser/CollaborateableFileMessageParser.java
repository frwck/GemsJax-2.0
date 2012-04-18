package org.gemsjax.client.communication.parser;

import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileError;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileErrorMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.CollaborateableFileSuccessfulMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class CollaborateableFileMessageParser {

	
	public ReferenceableCollaborateableFileMessage parseMessage(String messageXml) throws DOMException{
		
		// parse the XML document into a DOM
		    Document messageDom = XMLParser.parse(messageXml);
		    
		    NodeList surroundingElement = messageDom.getElementsByTagName(ReferenceableCollaborateableFileMessage.TAG);
		    
		    if (surroundingElement == null)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "No tag <"+ReferenceableCollaborateableFileMessage.TAG+"> found");
		 
		    if (surroundingElement.getLength()!=1)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "No or more than one <"+ReferenceableCollaborateableFileMessage.TAG+"> tag found");
			
		    
		    

		    // Check for reference Number
		    String referenceId = ((Element)surroundingElement.item(0)).getAttribute(ReferenceableCollaborateableFileMessage.ATTRIBUTE_REFERENCE_ID);
		    
		    
		    Node systemElement = surroundingElement.item(0);
		    
		    NodeList childNodes = systemElement.getChildNodes();
		    
		    if(childNodes== null || childNodes.getLength() == 0)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+ReferenceableCollaborateableFileMessage.TAG+"> does not contain child tags");
			
		    
		    if (childNodes.getLength()!=1)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+ReferenceableCollaborateableFileMessage.TAG+"> contains more that one child tags");
			
		    Element childElement = (Element)childNodes.item(0);
		    
		    if (childElement.getTagName().equals(CollaborateableFileSuccessfulMessage.TAG))
		    	return parseSuccessfulMessage(referenceId, childElement);
		    else
		    if (childElement.getTagName().equals(CollaborateableFileErrorMessage.TAG))
		    	return parseErrorMessage(referenceId, childElement);
		  
				   
		    
		    // If nothing could be parsed
		 	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+ReferenceableCollaborateableFileMessage.TAG+"> was found, but contains an unknown child tag <"+childElement.getTagName()+">");
	}
	
	
	private ReferenceableCollaborateableFileMessage parseErrorMessage(String referenceId, Element e) throws DOMException
	{
		CollaborateableFileError type = CollaborateableFileError.fromConstant(e.getAttribute(CollaborateableFileErrorMessage.ATTRIBUTE_REASON));
		if (type==null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Error type is null (after parsing)");
			
		return new CollaborateableFileErrorMessage(referenceId,type);
		
	}
	
	
	private ReferenceableCollaborateableFileMessage parseSuccessfulMessage(String referenceId, Element e) throws DOMException
	{
		return new CollaborateableFileSuccessfulMessage(referenceId);
		
	}
	
}
