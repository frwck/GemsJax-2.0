package org.gemsjax.shared.communication.message.search;

public class ExperimentResult {
	
	private String name;
	private String ownerName;
	private int id;
	
	public ExperimentResult(int id, String name, String ownerName)
	{
		this.id = id;
		this.name = name;
		this.ownerName = ownerName;
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
