package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class MoveMetaConnectionCommand  extends CommandImpl{
	
	private double oldX;
	private double oldY;
	private double x;
	private double y;
	
	private String metaConnectionId;
	
	
	public MoveMetaConnectionCommand(){}
	
	public MoveMetaConnectionCommand(String id, MetaConnection connection, double newX, double newY){
		setId(id);
		this.metaConnectionId = connection.getID();
		this.oldX = connection.getConnectionBoxX();
		this.oldY = connection.getConnectionBoxY();
		this.x = newX;
		this.y = newY;
	}
	
	
	
	@Override
	public void serialize(Archive a) throws Exception {
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		oldX = a.serialize("oldX", oldX).value;
		oldY = a.serialize("oldY", oldY).value;
		x = a.serialize("x", x).value;
		y = a.serialize("y", y).value;
		
	}


	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		
		mc.setConnectionBoxX(x);
		mc.setConnectionBoxY(y);
	}


	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		mc.setConnectionBoxX(oldX);
		mc.setConnectionBoxY(oldY);
	}

}
