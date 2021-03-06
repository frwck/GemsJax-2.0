package org.gemsjax.server.persistence.user;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.user.User;
import org.gemsjax.shared.user.UserOnlineState;

/**
 * This is the POJO to store information about a {@link User} via hibernate on the database 
 * @author Hannes Dorfmann
 *
 */
public class UserImpl implements User{

	private Integer id;
	private String displayedName;
	private String picture;
	
	/**
	 *@see #getCollaborateables()
	 */
	
	private Set<Collaborateable> collaborateables;
	
	private UserOnlineState onlineState ;
	
	
	public UserImpl()
	{
		onlineState = UserOnlineState.OFFLINE;
		collaborateables = new LinkedHashSet<Collaborateable>();
	}
	
	/**
	 * Get the {@link Collaborateable}s on which this user works collaborativ with other {@link User}s
	 * @return
	 */
	@Override
	public Set<Collaborateable> getCollaborateables()
	{
		return collaborateables;
	}
	
	public void setDisplayedName(String displayedName) {
		this.displayedName = displayedName;
	}



	public void setOnlineState(UserOnlineState onlineState) {
		this.onlineState = onlineState;
	}

	
	
	@Override
	public String getDisplayedName() {
		return displayedName;
	}
	
	

	@Override
	public Integer getId() {
		return id;
	}
	
	

	@Override
	public UserOnlineState getOnlineState() {
		return onlineState;
	}
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof UserImpl) ) return false;
		
		final UserImpl that = (UserImpl) other;
		
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

	@Override
	public String getProfilePicture() {
		return picture;
	}

	@Override
	public void setProfilePicture(String pictureUrl) {
		this.picture = pictureUrl;
	}
}
