package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.impl.MetaAttributeImpl;

public class DeleteMetaAttributeCommand extends CommandImpl{

	private String metaClassId;
	private String metaAttributeId;
	private String metaConnectionId;
	
	private String originalAttributeName;
	private String originalAttribueTypeName;
	
	
	public DeleteMetaAttributeCommand(){}
	
	public DeleteMetaAttributeCommand(String id, MetaClass metaClass, MetaAttribute attribute)
	{
		setId(id);
		this.metaClassId = metaClass.getID();
		this.metaAttributeId = attribute.getID();
		this.originalAttribueTypeName = attribute.getType().getName();
		this.originalAttributeName = attribute.getName();
	}
	
	
	public DeleteMetaAttributeCommand(String id, MetaConnection connection, MetaAttribute attribute)
	{
		setId(id);
		this.metaConnectionId = connection.getID();
		this.metaAttributeId = attribute.getID();
		this.originalAttribueTypeName = attribute.getType().getName();
		this.originalAttributeName = attribute.getName();
	}
	
	
	@Override
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaClassId = a.serialize("metaClassId", metaClassId).value;
		metaAttributeId = a.serialize("metaAttributeId", metaAttributeId).value;
		originalAttribueTypeName = a.serialize("originalAttribueTypeName", originalAttribueTypeName).value;
		originalAttributeName = a.serialize("originalAttributeName", originalAttributeName).value;
		metaConnectionId = a.serialize("metaConnectionId", metaConnectionId).value;
	}
	
	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		if (metaClassId!=null){
			MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
			MetaAttribute a = mc.getAttributeById(metaAttributeId);
			mc.removeAttribute(a);
		}
		else
		if (metaConnectionId!=null){
			MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
			MetaAttribute a = mc.getAttributeById(metaAttributeId);
			mc.removeAttribute(a);
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
		if(metaClassId!=null){
			MetaClass mc = (MetaClass) mm.getElementByID(metaClassId);
			MetaAttribute a = new MetaAttributeImpl(metaAttributeId, originalAttributeName, getTypeFromName(mm, originalAttribueTypeName));
			mc.addAttribute(a);
		}
		else
		if(metaConnectionId!=null){
			MetaConnection mc = (MetaConnection) mm.getElementByID(metaConnectionId);
			MetaAttribute a = new MetaAttributeImpl(metaAttributeId, originalAttributeName, getTypeFromName(mm, originalAttribueTypeName));
			mc.addAttribute(a);
		}
	}
	
	

}
