package org.gemsjax.server.persistence.collaboration;

import java.util.LinkedHashSet;
import java.util.Set;
import org.eclipse.jetty.websocket.WebSocket;
import org.gemsjax.shared.collaboration.Collaborateable;
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

	
	/**
	 * The {@link TransactionImpl} is executed ({@link #commit()}) on this {@link Collaborateable}
	 */
	private Collaborateable collaborateable;
	

	/**
	 * The {@link Command}s that are executed within this transaction.
	 * <b>NOTICE:</b> The commands are executed in the order as they are added to this list. 
	 */
	private Set<Command> commands;
	
	
	private User user;
	
	
	public TransactionImpl()
	{
		commands = new LinkedHashSet<Command>();
	}
	
	
	public TransactionImpl(String id, Collaborateable collaborateable)
	{
		this();
		this.id = id;
		this.collaborateable = collaborateable;
		
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
	public void commit()
	{
		for (Command c: commands)
			c.execute();
	}
	
	/**
	 * Calling this method will "undo" all the changes that were previously done on the {@link Collaborateable}.
	 */
	@Override
	public void rollback()
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
	}
	
	/**
	 * Remove a Command from the list
	 * @param c
	 */
	@Override
	public void removeCommand(Command c)
	{
		commands.remove(c);
	}
	
	@Override
	public Collaborateable getCollaborateable() {
		return collaborateable;
	}

	@Override
	public void setCollaborateable(Collaborateable collaborateable) {
		this.collaborateable = collaborateable;
	}
	
	@Override
	public Set<Command> getCommands() {
		return commands;
	}

	public void setCommands(Set<Command> commands) {
		this.commands = commands;
	}
	
	
	/**
	 * Transform this {@link TransactionImpl} to a XML representation (also the containing {@link Command}s by calling {@link Command#toXML()}.
	 * The XML representation is used to be send between the client, server and other clients via {@link WebSocket}s and is parsed by the receiver with a
	 * {@link TransactionParser}.
	 * @return
	 */
	@Override
	public String toXML()
	{
		//TODO implement
		return null;
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
	
	

}