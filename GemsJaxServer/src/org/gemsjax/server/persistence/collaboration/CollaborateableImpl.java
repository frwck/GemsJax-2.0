package org.gemsjax.server.persistence.collaboration;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

public class CollaborateableImpl implements Collaborateable
{
	
	public int id;
	private String keywords;
	private String name;
	private RegisteredUser owner;
	private Set<User> users;
	private Set<Transaction> transactions;
	private Map<User, Integer> vectorClock;
	private int publicPermission;
	
	public CollaborateableImpl()
	{
		users = new LinkedHashSet<User>();
		vectorClock = new HashMap<User, Integer>();
		transactions = new LinkedHashSet<Transaction>();
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
	
	@Override
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


	@Override
	public Set<Transaction> getTransactions() {
		return transactions;
	}


	@Override
	public int getPublicPermission() {
		return publicPermission;
	}


	@Override
	public void setPublicPermission(int permission) {
		publicPermission = permission;
	}

}