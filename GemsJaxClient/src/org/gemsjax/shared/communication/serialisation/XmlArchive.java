package org.gemsjax.shared.communication.serialisation;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Per conversation, a {@link Date} Object is serialized by its timestamp {@link Date#getTime()}
 * @author Hannes Dorfmann
 *
 */
public interface XmlArchive extends Archive {
	
	/**
	 * Define this as the root tag name, 
	 */
	public static final String ROOT_TAG_NAME ="tag";
	/**
	 * This xml attribute is set to true, if the value was null
	 */
	public static final String ATTRIBUTE_NULL_VALUE ="null";
	
	public static final String ATTRIBUTE_CLASS ="class";
	
	/**
	 * This tag is reserved for elements in a {@link Set}
	 */
	public static final String SUBTAG_SET_ELEMENT ="elem";

	/**
	 * This tag is reserved for elements in a {@link List}
	 */
	public static final String SUBTAG_LIST_ELEMENT = "elem";
	
	/**
	 * This tag is reserved for the key of a {@link Map} key-value pair
	 */
	public static final String SUBSUBTAG_MAP_ELEMENT_KEY="key";
	
	/**
	 * This tag is reserved for the value of a {@link Map} key-value pair
	 */
	public static final String SUBSUBTAG_MAP_ELEMENT_VALUE="val";
	
	
	public static final String SUBTAG_MAP_ELEMENT = "elem";
	
}


