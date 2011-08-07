package org.gemsjax.server.metamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.MetaModelElement;

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
	public void addAttribute(MetaAttribute attribute) {
		attributes.add(attribute);
		idMap.put(attribute.getID(), attribute);
	}


	@Override
	public void addBaseType(MetaBaseType baseType) {
		baseTypes.add(baseType);
		idMap.put(baseType.getID(), baseType);
	}


	@Override
	public void addMetaClass(MetaClass metaClass) {
		metaClasses.add(metaClass);
		idMap.put(metaClass.getID(), metaClass);
	}


	@Override
	public List<MetaAttribute> getAttributes() {
		return attributes;
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
	public void removeAttribute(MetaAttribute attribute) {
		attributes.remove(attribute);
		idMap.remove(attribute.getID());
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

}
