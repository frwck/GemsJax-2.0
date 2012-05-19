package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class RenameMetaConnectionCommand extends CommandImpl{
	
	private String metaConnectionId;
	private String oldName;
	private String newName;
	
	public RenameMetaConnectionCommand(){}
	
	public RenameMetaConnectionCommand(String id, MetaConnection metaConnection, String newName){
		setId(id);
		metaConnectionId = metaConnection.getID();
		oldName = metaConnection.getName();
		this.newName = newName;
	}
	
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		oldName = a.serialize("oldName", oldName).value;
		newName = a.serialize("newName", newName).value;
		
	}
	

	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		mc.setName(newName);
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		mc.setName(oldName);
	}

}
