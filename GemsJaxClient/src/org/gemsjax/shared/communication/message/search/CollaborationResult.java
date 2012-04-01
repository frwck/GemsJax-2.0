package org.gemsjax.shared.communication.message.search;

import org.gemsjax.shared.communication.message.collaborateablefile.CollaborationType;
 


public class CollaborationResult {
	
	private String name;
	private String ownerName;
	private int id;
	private CollaborationType type;
	private boolean _public;
	
	public CollaborationResult(int id, String name, String ownerName, boolean _public,  CollaborationType type)
	{
		this.name = name;
		this.ownerName = ownerName;
		this.id =id;
		this.type = type;
		this._public = _public;
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

	public boolean isPublic() {
		return _public;
	}
	
}
