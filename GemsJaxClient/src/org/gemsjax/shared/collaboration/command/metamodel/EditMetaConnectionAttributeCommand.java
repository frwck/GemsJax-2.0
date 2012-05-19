package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

public class EditMetaConnectionAttributeCommand extends CommandImpl {

	private String attributeId;
	private String newName;
	private String oldName;
	private String newMetaBaseTypeName;
	private String oldMetaBaseTypeName;
	private String metaConnectionId;
	
	public EditMetaConnectionAttributeCommand(){
		
	}
	
	public EditMetaConnectionAttributeCommand(String id, MetaConnection connection, MetaAttribute attribute, String newName, MetaBaseType newType){
		setId(id);
		this.attributeId = attribute.getID();
		this.metaConnectionId = connection.getID();
		this.newName = newName;
		this.newMetaBaseTypeName = newType.getName();
		this.oldName = attribute.getName();
		this.oldMetaBaseTypeName = attribute.getType().getName();
		
	}
	
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		attributeId = a.serialize("attributeId", attributeId).value;
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		newName = a.serialize("newName", newName).value;
		newMetaBaseTypeName = a.serialize("newMetaBaseTypeName", newMetaBaseTypeName).value;
		oldName = a.serialize("oldName", oldName).value;
		oldMetaBaseTypeName = a.serialize("oldMetaBaseTypeName", oldMetaBaseTypeName).value;
		
	}
	
	private MetaBaseType getTypeFromName(MetaModel mm, String metaBaseTypeName) throws ManipulationException{
		for(MetaBaseType t: mm.getBaseTypes())
			if(t.getName().equals(metaBaseTypeName))
				return t;
		
		throw new ManipulationException("Could not find a MetaBaseType with the name = "+metaBaseTypeName);
	}
	
	
	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		MetaBaseType type = getTypeFromName(mm, newMetaBaseTypeName);
		MetaAttribute a = mc.getAttributeById(attributeId);
		a.setName(newName);
		a.setType(type);
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		MetaBaseType type = getTypeFromName(mm, oldMetaBaseTypeName);
		MetaAttribute a = mc.getAttributeById(attributeId);
		a.setName(oldName);
		a.setType(type);
	}

}
