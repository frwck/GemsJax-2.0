package org.gemsjax.server.module;

import javax.servlet.http.HttpSession;

public class Authenticator {
	
	public static OnlineUser isAuthenticated(HttpSession session)
	{
		// TODO, do the right thing
		// OnlineUserManager.getInstance().getOnlineUser(session);
		
		return OnlineUserManager.getInstance().getOnlineUser(1);
	}

}
