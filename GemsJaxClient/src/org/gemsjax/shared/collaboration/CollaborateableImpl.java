package org.gemsjax.shared.collaboration;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.gemsjax.shared.user.RegisteredUser;
import org.gemsjax.shared.user.User;

public class CollaborateableImpl implements Collaborateable
{
	
	public Integer id;
	private String keywords;
	private String name;
	private RegisteredUser owner;
	private Set<User> users;
	private List<Transaction> transactions;
	private Map<Integer, Long> vectorClock;
	private int publicPermission;
	
	/**
	 * This field is reserved for hibernate to filter a MetaModel from a MetaModel that is part of an Experiment
	 */
	private boolean forExperiment;
	
	
	
	public CollaborateableImpl()
	{
		users = new LinkedHashSet<User>();
		vectorClock = new ConcurrentHashMap<Integer, Long>();
		transactions = new LinkedList<Transaction>();
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
	public Map<Integer,Long> getVectorClock() {
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
	public synchronized List<Transaction> getTransactions() {
		synchronized(transactions){
			return transactions;
		}
	}


	@Override
	public Permission getPublicPermission() {
		return Collaborateable.Permission.fromConstant(publicPermission);
	}


	@Override
	public void setPublicPermission(Permission permission) {
		
		publicPermission = permission.toConstant();
	}


	public boolean isForExperiment() {
		return forExperiment;
	}


	public void setForExperiment(boolean forExperiment) {
		this.forExperiment = forExperiment;
	}
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof CollaborateableImpl) ) return false;
		
		final CollaborateableImpl that = (CollaborateableImpl) other;
		
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
	public synchronized void addTransaction(Transaction t) {
		synchronized(transactions){
			t.setSequenceNumber(transactions.size()+1);
			transactions.add(t);
		}
		
	}


	@Override
	public void setId(int id) {
		this.id = id;
	}


}
