package org.gemsjax.client.metamodel;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.exception.MetaClassException;



public class MetaModelImpl implements MetaModel{
	
	private String name;
	
	private List<MetaClassImpl> metaClassList;
	
	public MetaModelImpl(String name)
	{
		this.name = name;
		metaClassList = new LinkedList<MetaClassImpl>();
		
	}
	
	/**
	 * Check if a Name is available.
	 * That means, that this name is not used by another {@link MetaClassImpl}
	 * @param desiredName
	 * @return true otherwise it will throw an {@link NameNotAvailableException}
	 * @throws NameNotAvailableException if the name is already assigned to another {@link MetaClassImpl}
	 */
	public boolean isMetaClassNameAvailable(String desiredName) throws MetaClassException
	{
		for (MetaClassImpl m: metaClassList)
			if (m.getName().equals(desiredName))
				throw new MetaClassException(desiredName);
		
		
		return true;
	}
	
	
	/**
	 * Creates a MetaClass and add this new MetaClass to this MetaModel
	 * @param metaClassName
	 * @throws MetaClassException if the name of the {@link MetaClassImpl} is already assigned to another {@link MetaClassImpl} and so not available
	 * @return The new created {@link MetaClassImpl} object
	 */
	public MetaClassImpl addMetaClass(String metaClassName) throws MetaClassException{
		
		if (isMetaClassNameAvailable(metaClassName))
		{
			MetaClassImpl mc = new MetaClassImpl(metaClassName);
			metaClassList.add(mc);
			return mc;
		}
		
		// Should never be reached, because isMetaClassNameAvailable should throw a MetaClassNameExcption
		return null;
	}
	
	//TODO remove MetaClass by reference and by name

}
