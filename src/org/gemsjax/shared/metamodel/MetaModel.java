package org.gemsjax.shared.metamodel;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.gemsjax.shared.metamodel.exception.MetaClassException;


/**
 * The common interface for the MetaModel implementation on client and server side.
 * So we define a common api that can be used for implementing concrete objects on server and client.
 * We also can implement common classes (for example to check the validity of a model) that can be used on
 * server and client side, by implementing such a common class only one time
 * @author Hannes Dorfmann
 *
 */
public interface MetaModel {

	
	/**
	 * Get the unique {@link MetaModel} ID
	 * @return
	 */
	public String getID();
	
	
	/**
	 * Get the name of this element
	 * @return
	 */
	public String getName();
	
	/**
	 * Set the name of this element.
	 * <b>Notice:</b> The name must also be unique in a MetaModel
	 * @param name
	 * @return
	 */
	public void setName(String name);

	/**
	 * Create and add a {@link MetaClass}
	 * @param metaClass
	 */
	public MetaClass addMetaClass(String id, String name) throws MetaClassException;
	
	/**
	 * Remove {@link MetaClass}
	 * @param metaClass
	 */
	public void removeMetaClass(MetaClass metaClass);
	
	/**
	 * Return a list with all {@link MetaClass}es, but the List it self is read only,
	 * by implementing it via {@link Collections#unmodifiableList(List)}.
	 * So use {@link #addMetaClass(String, String)} and {@link #removeMetaClass(MetaClass)}
	 * @return
	 */
	public List<MetaClass> getMetaClasses();
	
	/**
	 * Add a {@link MetaBaseType} to this MetaModel
	 * @param baseType
	 */
	public void addBaseType(MetaBaseType baseType);
	
	/**
	 * Remove a {@link MetaBaseType}
	 * @param baseType
	 */
	public void removeBaseType(MetaBaseType baseType);
	
	
	/**
	 * Get a List with all {@link MetaBaseType}s, but this list is read only.
	 * So use to {@link #addBaseType(MetaBaseType)} and {@link #removeBaseType(MetaBaseType)} to manipulate this list.
	 * @return
	 */
	public List<MetaBaseType> getBaseTypes();
	
	
	/**
	 * For a quick access to every {@link MetaClass}, {@link MetaAttribute}, {@link MetaContainmentRelation} and {@link MetaConnection}
	 * the MetaModelElement should provide this method, where you can get a element by its unique id. Normally a {@link HashMap} should be
	 * used to implement that. 
	 * @param id The unique ID
	 * @return
	 */
	public MetaModelElement getElementByID(String id);
	
}
