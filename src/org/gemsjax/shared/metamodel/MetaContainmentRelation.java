package org.gemsjax.shared.metamodel;

import org.gemsjax.shared.model.ModelClass;
import org.gemsjax.shared.Constants;

/**
 * The common interface for a meta model containment relation implementation on client and server side.
 * We define a common api that can be used for implementing concrete objects on server and client.
 * We also can implement common classes (for example to check the validity of a model) that can be used on
 * server and client side, by implementing a common class only one time, but can be used both with the concrete
 * server implementation and the concrete client implementation.
 * 
 * <br /><br />
 * A {@link MetaClass} can contain other {@link MetaClass}es. With this {@link MetaContainmentRelation} you
 * can model this relation. You can specify how much concrete elements (in the model layer, not the meta model layer) 
 * can be contained by the owning {@link MetaClass#addContainmentRelation(MetaContainmentRelation)}
 * @author Hannes Dorfmann
 *
 */
public interface MetaContainmentRelation extends MetaModelElement{
	
	/**
	 * This constant indicates, that the multiplicity of this relation is arbitrarily, or in UML language a "*" 
	 */
	public static final int MULTIPLICITY_MANY = Constants.MULTIPLICITY_MANY;
	
	public int getMax();
	public int getMin();
	/**
	 * Set the maximum number of concrete model objects (of the type {@link ModelClass}) that can be modeled by this {@link MetaContainmentRelation}
	 * @param max A int between 0 and a concrete maximum. You can also use 
	 */
	public void setMax(int max);
	public void setMin(int min);
	
	/**
	 * Say which {@link MetaClass} can be contained by the owning {@link MetaClass} ({@link MetaClass#addContainmentRelation(MetaContainmentRelation)})
	 * @param metaClass
	 */
	public void setMetaClass(MetaClass metaClass);
	public MetaClass getMetaClass();

}
