package org.gemsjax.server.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gemsjax.client.metamodel.MetaAttributeImpl;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.MetaModelElement;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;
import org.gemsjax.shared.metamodel.exception.MetaClassException;

public class MetaModelImpl  implements MetaModel {
	
	/**
	 * A unique id
	 */
	private String id;
	
	/**
	 * The name
	 */
	private String name;

	/**
	 * A map for quick access to all elements which are part of this MetaModel (like {@link MetaClassImpl}, {@link MetaConnectionImpl}, {@link MetaAttributeImpl}, {@link MetaContainmentRelationImpl}) 
	 */
	private Map <String, MetaModelElement> idMap;
	
	/**
	 * A MetaModel itself can have attributes, for example to add more information, like "company", project information, etc. 
	 */
	private List<MetaAttribute> attributes;
	
	/**
	 * The base types, that are available for this MetaModel, for example for the {@link MetaAttribute}s
	 */
	private List<MetaBaseType> baseTypes;
	
	private List<MetaClass> metaClasses;
	
	public MetaModelImpl(String id, String name)
	{
		this.idMap = new HashMap<String, MetaModelElement>();
		this.attributes = new ArrayList<MetaAttribute>();
		this.metaClasses = new ArrayList<MetaClass>();
	}




	@Override
	public void addBaseType(MetaBaseType baseType) {
		baseTypes.add(baseType);
		idMap.put(baseType.getID(), baseType);
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
		idMap.remove(baseType.getID());
	}


	@Override
	public void removeMetaClass(MetaClass metaClass) {
		metaClasses.remove(metaClass);
		idMap.remove(metaClass.getID());
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public MetaClass addMetaClass(String id, String name)
			throws MetaClassException {
		
		MetaClass metaClass = new MetaClassImpl(id, name);
		
		metaClasses.add(metaClass);
		idMap.put(metaClass.getID(), metaClass);
		
		return metaClass;
	}
	
	
	@Override
	public MetaAttribute addAttribute(String id, String name, MetaBaseType type) throws MetaAttributeException {
		
		for (MetaAttribute a: attributes)
			if (a.getID().equals(id) || a.getName().equals(name))
				throw new MetaAttributeException(name, this);
		
		MetaAttribute a = new MetaAttributeImpl(id, name, type);
		attributes.add(a);
		return a;
	}


	@Override
	public List<MetaAttribute> getAttributes() {
		return attributes;
	}


	@Override
	public void removeAttribute(MetaAttribute attribute) {
		attributes.remove(attribute);
	}

}
