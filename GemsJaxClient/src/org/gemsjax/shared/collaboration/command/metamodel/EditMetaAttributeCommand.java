package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class EditMetaAttributeCommand extends CommandImpl{
	
	private String attributeId;
		private String metaClassId;
	private String newName;
	private String oldName;
	private String newMetaBaseTypeName;
	private String oldMetaBaseTypeName;
	private String metaConnectionId;
	
	
	public EditMetaAttributeCommand(){
		
	}
	
	public EditMetaAttributeCommand(String id, String attributeId, MetaClass metaClass,
			String newName, String oldName, MetaBaseType newMetaBaseType,
			MetaBaseType oldMetaBaseType) {
		setId(id);
		this.attributeId = attributeId;
		this.metaClassId = metaClass.getID();
		this.newName = newName;
		this.oldName = oldName;
		this.newMetaBaseTypeName = newMetaBaseType.getName();
		this.oldMetaBaseTypeName = oldMetaBaseType.getName();
	}
	
	public EditMetaAttributeCommand(String id, String attributeId, MetaConnection metaConnection,
			String newName, String oldName, MetaBaseType newMetaBaseType,
			MetaBaseType oldMetaBaseType) {
		setId(id);
		this.attributeId = attributeId;
		this.metaConnectionId = metaConnection.getID();
		this.newName = newName;
		this.oldName = oldName;
		this.newMetaBaseTypeName = newMetaBaseType.getName();
		this.oldMetaBaseTypeName = oldMetaBaseType.getName();
	}
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		attributeId = a.serialize("attributeId", attributeId).value;
		metaClassId = a.serialize("metaClassId", metaClassId).value;
		newName = a.serialize("newName", newName).value;
		oldName = a.serialize("oldName", oldName).value;
		newMetaBaseTypeName = a.serialize("newMetaBaseTypeName", newMetaBaseTypeName).value;
		oldMetaBaseTypeName = a.serialize("oldMetaBaseTypeName", oldMetaBaseTypeName).value;
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
	}


	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		if (metaClassId!=null){
			MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
			MetaBaseType type = getTypeFromName(mm, newMetaBaseTypeName);
			MetaAttribute a = mc.getAttributeById(attributeId);
			a.setName(newName);
			a.setType(type);
		}
		else
		if (metaConnectionId!=null){
			MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
			MetaBaseType type = getTypeFromName(mm, newMetaBaseTypeName);
			MetaAttribute a = mc.getAttributeById(attributeId);
			a.setName(newName);
			a.setType(type);
		}	
	}

	
	private MetaBaseType getTypeFromName(MetaModel mm, String metaBaseTypeName) throws ManipulationException{
		for(MetaBaseType t: mm.getBaseTypes())
			if(t.getName().equals(metaBaseTypeName))
				return t;
		
		throw new ManipulationException("Could not find a MetaBaseType with the name = "+metaBaseTypeName);
	}
	

	@Override
	public void undo() throws ManipulationException {
		
		MetaModel mm = (MetaModel) getCollaborateable();
		
		if (metaClassId!=null){
			MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
			MetaBaseType type = getTypeFromName(mm, oldMetaBaseTypeName);
			MetaAttribute a = mc.getAttributeById(attributeId);
			a.setName(oldName);
			a.setType(type);
		}
		else
		if (metaConnectionId!=null){
			MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
			MetaBaseType type = getTypeFromName(mm, oldMetaBaseTypeName);
			MetaAttribute a = mc.getAttributeById(attributeId);
			a.setName(oldName);
			a.setType(type);
		}
		
	}
	
	

}
