package org.gemsjax.client.model;

import java.util.Map;
import java.util.Set;

import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.model.Model;
import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

public class ModelImpl implements Model{

	
	private int id;
	private String name;
	
	public ModelImpl(int id, String name){
		this.id = id; 
		this.name = name;
	}
	
	
	@Override
	public int getId() {
		return id;
	}

	@Override
	public RegisteredUser getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwner(RegisteredUser owner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Set<User> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Long> getVectorClock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getKeywords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKeywords(String keywords) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Transaction> getTransactions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission getPublicPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPublicPermission(Permission permission) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MetaModel getMetaModel() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
