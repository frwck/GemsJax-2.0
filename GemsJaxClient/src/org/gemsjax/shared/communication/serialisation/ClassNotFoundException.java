package org.gemsjax.shared.communication.serialisation;

/**
 * This Exception is throw, if you try to instantiate an Object by using the {@link ObjectFactory}, 
 * but there is no {@link ObjectInstantiator} registered for the desired Class
 * @author Hannes Dorfmann
 *
 */
public class ClassNotFoundException extends Exception{

	public ClassNotFoundException(String className){
		super("You try to instantiate an Object of type "+className+", but no ObjectInstantiator has been found for this class");
	}
	
}
