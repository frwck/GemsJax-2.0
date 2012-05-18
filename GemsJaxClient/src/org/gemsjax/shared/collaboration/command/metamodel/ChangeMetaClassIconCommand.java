package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;

public class ChangeMetaClassIconCommand extends CommandImpl {
	
	private String metaClassId;
	private String newIconUrl;
	private String oldIconUrl;
	
	public ChangeMetaClassIconCommand(){}
	public ChangeMetaClassIconCommand(String id, MetaClass metaClass, String newIconUrl){
		setId(id);
		metaClassId = metaClass.getID();
		oldIconUrl = metaClass.getIconURL();
		this.newIconUrl = newIconUrl;
	}
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaClassId = a.serialize("metaClassId",metaClassId).value;
		newIconUrl = a.serialize("newIconUrl",newIconUrl).value;
		oldIconUrl = a.serialize("oldIconUrl",oldIconUrl).value;
	}
	

	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
		mc.setIconURL(newIconUrl);
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
		mc.setIconURL(oldIconUrl);
	}

}
