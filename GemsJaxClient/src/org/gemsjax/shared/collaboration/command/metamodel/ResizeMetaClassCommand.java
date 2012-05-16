package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;

public class ResizeMetaClassCommand extends CommandImpl{
	
	private String metaClassId;
	private double oldWidth;
	private double oldHeight;
	private double width;
	private double height;
	
	
	public ResizeMetaClassCommand(){}
	
	public ResizeMetaClassCommand(String id, String metaClassId, double width, double height, double oldWidth, double oldHeight){
		setId(id);
		this.metaClassId = metaClassId;
		this.width = width;
		this.height = height;
		this.oldHeight =oldHeight;
		this.oldWidth = oldWidth;
	}

	@Override
	public void execute() throws ManipulationException {
		MetaModel metaModel = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) metaModel.getElementByID(metaClassId);
		mc.setWidth(width);
		mc.setHeight(height);
		
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel metaModel = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) metaModel.getElementByID(metaClassId);
		mc.setWidth(oldWidth);
		mc.setHeight(oldHeight);
		
	}
	
	@Override
	public void serialize(Archive a) throws Exception {
		super.serialize(a);
		metaClassId = a.serialize("metaClassId", metaClassId).value;
		width = a.serialize("width", width).value;
		height = a.serialize("height", height).value;
		oldWidth = a.serialize("oldWidth", oldWidth).value;
		oldHeight = a.serialize("oldHeight", oldHeight).value;
		
		
	}

	public String getMetaClassId() {
		return metaClassId;
	}

	public void setMetaClassId(String metaClassId) {
		this.metaClassId = metaClassId;
	}

	public double getOldWidth() {
		return oldWidth;
	}

	public void setOldWidth(double oldWidth) {
		this.oldWidth = oldWidth;
	}

	public double getOldHeight() {
		return oldHeight;
	}

	public void setOldHeight(double oldHeight) {
		this.oldHeight = oldHeight;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

}
