package org.gemsjax.shared.metamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.gemsjax.shared.collaboration.Collaborateable;
import org.gemsjax.shared.metamodel.exception.MetaAttributeException;
import org.gemsjax.shared.metamodel.exception.MetaBaseTypeException;
import org.gemsjax.shared.metamodel.exception.MetaClassException;


/**
 * The common interface for the MetaModel implementation on client and server side.
 * So we define a common api that can be used for implementing concrete objects on server and client.
 * We also can implement common classes (for example to check the validity of a model) that can be used on
 * server and client side, by implementing such a common class only one time
 * @author Hannes Dorfmann
 *
 */
public interface MetaModel  extends Collaborateable {

	
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
	public void addMetaClass(MetaClass metaClass) throws MetaClassException;
	
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
	 * @throws MetaBaseTypeException If you try to add a {@link MetaBaseType}, which was already added previously
	 */
	public void addBaseType(MetaBaseType baseType) throws MetaBaseTypeException;
	
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
	
	
	public MetaAttribute addAttribute(String id, String name, MetaBaseType type) throws MetaAttributeException;
	
	/**
	 * <b> Use this just as a read only list!</b> <br />
	 * Use {@link #addAttribute(String, String, MetaBaseType)} and {@link #removeAttribute(MetaAttribute)} to manipulate this list,
	 * but never manipulate this list directly
	 * @return
	 */
	public List<MetaAttribute> getAttributes();
	public void removeAttribute(MetaAttribute attribute);
	
}
