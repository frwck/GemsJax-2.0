package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.impl.MetaAttributeImpl;

public class CreateMetaConnectionAttributeCommand extends CommandImpl{

	private String metaConnectionId;
	private String metaAttributeId;
	private String name;
	private String metaTypeName;
	
	
	public CreateMetaConnectionAttributeCommand(){}
	
	public CreateMetaConnectionAttributeCommand(String id, MetaConnection metaConnection, String attributeId, String name, MetaBaseType type){
		setId(id);
		metaConnectionId = metaConnection.getID();
		this.name =name;
		metaTypeName = type.getName();
		metaAttributeId = attributeId;
	}
	
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
		metaAttributeId = a.serialize("metaAttributeId", metaAttributeId).value;
		name = a.serialize("name", name).value;
		metaTypeName = a.serialize("metaTypeName", metaTypeName).value;
	
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
		MetaBaseType type = getTypeFromName(mm, metaTypeName);
		mc.addAttribute(new MetaAttributeImpl(metaAttributeId, name, type));
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
		MetaAttribute a = mc.getAttributeById(metaAttributeId);
		mc.removeAttribute(a);
	}

}
