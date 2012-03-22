package org.gemsjax.shared.communication.message.notification;

import java.util.Date;

import org.gemsjax.shared.communication.message.request.Request;

public class Notification {

	private Long id;
	private Date date;
	private boolean read;
	
	
	public Notification(long id, Date date, boolean read) {
		super();
		this.id = id;
		this.date = date;
		this.read = read;
	}
	
	
	public long getId() {
		return id;
	}
	public Date getDate() {
		return date;
	}
	public boolean isRead() {
		return read;
	}
	
	public void setRead(boolean read)
	{
		this.read = read;
	}
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof Notification) ) return false;
		
		final Notification that = (Notification) other;
		
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
