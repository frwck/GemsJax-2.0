package org.gemsjax.shared.collaboration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.user.User;


/**
 * A {@link Transaction} contains a list of {@link Command}s (at least one {@link Command}) that are executed within a {@link Transaction},
 * because this {@link Command}s have a relation to each other, which means the {@link Command}s grouped in a transaction must be
 * executed in the given order to ensure that the transaction reaches an atomic state after the execution 
 * @author Hannes Dorfmann
 *
 */
public interface Transaction {
	
	
	/**
	 * Get the unique ID of the {@link Transaction}
	 * @return
	 */
	public String getId();
	
	/**
	 * Calling commit will "execute" this transaction on the {@link Collaborateable}, which means, that the {@link Command}s were executed in the given order
	 * by calling {@link Command#execute()} to manipulate data. After the commit the {@link Collaborateable} is in an atomic state.
	 * @throws SemanticException 
	 */
	public void commit() throws SemanticException;
	/**
	 * Calling this method will "undo" all the changes that were previously done on the {@link Collaborateable}.
	 */
	public void rollback() throws SemanticException;
	/**
	 * Add a command to this transaction by inserting the command at the last position in the command list
	 * @param c
	 */
	public void addCommand(Command c);
	
	/**
	 * Remove a Command from the list
	 * @param c
	 */
	public void removeCommand(Command c);
	
	
	public void setCommands(List<Command> commands);
	
	/**
	 * Get a list with all {@link Command}s that are executed whith this transaction.
	 * <b>Notice:</b> For the implementation should be used {@link LinkedHashSet}, because {@link LinkedHashSet} contains the order.
	 * @return
	 */
	public List<Command> getCommands();
	
	/**
	 * Get the {@link Collaborateable} on which this {@link Transaction} is executed
	 */
	public Collaborateable getCollaborateable();
	
	public int getCollaborateableId();
	public void setCollaborateableId(int id);
	
	/**
	 * Set the {@link Collaborateable} on which this {@link Transaction} is executed
	 * @param c
	 */
	public void setCollaborateable(Collaborateable c);
	
	/**
	 * Get the {@link User} who has created this {@link Transaction}
	 * @return
	 */
	public User getUser();
	
	/**
	 * Set the {@link User} who has created this {@link Transaction}
	 * @param u
	 */
	public void setUser(User u);
	
	public int getUserId();
	public void setUserId(int userId);
	
	public long getVectorClockEnrty(int userId);
	public void setVectorClockEntry(int userId, long value);
	
	public Map<Integer, Long> getVectorClock();
	

}
