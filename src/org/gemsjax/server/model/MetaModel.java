package org.gemsjax.server.model;

import java.util.HashMap;
import java.util.Map;

public class MetaModel {
	
	/**
	 * A unique id
	 */
	private String id;
	
	/**
	 * The name
	 */
	private String name;

	/**
	 * A map for quick access to all elements which are part of this MetaModel (like {@link MetaClass}, {@link MetaConnection}, {@link MetaAttribute}, {@link MetaContainmentRelation}) 
	 */
	private Map <String, MetaModelElement> idMap;
	
	
	public MetaModel(String id, String name)
	{
		this.idMap = new HashMap<String, MetaModelElement>();
	}

}
