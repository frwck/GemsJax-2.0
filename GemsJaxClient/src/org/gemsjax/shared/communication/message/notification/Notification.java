package org.gemsjax.shared.communication.message.notification;

import java.util.Date;

public class Notification {

	private long id;
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
}
