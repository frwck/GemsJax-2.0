package org.gemsjax.server.persistence.user;
import org.gemsjax.shared.user.User;
import org.gemsjax.shared.user.UserOnlineState;

public class UserImpl implements User{

	private int id;
	private String displayedName;
	
	private UserOnlineState onlineState ;
	
	
	public UserImpl()
	{
		onlineState = UserOnlineState.OFFLINE;
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
