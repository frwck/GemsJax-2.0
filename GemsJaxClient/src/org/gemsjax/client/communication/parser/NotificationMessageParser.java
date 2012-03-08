package org.gemsjax.client.communication.parser;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.communication.message.notification.CollaborationRequestNotification;
import org.gemsjax.shared.communication.message.notification.ExperimentRequestNotification;
import org.gemsjax.shared.communication.message.notification.FriendshipRequestNotification;
import org.gemsjax.shared.communication.message.notification.GetAllNotificationsAnswerMessage;
import org.gemsjax.shared.communication.message.notification.GetAllNotificationsMessage;
import org.gemsjax.shared.communication.message.notification.LiveCollaborationRequestNotificationMessage;
import org.gemsjax.shared.communication.message.notification.LiveExperimentRequestNotification;
import org.gemsjax.shared.communication.message.notification.LiveFriendshipNotificationMessage;
import org.gemsjax.shared.communication.message.notification.LiveQuickNotificationMessage;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.notification.NotificationErrorMessage;
import org.gemsjax.shared.communication.message.notification.NotificationMessage;
import org.gemsjax.shared.communication.message.notification.QuickNotification;
import org.gemsjax.shared.communication.message.notification.ReferenceableNotificationMessage;
import org.gemsjax.shared.communication.message.notification.SuccessfulNotificationMessage;
import org.gemsjax.shared.communication.message.request.AdminExperimentRequest;
import org.gemsjax.shared.communication.message.request.CollaborationRequest;
import org.gemsjax.shared.communication.message.request.FriendshipRequest;
import org.gemsjax.shared.communication.message.request.GetAllRequestsAnswerMessage;
import org.gemsjax.shared.communication.message.request.LiveAdminExperimentRequestMessage;
import org.gemsjax.shared.communication.message.request.LiveCollaborationRequestMessage;
import org.gemsjax.shared.communication.message.request.LiveFriendshipRequestMessage;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.ReferenceableRequestMessage;
import org.gemsjax.shared.communication.message.request.RequestChangedAnswerMessage;
import org.gemsjax.shared.communication.message.request.RequestError;
import org.gemsjax.shared.communication.message.request.RequestErrorMessage;
import org.gemsjax.shared.communication.message.request.RequestMessage;
import org.gemsjax.shared.notification.QuickNotification.QuickNotificationType;

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
 * <li>{@link LiveCollaborationRequestMessage}</li>
 * <li>{@link RequestChangedAnswerMessage}</li>
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class NotificationMessageParser {
	
	public NotificationMessage parseMessage(String messageXml) throws DOMException{
		
		// parse the XML document into a DOM
		    Document messageDom = XMLParser.parse(messageXml);
		    
		    NodeList surroundingElement = messageDom.getElementsByTagName(NotificationMessage.TAG);
		    
		    if (surroundingElement == null)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "No tag <"+NotificationMessage.TAG+"> found");
		 
		    /*
		    if (surroundingElement.getLength()!=1)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "No or more than one <"+NotificationMessage.TAG+"> tag found");
			
		    */
		    

		    // Check for reference Number if set
		    String referenceId = ((Element)surroundingElement.item(0)).getAttribute(ReferenceableNotificationMessage.ATTRIBUTE_REFERENCE_ID);
		    
		   
			
		    Node systemElement = surroundingElement.item(0);
		    
		    NodeList childNodes = systemElement.getChildNodes();
		    
		    if(childNodes== null || childNodes.getLength() == 0)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+NotificationMessage.TAG+"> does not contain child tags");
			
		    
		    if (childNodes.getLength()!=1)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+NotificationMessage.TAG+"> contains more that one child tags");
			
		    Element childElement = (Element)childNodes.item(0);
	
		    if (childElement.getTagName().equals(SuccessfulNotificationMessage.TAG))
	    		return new SuccessfulNotificationMessage(referenceId);
		    else
		    if (childElement.getTagName().equals(GetAllNotificationsAnswerMessage.TAG))
		    	return parseGetAllAnswer(referenceId, childElement);
		    else    
		    if (childElement.getTagName().equals(LiveQuickNotificationMessage.TAG))
		    	return parseLiveQuickMessage(childElement);
		    else
	    	if (childElement.getTagName().equals(LiveExperimentRequestNotification.TAG))
		    	return parseLiveExperimentMessage(childElement);
		    
	    	else
	    	if (childElement.getTagName().equals(LiveCollaborationRequestNotificationMessage.TAG))
		    	return parseLiveCollaborationMessage(childElement);
		    
	    	else
		    	if (childElement.getTagName().equals(LiveFriendshipNotificationMessage.TAG))
			    	return parseLiveFriendshipMessage(childElement);
		    
	    	else
	    	if (childElement.getTagName().equals(NotificationErrorMessage.TAG))
	    		return parseErrorMessage(referenceId, childElement);
		    
		    
		    throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the incoming message to a NotificationMessage");
		    
	}
	
	
	private NotificationErrorMessage parseErrorMessage(String referenceId, Element e)
	{
		NotificationError error = NotificationError.fromConstant(e.getAttribute(NotificationErrorMessage.ATTRIBUTE_REASON));
		
		if (error == null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the error reason");
		
		
		return new NotificationErrorMessage(referenceId, error);
		
	}
	
	
	private LiveCollaborationRequestNotificationMessage parseLiveCollaborationMessage(Element e)
	{
		long id, dateMs;
		String username, displayName, name;
		int colId;
		Date date;
		boolean read, accepted;
		
		
		try{
			id = Long.parseLong(e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_ID));
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the notification id to an int. Value: "+e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_ID));
		}
		
		try{
			colId = Integer.parseInt(e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_COLLABORATION_ID));
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaboration to an int. Value: "+e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_COLLABORATION_ID));
		}
		
		try{
			dateMs = Long.parseLong(e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_TIME));
			date = new Date(dateMs);
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the date time (ms) to long. Value: "+e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_TIME));
		}
		
		
		
		read = Boolean.parseBoolean(e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_READ));
		accepted = Boolean.parseBoolean(e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_COLLABORATION_ACCEPTED));
		
		
		displayName = e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_COLLABORATION_DISPLAYNAME);
		if (displayName== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+displayName);
	
		username = e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_COLLABORATION_USERNAME);
		if (username== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+username);
	
		name = e.getAttribute(LiveCollaborationRequestNotificationMessage.ATTRIBUTE_COLLABORATION_NAME);
		if (name== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name. Value: "+name);
	
		return new LiveCollaborationRequestNotificationMessage(new CollaborationRequestNotification(id, date, read, displayName, username, accepted, colId, name));
	}
	
	
	
	private LiveQuickNotificationMessage parseLiveQuickMessage(Element e)
	{
		long id, dateMs;
		String username, displayName;
		Date date;
		String optional;
		boolean read;
		QuickNotificationType type;
		
		
		try{
			id = Long.parseLong(e.getAttribute(LiveQuickNotificationMessage.ATTRIBUTE_ID));
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the notification id to an int. Value: "+e.getAttribute(LiveQuickNotificationMessage.ATTRIBUTE_ID));
		}
		
		
		try{
			dateMs = Long.parseLong(e.getAttribute(LiveQuickNotificationMessage.ATTRIBUTE_TIME));
			date = new Date(dateMs);
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the date time (ms) to long. Value: "+e.getAttribute(LiveQuickNotificationMessage.ATTRIBUTE_TIME));
		}
		
		
		
		read = Boolean.parseBoolean(e.getAttribute(LiveQuickNotificationMessage.ATTRIBUTE_READ));
		
		
		optional = e.getAttribute(LiveQuickNotificationMessage.ATTRIBUTE_OPTIONAL);
		
		try{
			type = QuickNotificationType.fromConstant(Integer.parseInt(e.getAttribute(LiveQuickNotificationMessage.ATTRIBUTE_TYPE)));
			if (type== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the quick notifiacation type. Value: null");
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the quick notification type constant (int) to QuickNotificationType. Value: "+e.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_QUICKNOTIFICATION_TYPE));
		}
		
		
		
		return new LiveQuickNotificationMessage( new QuickNotification(id, date, read, type, optional));
	}
	
	
	
	private LiveFriendshipNotificationMessage parseLiveFriendshipMessage(Element e)
	{
		long id, dateMs;
		String username, displayName, name;
		int expId;
		Date date;
		boolean read, accepted;
		
		
		try{
			id = Long.parseLong(e.getAttribute(LiveFriendshipNotificationMessage.ATTRIBUTE_ID));
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the notification id to an int. Value: "+e.getAttribute(LiveFriendshipNotificationMessage.ATTRIBUTE_ID));
		}
		
		
		try{
			dateMs = Long.parseLong(e.getAttribute(LiveFriendshipNotificationMessage.ATTRIBUTE_TIME));
			date = new Date(dateMs);
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the date time (ms) to long. Value: "+e.getAttribute(LiveFriendshipNotificationMessage.ATTRIBUTE_TIME));
		}
		
		
		
		read = Boolean.parseBoolean(e.getAttribute(LiveFriendshipNotificationMessage.ATTRIBUTE_READ));
		accepted = Boolean.parseBoolean(e.getAttribute(LiveFriendshipNotificationMessage.ATTRIBUTE_ACCEPTED));
		
		
		displayName = e.getAttribute(LiveFriendshipNotificationMessage.ATTRIBUTE_DISPLAYNAME);
		if (displayName== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+displayName);
	
		username = e.getAttribute(LiveFriendshipNotificationMessage.ATTRIBUTE_USERNAME);
		if (username== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+username);
	
		return new LiveFriendshipNotificationMessage(new FriendshipRequestNotification(id, date, read, displayName, username, accepted));
	}
	
	
	
	
	private LiveExperimentRequestNotification parseLiveExperimentMessage(Element e)
	{
		long id, dateMs;
		String username, displayName, name;
		int expId;
		Date date;
		String optional;
		boolean read, accepted;
		QuickNotificationType type;
		
		
		try{
			id = Long.parseLong(e.getAttribute(LiveExperimentRequestNotification.ATTRIBUTE_ID));
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the notification id to an int. Value: "+e.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_ID));
		}
		
		try{
			expId = Integer.parseInt(e.getAttribute(LiveExperimentRequestNotification.ATTRIBUTE_EXPERIMENT_ID));
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaboration to an int. Value: "+e.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_ID));
		}
		
		try{
			dateMs = Long.parseLong(e.getAttribute(LiveExperimentRequestNotification.ATTRIBUTE_TIME));
			date = new Date(dateMs);
		} catch(NumberFormatException ex){
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the date time (ms) to long. Value: "+e.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_TIME));
		}
		
		
		
		read = Boolean.parseBoolean(e.getAttribute(LiveExperimentRequestNotification.ATTRIBUTE_READ));
		accepted = Boolean.parseBoolean(e.getAttribute(LiveExperimentRequestNotification.ATTRIBUTE_EXPERIMENT_ACCEPTED));
		
		
		displayName = e.getAttribute(LiveExperimentRequestNotification.ATTRIBUTE_EXPERIMENT_DISPLAYNAME);
		if (displayName== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+displayName);
	
		username = e.getAttribute(LiveExperimentRequestNotification.ATTRIBUTE_EXPERIMENT_USERNAME);
		if (username== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+username);
	
		name = e.getAttribute(LiveExperimentRequestNotification.ATTRIBUTE_EXPERIMENT_NAME);
		if (name== null)
			throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name. Value: "+name);
	
		
		return new LiveExperimentRequestNotification(new ExperimentRequestNotification(id, date, read, displayName, username, accepted, expId, name));
	}
	
	
	
	private GetAllNotificationsAnswerMessage parseGetAllAnswer(String referenceId, Element e) throws DOMException
	{
		
		int colId, expId;
		long id;
		String name, displayName, username, optional;
		QuickNotificationType type;
		Date date;
		long dateMs;
		boolean read, accepted;
		
		// Collaborations
		NodeList surroundCollaborationRes = e.getElementsByTagName(GetAllNotificationsAnswerMessage.SUBTAG_COLLABORATION_REQUEST_RESULTS);
		
		if (surroundCollaborationRes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GetAllNotificationsAnswerMessage.SUBTAG_COLLABORATION_REQUEST_RESULTS+"> but got: "+surroundCollaborationRes.getLength());
		
		NodeList cols = ((Element) surroundCollaborationRes.item(0)).getElementsByTagName(GetAllNotificationsAnswerMessage.SUBTAG_COLLABORATIONNOTIFICATION);
		Set<CollaborationRequestNotification> colRequests = new LinkedHashSet<CollaborationRequestNotification>();
		
		Element u;
		for (int i =0; i<cols.getLength(); i++)
		{
			u = (Element) cols.item(i);
			try{
				id = Long.parseLong(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_ID));
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the notification id to an int. Value: "+u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_ID));
			}
			
			try{
				colId = Integer.parseInt(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_COLLABORATION_ID));
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaboration to an int. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_COLLABORATION_ID));
			}
			
			try{
				dateMs = Long.parseLong(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_TIME));
				date = new Date(dateMs);
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the date time (ms) to long. Value: "+u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_TIME));
			}
			
			
			
			read = Boolean.parseBoolean(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_READ));
			accepted = Boolean.parseBoolean(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_COLLABORATION_ACCEPTED));
			
			
			displayName = u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_COLLABORATION_DISPLAYNAME);
			if (displayName== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+displayName);
		
			username = u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_COLLABORATION_USERNAME);
			if (username== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+username);
		
			name = u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_COLLABORATION_NAME);
			if (name== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name. Value: "+name);
		
			 
			
			colRequests.add(new CollaborationRequestNotification(id, date, read, displayName, username, accepted, colId, name));
		}
		
		
		
		// Experiments
		
		NodeList surroundExperimentRes = e.getElementsByTagName(GetAllNotificationsAnswerMessage.SUBTAG_EXPERIMENT_REQUEST_RESULTS);
		
		if (surroundCollaborationRes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GetAllNotificationsAnswerMessage.SUBTAG_EXPERIMENT_REQUEST_RESULTS+"> but got: "+surroundCollaborationRes.getLength());
		
		NodeList exps = ((Element) surroundCollaborationRes.item(0)).getElementsByTagName(GetAllNotificationsAnswerMessage.SUBTAG_EXPERIMENTNOTIFICATION);
		Set<ExperimentRequestNotification> expRequests = new LinkedHashSet<ExperimentRequestNotification>();
		
		for (int i =0; i<exps.getLength(); i++)
		{
			u = (Element) exps.item(i);
			try{
				id = Long.parseLong(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_ID));
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the notification id to an int. Value: "+u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_ID));
			}
			
			try{
				expId = Integer.parseInt(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_EXPERIMENT_ID));
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaboration to an int. Value: "+u.getAttribute(GetAllRequestsAnswerMessage.ATTRIBUTE_EXPERIMENT_ID));
			}
			
			try{
				dateMs = Long.parseLong(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_TIME));
				date = new Date(dateMs);
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the date time (ms) to long. Value: "+u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_TIME));
			}
			
			
			
			read = Boolean.parseBoolean(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_READ));
			accepted = Boolean.parseBoolean(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_EXPERIMENT_ACCEPTED));
			
			
			displayName = u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_EXPERIMENT_DISPLAYNAME);
			if (displayName== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+displayName);
		
			username = u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_EXPERIMENT_USERNAME);
			if (username== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+username);
		
			name = u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_EXPERIMENT_NAME);
			if (name== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name. Value: "+name);
		
			 
			
			expRequests.add(new ExperimentRequestNotification(id, date, read, displayName, username, accepted, expId, name));
		}
		
		
		
		// Friendships
		
				NodeList surroundFriendshipRes = e.getElementsByTagName(GetAllNotificationsAnswerMessage.SUBTAG_FRIENDSHIP_REQUEST_RESULTS);		
				if (surroundCollaborationRes.getLength()!=1)
					throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GetAllNotificationsAnswerMessage.SUBTAG_FRIENDSHIP_REQUEST_RESULTS+"> but got: "+surroundCollaborationRes.getLength());
				
				NodeList friends = ((Element) surroundCollaborationRes.item(0)).getElementsByTagName(GetAllNotificationsAnswerMessage.SUBTAG_FRIENDSHIPNOTIFICATION);
				Set<FriendshipRequestNotification> frRequests = new LinkedHashSet<FriendshipRequestNotification>();
				
				for (int i =0; i<friends.getLength(); i++)
				{
					u = (Element) friends.item(i);
					try{
						id = Long.parseLong(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_ID));
					} catch(NumberFormatException ex){
						throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the notification id to an int. Value: "+u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_ID));
					}
					
					
					try{
						dateMs = Long.parseLong(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_TIME));
						date = new Date(dateMs);
					} catch(NumberFormatException ex){
						throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the date time (ms) to long. Value: "+u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_TIME));
					}
					
					
					
					read = Boolean.parseBoolean(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_READ));
					accepted = Boolean.parseBoolean(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_FRIENDSHIP_ACCEPTED));
					
					
					displayName = u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_FRIENDSHIP_DISPLAYNAME);
					if (displayName== null)
						throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+displayName);
				
					username = u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_FRIENDSHIP_USERNAME);
					if (username== null)
						throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+username);
				
					
					frRequests.add(new FriendshipRequestNotification(id, date, read, displayName, username, accepted));
				}
				
				
				// QuickNotifications
	
				NodeList quickRes = e.getElementsByTagName(GetAllNotificationsAnswerMessage.SUBTAG_QUICKNOTIFICATION_RESULTS);		
				if (surroundCollaborationRes.getLength()!=1)
					throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GetAllNotificationsAnswerMessage.SUBTAG_QUICKNOTIFICATION_RESULTS+"> but got: "+surroundCollaborationRes.getLength());
				
				NodeList quicks = ((Element) surroundCollaborationRes.item(0)).getElementsByTagName(GetAllNotificationsAnswerMessage.SUBTAG_QUICKNOTIFICATION);
				Set<QuickNotification> quickResults = new LinkedHashSet<QuickNotification>();
				
				for (int i =0; i<quicks.getLength(); i++)
				{
					u = (Element) quicks.item(i);
					try{
						id = Long.parseLong(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_ID));
					} catch(NumberFormatException ex){
						throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the notification id to an int. Value: "+u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_ID));
					}
					
					
					try{
						dateMs = Long.parseLong(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_TIME));
						date = new Date(dateMs);
					} catch(NumberFormatException ex){
						throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the date time (ms) to long. Value: "+u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_TIME));
					}
					
					
					
					read = Boolean.parseBoolean(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_READ));
					
					
					optional = u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_QUICKNOTIFICATION_OPTIONAL);
					
					try{
						type = QuickNotificationType.fromConstant(Integer.parseInt(u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_QUICKNOTIFICATION_TYPE)));
						if (type== null)
							throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the quick notifiacation type. Value: null");
					} catch(NumberFormatException ex){
						throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the quick notification type constant (int) to QuickNotificationType. Value: "+u.getAttribute(GetAllNotificationsAnswerMessage.ATTRIBUTE_QUICKNOTIFICATION_TYPE));
					}
					
					quickResults.add(new QuickNotification(id, date, read, type, optional));
				}

				
				
		return new GetAllNotificationsAnswerMessage(referenceId, quickResults, expRequests, frRequests, colRequests);
	}


}
