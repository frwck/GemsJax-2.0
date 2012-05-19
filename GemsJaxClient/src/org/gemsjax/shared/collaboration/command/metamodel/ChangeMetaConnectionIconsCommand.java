package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class ChangeMetaConnectionIconsCommand extends CommandImpl{
	
	private String metaConnectionId;
	private String newIcon;
	private String oldIcon;
	private boolean source;
	
	public ChangeMetaConnectionIconsCommand(){}
	
	public ChangeMetaConnectionIconsCommand(String id, MetaConnection metaConnection, String newIcon, boolean source ){
		setId(id);
		metaConnectionId = metaConnection.getID();
		this.newIcon = newIcon;
		this.source = source;
		
		if (source)
			oldIcon =metaConnection.getSourceIconURL();
		else
			oldIcon = metaConnection.getTargetIconURL();
		
	}
	
	@Override
	public void serialize (Archive a) throws Exception{
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		source = a.serialize("source", source).value;
		newIcon = a.serialize("newIcon", newIcon).value;
		oldIcon = a.serialize("oldIcon", oldIcon).value;
	}
	
	
	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		if (source)
			mc.setSourceIconURL(newIcon);
		else
			mc.setTargetIconURL(newIcon);
	}
	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		if (source)
			mc.setSourceIconURL(oldIcon);
		else
			mc.setTargetIconURL(oldIcon);
	}
	
	
	
	

}
