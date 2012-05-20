package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class ResizeMetaConnectionCommand extends CommandImpl{

	private String metaConnectionId;
	private double oldWidth;
	private double oldHeight;
	private double newWidth;
	private double newHeight;
	
	
	public ResizeMetaConnectionCommand(){}
	
	public ResizeMetaConnectionCommand(String id, MetaConnection connection, double width, double height){
		setId(id);
		this.metaConnectionId = connection.getID();
		this.newWidth = width;
		this.newHeight = height;
		this.oldHeight = connection.getConnectionBoxHeight();
		this.oldWidth = connection.getConnectionBoxWidth();
	}

	@Override
	public void execute() throws ManipulationException {
		MetaModel metaModel = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) metaModel.getElementByID(metaConnectionId);
		mc.setConnectionBoxWidth(newWidth);
		mc.setConnectionBoxHeight(newHeight);
		
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel metaModel = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) metaModel.getElementByID(metaConnectionId);
		mc.setConnectionBoxWidth(oldWidth);
		mc.setConnectionBoxHeight(oldHeight);
		
	}
	
	@Override
	public void serialize(Archive a) throws Exception {
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectoinId", metaConnectionId).value;
		newWidth = a.serialize("width", newWidth).value;
		newHeight = a.serialize("height", newHeight).value;
		oldWidth = a.serialize("oldWidth", oldWidth).value;
		oldHeight = a.serialize("oldHeight", oldHeight).value;
		
		
	}

}
