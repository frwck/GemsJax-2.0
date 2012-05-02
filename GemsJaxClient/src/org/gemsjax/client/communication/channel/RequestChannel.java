package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.handler.RequestChannelHandler;
import org.gemsjax.client.communication.parser.RequestMessageParser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.request.GetAllRequestsAnswerMessage;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.RequestChangedAnswerMessage;
import org.gemsjax.shared.communication.message.request.RequestErrorMessage;
import org.gemsjax.shared.communication.message.request.RequestMessage;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.xml.client.DOMException;

public class RequestChannel implements InputChannel, OutputChannel{

	private CommunicationConnection connection;
	private RegExp filer;
	private RequestMessageParser parser;
	private Set<RequestChannelHandler> handlers;
	
	public RequestChannel(CommunicationConnection connection)
	{
		this.connection = connection;
		this.parser = new RequestMessageParser();
		this.filer = RegExp.compile(RegExFactory.startWithTag(RequestMessage.TAG));
		this.connection.registerInputChannel(this);
		handlers = new LinkedHashSet<RequestChannelHandler>();
	}

	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
	
		try{
			RequestMessage m = parser.parseMessage(msg.getText());
			
			if (m instanceof GetAllRequestsAnswerMessage)
				for (RequestChannelHandler h: handlers)
					h.onGetAllRequestsAnswer((GetAllRequestsAnswerMessage) m);
			
			else
			if (m instanceof LiveRequestMessage)
				for (RequestChannelHandler h: handlers)
					h.onLiveRequestReceived((LiveRequestMessage) m);
			else
			if (m instanceof RequestChangedAnswerMessage)
				for (RequestChannelHandler h: handlers)
					h.onRequestAnsweredSuccessfully(((RequestChangedAnswerMessage) m).getReferenceId());
			
			else
			if (m instanceof RequestErrorMessage)
				for (RequestChannelHandler h : handlers)
					h.onRequestError(((RequestErrorMessage) m).getReferenceId(), ((RequestErrorMessage) m).getRequestError());
			
		}
		catch (DOMException e)
		{
			for (RequestChannelHandler h: handlers)
				h.onParseError(e);
		}
		
	}
	
	public void addRequestChannelHandler(RequestChannelHandler h)
	{
		handlers.add(h);
	}
	
	
	public void removeRequestChannelHandler(RequestChannelHandler h)
	{
		handlers.remove(h);
	}
	
	@Override
	public boolean isMatchingFilter(String msg) {
		return filer.test(msg);
	}

	@Override
	public void onMessageRecieved(Message msg) {
		// TODO Auto-generated method stub
		
	}
	
}
