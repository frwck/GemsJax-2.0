package org.gemsjax.shared.communication.message.search;

public class CollaborationResult {
	
	private String name;
	private String ownerName;
	private int id;
	
	public CollaborationResult(int id, String name, String ownerName)
	{
		this.name = name;
		this.ownerName = ownerName;
		this.id =id;
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
	
	
}
