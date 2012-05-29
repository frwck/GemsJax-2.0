package org.gemsjax.shared.metamodel.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.collaboration.CollaborateableImpl;
import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.MetaModelElement;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;
import org.gemsjax.shared.metamodel.exception.MetaBaseTypeException;
import org.gemsjax.shared.metamodel.exception.MetaClassException;
import org.gemsjax.shared.metamodel.exception.MetaConnectionException;
import org.gemsjax.shared.model.Model;



/**
 * The client side implementation
 * @author Hannes Dorfmann
 *
 */
public class MetaModelImpl extends CollaborateableImpl implements MetaModel{
	
	
	private List<MetaClass> metaClasses;
	private List<MetaBaseType> baseTypes;
	private List<MetaAttribute> attributes;
	
	
	private Set<Model> models;
	
	
	/**
	 * This map provides a quick access map for getting a element by its id
	 */
	private Map<String, MetaModelElement> idMap;
	
	public MetaModelImpl(int id, String name)
	{
		this();
		setId(id);
		setName(name);
		
	}
	
	public MetaModelImpl(){
		models = new LinkedHashSet<Model>();
		metaClasses = new ArrayList<MetaClass>();
		baseTypes = new ArrayList<MetaBaseType>();
		idMap = new HashMap<String, MetaModelElement>();	
		attributes = new ArrayList<MetaAttribute>();
	}
	
	
	/**
	 * Check if a Name is available.
	 * That means, that this name is not used by another {@link MetaClassImpl}
	 * @param desiredName
	 * @return true otherwise it will throw an {@link NameNotAvailableException}
	 * @throws NameNotAvailableException if the name is already assigned to another {@link MetaClass}
	 */
	public boolean isMetaClassNameAvailable(String desiredName)
	{
		for (MetaClass m: metaClasses)
			if (m.getName().equals(desiredName))
				return false;
		
		
		return true;
	}
	
	
	/**
	 * Add a {@link MetaClass} to this {@link MetaModel}
	 * @param metaClassName
	 * @throws MetaClassException if the name of the {@link MetaClassImpl} is already assigned to another {@link MetaClassImpl} and so not available
	 * @return The new created {@link MetaClassImpl} object
	 */
	@Override
	public void addMetaClass(MetaClass metaClass) throws MetaClassException {
		
		
		
		if (isMetaClassNameAvailable(metaClass.getName()))
		{	
			metaClasses.add(metaClass);
			idMap.put(metaClass.getID(), metaClass);
		}
		
		else
			throw new MetaClassException(metaClass.getName(), this);
	}

	@Override
	public void addBaseType(MetaBaseType baseType) throws MetaBaseTypeException {
		
		for (MetaBaseType t: baseTypes)
			if (t.getID().equals(baseType.getID()) || t.getName().equals(baseType.getName()))
				throw new MetaBaseTypeException(this,baseType.getName());
		
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
	public List<MetaClass> getMetaClasses() {
		return metaClasses;
	}

	

	@Override
	public void removeBaseType(MetaBaseType baseType) {
		baseTypes.remove(baseType);
	}

	@Override
	public void removeMetaClass(MetaClass metaClass) {
		metaClasses.remove(metaClass);
		idMap.remove(metaClass.getID());
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

	


	@Override
	public Set<Model> getModels() {
		return models;
	}

	

	@Override
	public boolean isClassRelationNameAvailable(String name) {
		
		String toUpper = name.toUpperCase();
		
		for (MetaClass m: metaClasses){
			if (m.getName().toUpperCase().equals(toUpper))
				return false;
		
			for(MetaConnection c : m.getConnections())
				if (c.getName().toUpperCase().equals(toUpper))
						return false;
		}
		
		
		return true;
	}

	@Override
	public void addMetaConnection(MetaConnection connection, MetaClass owner) throws MetaConnectionException {
		
		for (MetaClass c: metaClasses)
			for (MetaConnection con : c.getConnections())
				if (con.getName().equals(connection.getName()))
					throw new MetaConnectionException(c, con.getName());
		
		
		if (connection.getSource()!=owner)
			throw new MetaConnectionException("The owner ist not the same as the specified owner in the connection");
		
		
		owner.addConnection(connection);
		idMap.put(connection.getID(), connection);
		
	}

	@Override
	public void removeMetaConnection(MetaConnection connection)
			throws MetaConnectionException {
	
		connection.getSource().removeConnection(connection);
		idMap.remove(connection.getID());
		
	}

	@Override
	public MetaModelElement getAnchorPointOwner(AnchorPoint ap) {
		for (MetaClass c: metaClasses)
			for (MetaConnection con : c.getConnections())
				if (con.getAnchorPointById(ap.getID())!=null)
					return con;
		
		
		//TODO check MetaInheritance
		//TODO check MetaContainment
		
		
		return null;
	}

	

	
}
