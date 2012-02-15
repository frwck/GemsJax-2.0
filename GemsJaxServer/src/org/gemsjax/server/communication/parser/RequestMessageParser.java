package org.gemsjax.server.communication.parser;

import java.io.IOException;
import java.io.StringReader;

import org.gemsjax.shared.communication.message.request.AcceptRequestMessage;
import org.gemsjax.shared.communication.message.request.GetAllRequestsMessage;
import org.gemsjax.shared.communication.message.request.ReferenceableRequestMessage;
import org.gemsjax.shared.communication.message.request.RejectRequestMessage;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author Hannes Dorfmann
 *
 */
public class RequestMessageParser extends AbstractContentHandler {
	
	private boolean startRequest, endRequest;
	private boolean startAccept, endAccept;
	private boolean startReject, endReject;
	private boolean startGetAll, endGetAll;
	private String referenceId;
	private Integer requestId;
	
	
	
	public RequestMessageParser(){	
		
	}
	
	/**
	 * 
	 * @param xml The xml representation of a {@link LoginMessage}, which is parsed to a {@link LoginMessage} object
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public ReferenceableRequestMessage parse(String xml) throws SAXException, IOException
	{
		XMLReader xmlReader = XMLReaderFactory.createXMLReader();
	    InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml));
	    
	    inputSource.setEncoding("UTF-8");
	    xmlReader.setContentHandler(this);
	    xmlReader.parse(inputSource);
	    
	   
	    if (startAccept && endAccept) 
	    	return new AcceptRequestMessage(referenceId, requestId);
	    
	    if (startReject && endReject)
	    	return new RejectRequestMessage(referenceId, requestId);
	    
	    if (startGetAll && endGetAll)
	    	return new GetAllRequestsMessage(referenceId);
	    	
	    throw new SAXException("Unexcpected Parse error: Could not determine the type of the received message");
	}

	
	
	

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		
		if (localName.equals(AcceptRequestMessage.TAG))
			endAccept = true;
		else
		if (localName.equals(RejectRequestMessage.TAG)){
			endReject = true;
		}
		else
		if (localName.equals(ReferenceableRequestMessage.TAG)){
			endRequest = true;
		}
		else
		if (localName.equals(GetAllRequestsMessage.TAG)){
			endGetAll = true;
		}
		
	}


	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		
		if (localName.equals(ReferenceableRequestMessage.TAG))
		{
			startRequest = true;
			referenceId = atts.getValue(ReferenceableRequestMessage.ATTRIBUTE_REFERENCE_ID);
		}
		else
		if (localName.equals(AcceptRequestMessage.TAG)){
			startAccept = true;
			try{
				requestId = Integer.parseInt(atts.getValue(AcceptRequestMessage.ATTRIBUTE_REQUEST_ID));
			}
			catch (NumberFormatException e)
			{
				throw new SAXException("Could not parse the request id. Value is: "+atts.getValue(AcceptRequestMessage.ATTRIBUTE_REQUEST_ID));
			}
		}
			
		else
		if (localName.equals(RejectRequestMessage.TAG)){
			startReject = true;
			try{
				requestId = Integer.parseInt(atts.getValue(RejectRequestMessage.ATTRIBUTE_REQUEST_ID));
			}
			catch (NumberFormatException e)
			{
				throw new SAXException("Could not parse the request id. Value is: "+atts.getValue(AcceptRequestMessage.ATTRIBUTE_REQUEST_ID));
			}
		}
		else
		if (localName.equals(GetAllRequestsMessage.TAG)){	
			startGetAll = true;
		}
	
	}
	


	@Override
	public void endDocument() throws SAXException {
		
		if (!startRequest)
			throw new SAXException("Start <"+ReferenceableRequestMessage.TAG+"> Tag not found");
		
		if (!endRequest)
			throw new SAXException("End </"+ReferenceableRequestMessage.TAG+"> Tag not found");
		
		if (startAccept != endAccept)
			throw new SAXException("<"+AcceptRequestMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (startReject != endReject)
			throw new SAXException("<"+RejectRequestMessage.TAG+"> missmatch: An opening or closing tag is missing");
		

		if (startGetAll != endGetAll)
			throw new SAXException("<"+GetAllRequestsMessage.TAG+"> missmatch: An opening or closing tag is missing");
		
		if (referenceId == null)
			throw new SAXException("Reference Id is missing");
		
		if (requestId == null)
			throw new SAXException("Search string is missing");
		
		
		if ((startAccept && startReject) || (startAccept && startGetAll) || (startReject && startGetAll))
			throw new SAXException("The received message is a <"+AcceptRequestMessage.TAG+"> and <"+RejectRequestMessage.TAG+"> at the same time. Thats not allowed.");
		
		
	}


	@Override
	public void startDocument() throws SAXException {
		
		startAccept = false;
		endAccept = false;
		startReject = false;
		endReject = false;
		startRequest = false;
		endRequest = false;
		startGetAll = false;
		endGetAll = false;
		
		referenceId = null;
		requestId = null;
		
	}
	
	
	
	public String getCurrentReferenceId()
	{
		return referenceId;
	}

}
