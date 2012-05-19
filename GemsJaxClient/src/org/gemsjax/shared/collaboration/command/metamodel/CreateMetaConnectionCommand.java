package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.impl.MetaConnectionImpl;

public class CreateMetaConnectionCommand extends CommandImpl{

	private String metaConnectionId;
	private String name;
	private String metaClassSourceId;
	private String metaClassTargetId;
	
	private double sourcePointX;
	private double sourcePointY;
	
	private double sourceBoxPointX;
	private double sourceBoxPointY;
	
	private double targetPointX;
	private double targetPointY;
	
	private double targetBoxPointX;
	private double targetBoxPointY;
	
	private double boxX;
	private double boxY;
	private double boxWidth;
	private double boxHeight;
	
	private String sourceBoxPointId;
	private String sourcePointId;
	private String targetBoxPointId;
	private String targetPointId;
	
	public CreateMetaConnectionCommand(){}
	
	public CreateMetaConnectionCommand(String id, MetaConnection mc) {
		setId(id);
		this.metaConnectionId = mc.getID();
		this.name = mc.getName();
		this.metaClassSourceId = mc.getSource().getID();
		this.metaClassTargetId = mc.getTarget().getID();
		
		this.sourceBoxPointX = mc.getSourceConnectionBoxRelativePoint().x;
		this.sourceBoxPointY = mc.getSourceConnectionBoxRelativePoint().y;

		this.sourcePointX = mc.getSourceRelativePoint().x;
		this.sourcePointY = mc.getSourceRelativePoint().y;
		
		this.targetBoxPointX = mc.getTargetConnectionBoxRelativePoint().x;
		this.targetBoxPointY = mc.getTargetConnectionBoxRelativePoint().y;
		
		this.targetPointX = mc.getTargetRelativePoint().x;
		this.targetPointY = mc.getTargetRelativePoint().y;
		
		this.sourceBoxPointId = mc.getSourceConnectionBoxRelativePoint().getID();
		this.sourcePointId = mc.getSourceRelativePoint().getID();
		this.targetBoxPointId = mc.getTargetConnectionBoxRelativePoint().getID();
		this.targetPointId = mc.getTargetRelativePoint().getID();
		
		this.boxX = mc.getConnectionBoxX();
		this.boxY = mc.getConnectionBoxY();
		this.boxWidth = mc.getConnectionBoxWidth();
		this.boxHeight = mc.getConnectionBoxHeight();
	}
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		name = a.serialize("name", name).value;
		metaClassSourceId = a.serialize("metaClassSourceId", metaClassSourceId).value;
		metaClassTargetId = a.serialize("metaClassTargetId", metaClassTargetId).value;
		sourceBoxPointX = a.serialize("sourceBoxPointX", sourceBoxPointX).value;
		sourceBoxPointY = a.serialize("sourceBoxPointX", sourceBoxPointY).value;
		sourcePointX = a.serialize("sourcePointX", sourcePointX).value;
		sourcePointY = a.serialize("sourcePointX", sourcePointY).value;
		targetBoxPointX = a.serialize("targetBoxPointX", targetBoxPointX).value;
		targetBoxPointY = a.serialize("targetBoxPointY", targetBoxPointY).value;
		targetPointX = a.serialize("targetPointX", targetPointX).value;
		targetPointY = a.serialize("targetPointY", targetPointY).value;
		
		sourceBoxPointId = a.serialize("sourceBoxPointId", sourceBoxPointId).value;
		sourcePointId = a.serialize("sourcePointId", sourcePointId).value;
		targetBoxPointId = a.serialize("targetBoxPointId", targetBoxPointId).value;
		targetPointId = a.serialize("targetPointId", targetPointId).value;
		
		
		boxX = a.serialize("boxX", boxX).value;
		boxY = a.serialize("boxY", boxY).value;
		boxWidth = a.serialize("boxWidth", boxWidth).value;
		boxHeight = a.serialize("boxHeight", boxHeight).value;
		
	}

	@Override
	public void execute() throws ManipulationException {
		
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass source = (MetaClass) mm.getElementByID(metaClassSourceId);
		MetaClass target = (MetaClass) mm.getElementByID(metaClassTargetId);
		
		
		MetaConnectionImpl connection = new MetaConnectionImpl(metaConnectionId, name, source, target);
		connection.setConnectionBoxHeight(boxHeight);
		connection.setConnectionBoxWidth(boxWidth);
		connection.setConnectionBoxX(boxX);
		connection.setConnectionBoxY(boxY);
		
		AnchorPoint sourcePoint = new AnchorPoint(sourcePointId, sourcePointX, sourcePointY);
		AnchorPoint sourceBoxPoint = new AnchorPoint(sourceBoxPointId, sourceBoxPointX, sourceBoxPointY);
		AnchorPoint targetPoint = new AnchorPoint(targetPointId, targetPointX, targetPointY);
		AnchorPoint targetBoxPoint = new AnchorPoint(targetBoxPointId, targetBoxPointX, targetBoxPointY);
		
		sourcePoint.setNextAnchorPoint(sourceBoxPoint);
		targetBoxPoint.setNextAnchorPoint(targetPoint);
		
		connection.setSourceConnectionBoxRelativePoint(sourceBoxPoint);
		connection.setSourceRelativePoint(sourcePoint);
		
		connection.setTargetConnectionBoxRelativePoint(targetBoxPoint);
		connection.setTargetRelativePoint(targetPoint);
		
		
		mm.addMetaConnection(connection, source);
		
	}

	@Override
	public void undo() throws ManipulationException {
		
		MetaModel mm = (MetaModel) getCollaborateable();
		
		MetaConnection mc =(MetaConnection) mm.getElementByID(metaConnectionId);
		mm.removeMetaConnection(mc);
		
		
	}

}
