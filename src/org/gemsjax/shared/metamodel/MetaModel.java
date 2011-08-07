package org.gemsjax.shared.metamodel;

import java.util.HashMap;
import java.util.List;


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
	 * Add a {@link MetaClass}
	 * @param metaClass
	 */
	public void addMetaClass(MetaClass metaClass);
	
	/**
	 * Remove {@link MetaClass}
	 * @param metaClass
	 */
	public void removeMetaClass(MetaClass metaClass);
	
	
	public List<MetaClass> getMetaClasses();
	
	
	public void addBaseType(MetaBaseType baseType);
	
	public void removeBaseType(MetaBaseType baseType);
	
	public List<MetaBaseType> getBaseTypes();
	
	
	public void addAttribute(MetaAttribute attribute);
	public void removeAttribute(MetaAttribute attribute);
	public List<MetaAttribute> getAttributes();
	
	/**
	 * For a quick access to every {@link MetaClass}, {@link MetaAttribute}, {@link MetaContainmentRelation} and {@link MetaConnection}
	 * the MetaModelElement should provide this method, where you can get a element by its unique id. Normally a {@link HashMap} should be
	 * used to implement that. 
	 * @param id The unique ID
	 * @return
	 */
	public MetaModelElement getElementByID(String id);
	
}
