package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class ChangeMetaConnectionTargetCommand extends CommandImpl{
	
	private String metaConnectionId;
	private String newMetaClassId;
	private String oldMetaClassId;
	private String targetAnchorId;
	private double newX, newY;
	private double oldX, oldY;
	
	
	public ChangeMetaConnectionTargetCommand(){}
	public ChangeMetaConnectionTargetCommand(String id, MetaConnection connection, MetaClass newSource, double newX, double newY){
		setId(id);
		metaConnectionId = connection.getID();
		oldMetaClassId = connection.getSource().getID();
		targetAnchorId = connection.getTargetRelativePoint().getID();
		oldX = connection.getSourceRelativePoint().x;
		oldY = connection.getSourceRelativePoint().y;
		this.newX=newX;
		this.newY = newY;
		this.newMetaClassId = newSource.getID();
		
		
	}
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		oldMetaClassId = a.serialize("oldMetaClassId", oldMetaClassId).value;
		targetAnchorId = a.serialize("sourceAnchorId", targetAnchorId).value;
		oldX = a.serialize("oldX", oldX).value;
		oldY = a.serialize("oldY", oldY).value;
		newX = a.serialize("newX", newX).value;
		newY = a.serialize("newY", newY).value;
		newMetaClassId = a.serialize("newMetaClassId", newMetaClassId).value;
		
	}
	
	
	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection con = (MetaConnection)mm.getElementByID(metaConnectionId);
		
		// remove old source
		con.getSource().removeConnection(con);
		
		// new Source
		MetaClass newTarget = (MetaClass) mm.getElementByID(newMetaClassId);
		con.setTarget(newTarget);
		newTarget.addConnection(con);
		con.getTargetRelativePoint().x = newX;
		con.getTargetRelativePoint().x = newY;
		
		
		
	}
	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection con = (MetaConnection)mm.getElementByID(metaConnectionId);
		
		// remove new source
		con.getSource().removeConnection(con);
		
		// add old Source
		MetaClass oldTarget = (MetaClass) mm.getElementByID(oldMetaClassId);
		con.setTarget(oldTarget);
		oldTarget.addConnection(con);
		con.getTargetRelativePoint().x = oldX;
		con.getTargetRelativePoint().x = oldY;
	}
}
