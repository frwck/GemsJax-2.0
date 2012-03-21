package org.gemsjax.shared.communication.message.request;

import java.util.Date;


public class Request {
	
	private Long id;
	private String requesterUsername;
	private String requesterDisplayName;
	private Date date;
	
	
	public Request(long id, String requesterDisplayName, String requesterUsername, Date date)
	{
		this.id = id;
		this.requesterDisplayName = requesterDisplayName;
		this.requesterUsername = requesterUsername;
		this.date = date;
	}
	
	

	public long getId() {
		return id;
	}


	public String getRequesterUsername() {
		return requesterUsername;
	}


	public String getRequesterDisplayName() {
		return requesterDisplayName;
	}


	public Date getDate() {
		return date;
	}

	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof Request) ) return false;
		
		final Request that = (Request) other;
		
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
