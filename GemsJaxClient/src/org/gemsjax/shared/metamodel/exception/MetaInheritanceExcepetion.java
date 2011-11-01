package org.gemsjax.shared.metamodel.exception;

import org.gemsjax.shared.metamodel.MetaClass;

/**
 * This exception is thrown, when you try to create a inheritance between the super class ({@link #getSuperClass()}) 
 * and the sub class ({@link #getSubClass()}).
 * @author Hannes Dorfmann
 *
 */
public class MetaInheritanceExcepetion extends Exception {
	
	
	private MetaClass superClass;
	private MetaClass subClass;
	
	
	public MetaInheritanceExcepetion(MetaClass subClass, MetaClass superClass)
	{
		this.subClass = subClass;
		this.superClass = superClass;
	}



	public MetaClass getSuperClass() {
		return superClass;
	}


	public MetaClass getSubClass() {
		return subClass;
	}

}
