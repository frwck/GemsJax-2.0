package org.gemsjax.server.metamodel;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaContainmentRelation;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;
import org.gemsjax.shared.metamodel.exception.MetaConnectionException;
import org.gemsjax.shared.metamodel.exception.MetaInheritanceExcepetion;
/**
 * This class represents a MetaClass in a MetaModel
 * @author Hannes Dorfmann
 *
 */
public class MetaClassImpl implements MetaClass{

	/**
	 * The unique id
	 */
	private String id;
	
	/**
	 * The unique name (within the MetaModel)
	 */
	private String name;
	
	
	/**
	 * A flag to indicate, whenever this class is abstract or not
	 */
	private boolean _abstract;
	
	/**
	 * The list with all {@link MetaConnection} that has this class as start / source element.
	 */
	private List<MetaConnection> connections;
	
	
	/**
	 * The list with all {@link MetaAttribute}s to model attributes, which belong to this {@link MetaClassImpl}
	 */
	private List<MetaAttribute> attributes;
	
	
	private List<MetaClass> inheritances;
	
	
	private List<MetaContainmentRelation> containmentRelations;
	
	
	public MetaClassImpl(String id, String name) {
		this.id = id;
		this.name = name;
		_abstract = false;
		connections = new ArrayList<MetaConnection>();
		attributes = new ArrayList<MetaAttribute>();
		inheritances = new ArrayList<MetaClass>();
		containmentRelations = new ArrayList<MetaContainmentRelation>();
	}


	public boolean isAbstract() {
		return _abstract;
	}


	public void setAbstract(boolean abstract1) {
		_abstract = abstract1;
	}

	@Override
	public List<MetaConnection> getConnections()
	{
		return connections;
	}
	
	
	
	@Override
	public void removeConnection(MetaConnection c)
	{
		connections.remove(c);
	}
	
	@Override
	public List<MetaAttribute> getAttributes()
	{
		return attributes;
	}
	
	/**
	 *	Its just a short cut for {@link #getAttributes()}.add();
	 * @param c
	 */
	public void addAttribute(MetaAttributeImpl a)
	{
		//TODO is a check for duplicate names needed?
		
		attributes.add(a);
	}
	
	/**
	 * It's just a short cut for {@link #getAttributes()}.remove();
	 * If the element, which should be removed is not in the attributes list, nothing happens
	 * @param a
	 */
	public void removeConnection(MetaAttributeImpl a)
	{
		attributes.remove(a);
	}


	@Override
	public MetaAttribute addAttribute(String id, String name, MetaBaseType type)
			throws MetaAttributeException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public MetaConnection addConnection(String id, String name, int lower,
			int upper) throws MetaConnectionException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public MetaContainmentRelation addContainmentRelation(String id, int max,
			int min) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void addInheritance(MetaClass superMetaClass)
			throws MetaInheritanceExcepetion {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<MetaContainmentRelation> getContainmentRelations() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<MetaBaseType> getInheritances() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void removeAttribute(MetaAttribute attribute) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeContainmentRelation(MetaContainmentRelation relation) {
		containmentRelations.remove(relation);
	}


	@Override
	public void removeInheritance(MetaClass type) {
		this.inheritances.remove(type);
	}


	@Override
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String getID() {
		return id;
	}
	
}
