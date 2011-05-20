package org.gemsjax.client.admin.model.metamodel;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.canvas.MetaClassDrawable;

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

}
