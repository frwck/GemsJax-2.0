package org.gemsjax.shared.collaboration.command;

import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class MoveMetaConnectionAchnorPointCommand extends CommandImpl {

	private String anchorId;
	private String metaConnectionId;
	private double newX;
	private double oldX;
	private double newY;
	private double oldY;
	
	public MoveMetaConnectionAchnorPointCommand(){}
	
	public MoveMetaConnectionAchnorPointCommand(String id, MetaConnection connection, AnchorPoint ap,  double newX, double newY){
		setId(id);
		metaConnectionId = connection.getID();
		this.newX = newX;
		this.newY = newY;
		this.oldX = ap.x;
		this.oldY = ap.y;
		this.anchorId = ap.getID();
	}
	
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		newX = a.serialize("newX", newX).value;
		newY = a.serialize("newY", newY).value;
		oldX = a.serialize("oldX", oldX).value;
		oldY = a.serialize("oldY", oldY).value;
		anchorId = a.serialize("anchorId", anchorId).value;
	}
	
	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		AnchorPoint ap = mc.getAnchorPointById(anchorId);
		ap.x = newX;
		ap.y = newY;
		
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		AnchorPoint ap = mc.getAnchorPointById(anchorId);
		ap.x = oldX;
		ap.y = oldY;
	}

}
