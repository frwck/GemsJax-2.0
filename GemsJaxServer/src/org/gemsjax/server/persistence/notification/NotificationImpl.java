package org.gemsjax.server.persistence.notification;

import java.util.Date;

import org.gemsjax.server.persistence.collaboration.command.CommandImpl;
import org.gemsjax.shared.notification.Notification;
import org.gemsjax.shared.user.RegisteredUser;

/**
 * The implementation of {@link Notification} on the server side
 * @author Hannes Dorfmann
 *
 */
public class NotificationImpl implements Notification{
	
	private Long id;
	private int codeNumber;
	private Date date;
	private String optionalMessage;
	private boolean read;
	private RegisteredUser receiver;
	
	
	@Override
	public int getCodeNumber() {
		return codeNumber;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public String getOptionalMessage() {
		return optionalMessage;
	}

	@Override
	public boolean isRead() {
		return read;
	}

	@Override
	public void setCodeNumber(int codeNumber) {
		this.codeNumber = codeNumber;
	}

	@Override
	public void setDate(Date date) {
		this.date  = date;
	}

	@Override
	public void setOptionalMessage(String message) {
		this.optionalMessage = message;
	}

	@Override
	public void setRead(boolean read) {
		this.read = read;
	}

	public RegisteredUser getReceiver() {
		return receiver;
	}

	public void setReceiver(RegisteredUser receiver) {
		this.receiver = receiver;
	}
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof NotificationImpl) ) return false;
		
		final NotificationImpl that = (NotificationImpl) other;
		
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
