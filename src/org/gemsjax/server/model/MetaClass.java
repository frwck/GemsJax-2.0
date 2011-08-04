package org.gemsjax.server.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a MetaClass in a MetaModel
 * @author Hannes Dorfmann
 *
 */
public class MetaClass extends MetaBaseType{

	
	/**
	 * A flag to indicate, whenever this class is abstract or not
	 */
	private boolean Abstract;
	
	/**
	 * The list with all {@link MetaConnection}s that has this class as {@link MetaConnection#getSource()} as start / source element.
	 */
	private List<MetaConnection> connections;
	
	
	/**
	 * The list with all {@link MetaAttribute}s to model attributes, which belong to this {@link MetaClass}
	 */
	private List<MetaAttribute> attributes;
	
	
	public MetaClass(String id, String name) {
		super(id, name);
		Abstract = false;
		connections = new ArrayList<MetaConnection>();
		attributes = new ArrayList<MetaAttribute>();
	}


	public boolean isAbstract() {
		return Abstract;
	}


	public void setAbstract(boolean abstract1) {
		Abstract = abstract1;
	}

	
	public List<MetaConnection> getConnections()
	{
		return connections;
	}
	
	/**
	 *	Its just a short cut for {@link #getConnections()}.add();
	 * @param c
	 */
	public void addConnection(MetaConnection c)
	{
		//TODO is a check for duplicate names needed?
		
		connections.add(c);
	}
	
	/**
	 * It's just a short cut for {@link #getConnections()}.remove();
	 * If the element, which should be removed is not in the connection list, nothing happens
	 * @param c
	 */
	public void removeConnection(MetaConnection c)
	{
		connections.remove(c);
	}
	
	public List<MetaAttribute> getAttributes()
	{
		return attributes;
	}
	
	/**
	 *	Its just a short cut for {@link #getAttributes()}.add();
	 * @param c
	 */
	public void addAttribute(MetaAttribute a)
	{
		//TODO is a check for duplicate names needed?
		
		attributes.add(a);
	}
	
	/**
	 * It's just a short cut for {@link #getAttributes()}.remove();
	 * If the element, which should be removed is not in the attributes list, nothing happens
	 * @param a
	 */
	public void removeConnection(MetaAttribute a)
	{
		attributes.remove(a);
	}
	
}
