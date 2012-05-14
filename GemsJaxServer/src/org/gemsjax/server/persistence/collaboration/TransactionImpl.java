package org.gemsjax.server.persistence.collaboration;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.collaboration.SemanticException;
import org.gemsjax.shared.collaboration.Transaction;
import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.user.User;

/**
 * A {@link TransactionImpl} contains a list of {@link Command}s (at least one {@link Command}) that are executed within a {@link TransactionImpl},
 * because this {@link Command}s have a relation to each other, which means the {@link Command}s grouped in a transaction must be
 * executed in the given order to ensure that the transaction reaches an atomic state after the execution 
 * @author Hannes Dorfmann
 *
 */
public class TransactionImpl implements Transaction {
	
	/**
	 * The unique ID
	 */
	private String id;

	private int sequenceNumber;
	private Map<User, Long> vectorClock;

	
	/**
	 * The {@link TransactionImpl} is executed ({@link #commit()}) on this {@link Collaborateable}
	 */
	private Collaborateable collaborateable;
	

	/**
	 * The {@link Command}s that are executed within this transaction.
	 * <b>NOTICE:</b> The commands are executed in the order as they are added to this list. 
	 */
	// The internal database strukture
	private Set<Command> commands;
	
	private List<Command> commandsAsList;
	
	private User user;
	
	
	public TransactionImpl()
	{
		commands = new LinkedHashSet<Command>();
		vectorClock = new LinkedHashMap<User, Long>();
		commandsAsList = new LinkedList<Command>();
	}
	
	
	public TransactionImpl(String id, Collaborateable collaborateable)
	{
		this();
		this.id = id;
		this.collaborateable = collaborateable;
		
	}
	
	public Map<User, Long> getUserVectorClock(){
		return vectorClock;
	}
	
	/**
	 * Get the unique ID of the {@link TransactionImpl}
	 * @return
	 */
	@Override
	public String getId()
	{
		return id;
	}
	
	/**
	 * Calling commit will "execute" this transaction on the {@link Collaborateable}, which means, that the {@link Command}s were executed in the given order
	 * by calling {@link Command#execute()} to manipulate data. After the commit the {@link Collaborateable} is in an atomic state.
	 */
	@Override
	public void commit() throws SemanticException
	{
		for (Command c: commands)
			c.execute();
	}
	
	/**
	 * Calling this method will "undo" all the changes that were previously done on the {@link Collaborateable}.
	 */
	@Override
	public void rollback() throws SemanticException
	{
		for (Command c: commands)
			c.undo();
	}
	
	/**
	 * Add a command to this transaction by inserting the command at the last position in the command list
	 * @param c
	 */
	@Override
	public void addCommand(Command c)
	{
		commands.add(c);
		commandsAsList.add(c);
	}
	
	/**
	 * Remove a Command from the list
	 * @param c
	 */
	@Override
	public void removeCommand(Command c)
	{
		commands.remove(c);
		commandsAsList.remove(c);
	}
	
	@Override
	public Collaborateable getCollaborateable() {
		return collaborateable;
	}

	@Override
	public void setCollaborateable(Collaborateable collaborateable) {
		this.collaborateable = collaborateable;
	}
	


	public void setCommands(Set<Command> commands) {
		this.commands = commands;
		this.commandsAsList.clear();
		this.commandsAsList.addAll(commands);
	}
	
	


	@Override
	public User getUser() {
		return user;
	}


	@Override
	public void setUser(User user) {
		this.user = user;
	}


	public int getSequenceNumber() {
		return sequenceNumber;
	}


	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	

	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof TransactionImpl) ) return false;
		
		final TransactionImpl that = (TransactionImpl) other;
		
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
	public int getCollaborateableId() {
		return collaborateable.getId();
	}


	@Override
	public int getUserId() {
		return user.getId();
	}


	@Override
	public Map<Integer, Long> getVectorClock() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public long getVectorClockEnrty(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setCollaborateableId(int arg0) {
		
	}


	@Override
	public void setCommands(List<Command> c) {
		commands.clear();
		commands.addAll(c);
		this.commandsAsList = c;
	}


	@Override
	public void setUserId(int arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setVectorClockEntry(int arg0, long arg1) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Command> getCommands() {
		return commandsAsList;
	}


	public void setId(String id) {
		this.id = id;
	}
}
