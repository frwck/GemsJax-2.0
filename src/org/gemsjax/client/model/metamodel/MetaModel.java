package org.gemsjax.client.model.metamodel;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.admin.model.metamodel.exception.MetaClassNameException;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.canvas.Drawable;



public class MetaModel {
	
	private String name;
	
	private List<MetaClass> metaClassList;
	
	public MetaModel(String name)
	{
		this.name = name;
		metaClassList = new LinkedList<MetaClass>();
		
	}
	
	/**
	 * Check if a Name is available.
	 * That means, that this name is not used by another {@link MetaClass}
	 * @param desiredName
	 * @return true otherwise it will throw an {@link NameNotAvailableException}
	 * @throws NameNotAvailableException if the name is already assigned to another {@link MetaClass}
	 */
	public boolean isMetaClassNameAvailable(String desiredName) throws MetaClassNameException
	{
		for (MetaClass m: metaClassList)
			if (m.getName().equals(desiredName))
				throw new MetaClassNameException(desiredName);
		
		
		return true;
	}
	
	
	/**
	 * Creates a MetaClass and add this new MetaClass to this MetaModel
	 * @param metaClassName
	 * @throws MetaClassNameException if the name of the {@link MetaClass} is already assigned to another {@link MetaClass} and so not available
	 * @return The new created {@link MetaClass} object
	 */
	public MetaClass addMetaClass(String metaClassName) throws MetaClassNameException{
		
		if (isMetaClassNameAvailable(metaClassName))
		{
			MetaClass mc = new MetaClass(metaClassName);
			metaClassList.add(mc);
			return mc;
		}
		
		// Should never be reached, because isMetaClassNameAvailable should throw a MetaClassNameExcption
		return null;
	}
	
	//TODO remove MetaClass by reference and by name

}
