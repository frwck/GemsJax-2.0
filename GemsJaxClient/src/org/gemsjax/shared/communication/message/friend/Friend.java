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
	

}
