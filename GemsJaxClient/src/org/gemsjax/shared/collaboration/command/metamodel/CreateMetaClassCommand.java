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
	private String id;
	private double width;
	private double height;
	
	
	// NON serialize
	private MetaModel metaModel;

	
	
	public CreateMetaClassCommand(){}
	
	public CreateMetaClassCommand(String id, String name, double x, double y, double width, double height){
		this.id = id;
		this.name =name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	

	@Override
	public void serialize(Archive a) throws Exception {
		x = a.serialize("x", x).value;
		y = a.serialize("y", y).value;
		name = a.serialize("name",name).value;
		id = a.serialize("id", id).value;
		width = a.serialize("width", width).value;
		height = a.serialize("height", height).value;
	}
	
	
	@Override
	public void execute() throws SemanticException {
		MetaClass mc  = new MetaClassImpl(id, name);
		mc.setWidth(width);
		mc.setHeight(height);
		mc.setX(x);
		mc.setY(y);
		
		metaModel.addMetaClass(mc);
		
	}

	@Override
	public void undo() {
		MetaClass mc = (MetaClass) metaModel.getElementByID(id);
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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
