package org.gemsjax.client.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.MetaModelElement;
import org.gemsjax.shared.metamodel.exception.MetaBaseTypeException;
import org.gemsjax.shared.metamodel.exception.MetaClassException;


/**
 * The client side implementation
 * @author Hannes Dorfmann
 *
 */
public class MetaModelImpl implements MetaModel{
	
	private String name;
	
	private String id;
	
	private List<MetaClass> metaClasses;
	private List<MetaBaseType> baseTypes;
	
	/**
	 * This map provides a quick access map for getting a element by its id
	 */
	private Map<String, MetaModelElement> idMap;
	
	public MetaModelImpl(String id, String name)
	{
		this.name = name;
		this.id = id;
		
		metaClasses = new ArrayList<MetaClass>();
		baseTypes = new ArrayList<MetaBaseType>();
		idMap = new HashMap<String, MetaModelElement>();		
	}
	
	/**
	 * Check if a Name is available.
	 * That means, that this name is not used by another {@link MetaClassImpl}
	 * @param desiredName
	 * @return true otherwise it will throw an {@link NameNotAvailableException}
	 * @throws NameNotAvailableException if the name is already assigned to another {@link MetaClass}
	 */
	public boolean isMetaClassNameAvailable(String desiredName) throws MetaClassException
	{
		for (MetaClass m: metaClasses)
			if (m.getName().equals(desiredName))
				throw new MetaClassException(desiredName, this);
		
		
		return true;
	}
	
	
	/**
	 * Creates a MetaClass and add this new MetaClass to this MetaModel
	 * @param metaClassName
	 * @throws MetaClassException if the name of the {@link MetaClassImpl} is already assigned to another {@link MetaClassImpl} and so not available
	 * @return The new created {@link MetaClassImpl} object
	 */
	@Override
	public MetaClass addMetaClass(String id, String metaClassName) throws MetaClassException{
		
		if (isMetaClassNameAvailable(metaClassName))
		{
			MetaClassImpl mc = new MetaClassImpl(id,metaClassName);
			metaClasses.add(mc);
			return mc;
		}
		
		// Should never be reached, because isMetaClassNameAvailable should throw a MetaClassNameExcption
		return null;
	}

	@Override
	public void addBaseType(MetaBaseType baseType) throws MetaBaseTypeException {
		
		for (MetaBaseType t: baseTypes)
			if (t.getID().equals(baseType.getID()) || t.getName().equals(baseType.getName()))
				throw new MetaBaseTypeException(this);
		
		baseTypes.add(baseType);
	}


	@Override
	public List<MetaBaseType> getBaseTypes() {
		return baseTypes;
	}

	@Override
	public MetaModelElement getElementByID(String id) {
		return idMap.get(id);
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public List<MetaClass> getMetaClasses() {
		return metaClasses;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void removeBaseType(MetaBaseType baseType) {
		baseTypes.remove(baseType);
	}

	@Override
	public void removeMetaClass(MetaClass metaClass) {
		metaClasses.remove(metaClass);
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
}
