package org.gemsjax.shared.communication.message.request;

import java.util.Date;

public class Request {
	
	private int id;
	private String requesterUsername;
	private String requesterDisplayName;
	private Date date;
	
	
	public Request(int id, String requesterDisplayName, String requesterUsername, Date date)
	{
		this.id = id;
		this.requesterDisplayName = requesterDisplayName;
		this.requesterUsername = requesterUsername;
		this.date = date;
	}
	
	

	public int getId() {
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

}