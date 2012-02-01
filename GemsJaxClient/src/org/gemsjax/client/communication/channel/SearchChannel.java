package org.gemsjax.client.communication.channel;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.communication.channel.handler.SearchChannelHandler;
import org.gemsjax.client.communication.parser.SearchMessageParser;
import org.gemsjax.shared.RegExFactory;
import org.gemsjax.shared.communication.CommunicationConnection;
import org.gemsjax.shared.communication.CommunicationConnection.ErrorListener;
import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.InputMessage;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.search.GlobalSearchResultMessage;
import org.gemsjax.shared.communication.message.search.ReferenceableSearchMessage;
import org.gemsjax.shared.communication.message.search.SearchResultErrorMessage;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.xml.client.DOMException;

public class SearchChannel implements InputChannel, OutputChannel, ErrorListener{

	private CommunicationConnection connection;
	private RegExp regEx;
	private Set<SearchChannelHandler> handlers;
	private SearchMessageParser parser;
	
	public SearchChannel(CommunicationConnection connection)
	{
		this.connection = connection;
		this.connection.registerInputChannel(this);
		this.connection.addErrorListener(this);
		handlers = new LinkedHashSet<SearchChannelHandler>();
		regEx = RegExp.compile( RegExFactory.startWithTag(ReferenceableSearchMessage.TAG) );
		parser = new SearchMessageParser();
	}
	
	
	@Override
	public void send(Message message) throws IOException {
		connection.send(message);
	}

	@Override
	public void onMessageReceived(InputMessage msg) {
		try{
			
			ReferenceableSearchMessage m = parser.parseMessage(msg.getText());
			

			if (m instanceof GlobalSearchResultMessage)
				fireSearchResult((GlobalSearchResultMessage)m);
			else
			if (m instanceof SearchResultErrorMessage)
				fireErrorResult((SearchResultErrorMessage) m);
				
			
		}catch (DOMException e)
		{
			for (SearchChannelHandler h : handlers)
				h.onUnexpectedError(null, e);
		}
		
	}
	
	private void fireErrorResult(SearchResultErrorMessage e)
	{
		for (SearchChannelHandler h : handlers)
			h.onSearchResultError(e.getReferenceId(), e.getSearchError());	
	}
	
	
	private void fireSearchResult(GlobalSearchResultMessage m)
	{
		for (SearchChannelHandler h : handlers)
			h.onSearchResultReceived(m.getReferenceId(), m.getUserResults(), m.getCollaborationResults(), m.getExperimentResults());
	}
	

	@Override
	public boolean isMatchingFilter(String msg) {
		return regEx.test(msg);
	}


	@Override
	public void onError(Throwable t) {
		
		for (SearchChannelHandler h: handlers)
			h.onUnexpectedError(null, t);
		
	}

}
