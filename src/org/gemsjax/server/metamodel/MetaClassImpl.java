package org.gemsjax.server.metamodel;

import java.util.ArrayList;
import java.util.List;
import org.gemsjax.shared.metamodel.MetaClass;
/**
 * This class represents a MetaClass in a MetaModel
 * @author Hannes Dorfmann
 *
 */
public class MetaClassImpl extends MetaBaseTypeImpl{

	
	/**
	 * A flag to indicate, whenever this class is abstract or not
	 */
	private boolean _abstract;
	
	/**
	 * The list with all {@link MetaConnectionImpl}s that has this class as {@link MetaConnectionImpl#getSource()} as start / source element.
	 */
	private List<MetaConnectionImpl> connections;
	
	
	/**
	 * The list with all {@link MetaAttributeImpl}s to model attributes, which belong to this {@link MetaClassImpl}
	 */
	private List<MetaAttributeImpl> attributes;
	
	
	public MetaClassImpl(String id, String name) {
		super(id, name);
		_abstract = false;
		connections = new ArrayList<MetaConnectionImpl>();
		attributes = new ArrayList<MetaAttributeImpl>();
	}


	public boolean isAbstract() {
		return _abstract;
	}


	public void setAbstract(boolean abstract1) {
		_abstract = abstract1;
	}

	
	public List<MetaConnectionImpl> getConnections()
	{
		return connections;
	}
	
	/**
	 *	Its just a short cut for {@link #getConnections()}.add();
	 * @param c
	 */
	public void addConnection(MetaConnectionImpl c)
	{
		//TODO is a check for duplicate names needed?
		
		connections.add(c);
	}
	
	/**
	 * It's just a short cut for {@link #getConnections()}.remove();
	 * If the element, which should be removed is not in the connection list, nothing happens
	 * @param c
	 */
	public void removeConnection(MetaConnectionImpl c)
	{
		connections.remove(c);
	}
	
	public List<MetaAttributeImpl> getAttributes()
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
	
}
