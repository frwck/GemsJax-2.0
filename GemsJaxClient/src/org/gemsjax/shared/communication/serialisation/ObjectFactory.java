package org.gemsjax.shared.communication.serialisation;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@link ObjectFactory} is used to create new instances of object.
 * This is done by calling {@link #createObject(String)}, which will call the corresponging (registered) {@link ObjectInstantiator}
 * 
 * @author Hannes Dorfmann
 *
 */
public class ObjectFactory {
	private Map<String, ObjectInstantiator> objectFactories;
	
	public ObjectFactory(){
		objectFactories = new LinkedHashMap<String, ObjectInstantiator>();
	}

	public Object createObject(String className) throws ClassNotFoundException {
		
		if (!objectFactories.containsKey(className))
			throw new ClassNotFoundException(className);
		
		return this.objectFactories.get(className).newInstance();
	}

	public void register(String name, ObjectInstantiator factory) {
		this.objectFactories.put(name, factory);
	}
	
	public void unregister(String name){
		this.objectFactories.remove(name);
	}
}
