package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class ChangeMetaConnectionIconsCommand extends CommandImpl{
	
	private String metaConnectionId;
	private String newSourceIcon;
	private String newTargetIcon;
	private String oldSourceIcon;
	private String oldTargetIcon;
	
	public ChangeMetaConnectionIconsCommand(){}
	
	public ChangeMetaConnectionIconsCommand(String id, MetaConnection metaConnection, String newSourceIcon, String newTargetIcon){
		setId(id);
		metaConnectionId = metaConnection.getID();
		this.newSourceIcon = newSourceIcon;
		this.newTargetIcon = newTargetIcon;
		this.oldSourceIcon = metaConnection.getSourceIconURL();
		this.oldTargetIcon = metaConnection.getTargetIconURL();
	}
	
	@Override
	public void serialize (Archive a) throws Exception{
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		newSourceIcon = a.serialize("newSourceIcon", newSourceIcon).value;
		newTargetIcon = a.serialize("newTargetIcon", newTargetIcon).value;
		oldSourceIcon = a.serialize("oldSourceIcon", oldSourceIcon).value;
		oldTargetIcon = a.serialize("oldTargetIcon", oldTargetIcon).value;
	}
	
	
	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		mc.setSourceIconURL(newSourceIcon);
		mc.setTargetIconURL(newTargetIcon);
	}
	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		mc.setSourceIconURL(oldSourceIcon);
		mc.setTargetIconURL(oldTargetIcon);
	}
	
	
	
	

}
