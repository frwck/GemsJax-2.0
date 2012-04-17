package org.gemsjax.client.user;

import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.Experiment;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.UserOnlineState;

public class RegisteredUserImpl implements RegisteredUser{

	
	public static final String NO_PROFILE_PICTURE_URL="/images/NoProfilePicture.jpg";
	
	
	private Integer id;
	private String displayedName;
	private UserOnlineState onlineState;
	private String email;
	private String profilePicture;
	
	public RegisteredUserImpl(Integer id, String displayedName, UserOnlineState onlineState)
	{
		this.id = id;
		this.displayedName = displayedName;
		this.onlineState = onlineState;
	}
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getDisplayedName() {
		return displayedName;
	}

	@Override
	public Set<Collaborateable> getCollaborateables() {
		// TODO needed?
		return null;
	}

	@Override
	public UserOnlineState getOnlineState() {
		return onlineState;
	}

	@Override
	public String getUsername() {
		// TODO needed?
		return null;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setDisplayedName(String displayedName) {
		this.displayedName = displayedName;
	}

	@Override
	public Set<Collaborateable> getOwnedCollaborateables() {
		// TODO needed?
		return null;
	}

	@Override
	public Set<Experiment> getAdministratedExperiments() {
		// TODO needed?
		return null;
	}

	@Override
	public Set<Experiment> getOwnedExperiments() {
		// TODO Aneeded?
		return null;
	}

	@Override
	public void setProfilePicture(String pictureUrl) {
		profilePicture=pictureUrl;
	}

	@Override
	public String getProfilePicture() {
		return profilePicture;
	}

	@Override
	public void setOnlineState(UserOnlineState onlineState) {
		this.onlineState = onlineState;
	}

	@Override
	public Set<RegisteredUser> getAllFriends() {
		// TODO Auto-generated method stub
		return null;
	}

}
