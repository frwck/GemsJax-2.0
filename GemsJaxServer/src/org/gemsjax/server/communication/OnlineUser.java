package org.gemsjax.server.communication;

import org.gemsjax.server.persistence.notification.NotificationImpl;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.user.User;

import com.mysql.jdbc.Messages;

/**
 * A {@link OnlineUser} wraps a simple {@link User} and his current {@link OutputChannel}.
 * In other words: A {@link OnlineUser} maps a {@link User} to an {@link OutputChannel}.
 * So the only job this class has to do is to provide a {@link OutputChannel} to this {@link User}, which is already
 * authenticated and reside on the other end of the connection (with a browser), 
 * ready to get {@link Messages} from the server (via an {@link OutputChannel}).
 * @author Hannes Dorfmann
 *
 */
public class OnlineUser {
	
	private User user;
	private OutputChannel outputChannel;
	
	
	public OnlineUser(User user)
	{
		this.user = user;
	}


	public OutputChannel getOutputChannel() {
		return outputChannel;
	}


	public void setOutputChannel(OutputChannel outputChannel) {
		this.outputChannel = outputChannel;
	}
	
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof OnlineUser) ) return false;
		
		final OnlineUser that = (OnlineUser) other;
		
		if (user.getId()!= null && that.user.getId() != null)
			return this.user.getId().equals(that.user.getId());
		
		return false;
	}
		
	@Override
	public int hashCode() {
		if (user.getId() != null)
			return user.getId().hashCode();
		else
			return super.hashCode();
	}
	
	
	

	
	

}
