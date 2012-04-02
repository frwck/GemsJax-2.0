package org.gemsjax.server.communication.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.communication.message.collaborateablefile.CollaborationType;
import org.gemsjax.shared.communication.message.collaborateablefile.GetAllCollaborateablesMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.NewCollaborateableFileMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.ReferenceableCollaborateableFileMessage;
import org.gemsjax.shared.communication.message.collaborateablefile.UpdateCollaborateableFileMessage;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 
 * @author Hannes Dorfmann
 */
public class CollaboratableFileMessageParser extends AbstractContentHandler {
	
	private boolean startFile, endFile;
	private boolean startNew, endNew;
	private boolean startUpdate, endUpdate;
	private boolean startGetAll, endGetAll;
	
	private boolean startKeywords, endKeywords;
	private boolean startAdmins, endAdmins;
	private boolean startCollaborators, endCollaborators;
	
	private boolean startAdmin, endAdmin;
	private boolean startCollaborator, endCollaborator;
	
	
	private String referenceId;
	
	private CollaborationType type;
	private String name;
	private Boolean _public;
	private String keywords;
	private Set<Integer> addAdminIds;
	private Set<Integer> removeAdminIds;
	private Set<Integer> addCollaboratorIds;
	private Set<Integer> removeCollaboratorIds;
	private Integer collaborateableId;
	
	
	
	
	public CollaboratableFileMessageParser(){	
		
	}
	
	/**
	 * 
	 * @param xml The xml representation of a {@link LoginMessage}, which is parsed to a {@link LoginMessage} object
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public ReferenceableCollaborateableFileMessage parse(String xml) throws SAXException, IOException
	{
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	    InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml));
	    
	    inputSource.setEncoding("UTF-8");
	    xmlReader.setContentHandler(this);
	    xmlReader.parse(inputSource);
	    
	   
	    if (startNew && endNew) 
	    {
	    	if (referenceId!=null)	
	    		return new NewCollaborateableFileMessage(referenceId, name, type, addAdminIds, addCollaboratorIds, _public, keywords);
	    	else
	    		throw new SAXException("reference id is null");
	    }
	    
	    if (startUpdate && endUpdate)
	    	if (referenceId!=null)	
		    	if (collaborateableId!=null){
		    		UpdateCollaborateableFileMessage cm = new UpdateCollaborateableFileMessage(referenceId, collaborateableId);
		    		cm.setAddAdminIds(addAdminIds);
		    		cm.setRemoveAdminIds(removeAdminIds);
		    		cm.setName(name);
		    		cm.setKeywords(keywords);
		    		cm.setPublic(_public);
		    		cm.setAddCollaboratorIds(addCollaboratorIds);
		    		cm.setRemoveCollaboratorIds(removeCollaboratorIds);
		    		return cm;
		    	}
    		else
    			throw new SAXException("Collaborateable Id is null");
    	else
    		throw new SAXException("reference id is null");
	    	
	    
	    if (startGetAll && endGetAll){
	    	if (referenceId!=null)
	    		return new GetAllCollaborateablesMessage(referenceId);
	    	else
	    		throw new SAXException("Reference Id was null");
	    }
	    throw new SAXException("Unexcpected Parse error: Could not determine the type of the received message");
	}

	
	
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		if (localName.equals(ReferenceableCollaborateableFileMessage.TAG))
		{
			endFile = true;
		}
		else
		if (localName.equals(NewCollaborateableFileMessage.TAG)){
			endNew = true;
		}
		else
		if (localName.equals(UpdateCollaborateableFileMessage.TAG)){
			endUpdate = true;
		}
		else
		if (localName.equals(GetAllCollaborateablesMessage.TAG))
			endGetAll = true;
		else
		if (endNew){ // if its a new messages
			
			if (localName.equals(NewCollaborateableFileMessage.SUBTAG_KEYWORDS)){
				endKeywords = true;
				keywords = getCurrentValue();
			}
			else
			if (localName.equals(NewCollaborateableFileMessage.SUBTAG_ADMINS)){	
				endAdmins = true;
			}
			else
			if (localName.equals(NewCollaborateableFileMessage.SUBTAG_COLLABORATORS)){	
				endCollaborators = true;
			}
			
		}
		else
		if (endUpdate){ // if its a update message
			if (localName.equals(UpdateCollaborateableFileMessage.SUBTAG_KEYWORDS)){
				endKeywords = true;
				keywords = getCurrentValue();
			}
			else
			if (localName.equals(UpdateCollaborateableFileMessage.SUBTAG_ADMINS)){	
				endAdmins = true;
			}
			else
			if (localName.equals(UpdateCollaborateableFileMessage.SUBTAG_COLLABORATORS)){	
				endCollaborators = true;
			}
		
		}

		
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		if (localName.equals(ReferenceableCollaborateableFileMessage.TAG))
		{
			startFile = true;
			referenceId = atts.getValue(ReferenceableCollaborateableFileMessage.ATTRIBUTE_REFERENCE_ID);
		}
		else
		if (localName.equals(NewCollaborateableFileMessage.TAG)){
			startNew = true;
			name = atts.getValue(NewCollaborateableFileMessage.ATTRIBUTE_NAME);
			_public = Boolean.parseBoolean(NewCollaborateableFileMessage.ATTRIBUTE_PUBLIC);
			type = CollaborationType.fromConstant(atts.getValue(NewCollaborateableFileMessage.ATTRIBUTE_TYPE));
			if (type==null)
				throw new SAXException("Could not parse or dertermine the CollabortaionType");
		}
		else
		if (localName.equals(UpdateCollaborateableFileMessage.TAG)){
			startUpdate = true;
			try{
				collaborateableId = Integer.parseInt(atts.getValue(UpdateCollaborateableFileMessage.ATTRIBUTE_COLLABORATEABLE_ID));
			}
			catch (NumberFormatException e)
			{
				throw new SAXException("Could not parse the collaborateable id. Value is: "+atts.getValue(UpdateCollaborateableFileMessage.ATTRIBUTE_COLLABORATEABLE_ID));
			}
		}
		else
		if (localName.equals(GetAllCollaborateablesMessage.TAG))
			startGetAll = true;
		else
		if (startNew){ // if its a new messages
			
			if (localName.equals(NewCollaborateableFileMessage.SUBTAG_KEYWORDS)){
				startKeywords = true;
				
			}
			else
			if (localName.equals(NewCollaborateableFileMessage.SUBTAG_ADMINS)){	
				startAdmins = true;
			}
			else
			if (localName.equals(NewCollaborateableFileMessage.SUBSUBTAG_ADD_ADMIN))
				try{
					addAdminIds.add(Integer.parseInt(NewCollaborateableFileMessage.ATTRIBUTE_ADMIN_ID));
				}
				catch(NumberFormatException e){
					throw new SAXException("Could not parse a Admin ID");
				}
			else
			if (localName.equals(NewCollaborateableFileMessage.SUBTAG_COLLABORATORS)){	
				startCollaborators = true;
			}
			else
			if (localName.equals(NewCollaborateableFileMessage.SUBSUBTAG_ADD_COLLABORATOR))
				try{
					addCollaboratorIds.add(Integer.parseInt(NewCollaborateableFileMessage.ATTRIBUTE_COLLABORATOR_ID));
				}
				catch(NumberFormatException e){
					throw new SAXException("Could not parse a Collaborator ID");
				}
		}
		else
		if (startUpdate){ // if its a update message
			if (localName.equals(UpdateCollaborateableFileMessage.SUBTAG_KEYWORDS)){
				startKeywords = true;
			}
			else
			if (localName.equals(UpdateCollaborateableFileMessage.SUBTAG_ADMINS)){	
				startAdmins = true;
			}
			else
			if (localName.equals(UpdateCollaborateableFileMessage.SUBSUBTAG_ADD_ADMIN))
				try{
					addAdminIds.add(Integer.parseInt(UpdateCollaborateableFileMessage.ATTRIBUTE_ADMIN_ID));
				}
				catch(NumberFormatException e){
					throw new SAXException("Could not parse a Admin ID");
				}
			else
			if (localName.equals(UpdateCollaborateableFileMessage.SUBTAG_COLLABORATORS)){	
				startCollaborators = true;
			}
			else
			if (localName.equals(UpdateCollaborateableFileMessage.SUBSUBTAG_ADD_COLLABORATOR))
				try{
					addCollaboratorIds.add(Integer.parseInt(UpdateCollaborateableFileMessage.ATTRIBUTE_COLLABORATOR_ID));
				}
				catch(NumberFormatException e){
					throw new SAXException("Could not parse a Collaborator ID");
				}
		
		}
	
	}
	


	@Override
	public void endDocument() throws SAXException {
		
		if (!startFile)
			throw new SAXException("Start <"+ReferenceableCollaborateableFileMessage.TAG+"> Tag not found");
		
		if (!endFile)
			throw new SAXException("End </"+ReferenceableCollaborateableFileMessage.TAG+"> Tag not found");
		
		if (startNew != endNew)
			throw new SAXException("<"+NewCollaborateableFileMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (startUpdate != endUpdate)
			throw new SAXException("<"+UpdateCollaborateableFileMessage.TAG+"> missmatch: An opening or closing tag is missing");
		

		if (startGetAll != endGetAll)
			throw new SAXException("<"+GetAllCollaborateablesMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		
		if ((startNew && startUpdate) || (startNew && startGetAll) || (startUpdate && startGetAll))
			throw new SAXException("The received message is a <"+NewCollaborateableFileMessage.TAG+"> and <"+UpdateCollaborateableFileMessage.TAG+"> at the same time. Thats not allowed.");
		
	}


	@Override
	public void startDocument() throws SAXException {
		
		startNew = false;
		endNew = false;
		startUpdate = false;
		endUpdate = false;
		startFile = false;
		endFile = false;
		startGetAll = false;
		endGetAll = false;
		
		startAdmins = false;
		endAdmins = false;
		startCollaborators = false;
		endCollaborators = false;
		
		referenceId = null;
		collaborateableId = null;
		addAdminIds = new LinkedHashSet<Integer>();
		addCollaboratorIds = new LinkedHashSet<Integer>();
		removeAdminIds = new LinkedHashSet<Integer>();
		removeCollaboratorIds = new LinkedHashSet<Integer>();
		type = null;
		_public = null;
		keywords = null;
		
		
		
	}
	
	
	
	public String getCurrentReferenceId()
	{
		return referenceId;
	}

}
