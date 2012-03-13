package org.gemsjax.server.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.http.protocol.RequestExpectContinue;
import org.gemsjax.server.communication.channel.handler.RequestChannelHandler;
import org.gemsjax.server.communication.parser.RequestMessageParser;
import org.gemsjax.server.module.OnlineUser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.request.AcceptRequestMessage;
import org.gemsjax.shared.communication.message.request.GetAllRequestsMessage;
import org.gemsjax.shared.communication.message.request.ReferenceableRequestMessage;
import org.gemsjax.shared.communication.message.request.RejectRequestMessage;
import org.gemsjax.shared.communication.message.request.RequestError;
import org.gemsjax.shared.communication.message.request.RequestErrorMessage;
import org.gemsjax.shared.communication.message.request.RequestMessage;
import org.xml.sax.SAXException;

public class RequestChannel implements OutputChannel, InputChannel {
	
	private CommunicationConnection connection;
	private String regexFilter;
	private Set<RequestChannelHandler> handlers;
	private OnlineUser user;
	
	
	public RequestChannel(CommunicationConnection connection, OnlineUser user)
	{
		this.connection = connection;
		connection.registerInputChannel(this);
		this.regexFilter = RegExFactory.startWithTag(RequestMessage.TAG);
		this.handlers = new LinkedHashSet<RequestChannelHandler>();
		this.user = user;
	}

	
	public void addRequestChannelHandler(RequestChannelHandler h)
	{
		this.handlers.add(h);
	}
	
	public void removeRequestChannelHandler(RequestChannelHandler h)
	{
		this.handlers.remove(h);
	}
	
	
	@Override
	public boolean isMatchingFilter(String m) {
		return m.matches(regexFilter);
	}

	@Override
	public void onMessageReceived(InputMessage m) {
		RequestMessageParser parser = new RequestMessageParser();
		
		try {
			ReferenceableRequestMessage rm = parser.parse(m.getText());
			
			if (rm instanceof GetAllRequestsMessage)
			{
				for (RequestChannelHandler h: handlers)
					h.onGetAllRequests(user, rm.getReferenceId());
			}
			else
			if (rm instanceof AcceptRequestMessage)
				for (RequestChannelHandler h: handlers)
					h.onAcceptRequest(user, ((AcceptRequestMessage) rm).getRequestId(), rm.getReferenceId());
			else
			if (rm instanceof RejectRequestMessage)
				for (RequestChannelHandler h: handlers)
					h.onRejectRequest(user, ((RejectRequestMessage) rm).getRequestId(), rm.getReferenceId());
			
		} catch (SAXException e) {
			try {
				send(new  RequestErrorMessage(parser.getCurrentReferenceId(), RequestError.PARSING));
			} catch (IOException e1) {
				//TODO what to do, if could not send message
				e1.printStackTrace();
			}
		} catch (IOException e) {
			try {
				send(new  RequestErrorMessage(parser.getCurrentReferenceId(), RequestError.PARSING));
			} catch (IOException e1) {
				//TODO what to do, if could not send message
				e1.printStackTrace();
			}
		}
		
	}

	@Override
	public void send(Message m) throws IOException {
		connection.send(m);
	}

}
