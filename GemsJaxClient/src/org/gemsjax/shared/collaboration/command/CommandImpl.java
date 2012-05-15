package org.gemsjax.shared.collaboration.command;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;


public abstract class CommandImpl implements Command, Serializable{
	
	private String id;
	private int sequenceNumber;
	
	public CommandImpl()
	{
		
	}
	
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}


	public int getSequenceNumber() {
		return sequenceNumber;
	}


	public void setSequenceNumber(int sequenz) {
		this.sequenceNumber = sequenz;
	}
	
	public void serialize(Archive a) throws Exception{
		id = a.serialize("id", id).value;
		sequenceNumber = a.serialize("sequenceNumber", sequenceNumber).value;
	}
	
	
	@Override
	public boolean equals(Object other) {
		
		if (this==other) return true;
		
		if ( !(other instanceof CommandImpl) ) return false;
		
		final CommandImpl that = (CommandImpl) other;
		
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

}
