package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;

public class RenameMetaClassCommand extends CommandImpl{
	
	private String metaClassId;
	private String newName;
	private String oldName;
	
	
	public RenameMetaClassCommand(){}
	
	public RenameMetaClassCommand(String id, MetaClass metaClass, String newName){
		setId(id);
		this.metaClassId = metaClass.getID();
		this.newName = newName;
		this.oldName = metaClass.getName();
	}
	
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaClassId = a.serialize("metaClassId", metaClassId).value;
		newName = a.serialize("newName", newName).value;
		oldName = a.serialize("oldName", oldName).value;
	}
	
	
	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
		mc.setName(newName);
	}
	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
		mc.setName(oldName);
	}
	
	
}
