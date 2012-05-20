package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class ChangeMetaConnectionSourceCommand extends CommandImpl{
	
	private String metaConnectionId;
	private String newMetaClassId;
	private String oldMetaClassId;
	private String sourceAnchorId;
	private double newX, newY;
	private double oldX, oldY;
	
	
	public ChangeMetaConnectionSourceCommand(){}
	public ChangeMetaConnectionSourceCommand(String id, MetaConnection connection, MetaClass newSource, double newX, double newY){
		setId(id);
		metaConnectionId = connection.getID();
		oldMetaClassId = connection.getSource().getID();
		sourceAnchorId = connection.getSourceRelativePoint().getID();
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
		sourceAnchorId = a.serialize("sourceAnchorId", sourceAnchorId).value;
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
		MetaClass newSource = (MetaClass) mm.getElementByID(newMetaClassId);
		con.setSource(newSource);
		newSource.addConnection(con);
		con.getSourceRelativePoint().x = newX;
		con.getSourceRelativePoint().x = newY;
		
		
		
	}
	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection con = (MetaConnection)mm.getElementByID(metaConnectionId);
		
		// remove new source
		con.getSource().removeConnection(con);
		
		// add old Source
		MetaClass oldsource = (MetaClass) mm.getElementByID(oldMetaClassId);
		con.setSource(oldsource);
		oldsource.addConnection(con);
		con.getSourceRelativePoint().x = oldX;
		con.getSourceRelativePoint().x = oldY;
	}
}
