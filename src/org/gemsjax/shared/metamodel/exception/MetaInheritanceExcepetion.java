package org.gemsjax.shared.metamodel.exception;

import org.gemsjax.shared.metamodel.MetaClass;

/**
 * This exception is thrown, when you try to create a inheritance between the super class ({@link #getSuperClass()}) 
 * and the sub class ({@link #getSubClass()}).
 * @author Hannes Dorfmann
 *
 */
public class MetaInheritanceExcepetion extends Exception {
	
	public enum InheritanceExceptionReason
	{
		ALREADY_EXISTS
	}
	
	
	private InheritanceExceptionReason reason;
	private MetaClass superClass;
	private MetaClass subClass;
	
	
	public MetaInheritanceExcepetion(InheritanceExceptionReason reason, MetaClass subClass, MetaClass superClass)
	{
		this.reason = reason;
		this.subClass = subClass;
		this.superClass = superClass;
	}


	public InheritanceExceptionReason getReason() {
		return reason;
	}


	public MetaClass getSuperClass() {
		return superClass;
	}


	public MetaClass getSubClass() {
		return subClass;
	}

}
