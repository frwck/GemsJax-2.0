package org.gemsjax.server.persistence.request;

import java.util.Date;

import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.request.Request;
import org.gemsjax.shared.request.Request.RequestState;
import org.gemsjax.shared.user.RegisteredUser;

public class RequestImpl implements Request {
	
	
	/**
	 * This field is used internally for hibernate, to realise a mapping between
	 * {@link RequestState} and a database value
	 */
	private int requestState;
	
	
	private Long id;
	private Date date;
	private RegisteredUser sender;
	private RegisteredUser receiver;
	
	
	
	@Override
	public Date getDate() {
		return date;
	}
	@Override
	public long getId() {
		return id;
	}
	@Override
	public RegisteredUser getReceiver() {
		return receiver;
	}
	@Override
	public RequestState getRequestState() {
		
		switch(requestState)
		{
			case 0:	return RequestState.NEW;
			case 1: return RequestState.ACCEPT;
			case 2: return RequestState.REJECT;
		}
		
		return RequestState.NEW;
	}

	@Override
	public RegisteredUser getSender() {
		return sender;
	}
	@Override
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public void setReceiver(RegisteredUser receiver) {
		this.receiver = receiver;
	}
	@Override
	public void setSender(RegisteredUser sender) {
		this.sender = sender;
	}
	@Override
	public void setRequestState(RequestState state) {
		
		switch(state)
		{
		case NEW: requestState = 0; break;
		case ACCEPT: requestState = 1; break;
		case REJECT: requestState = 2; break;
		}
		
	}
	
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof RequestImpl) ) return false;
		
		final RequestImpl that = (RequestImpl) other;
		
		if (id != null && that.id != null)
			return this.id.equals(that.id);
		
		return false;
	}
		
	@Override
	public int hashCode() {
		if (id != null)
			return id.hashCode();
		else
			return super.hashCode();
	}
	
}
