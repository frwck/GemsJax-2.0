package org.gemsjax.server.model;

/**
 * This class implements base type (or call it classifier).
 * The BaseType are String, int, float, double, char etc. 
 * @author Hannes Dorfmann
 *
 */
public class MetaBaseType implements MetaModelElement{

	/**
	 * The unique ID in this MetaModel
	 */
	private String id;
	/**
	 * The name, must be also unique, that means, that there can not be another MetaBasType with the same name in the same MetaModel
	 */
	private String name;
	
	
	
	public MetaBaseType (String id, String name)
	{
		this.id = id;
		this.name  = name;
	}

	/**
	 * @see #id
	 * @return
	 */
	public String getID() {
		return id;
	}

	/**
	 *  @see #id
	 * @param id
	 */
	public void setID(String id) {
		this.id = id;
	}

	/**
	 *  @see #name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @see #name
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

	
}