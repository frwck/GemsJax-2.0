package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.SemanticException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.impl.MetaClassImpl;

public class CreateMetaClassCommand extends CommandImpl{

	// Serialize stuff
	private double x;
	private double y;
	private String name;
	private String metaClassId;
	private double width;
	private double height;
	
	
	// NON serialize
	private MetaModel metaModel;

	
	public CreateMetaClassCommand(){}
	
	public CreateMetaClassCommand(String commandId, String metaClassId, String name, double x, double y, double width, double height, MetaModel metaModel){
		setId(commandId);
		this.metaClassId = metaClassId;
		this.name =name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.metaModel = metaModel;
	}
	

	@Override
	public void serialize(Archive a) throws Exception {
		super.serialize(a);
		x = a.serialize("x", x).value;
		y = a.serialize("y", y).value;
		name = a.serialize("name",name).value;
		metaClassId = a.serialize("metaClassId", metaClassId).value;
		width = a.serialize("width", width).value;
		height = a.serialize("height", height).value;
	}
	
	
	@Override
	public void execute() throws SemanticException {
		MetaClass mc  = new MetaClassImpl(metaClassId, name);
		mc.setWidth(width);
		mc.setHeight(height);
		mc.setX(x);
		mc.setY(y);
		
		metaModel.addMetaClass(mc);
		
	}

	@Override
	public void undo() {
		MetaClass mc = (MetaClass) metaModel.getElementByID(metaClassId);
		metaModel.removeMetaClass(mc);
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMetaClassId() {
		return metaClassId;
	}


	public void setMetaClassId(String id) {
		this.metaClassId = id;
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


	public MetaModel getMetaModel() {
		return metaModel;
	}


	public void setMetaModel(MetaModel metaModel) {
		this.metaModel = metaModel;
	}

}
