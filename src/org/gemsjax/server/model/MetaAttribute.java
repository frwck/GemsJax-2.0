package org.gemsjax.server.model;

/**
 * This class represents Attributes. This attributes are used by the {@link MetaClass} and {@link MetaConnection} to model attributes.
 * @author Hannes Dorfmann
 *
 */
public class MetaAttribute implements MetaModelElement{

	private String id;
	private String name;
	private MetaBaseType type;
	
	
	public MetaAttribute(String id, String name, MetaBaseType type)
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

	public MetaBaseType getType() {
		return type;
	}

	public void setType(MetaBaseType type) {
		this.type = type;
	}

}
