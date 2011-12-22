package org.gemsjax.client.communication.parser;

import org.gemsjax.shared.communication.message.system.LoginAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.SystemMessage;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage.LoginAnswerStatus;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;

import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.Node;

/**
 * This is a parser to parse {@link SystemMessage}s
 * @author Hannes Dorfmann
 *
 */
public class SystemMessageParser {
	
	
	/**
	 * Parse a {@link SystemMessage} from a String.
	 * See the communication protocol specification.
	 * @param messageXml
	 * @return
	 * @throws DOMException
	 */
	public SystemMessage parseMessage(String messageXml) throws DOMException{
		
		// parse the XML document into a DOM
		    Document messageDom = XMLParser.parse(messageXml);
		    
		    NodeList surroundingElement = messageDom.getElementsByTagName(SystemMessage.TAG);
		    
		    if (surroundingElement == null)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "No tag <"+SystemMessage.TAG+"> found");
		 
		    if (surroundingElement.getLength()!=1)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "No or more than one <"+SystemMessage.TAG+"> tag found");
			 
		    
		    Node systemElement = surroundingElement.item(0);
		    
		    NodeList childNodes = systemElement.getChildNodes();
		    
		    if(childNodes== null || childNodes.getLength() == 0)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+SystemMessage.TAG+"> does not contain child tags");
			
		    
		    if (childNodes.getLength()!=1)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+SystemMessage.TAG+"> contains more that one child tags");
			
		    
		    Element childElement = (Element)childNodes.item(0);
		    
		    if (childElement.getTagName().equals(LoginAnswerMessage.TAG))
		    	return parseLoginAnswer(childElement);
		    else
		    if (childElement.getTagName().equals(RegistrationAnswerMessage.TAG))
		    	return parseRegistrationAnswerMessage(childElement);
		    
		    
		    
		    
		    // If nothing could be parsed
		 	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+SystemMessage.TAG+"> was found, but contains an unknown child tag <"+childElement.getTagName()+">");
				
	}
	
	
	/**
	 * Parse a {@link LoginAnswerMessage}
	 * @param e The start {@link Element} with the tag {@link LoginAnswerMessage#TAG}
	 * @return The parsed {@link LoginAnswerMessage}
	 * @throws DOMException if a an parse error has occurred
	 */
	private LoginAnswerMessage parseLoginAnswer(Element e) throws DOMException
	{
		
		String statusAttribute  = e.getAttribute(LoginAnswerMessage.STATUS_ATTRIBUTE);
		
		LoginAnswerStatus status = LoginAnswerMessage.stringToAnswerStatus(statusAttribute);
		
		if (status== null)
		 	throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the status of the LoginAnswerMessage");
		
		
		String experimentGroup = e.getAttribute(LoginAnswerMessage.EXPERIMENT_GROUP_ATTRIBUTE);
		if(experimentGroup!=null && !experimentGroup.isEmpty())
		{
			try{
				Integer exGrId = Integer.parseInt(experimentGroup);
				return new LoginAnswerMessage(status, exGrId);

			}catch(NumberFormatException ex)
			{
				throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the ExperimentGroupId to a Integer. Value: "+experimentGroup);
			}
			
		}
		
		else
			return new LoginAnswerMessage(status);
	}
	
	
	/**
	 * Parse a {@link RegistrationAnswerMessage}
	 * @param e The start element with the tag {@link RegistrationAnswerMessage#TAG}
	 * @return the parsed {@link RegistrationAnswerMessage}
	 * @throws DOMException if an parse has error occurred
	 */
	private RegistrationAnswerMessage parseRegistrationAnswerMessage(Element e) throws DOMException
	{
		String statusAttribute  = e.getAttribute(RegistrationAnswerMessage.STATUS_ATTRIBUTE);
		
		RegistrationAnswerStatus status = RegistrationAnswerMessage.answereStatusFromString(statusAttribute);
		
		if (status== null)
		 	throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the status of the RegistartaionAnswerMessage");
		
		
		String failString = e.getAttribute(RegistrationAnswerMessage.FAIL_STRING_ATTRIBUTE);
		
		if(failString!=null && !failString.isEmpty())
			return new RegistrationAnswerMessage(status, failString);
		
		else
			return new RegistrationAnswerMessage(status);
	}
	
	
	
	

}
