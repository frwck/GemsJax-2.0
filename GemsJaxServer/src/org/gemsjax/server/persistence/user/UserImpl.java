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

	private int id;
	private String displayedName;
	
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
	public int getId() {
		return id;
	}
	
	

	@Override
	public UserOnlineState getOnlineState() {
		return onlineState;
	}

}
