package org.gemsjax.shared.collaboration;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.websocket.WebSocket;
import org.gemsjax.shared.collaboration.command.Command;

/**
 * A {@link Transaction} contains a list of {@link Command}s (at least one {@link Command}) that are executed within a {@link Transaction},
 * because this {@link Command}s have a relation to each other, which means the {@link Command}s grouped in a transaction must be
 * executed in the given order to ensure that the transaction reaches an atomic state after the execution 
 * @author Hannes Dorfmann
 *
 */
public class Transaction {
	
	/**
	 * The unique ID
	 */
	private String id;
	
	/**
	 * The {@link Transaction} is executed ({@link #commit()}) on this {@link Collaborateable}
	 */
	private Collaborateable collaborateable;
	
	/**
	 * The {@link Command}s that are executed within this transaction.
	 * <b>NOTICE:</b> The commands are executed in the order as they are added to this list. 
	 */
	private List<Command> commands;
	
	
	
	public Transaction(String id, Collaborateable collaborateable)
	{
		this.id = id;
		this.collaborateable = collaborateable;
		commands = new ArrayList<Command>();
	}
	
	/**
	 * Get the unique ID of the {@link Transaction}
	 * @return
	 */
	public String getID()
	{
		return id;
	}
	
	/**
	 * Calling commit will "execute" this transaction on the {@link Collaborateable}, which means, that the {@link Command}s were executed in the given order
	 * by calling {@link Command#execute()} to manipulate data. After the commit the {@link Collaborateable} is in an atomic state.
	 */
	public void commit()
	{
		for (Command c: commands)
			c.execute();
	}
	
	/**
	 * Calling this method will "undo" all the changes that were previously done on the {@link Collaborateable}.
	 */
	public void rollback()
	{
		for (Command c: commands)
			c.undo();
	}
	
	/**
	 * Add a command to this transaction by inserting the command at the last position in the command list
	 * @param c
	 */
	public void addCommand(Command c)
	{
		commands.add(c);
	}
	
	/**
	 * Add a command at the specified position in the list.
	 * @see List#add(int, Object)
	 * @param c
	 * @param position
	 */
	public void addCommand(Command c, int position)
	{
		commands.add(position,c);
	}
	
	/**
	 * Remove a Command from the list
	 * @param c
	 */
	public void removeCommand(Command c)
	{
		commands.remove(c);
	}
	
	
	/**
	 * Transform this {@link Transaction} to a XML representation (also the containing {@link Command}s by calling {@link Command#toXML()}.
	 * The XML representation is used to be send between the client, server and other clients via {@link WebSocket}s and is parsed by the receiver with a
	 * {@link TransactionParser}.
	 * @return
	 */
	public String toXML()
	{
		//TODO implement
		return null;
	}
	
	

}