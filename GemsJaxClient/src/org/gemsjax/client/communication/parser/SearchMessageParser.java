package org.gemsjax.client.communication.parser;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.communication.message.collaboration.CollaborationType;
import org.gemsjax.shared.communication.message.friend.FriendErrorAnswerMessage;
import org.gemsjax.shared.communication.message.search.CollaborationResult;
import org.gemsjax.shared.communication.message.search.ExperimentResult;
import org.gemsjax.shared.communication.message.search.GlobalSearchResultMessage;
import org.gemsjax.shared.communication.message.search.ReferenceableSearchMessage;
import org.gemsjax.shared.communication.message.search.SearchError;
import org.gemsjax.shared.communication.message.search.SearchResultErrorMessage;
import org.gemsjax.shared.communication.message.search.UserResult;
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
	
		    
		    if (childElement.getTagName().equals(GlobalSearchResultMessage.TAG))
		    	return parseResultMessage(referenceId, childElement);
		    else    
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
	
	
	
	private ReferenceableSearchMessage parseResultMessage(String referenceId, Element e) throws DOMException
	{
		int id;
		String displayName, username, profilePicture, name, ownerName;
		boolean colLaborator, coAdmin;
		CollaborationType colType;
		
		// Users
		NodeList surroundUserRes = e.getElementsByTagName(GlobalSearchResultMessage.SUBTAG_USER_RESULT);
		
		if (surroundUserRes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GlobalSearchResultMessage.SUBTAG_USER_RESULT+"> but got: "+surroundUserRes.getLength());
		
		NodeList users = ((Element) surroundUserRes.item(0)).getElementsByTagName(GlobalSearchResultMessage.SUBTAG_USER);
		Set<UserResult> userResults = new LinkedHashSet<UserResult>();
		
		Element u;
		for (int i =0; i<users.getLength(); i++)
		{
			u = (Element) users.item(i);
			try{
				id = Integer.parseInt(u.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_USER_ID));
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the user id to an int. Value: "+u.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_USER_ID));
			}
			
			displayName = u.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_USER_DISPLAY_NAME);
			if (displayName== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the display name. Value: "+u.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_USER_DISPLAY_NAME));
		
			username = u.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_USER_USERNAME);
			if (username== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the username. Value: "+u.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_USER_USERNAME));
		
			profilePicture = u.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_USER_PROFILE_PICTURE);
			if (profilePicture== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the profile picture url. Value: "+u.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_USER_PROFILE_PICTURE));
		
			userResults.add(new UserResult(id, username, displayName, profilePicture));
		}
		
		
		// Collaborations
		
		NodeList surroundColRes = e.getElementsByTagName(GlobalSearchResultMessage.SUBTAG_COLLABORATEABLE_RESULT);
		
		if (surroundColRes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GlobalSearchResultMessage.SUBTAG_COLLABORATEABLE_RESULT+"> but got: "+surroundColRes.getLength());
		
		NodeList collabs = ((Element)surroundColRes.item(0)).getElementsByTagName(GlobalSearchResultMessage.SUBTAG_COLLABORATEABLE);
		Set<CollaborationResult> collaborationResults = new LinkedHashSet<CollaborationResult>();
		
		Element c;
		for (int i =0; i<collabs.getLength(); i++)
		{
			c = (Element) collabs.item(i);
			try{
				id = Integer.parseInt(c.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_COLLABORATEABLE_ID));
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaborateable id to an int. Value: "+c.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_COLLABORATEABLE_ID));
			}
			
			name = c.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_COLLABORATEABLE_NAME);
			if (name== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaborateable name. Value: "+c.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_COLLABORATEABLE_NAME));
		
			ownerName = c.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_COLLABORATEABLE_OWNER_NAME);
			if (ownerName== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name of the owner of a collaborateable. Value: "+c.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_COLLABORATEABLE_OWNER_NAME));
		
			colType = CollaborationType.fromConstant(c.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_COLLABORATEABLE_TYPE));
			if (colType == null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the collaboration type. Value: "+c.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_COLLABORATEABLE_TYPE));
		
			
			colLaborator = Boolean.parseBoolean(c.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_COLLABORATEABLE_COLLABORATOR));
			
			collaborationResults.add(new CollaborationResult(id, name, ownerName, colLaborator, colType));
		}
		
		
	// Experiments
		
		NodeList surroundExpRes = e.getElementsByTagName(GlobalSearchResultMessage.SUBTAG_EXPERIMENT_RESULT);
		
		if (surroundExpRes.getLength()!=1)
			throw new DOMException(DOMException.SYNTAX_ERR,"Expected exactly one <"+GlobalSearchResultMessage.SUBTAG_EXPERIMENT_RESULT+"> but got: "+surroundExpRes.getLength());
		
		NodeList experiments = ((Element)surroundExpRes.item(0)).getElementsByTagName(GlobalSearchResultMessage.SUBTAG_EXPERIMENT);
		Set<ExperimentResult> experimentResults = new LinkedHashSet<ExperimentResult>();
		
		Element exp;
		for (int i =0; i<experiments.getLength(); i++)
		{
			exp = (Element) experiments.item(i);
			try{
				id = Integer.parseInt(exp.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_EXPERIMENT_ID));
			} catch(NumberFormatException ex){
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the experiment id to an int. Value: "+exp.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_EXPERIMENT_ID));
			}
			
			name = exp.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_EXPERIMENT_NAME);
			if (name== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the experiment name. Value: "+exp.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_EXPERIMENT_NAME));
		
			ownerName = exp.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_EXPERIMENT_OWNER_NAME);
			if (ownerName== null)
				throw new DOMException(DOMException.SYNTAX_ERR,"Could not parse the name of the owner of a experiment. Value: "+exp.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_EXPERIMENT_OWNER_NAME));
		
			
			coAdmin = Boolean.parseBoolean(exp.getAttribute(GlobalSearchResultMessage.ATTRIBUTE_EXPERIMENT_CO_ADMIN));
			
			experimentResults.add(new ExperimentResult(id, name, ownerName, coAdmin));
		}		
		
		
		return new GlobalSearchResultMessage(referenceId, userResults, collaborationResults, experimentResults);
	}


}
