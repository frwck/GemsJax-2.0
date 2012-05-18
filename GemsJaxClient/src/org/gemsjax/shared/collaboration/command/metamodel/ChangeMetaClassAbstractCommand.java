package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;

public class ChangeMetaClassAbstractCommand extends CommandImpl{
	
	private String metaClassId;
	private boolean oldIsAbstract;
	private boolean newIsAbstract;
	
	
	public ChangeMetaClassAbstractCommand(){}
	
	public ChangeMetaClassAbstractCommand(String id, MetaClass metaClass, boolean isAbstract){
		setId(id);
		metaClassId = metaClass.getID();
		oldIsAbstract = metaClass.isAbstract();
		newIsAbstract = isAbstract;
	}
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaClassId = a.serialize("metaClassId", metaClassId).value;
		oldIsAbstract = a.serialize("oldIsAbstract", oldIsAbstract).value;
		newIsAbstract = a.serialize("newIsAbstract", newIsAbstract).value;
		
	}

	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
		mc.setAbstract(newIsAbstract);
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
		mc.setAbstract(oldIsAbstract);
	}

}
