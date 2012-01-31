package org.gemsjax.client.communication.parser;

import java.util.LinkedHashSet;
import java.util.Set;
import org.gemsjax.shared.communication.message.friend.AllFriendsAnswerMessage;
import org.gemsjax.shared.communication.message.friend.CancelFriendshipAnswerMessage;
import org.gemsjax.shared.communication.message.friend.Friend;
import org.gemsjax.shared.communication.message.friend.FriendError;
import org.gemsjax.shared.communication.message.friend.FriendErrorAnswerMessage;
import org.gemsjax.shared.communication.message.friend.FriendMessage;
import org.gemsjax.shared.communication.message.friend.FriendUpdateMessage;
import org.gemsjax.shared.communication.message.friend.FriendshipCanceledMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendAddedMessage;
import org.gemsjax.shared.communication.message.friend.NewFriendshipRequestAnswerMessage;
import org.gemsjax.shared.communication.message.friend.ReferenceableFriendMessage;
import org.gemsjax.shared.communication.message.system.LoginAnswerMessage;
import org.gemsjax.shared.user.UserOnlineState;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

public class FriendMessageParser {

	/**
	 * Parse a {@link FriendMessage} from a String.
	 * See the communication protocol specification.
	 * @param messageXml
	 * @return
	 * @throws DOMException
	 */
	public FriendMessage parseMessage(String messageXml) throws DOMException{
		
		// parse the XML document into a DOM
		    Document messageDom = XMLParser.parse(messageXml);
		    
		    NodeList surroundingElement = messageDom.getElementsByTagName(FriendMessage.TAG);
		    
		    if (surroundingElement == null)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "No tag <"+FriendMessage.TAG+"> found");
		 
		    if (surroundingElement.getLength()!=1)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "No or more than one <"+FriendMessage.TAG+"> tag found");
			
		    
		    

		    // Check for reference Number
		    String referenceId = ((Element)surroundingElement.item(0)).getAttribute(ReferenceableFriendMessage.ATTRIBUTE_REFERENCE_ID);
		    
		    
		    Node systemElement = surroundingElement.item(0);
		    
		    NodeList childNodes = systemElement.getChildNodes();
		    
		    if(childNodes== null || childNodes.getLength() == 0)
		      	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+FriendMessage.TAG+"> does not contain child tags");
			
		    
		    if (childNodes.getLength()!=1)
		    	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+FriendMessage.TAG+"> contains more that one child tags");
			
		    Element childElement = (Element)childNodes.item(0);
		    
		    if (childElement.getTagName().equals(AllFriendsAnswerMessage.TAG))
		    	return parseAddFriendsAnswer(referenceId, childElement);
		    else
		    if (childElement.getTagName().equals(FriendUpdateMessage.TAG))
		    	return parseFriendUpdateMessage(childElement);
		    else
		    if (childElement.getTagName().equals(FriendErrorAnswerMessage.TAG))
		    	return parseFriendErrorMessage(referenceId, childElement);
		    else
		    if (childElement.getTagName().equals(FriendshipCanceledMessage.TAG))
		    	return parseFriendShipChanceledMessage(childElement);
		    else
		    if (childElement.getTagName().equals(CancelFriendshipAnswerMessage.TAG))
		    	return parseCancelFriendshipAnswerMessage(referenceId, childElement);
		    
		    else
		    if (childElement.getTagName().equals(NewFriendshipRequestAnswerMessage.TAG))
			  	return parseFriendshipRequestAnswer(referenceId, childElement);
		    
		    else
		    if (childElement.getTagName().equals(NewFriendAddedMessage.TAG))
				 return parseNewFriendAdded( childElement);
				   
		    
		    // If nothing could be parsed
		 	throw new DOMException(DOMException.SYNTAX_ERR, "The <"+FriendErrorAnswerMessage.TAG+"> was found, but contains an unknown child tag <"+childElement.getTagName()+">");
	}
	
	
	
	private FriendMessage parseNewFriendAdded(Element e) {
		
		NodeList friendNodes = e.getChildNodes();

		if (friendNodes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+NewFriendAddedMessage.SUBTAG_FRIEND+"> but got "+friendNodes.getLength());

		Element fe = (Element) friendNodes.item(0);

		if (!fe.getTagName().equals(FriendUpdateMessage.SUBTAG_FRIEND))
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected a <"+NewFriendAddedMessage.SUBTAG_FRIEND+"> but got "+fe.getTagName());

		String idAtt = fe.getAttribute(NewFriendAddedMessage.ATTRIBUTE_ID);
		String dispName=fe.getAttribute(NewFriendAddedMessage.ATTRIBUTE_DISPLAY_NAME);
		String onState=fe.getAttribute(NewFriendAddedMessage.ATTRIUBTE_ONLINE_STATE);
		String picture = fe.getAttribute(NewFriendAddedMessage.ATTRIBUTE_PROFILE_PICTURE);

		int id;
		if (idAtt == null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The id of a friend was null");

		try{
			id = Integer.parseInt(idAtt);

		}catch(NumberFormatException ex)
		{
			throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the id to a Integer. Value: "+idAtt);
		}



		if (dispName==null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The display name of a friend was null");

		if (onState==null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The online state of a friend was null");

		UserOnlineState onlineStatus = UserOnlineState.toOnlineState(onState);

		if (onlineStatus==null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The online state of a friend was "+onState+". Thats undefined!");


		Friend friend = new Friend(id, dispName, onlineStatus, picture);
		
		
		return new NewFriendAddedMessage(friend);
	
	}



	/**
	 * Parse a {@link FriendshipCanceledMessage}
	 * @param e
	 * @return
	 */
	private FriendshipCanceledMessage parseFriendShipChanceledMessage(Element e)
	{
		NodeList friendNodes = e.getChildNodes();
		
		if (friendNodes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+FriendshipCanceledMessage.SUBTAG_FRIEND+"> but got "+friendNodes.getLength());
		
		Set<Integer> ids = new LinkedHashSet<Integer>();
		for (int i =0; i<friendNodes.getLength(); i++)
		{
			
			Element fe = (Element) friendNodes.item(i);
			
			if (!fe.getTagName().equals(FriendshipCanceledMessage.SUBTAG_FRIEND))
				throw new DOMException(DOMException.SYNTAX_ERR,"Expected a <"+FriendshipCanceledMessage.SUBTAG_FRIEND+"> but got "+fe.getTagName());
			
			String idAtt = fe.getAttribute(FriendUpdateMessage.ATTRIBUTE_ID);
		
			int id;
			if (idAtt == null)
				throw new DOMException(DOMException.SYNTAX_ERR, "The id of a friend was null");
			
			try{
				id = Integer.parseInt(idAtt);
				
			}catch(NumberFormatException ex)
			{
				throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the id to a Integer. Value: "+idAtt);
			}
			
			
			ids.add(id);
			
		}	
		
		return new FriendshipCanceledMessage(ids);
	
		
	}
	
	
	
	private FriendMessage parseFriendshipRequestAnswer(String referenceId, Element e)
	{
		NodeList friendNodes = e.getChildNodes();

		if (friendNodes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+NewFriendshipRequestAnswerMessage.SUBTAG_FRIEND+"> but got "+friendNodes.getLength());

		Element fe = (Element) friendNodes.item(0);

		if (!fe.getTagName().equals(FriendUpdateMessage.SUBTAG_FRIEND))
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected a <"+NewFriendshipRequestAnswerMessage.SUBTAG_FRIEND+"> but got "+fe.getTagName());

		String idAtt = fe.getAttribute(NewFriendshipRequestAnswerMessage.ATTRIBUTE_FRIEND_ID);
		String dispName=fe.getAttribute(NewFriendshipRequestAnswerMessage.ATTRIBUTE_DISPLAY_NAME);
		String onState=fe.getAttribute(NewFriendshipRequestAnswerMessage.ATTRIBUTE_ONLINE_STATE);
		String picture = fe.getAttribute(NewFriendshipRequestAnswerMessage.ATTRIBUTE_PROFILE_PICTURE);

		int id;
		if (idAtt == null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The id of a friend was null");

		try{
			id = Integer.parseInt(idAtt);

		}catch(NumberFormatException ex)
		{
			throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the id to a Integer. Value: "+idAtt);
		}



		if (dispName==null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The display name of a friend was null");

		if (onState==null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The online state of a friend was null");

		UserOnlineState onlineStatus = UserOnlineState.toOnlineState(onState);

		if (onlineStatus==null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The online state of a friend was "+onState+". Thats undefined!");


		Friend friend = new Friend(id, dispName, onlineStatus, picture);
		
		
		return new NewFriendshipRequestAnswerMessage(referenceId, friend);
	
	}
	
	
	
	/**
	 * Parse a {@link AllFriendsAnswerMessage}
	 * @param referenceId 
	 * @param e The start {@link Element} with the tag {@link AllFriendsAnswerMessage#TAG}
	 * @return The parsed {@link LoginAnswerMessage}
	 * @throws DOMException if a an parse error has occurred
	 */
	private AllFriendsAnswerMessage parseAddFriendsAnswer(String referenceId, Element e) throws DOMException
	{
		NodeList friendNodes = e.getChildNodes();
		Element current;
		
		Set<Friend> friends = new LinkedHashSet<Friend>();
		int id;
		String dispName;
		String onState;
		String picture;
		String idAtt;
		UserOnlineState onlineStatus = null;
		
		
		for (int i=0; i<friendNodes.getLength(); i++)
		{
			onlineStatus = null;
			current = (Element)friendNodes.item(i);
			// check if its a expected friend element
			
			if (!current.getTagName().equals(AllFriendsAnswerMessage.SUBTAG_FRIEND))
				throw new DOMException(DOMException.SYNTAX_ERR, "A <"+AllFriendsAnswerMessage.SUBTAG_FRIEND+"> is expected, but a <"+current.getTagName()+"> was received");
			
			
			idAtt = current.getAttribute(AllFriendsAnswerMessage.ATTRIBUTE_ID);
			dispName=current.getAttribute(AllFriendsAnswerMessage.ATTRIBUTE_DISPLAY_NAME);
			onState=current.getAttribute(AllFriendsAnswerMessage.ATTRIUBTE_ONLINE_STATE);
			picture = current.getAttribute(AllFriendsAnswerMessage.ATTRIBUTE_PROFILE_PICTURE);
			
			if (idAtt == null)
				throw new DOMException(DOMException.SYNTAX_ERR, "The id of a friend was null");
			
			try{
				id = Integer.parseInt(idAtt);
				
			}catch(NumberFormatException ex)
			{
				throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the id to a Integer. Value: "+idAtt);
			}
			
			
			
			if (dispName==null)
				throw new DOMException(DOMException.SYNTAX_ERR, "The display name of a friend was null");
			
			if (onState==null)
				throw new DOMException(DOMException.SYNTAX_ERR, "The online state of a friend was null");
			
			onlineStatus = UserOnlineState.toOnlineState(onState);
			
			if (onlineStatus==null)
				throw new DOMException(DOMException.SYNTAX_ERR, "The online state of a friend was "+onState+". Thats undefined!");
			
			
			// Finally all attributes are corect
			
			friends.add(new Friend(id, dispName, onlineStatus, picture));
		}
		
			
		return new AllFriendsAnswerMessage(referenceId, friends);
	}
	
	
	/**
	 * Parse a {@link FriendUpdateMessage}
	 * @param e The start element with the tag {@link FriendUpdateMessage#TAG}
	 * @return the parsed {@link FriendUpdateMessage}
	 * @throws DOMException if an parse has error occurred
	 */
	private FriendUpdateMessage parseFriendUpdateMessage(Element e) throws DOMException
	{
		NodeList friendNodes = e.getChildNodes();

		if (friendNodes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+FriendUpdateMessage.SUBTAG_FRIEND+"> but got "+friendNodes.getLength());

		Element fe = (Element) friendNodes.item(0);

		if (!fe.getTagName().equals(FriendUpdateMessage.SUBTAG_FRIEND))
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected a <"+FriendUpdateMessage.SUBTAG_FRIEND+"> but got "+fe.getTagName());

		String idAtt = fe.getAttribute(FriendUpdateMessage.ATTRIBUTE_ID);
		String dispName=fe.getAttribute(FriendUpdateMessage.ATTRIBUTE_DISPLAY_NAME);
		String onState=fe.getAttribute(FriendUpdateMessage.ATTRIUBTE_ONLINE_STATE);
		String picture = fe.getAttribute(FriendUpdateMessage.ATTRIBUTE_PROFILE_PICTURE);

		int id;
		if (idAtt == null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The id of a friend was null");

		try{
			id = Integer.parseInt(idAtt);

		}catch(NumberFormatException ex)
		{
			throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the id to a Integer. Value: "+idAtt);
		}



		if (dispName==null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The display name of a friend was null");

		if (onState==null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The online state of a friend was null");

		UserOnlineState onlineStatus = UserOnlineState.toOnlineState(onState);

		if (onlineStatus==null)
			throw new DOMException(DOMException.SYNTAX_ERR, "The online state of a friend was "+onState+". Thats undefined!");


		Friend friend = new Friend(id, dispName, onlineStatus, picture);

		return new FriendUpdateMessage(friend);

	}
	
	
	
	private FriendErrorAnswerMessage parseFriendErrorMessage(String referenceId, Element e) throws DOMException
	{
		String typeAttribute = e.getAttribute(FriendErrorAnswerMessage.ATTRIBUTE_TYPE);
		FriendError type;
	
			type = FriendError.fromConstant(typeAttribute);
			
		if (type==null)
			throw new DOMException(DOMException.SYNTAX_ERR,"FriendError type is null (after parsing)");
			
		String additionalInfo = e.getNodeValue();
		
		return new FriendErrorAnswerMessage(referenceId,type, additionalInfo);
		
	}
	
	
	private FriendMessage parseCancelFriendshipAnswerMessage(String referenceId, Element e)
	{
		NodeList friendNodes = e.getChildNodes();
		Element current;
		
		Set<Integer> exFriendsIds = new LinkedHashSet<Integer>();
		int id;
		String idAtt;
		
		for (int i=0; i<friendNodes.getLength(); i++)
		{
			current = (Element)friendNodes.item(i);
			// check if its a expected friend element
			
			if (!current.getTagName().equals(CancelFriendshipAnswerMessage.SUBTAG_FRIEND))
				throw new DOMException(DOMException.SYNTAX_ERR, "A <"+CancelFriendshipAnswerMessage.SUBTAG_FRIEND+"> is expected, but a <"+current.getTagName()+"> was received");
			
			
			idAtt = current.getAttribute(CancelFriendshipAnswerMessage.ATTRIBUTE_FRIEND_ID);
			
			if (idAtt == null)
				throw new DOMException(DOMException.SYNTAX_ERR, "The id of a exfriend was null");
			
			try{
				id = Integer.parseInt(idAtt);
				exFriendsIds.add(id);
			}catch(NumberFormatException ex)
			{
				throw new DOMException(DOMException.SYNTAX_ERR, "Could not parse the id to a Integer. Value: "+idAtt);
			}
			
		}
		
		
		return new CancelFriendshipAnswerMessage(referenceId, exFriendsIds);
	}
}
