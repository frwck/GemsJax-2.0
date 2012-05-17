package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.impl.MetaAttributeImpl;

public class CreateMetaAttributeCommand extends CommandImpl {

	private String name;
	private String metaBaseTypeName;
	private String metaClassId;
	private String metaAttributeId;
	
	public CreateMetaAttributeCommand(){}
	
		
	public CreateMetaAttributeCommand(String id, String metaClassId, String metaAttributeId, String name, MetaBaseType metaBaseType ){
		setId(id);
		this.metaAttributeId = metaAttributeId;
		this.metaClassId = metaClassId;
		this.metaBaseTypeName = metaBaseType.getName();
		this.name = name;
	}
	
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		name = a.serialize("name", name).value;
		metaBaseTypeName = a.serialize("metaBaseTypeName", metaBaseTypeName).value;
		metaClassId = a.serialize("metaClassId", metaClassId).value;
		metaAttributeId = a.serialize("metaAttributeId",metaAttributeId).value;
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
		MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
		MetaBaseType type = getTypeFromName(mm, metaBaseTypeName);
		mc.addAttribute(new MetaAttributeImpl(metaAttributeId, name, type));
		
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
		MetaAttribute a = mc.getAttributeById(metaAttributeId);
		mc.removeAttribute(a);
	}

}
