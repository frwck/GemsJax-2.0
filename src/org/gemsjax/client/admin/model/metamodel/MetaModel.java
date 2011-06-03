package org.gemsjax.client.admin.model.metamodel;

import java.util.LinkedList;
import java.util.List;
<<<<<<< HEAD

import org.gemsjax.client.canvas.MetaClassDrawable;
=======
import org.gemsjax.client.canvas.Drawable;

import org.gemsjax.client.canvas.Drawable;
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248

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
	public boolean isMetaClassNameAvailable(String desiredName) throws NameNotAvailableException
	{
		for (MetaClass m: metaClassList)
			if (m.getName().equals(desiredName))
				throw new NameNotAvailableException(desiredName);
		
		
		return true;
	}
	
	
	/**
	 * Add a given MetaClass to this MetaModel
	 * @param m
	 * @throws NameNotAvailableException if the name of the {@link MetaClass} is already assigned to another {@link MetaClass} and so not available
	 */
	public void addMetaClass(MetaClass m) throws NameNotAvailableException{
		
		if (isMetaClassNameAvailable(m.getName()))
			metaClassList.add(m);
	}
<<<<<<< HEAD
=======
	
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248

}
