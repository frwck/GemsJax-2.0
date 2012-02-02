package org.gemsjax.shared.communication.message.search;

public class ExperimentResult {
	
	private String name;
	private String ownerName;
	private int id;
	private boolean coAdmin;
	
	public ExperimentResult(int id, String name, String ownerName, boolean coAdmin)
	{
		this.id = id;
		this.name = name;
		this.ownerName = ownerName;
		this.coAdmin = coAdmin;
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

	public boolean isCoAdmin() {
		return coAdmin;
	}

}
