package org.gemsjax.server.module;

import java.util.Set;

import org.gemsjax.server.communication.OnlineUser;
import org.gemsjax.server.communication.channel.handler.FriendsChannelHandler;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;

public class FriendModule implements FriendsChannelHandler{
	
	private static FriendModule INSTANCE;
	private UserDAO userDAO;

	private FriendModule()
	{
		userDAO = new HibernateUserDAO();
	}
	
	public static void init()
	{
		INSTANCE = new FriendModule();
	}
	
	
	public static FriendModule getInstance() {
		
		if (INSTANCE==null)
			init();
		
		return INSTANCE;
	}
	
	
	@Override
	public void onGetAllFriends(OnlineUser requester) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCancelFriendships(OnlineUser requester, Set<Integer> friendIds) {
		// TODO Auto-generated method stub
		
	}

}
