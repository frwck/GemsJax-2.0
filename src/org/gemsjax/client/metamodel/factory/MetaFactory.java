package org.gemsjax.client.metamodel.factory;


import java.util.List;

import org.gemsjax.client.metamodel.MetaInheritanceImpl;
import org.gemsjax.client.util.UUID;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaInheritance;

public class MetaFactory {

	private  MetaFactory() {}
	
	
	private static String nextID()
	{
		return UUID.uuid().toString();
	}
	
	/**
	 * Creates a new {@link MetaInheritance} object. The unique ID will be generated and assigned.
	 * Use this method to create a brand new object
	 * @param superClass
	 * @return
	 */
	public static MetaInheritance createInheritance(MetaClass superClass) {
		
		MetaInheritance i = new MetaInheritanceImpl(nextID(), superClass);
		
		return i;
	}
	
	/**
	 * <b>Notice: This method should only be used, if you want to create a {@link MetaInheritance} which already was collaboratively created by another user on another computer</b>,
	 * because with this method you creates a new {@link MetaInheritance} object with the given ID which must be unique.
	 * If you want to create a new {@link MetaInheritance} object cause of local user interaction (example clicking on canvas) then use {@link #createInheritance(MetaClass)}.
	 * @param id The unique ID
	 * @param superClass The superClass
	 * @param superAnchorID The unique ID of the {@link AnchorPoint} on the super class
	 * @param superX The relative x coordinate for the super class {@link AnchorPoint}
	 * @param superY The relative y coordinate for the super class {@link AnchorPoint}
	 * @param classAnchorID
	 * @param classX The relative x coordinate for the class (which derives from the super class) {@link AnchorPoint}
	 * @param classY The relative y coordinate for the class (which derives from the super class) {@link AnchorPoint}
	 * @param otherAnchorPoints The list with the {@link AnchorPoint}s that are between the {@link MetaInheritance#getClassRelativeAnchorPoint()} and {@link MetaInheritance#getSuperClassRelativeAnchorPoint()} in the given list order <b>(the {@link AnchorPoint#getNextAnchorPoint()} must already be set correct)</b>.
	 *			That means that the list element with index 0 is the next element of the "class  {@link AnchorPoint}" and the list element with index 1 is the next  {@link AnchorPoint} of the element with index 0 ...
	 * @return The create {@link MetaInheritance} object
	 */
	public static MetaInheritance createExistingInheritance(String id, MetaClass superClass, String superAnchorID, double superX, double superY, String classAnchorID,  double classX, double classY, List<AnchorPoint> otherAnchorPoints )
	{
		MetaInheritance inh = new MetaInheritanceImpl(id, superClass);
		
		AnchorPoint sa = new AnchorPoint(superAnchorID, superX, superY);
		AnchorPoint ca = new AnchorPoint(classAnchorID, classX, classY);
		
		if (otherAnchorPoints == null || otherAnchorPoints.size() == 0)
			ca.setNextAnchorPoint(sa);
		else
		{
			ca.setNextAnchorPoint(otherAnchorPoints.get(0));
			otherAnchorPoints.get(otherAnchorPoints.size()-1).setNextAnchorPoint(sa);
		}
		
		inh.setClassRelativeAnchorPoint(ca);
		inh.setSuperClassRelativeAnchorPoint(sa);
		
		return inh;
	}
	
	

	
	
}
