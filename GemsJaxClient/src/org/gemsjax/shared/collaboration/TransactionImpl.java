package org.gemsjax.shared.collaboration;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;
import org.gemsjax.shared.user.User;

public class TransactionImpl implements Transaction, Serializable{

	private String id;
	private int collaborateableId;
	private Collaborateable collaborateable;
	private List<Command> commands;
	private User user;
	private int userId;
	private Map<Integer, Long> vectorClock;
	/**
	 * Used by the server only
	 */
	private int sequenceNumber;
	
	
	public TransactionImpl(){
		commands = new LinkedList<Command>();
		vectorClock = new LinkedHashMap<Integer, Long>();
	}
	
	
	public TransactionImpl(String id){
		this();
		this.id = id;
	}
	
	
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void commit() throws SemanticException {
		
		for (Command c: commands)
			c.execute();
		
	}

	@Override
	public void rollback() throws SemanticException{
		for (int i = commands.size()-1; i>=0;i--)
			commands.get(i).undo();
	}

	@Override
	public void addCommand(Command c) {
		commands.add(c);
	}

	@Override
	public void removeCommand(Command c) {
		commands.remove(c);
	}

	@Override
	public List<Command> getCommands() {
		return commands;
	}

	@Override
	public Collaborateable getCollaborateable() {
		return collaborateable;
	}

	@Override
	public void setCollaborateable(Collaborateable c) {
		this.collaborateable = c;
		collaborateableId = c.getId();
	}


	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void setUser(User u) {
		this.user = u;
		this.userId = u.getId();
	}

	@Override
	public int getCollaborateableId() {
		return collaborateableId;
	}

	@Override
	public void setCollaborateableId(int id) {
		this.collaborateableId = id;
	}


	@Override
	public int getUserId() {
		return userId;
	}


	@Override
	public void setUserId(int userId) {
		this.userId = userId;
	}


	@Override
	public long getVectorClockEnrty(int userId) {
		Long val  = vectorClock.get(userId);
		
		if (val == null)
			return 0;
		else
			return val;
	}


	@Override
	public void setVectorClockEntry(int userId, long value) {
		vectorClock.put(userId, value);
	}


	@Override
	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}


	@Override
	public Map<Integer, Long> getVectorClock() {
		return vectorClock;
	}


	public void setId(String id) {
		this.id = id;
	}


	@Override
	public void serialize(Archive a) throws Exception {
		id = a.serialize("id", id).value;
		userId = a.serialize("userId", userId).value;
		collaborateableId = a.serialize("collaborateableId", collaborateableId).value;
		vectorClock = a.serialize("vectorClock", vectorClock).value;
		commands = a.serialize("commands", commands).value;
		
	}


	/**
	 * Server only
	 * @return
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}


	/**
	 * Server only
	 * @param sequenceNumber
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
}
