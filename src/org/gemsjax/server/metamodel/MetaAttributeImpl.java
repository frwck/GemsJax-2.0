package org.gemsjax.server.metamodel;

import org.gemsjax.shared.metamodel.MetaAttribute;
import org.gemsjax.shared.metamodel.MetaBaseType;

/**
 * This class represents Attributes. This attributes are used by the {@link MetaClassImpl} and {@link MetaConnectionImpl} to model attributes.
 * @author Hannes Dorfmann
 *
 */
public class MetaAttributeImpl implements MetaAttribute{

	private String id;
	private String name;
	private MetaBaseType type;
	
	
	public MetaAttributeImpl(String id, String name, MetaBaseType type)
	{
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public MetaBaseType getType() {
		return type;
	}

	@Override
	public void setType(MetaBaseType type) {
		this.type = type;
	}

}
