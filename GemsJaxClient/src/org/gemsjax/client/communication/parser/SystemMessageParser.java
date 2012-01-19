package org.gemsjax.client.communication.parser;

import org.gemsjax.shared.communication.message.CommunicationError;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.SystemErrorMessage;
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
		    else
		    if (childElement.getTagName().equals(SystemErrorMessage.TAG))
		    	return parseSystemErrorMessage(childElement);
		    
		    
		    
		    
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
		
		String statusAttribute  = e.getAttribute(LoginAnswerMessage.ATTRIBUTE_STATUS);
		String uidAttribute = e.getAttribute(LoginAnswerMessage.ATTRIBUTE_USER_ID);
		String displayNameAttribute = e.getAttribute(LoginAnswerMessage.ATTRIBUTE_DISPLAYED_NAME);
		String notiAttribute = e.getAttribute(LoginAnswerMessage.ATTRUBUTE_UNREAD_NOTIFICATION_COUNT);
		String experimentGroupAttribute = e.getAttribute(LoginAnswerMessage.ATTRIBUTE_EXPERIMENT_GROUP);
		
		LoginAnswerStatus status = LoginAnswerMessage.stringToAnswerStatus(statusAttribute);
		
		if (status== null)
		 	throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the status of the LoginAnswerMessage");
		
		// If Login successful
		if (status==LoginAnswerStatus.OK)
		{
			int userId;
			// Check if all needed attributes are set:
			if (uidAttribute==null  || uidAttribute.isEmpty())
				throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse a LoginAnswerMessage, because the unique user id is not set or missing \""+LoginAnswerMessage.ATTRIBUTE_USER_ID+"\"");
			
			if (displayNameAttribute==null  || displayNameAttribute.isEmpty())
				throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse a LoginAnswerMessage, because the display name is not set or missing \""+LoginAnswerMessage.ATTRIBUTE_DISPLAYED_NAME+"\"");
			
			try{
				userId = Integer.parseInt(uidAttribute);
			}catch(NumberFormatException ex)
			{
				throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the unique user id to a Integer. Value: "+uidAttribute);
			}
			
			// Check if its a Experiment Login
			if(experimentGroupAttribute!=null && !experimentGroupAttribute.isEmpty())
			{
				try{
					int exGrId = Integer.parseInt(experimentGroupAttribute);
					return new LoginAnswerMessage(userId, exGrId, displayNameAttribute);
				}catch(NumberFormatException ex)
				{
					throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the ExperimentGroupId to a Integer. Value: "+experimentGroupAttribute);
				}
				
			}
			else { // its a normal Login (not experiment)
				
				// check if notification count is set
				if (notiAttribute==null  || notiAttribute.isEmpty())
					throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse a LoginAnswerMessage, because the unread notification count is not set or missing: \""+LoginAnswerMessage.ATTRUBUTE_UNREAD_NOTIFICATION_COUNT+"\"");
				
				
				
				
				try{
					int notiCount = Integer.parseInt(notiAttribute);
					return new LoginAnswerMessage(userId, displayNameAttribute, notiCount);
				}catch(NumberFormatException ex)
				{
					throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the unread notification count to a Integer. Value: "+notiAttribute);
				}
			}
				
			
		} // End if login sucessful
		else
			return new LoginAnswerMessage(LoginAnswerStatus.FAIL); // login fail
	}
	
	
	/**
	 * Parse a {@link RegistrationAnswerMessage}
	 * @param e The start element with the tag {@link RegistrationAnswerMessage#TAG}
	 * @return the parsed {@link RegistrationAnswerMessage}
	 * @throws DOMException if an parse has error occurred
	 */
	private RegistrationAnswerMessage parseRegistrationAnswerMessage(Element e) throws DOMException
	{
		String statusAttribute  = e.getAttribute(RegistrationAnswerMessage.ATTRIBUTE_STATUS);
		
		RegistrationAnswerStatus status = RegistrationAnswerMessage.answereStatusFromString(statusAttribute);
		
		if (status== null)
		 	throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the status of the RegistartaionAnswerMessage");
		
		
		String failString = e.getAttribute(RegistrationAnswerMessage.ATTRIBUTE_FAIL_STRING);
		
		if(failString!=null && !failString.isEmpty())
			return new RegistrationAnswerMessage(status, failString);
		
		else
			return new RegistrationAnswerMessage(status);
	}
	
	
	
	private SystemErrorMessage parseSystemErrorMessage(Element e) throws DOMException
	{
		String typeAttribute = e.getAttribute(SystemErrorMessage.ATTRIBUTE_TYPE);
		CommunicationError.ErrorType type;
		
		try{
			int typeNr = Integer.parseInt(typeAttribute);
			type = CommunicationError.intToErrorType(typeNr);
			
			if (type==null)
				throw new DOMException(DOMException.SYNTAX_ERR,"CommunicationError type is null (after parsing int to ErrorType)");
			
		}catch(NumberFormatException ex)
		{
			throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the error type to a Integer. Value: "+typeAttribute);
		}
		
		
		String additionalInfo = e.getNodeValue();
		
		return new SystemErrorMessage(new CommunicationError(type, additionalInfo));
		
	}
	
	
	

}
