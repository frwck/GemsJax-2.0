package org.gemsjax.client.experiment;

import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.experiment.ExperimentGroup;
import org.gemsjax.shared.user.ExperimentUser;
import org.gemsjax.shared.user.UserOnlineState;

public class ExperimentUserImpl implements ExperimentUser {

	private int id;
	private String username;
	private ExperimentGroup experimentGroup;
	private UserOnlineState onlineState;
	private String profilePicture;
	
	
	public ExperimentUserImpl(int id, String username)
	{
		this.id =id;
		this.username = username;
	}
	
	
	
	@Override
	public ExperimentGroup getExperimentGroup() {
		return experimentGroup;
	}
	
	/**
	 * <b>For Hibernate only</b><br />Should only called one time to set the {@link ExperimentGroup} and to store it 
	 * via Hibernate in the database
	 * @param group
	 */
	public void setExperimentGroup(ExperimentGroup group)
	{
		this.experimentGroup = group;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	/**
	 * <b>For Hibernate only</b><br />Should only called one time to set the username and to store it 
	 * via Hibernate in the database
	 * @param group
	 */
	public void setUsername (String username)
	{
		this.username = username;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public String getDisplayedName() {
		return username;
	}

	@Override
	public Set<Collaborateable> getCollaborateables() {
		// Not needed on client side
		return null;
	}

	@Override
	public UserOnlineState getOnlineState() {
		return onlineState;
	}

	@Override
	public void setOnlineState(UserOnlineState onlineState) {
		this.onlineState = onlineState;
	}

	@Override
	public void setProfilePicture(String pictureUrl) {
		this.profilePicture = pictureUrl;
	}

	@Override
	public String getProfilePicture() {
		return profilePicture;
	}

	@Override
	public void setDisplayedName(String displayedName) {
		this.username = displayedName;
	}

}
