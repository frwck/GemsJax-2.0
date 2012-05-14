package org.gemsjax.shared.collaboration.command;


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
