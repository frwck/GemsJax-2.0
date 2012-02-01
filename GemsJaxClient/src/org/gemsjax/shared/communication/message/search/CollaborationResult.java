package org.gemsjax.shared.communication.message.search;

import org.gemsjax.shared.communication.message.collaboration.CollaborationType;
 


public class CollaborationResult {
	
	private String name;
	private String ownerName;
	private int id;
	private CollaborationType type;
	
	public CollaborationResult(int id, String name, String ownerName, CollaborationType type)
	{
		this.name = name;
		this.ownerName = ownerName;
		this.id =id;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public int getId() {
		return id;
	}
	
	
	public CollaborationType getType()
	{
		return type;
	}
	
}
