package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;

public class MoveMetaClassCommand  extends CommandImpl{

	private double oldX;
	private double oldY;
	
	private double x;
	private double y;
	
	private String metaClassId;
	
	
	public MoveMetaClassCommand(){}
	
	
	public MoveMetaClassCommand(String commandId, String  metaClassId, double x, double y, double oldX, double oldY){
		setId(commandId);
		this.metaClassId = metaClassId;
		this.x = x;
		this.y = y;
		this.oldX = oldX;
		this.oldY = oldY;
	}
	
	
	
	@Override
	public void serialize(Archive a) throws Exception {
		super.serialize(a);
		metaClassId = a.serialize("metaClassId", metaClassId).value;
		oldX = a.serialize("oldX", oldX).value;
		oldY = a.serialize("oldY", oldY).value;
		x = a.serialize("x", x).value;
		y = a.serialize("y", y).value;
		
	}
	
	
	@Override
	public void execute() throws ManipulationException {
		MetaModel metaModel = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) metaModel.getElementByID(metaClassId);
		mc.setX(x);
		mc.setY(y);
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel metaModel = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) metaModel.getElementByID(metaClassId);
		mc.setX(oldX);
		mc.setY(oldY);
	}


	public double getOldX() {
		return oldX;
	}


	public void setOldX(double oldX) {
		this.oldX = oldX;
	}


	public double getOldY() {
		return oldY;
	}


	public void setOldY(double oldY) {
		this.oldY = oldY;
	}


	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}


	public String getMetaClassId() {
		return metaClassId;
	}


	public void setMetaClassId(String metaClassId) {
		this.metaClassId = metaClassId;
	}
	
	

}
