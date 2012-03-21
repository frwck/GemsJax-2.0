package org.gemsjax.shared.communication.message.friend;

import org.gemsjax.shared.user.User;
import org.gemsjax.shared.user.UserOnlineState;

/**
 * This is a little helper class, to represent a "friend" of a {@link User}.
 * Note, that this class is used for the communication between server and client
 * and is not saved persistently on server side.
 * @author Hannes Dorfmann
 *
 */
public class Friend {
	
	private Integer id;
	private String displayName;
	
	private UserOnlineState onlineState;
	
	private String profilePicture;
	
	
	
	public Friend(int id, String displayName, UserOnlineState onlineState, String profilePicture)
	{
		this.id = id;
		this.displayName = displayName;
		this.onlineState = onlineState;
		this.profilePicture = profilePicture;
	}



	public Integer getId() {
		return id;
	}



	public String getDisplayName() {
		return displayName;
	}



	public UserOnlineState getOnlineState() {
		return onlineState;
	}



	public String getProfilePicture() {
		return profilePicture;
	}
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof Friend) ) return false;
		
		final Friend that = (Friend) other;
		
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
