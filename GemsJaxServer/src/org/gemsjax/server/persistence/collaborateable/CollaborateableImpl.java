package org.gemsjax.server.persistence.collaborateable;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

public class CollaborateableImpl implements Collaborateable
{
	
	public int id;
	private String keywords;
	private String name;
	private RegisteredUser owner;
	private Set<User> users;
	private Map<User, Integer> vectorClock;
	
	
	public CollaborateableImpl()
	{
		users = new LinkedHashSet<User>();
		vectorClock = new HashMap<User, Integer>();
	}


	@Override
	public int getId() {
		return id;
	}


	@Override
	public String getKeywords() {
		return keywords;
	}


	@Override
	public String getName() {
		return name;
	}


	@Override
	public RegisteredUser getOwner() {
		return owner;
	}
	
	public void setOwner(RegisteredUser owner)
	{
		this.owner = owner;
	}


	@Override
	public Set<User> getUsers() {
		return users;
	}


	@Override
	public Map<User,Integer> getVectorClock() {
		return vectorClock;
	}


	@Override
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}

}
