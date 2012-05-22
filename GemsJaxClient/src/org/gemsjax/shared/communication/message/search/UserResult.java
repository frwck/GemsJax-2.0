package org.gemsjax.shared.communication.message.search;

public class UserResult {
	
	private String username;
	private String displayName;
	private String profilePicture;
	private int userId;
	
	
	public UserResult(int userId, String username, String displayName, String profilePicture)
	{
		this.userId = userId;
		this.username = username;
		this.displayName = displayName;
		this.profilePicture = profilePicture;
	}

	public String getUsername() {
		return username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public int getUserId() {
		return userId;
	}


}
