package org.gemsjax.server.persistence.collaboration.command;

import org.gemsjax.shared.collaboration.command.Command;

public abstract class CommandImpl implements Command {
	
	private String id;
	private int sequenceNumber;
	
	public CommandImpl()
	{
		
	}
	
	
	public String getId()
	{
		return id;
	}


	public int getSequenceNumber() {
		return sequenceNumber;
	}


	public void setSequenceNumber(int sequenz) {
		this.sequenceNumber = sequenz;
	}

}
