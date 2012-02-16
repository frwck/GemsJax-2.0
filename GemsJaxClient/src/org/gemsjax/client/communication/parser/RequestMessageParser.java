package org.gemsjax.client.communication.parser;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.communication.message.request.AdminExperimentRequest;
import org.gemsjax.shared.communication.message.request.CollaborationRequest;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.GetAllRequestsAnswerMessage;
import org.gemsjax.shared.communication.message.request.LiveAdminExperimentRequestMessage;
import org.gemsjax.shared.communication.message.request.LiveCollaborationRequest;
import org.gemsjax.shared.communication.message.request.LiveFriendshipRequestMessage;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.ReferenceableRequestMessage;
import org.gemsjax.shared.communication.message.request.RequestChangedAnswerMessage;
import org.gemsjax.shared.communication.message.request.RequestError;
import org.gemsjax.shared.communication.message.request.RequestErrorMessage;
import org.gemsjax.shared.communication.message.request.RequestMessage;

import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
/**
 * This parser parses:
 * <ul>
 * <li>{@link GetAllRequestsAnswerMessage}</li>
 * <li>{@link LiveAdminExperimentRequestMessage}</li>
 * <li>{@link LiveFriendshipRequestMessage}</li>
 * <li>{@link LiveCollaborationRequest}</li>
 * <li>{@link RequestChangedAnswerMessage}</li>
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class RequestMessageParser {
	
	public RequestMessage parseMessage(String messageXml) throws DOMException{
		
		// parse the XML document into a DOM
		    Document messageDom = XMLParser.parse(messageXml);
		    
		    NodeList surroundingElement = messageDom.getElementsByTagName(ReferenceableRequestMessage.TAG);
		    
		    if (surroundingElement == null)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "No tag <"+ReferenceableRequestMessage.TAG+"> found");
		 
		    if (surroundingElement.getLength()!=1)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "No or more than one <"+ReferenceableRequestMessage.TAG+"> tag found");
			
		    
		    

		    // Check for reference Number if set
		    String referenceId = ((Element)surroundingElement.item(0)).getAttribute(ReferenceableRequestMessage.ATTRIBUTE_REFERENCE_ID);
		    
		   
			
		    Node systemElement = surroundingElement.item(0);
		    
		    NodeList childNodes = systemElement.getChildNodes();
		    
		    if(childNodes== null || childNodes.getLength() == 0)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+ReferenceableRequestMessage.TAG+"> does not contain child tags");
			
		    
		    if (childNodes.getLength()!=1)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+ReferenceableRequestMessage.TAG+"> contains more that one child tags");
			
		    Element childElement = (Element)childNodes.item(0);
	
		    
		    if (childElement.getTagName().equals(GetAllRequestsAnswerMessage.TAG))
		    	return parseGetAllAnswer(referenceId, childElement);
		    else    
		    if (childElement.getTagName().equals(RequestErrorMessage.TAG))
		    	return parseErrorMessage(referenceId, childElement);
		    else
	    	if (childElement.getTagName().equals(LiveRequestMessage.TAG))
		    	return parseLive(childElement);
		    
	    	else
	    	if (childElement.getTagName().equals(RequestChangedAnswerMessage.TAG))
		    	return new RequestChangedAnswerMessage(referenceId);
		    
		    
		    return null;
		    
	}
	
	
	
	
	
	private LiveRequestMessage parseLive(Element e)
	{
		long reqId, dateMs;
		String username, displayName;
		Date date;
		
		
		NodeList childNodes = e.getChildNodes();
	    
	    if(childNodes== null || childNodes.getLength() == 0)
	      	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+LiveRequestMessage.TAG+"> does not contain child tags");
		
	    
	    if (childNodes.getLength()!=1)
	    	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+LiveRequestMessage.TAG+"> contains more that one child tags");
		
	    Element childElement = (Element)childNodes.item(0);
	    
		
		try{
			reqId = Long.parseLong(e.getAttribute(LiveRequestMessage.ATTRIBUTE_ID));
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the request id to an int. Value: "+e.getAttribute(LiveRequestMessage.ATTRIBUTE_ID));
		}
		
			
		try{
			dateMs = Long.parseLong(e.getAttribute(LiveRequestMessage.ATTRIBUTE_DATETIME));
			date = new Date(dateMs);
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the request date time (ms) to long. Value: "+e.getAttribute(LiveRequestMessage.ATTRIBUTE_DATETIME));
		}
		
		
		displayName = e.getAttribute(LiveRequestMessage.ATTRIBUTE_REQUESTER_DISPLAY_NAME);
		if (displayName== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+e.getAttribute(LiveRequestMessage.ATTRIBUTE_REQUESTER_DISPLAY_NAME));
	
		username = e.getAttribute(LiveRequestMessage.ATTRIBUTE_REQUESTER_USERNAME);
		if (username== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+e.getAttribute(LiveRequestMessage.ATTRIBUTE_REQUESTER_USERNAME));
	
		
		
		if (childElement.getTagName().equals(LiveAdminExperimentRequestMessage.TAG))
			return parseLiveExperiment(childElement, reqId, date, displayName, username);
		else
		if (childElement.getTagName().equals(LiveCollaborationRequest.TAG))
			return parseLiveExperiment(childElement, reqId, date, displayName, username);
		else
		if (childElement.getTagName().equals(LiveFriendshipRequestMessage.TAG))
			return new LiveFriendshipRequestMessage(new FriendshipRequest(reqId, displayName, username, date));
		
		
		
		
		return null;
	}
	
	
	
	private LiveRequestMessage parseLiveExperiment(Element u, long reqId, Date date, String displayName, String username) throws DOMException
	{
		int expId;
		String name;
		
		try{
			expId = Integer.parseInt(u.getAttribute(LiveAdminExperimentRequestMessage.ATTRIBUTE_EXPERIMENT_ID));
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaboration to an int. Value: "+u.getAttribute(LiveAdminExperimentRequestMessage.ATTRIBUTE_EXPERIMENT_ID));
		}
	
		name = u.getAttribute(LiveAdminExperimentRequestMessage.ATTRIBUTE_EXPERIMENT_NAME);
		if (name== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name. Value: "+u.getAttribute(LiveAdminExperimentRequestMessage.ATTRIBUTE_EXPERIMENT_NAME));
	
		 
		return new LiveAdminExperimentRequestMessage(new AdminExperimentRequest(reqId, displayName, username, date, expId, name));
		
	}
	
	
	
	private LiveRequestMessage parseLiveCollaboration(Element u, long reqId, Date date, String displayName, String username) throws DOMException
	{
		int colId;
		String name;
		
		try{
			colId = Integer.parseInt(u.getAttribute(LiveCollaborationRequest.ATTRIBUTE_COLLABORATION_ID));
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaboration id to an int. Value: "+u.getAttribute(LiveCollaborationRequest.ATTRIBUTE_COLLABORATION_ID));
		}
	
		name = u.getAttribute(LiveCollaborationRequest.ATTRIBUTE_COLLABORATION_NAME);
		if (name== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name. Value: "+u.getAttribute(LiveCollaborationRequest.ATTRIBUTE_COLLABORATION_NAME));
	
		 
		return new LiveCollaborationRequest(new CollaborationRequest(reqId, displayName, username, date, colId, name));
		
	}
	
	
	
	private ReferenceableRequestMessage parseErrorMessage(String referenceId, Element e) throws DOMException
	{
		String typeAttribute = e.getAttribute(RequestErrorMessage.ATTRIBUTE_REASON);
		RequestError type;
	
			type = RequestError.fromConstant(typeAttribute);
			
		if (type==null)
			throw new DOMException(DOMException.SYNTAX_ERR,"RequestError type is null (after parsing)");
			
		String additionalInfo = e.getNodeValue();
		
		return new RequestErrorMessage(referenceId,type);
		
	}
	
	
	
	private ReferenceableRequestMessage parseGetAllAnswer(String referenceId, Element e) throws DOMException
	{
		
		int colId, expId;
		long reqId;
		String name, displayName, username;
		Date date;
		long dateMs;
		
		// Collaborations
		NodeList surroundCollaborationRes = e.getElementsByTagName(GetAllRequestsAnswerMessage.SUBTAG_COLLABORATION_REQUESTS);
		
		if (surroundCollaborationRes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GetAllRequestsAnswerMessage.SUBTAG_COLLABORATION_REQUESTS+"> but got: "+surroundCollaborationRes.getLength());
		
		NodeList cols = ((Element) surroundCollaborationRes.item(0)).getElementsByTagName(GetAllRequestsAnswerMessage.SUBTAG_COLLABORATION);
		Set<CollaborationRequest> colRequests = new LinkedHashSet<CollaborationRequest>();
		
		Element u;
		for (int i =0; i<cols.getLength(); i++)
		{
			u = (Element) cols.item(i);
			try{
				reqId = Long.parseLong(u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_REQUEST_ID));
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the request id to an int. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_ID));
			}
			
			try{
				colId = Integer.parseInt(u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_ID));
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaboration to an int. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_ID));
			}
			
			try{
				dateMs = Long.parseLong(u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABROTAION_REQUEST_DATETIME));
				date = new Date(dateMs);
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the request date time (ms) to long. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABROTAION_REQUEST_DATETIME));
			}
			
			displayName = u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_REQUESTER_DISPLAYNAME);
			if (displayName== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_REQUESTER_DISPLAYNAME));
		
			username = u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_REQUESTER_USERNAME);
			if (username== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_REQUESTER_USERNAME));
		
			name = u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_NAME);
			if (name== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_NAME));
		
			 
			
			colRequests.add(new CollaborationRequest(reqId, displayName, username, date, colId, name));
		}
		
		
		
		// Experiments
		NodeList surroundExpRes = e.getElementsByTagName(GetAllRequestsAnswerMessage.SUBTAG_EXPERIMENT_REQUESTS);
				
			if (surroundExpRes.getLength()!=1)
				throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GetAllRequestsAnswerMessage.SUBTAG_EXPERIMENT_REQUESTS+"> but got: "+surroundExpRes.getLength());
			
			cols = ((Element) surroundExpRes.item(0)).getElementsByTagName(GetAllRequestsAnswerMessage.SUBTAG_EXPERIMENT);
			Set<AdminExperimentRequest> expRequests = new LinkedHashSet<AdminExperimentRequest>();
			
			
			for (int i =0; i<cols.getLength(); i++)
			{
				u = (Element) cols.item(i);
				try{
					reqId = Long.parseLong(u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_REQUEST_ID));
				} catch(NumberFormatException ex){
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the request id to an int. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_REQUEST_ID));
				}
				
				try{
					expId = Integer.parseInt(u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_ID));
				} catch(NumberFormatException ex){
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaboration to an int. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_ID));
				}
				
				try{
					dateMs = Long.parseLong(u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_REQUEST_DATETIME));
					date = new Date(dateMs);
				} catch(NumberFormatException ex){
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the request date time (ms) to long. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_REQUEST_DATETIME));
				}
				
				displayName = u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_REQUESTER_DISPLAYNAME);
				if (displayName== null)
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_REQUESTER_DISPLAYNAME));
			
				username = u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_REQUESTER_USERNAME);
				if (username== null)
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_REQUESTER_USERNAME));
			
				name = u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_NAME);
				if (name== null)
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_NAME));
			
				 
				
				expRequests.add(new AdminExperimentRequest(reqId, displayName, username, date, expId, name));
			}
		
			
			
			// Experiments
			NodeList surroundFrRes = e.getElementsByTagName(GetAllRequestsAnswerMessage.SUBTAG_FRIENDSHIP_REQUESTS);
					
			if (surroundFrRes.getLength()!=1)
				throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GetAllRequestsAnswerMessage.SUBTAG_FRIENDSHIP_REQUESTS+"> but got: "+surroundFrRes.getLength());
			
			cols = ((Element) surroundExpRes.item(0)).getElementsByTagName(GetAllRequestsAnswerMessage.SUBTAG_EXPERIMENT);
			Set<FriendshipRequest> frRequests = new LinkedHashSet<FriendshipRequest>();
			
			
			for (int i =0; i<cols.getLength(); i++)
			{
				u = (Element) cols.item(i);
				try{
					reqId = Long.parseLong(u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_FRIENDSHIP_REQUEST_ID));
				} catch(NumberFormatException ex){
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the request id to an int. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_FRIENDSHIP_REQUEST_ID));
				}
				
				
				try{
					dateMs = Long.parseLong(u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_FRIENDSHIP_REQUEST_DATETIME));
					date = new Date(dateMs);
				} catch(NumberFormatException ex){
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the request date time (ms) to long. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_FRIENDSHIP_REQUEST_DATETIME));
				}
				
				displayName = u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_FRIENDSHIP_REQUESTER_DISPLAYNAME);
				if (displayName== null)
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_FRIENDSHIP_REQUESTER_DISPLAYNAME));
			
				username = u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_FRIENDSHIP_REQUESTER_USERNAME);
				if (username== null)
					throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_FRIENDSHIP_REQUESTER_USERNAME));
			
							 
				
				frRequests.add(new FriendshipRequest(reqId, displayName, username, date));
			}
		
		return new GetAllRequestsAnswerMessage(referenceId, frRequests, expRequests, colRequests);
	}


}
