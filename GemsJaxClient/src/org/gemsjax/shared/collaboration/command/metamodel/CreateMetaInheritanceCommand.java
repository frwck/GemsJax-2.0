package org.gemsjax.shared.collaboration.command.metamodel;

import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.collaboration.ManipulationException;
import org.gemsjax.shared.collaboration.command.CommandImpl;
import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaInheritance;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.impl.MetaInheritanceImpl;

public class CreateMetaInheritanceCommand extends CommandImpl{

	private String metaInheritanceId;
	private String metaClassId;
	private String superClassId;
	
	private String ownerClassAnchorId;
	private double ownerClassAnchorX;
	private double ownerClassAnchorY;
	
	private String superClassAnchorId;
	private double superClassAnchorX;
	private double superClassAnchorY;
	
	
	public CreateMetaInheritanceCommand(){}
	
	public CreateMetaInheritanceCommand(String id, MetaInheritance inh){
		setId(id);
		metaInheritanceId = inh.getID();
		MetaClass clazz = inh.getOwnerClass();
		MetaClass superClass = inh.getSuperClass();
		metaClassId = clazz.getID();
		superClassId = superClass.getID();
		
		ownerClassAnchorId = inh.getOwnerClassRelativeAnchorPoint().getID();
		ownerClassAnchorX = inh.getOwnerClassRelativeAnchorPoint().x;
		ownerClassAnchorY = inh.getOwnerClassRelativeAnchorPoint().y;
		
		superClassAnchorId = inh.getSuperClass().getID();
		superClassAnchorX = inh.getSuperClassRelativeAnchorPoint().x;
		superClassAnchorY = inh.getSuperClassRelativeAnchorPoint().y;
	}
	
	public void serialize(Archive a) throws Exception{
		super.serialize(a);
		metaInheritanceId = a.serialize("metaInheritanceId", metaInheritanceId).value;
		metaClassId = a.serialize("metaClassId", metaClassId).value;
		superClassId = a.serialize("superClassId", superClassId).value;
		ownerClassAnchorId = a.serialize("ownerClassAnchorId", ownerClassAnchorId).value;
		ownerClassAnchorX = a.serialize("ownerClassAnchorX", ownerClassAnchorX).value;
		ownerClassAnchorY = a.serialize("ownerClassAnchorY", ownerClassAnchorY).value;
		superClassAnchorId = a.serialize("superClassAnchorId", superClassAnchorId).value;
		superClassAnchorX = a.serialize("superClassAnchorX", superClassAnchorX).value;
		superClassAnchorY = a.serialize("superClassAnchorY", superClassAnchorY).value;
	}
	
	
	@Override
	public void execute() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass ownerClass = (MetaClass) mm.getElementByID(metaClassId);
		MetaClass superClass = (MetaClass) mm.getElementByID(superClassId);
		
		MetaInheritance inh = new MetaInheritanceImpl(metaInheritanceId, ownerClass, superClass);
		AnchorPoint sourcePoint = new AnchorPoint(ownerClassAnchorId, ownerClassAnchorX, ownerClassAnchorY);
		AnchorPoint superPoint = new AnchorPoint(superClassAnchorId, superClassAnchorX, superClassAnchorY);
		sourcePoint.setNextAnchorPoint(superPoint);
		inh.setOwnerClassRelativeAnchorPoint(sourcePoint);
		inh.setSuperClassRelativeAnchorPoint(superPoint);
		
		ownerClass.addInheritance(inh);
		
	}

	@Override
	public void undo() throws ManipulationException {
		MetaModel mm = (MetaModel) getCollaborateable();
		MetaClass ownerClass = (MetaClass) mm.getElementByID(metaClassId);
		MetaInheritance inheritance = ownerClass.getInheritanceById(metaInheritanceId);
		ownerClass.removeInheritance(inheritance);
	}

}
