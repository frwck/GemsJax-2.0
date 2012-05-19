package org.gemsjax.client.communication.serialisation;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.ClassNotFoundException;
import org.gemsjax.shared.communication.serialisation.ObjectFactory;
import org.gemsjax.shared.communication.serialisation.ObjectInstantiator;
import org.gemsjax.shared.communication.serialisation.Serializable;
import org.gemsjax.shared.communication.serialisation.XmlArchive;
import org.gemsjax.shared.communication.serialisation.XmlSavingArchive;
import org.gemsjax.shared.communication.serialisation.Archive.Holder;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

/**
 * This class is a GWT xml deserilaizer. Use {@link #deserialize()} to retrieve
 * a Object from its xml representation generated by an
 * {@link XmlSavingArchive#serialize(Object)}.
 * 
 * Supported primitiv datatypes are:
 * <ul>
 * <li>java.lang.Integer</li>
 * <li>java.lang.String</li>
 * <li>java.lang.Float</li>
 * <li>java.lang.Double</li>
 * <li>java.lang.Long</li>
 * <li>java.lang.Date</li>
 * <li>java.lang.Boolean</li>
 * <li>java.lang.Short</li>
 * <li>java.lang.Char</li>
 * </ul>
 * 
 * All other classes must implement there {@link ObjectInstantiator} which must
 * be registrated to the constructor passed parameter {@link ObjectFactory}
 * 
 * 
 * Unfortunately arrays are not supported so far
 * 
 * @author Hannes Dorfmann
 * 
 */
public class GwtXmlLoadingArchive implements XmlArchive {

	private ObjectFactory factory;
	private Document dom;
	private Element currentElement;
	private static Set<String> primitivDataTypes;

	public GwtXmlLoadingArchive(String xmlRepresentation, ObjectFactory factory)
			throws Exception {

		dom = XMLParser.parse(xmlRepresentation);
		currentElement = (Element) dom.getFirstChild();
		this.factory = factory;

		if (primitivDataTypes == null) {
			primitivDataTypes = new LinkedHashSet<String>();
			primitivDataTypes.add(Integer.class.getName());
			primitivDataTypes.add(String.class.getName());
			primitivDataTypes.add(Float.class.getName());
			primitivDataTypes.add(Double.class.getName());
			primitivDataTypes.add(Date.class.getName());
			primitivDataTypes.add(Boolean.class.getName());
			primitivDataTypes.add(Short.class.getName());
			primitivDataTypes.add(Character.class.getName());
			primitivDataTypes.add(Long.class.getName());

		}

	}

	private Object createObject(String className) throws ClassNotFoundException {

		return factory.createObject(className);
	}

	public Object deserialize() throws Exception {
		return serialize(ROOT_TAG_NAME, null).value;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Holder<T> serialize(String key, T value) throws Exception {

		String xmlKey = currentElement.getTagName();
		String xmlClass = currentElement.getAttribute(ATTRIBUTE_CLASS);


		if (!key.equals(xmlKey))
			throw new Exception("key missmatch " + key + " " + xmlKey);
		
		// check for null values
		if (currentElement.hasAttribute(XmlArchive.ATTRIBUTE_NULL_VALUE)
				&& Boolean.parseBoolean(currentElement
						.getAttribute(XmlArchive.ATTRIBUTE_NULL_VALUE))){
			currentElement = (Element) currentElement.getNextSibling();
			return new Holder<T>(null);
		}

		// Check Primitive types
		boolean isPrimitive = primitivDataTypes.contains(xmlClass);

		if (isPrimitive) {

			Holder<T> result = null;
			String content = currentElement.getFirstChild().getNodeValue();
			
			if (xmlClass.equals(Integer.class.getName()))
				result = new Holder<T>((T) new Integer(	Integer.parseInt(content)));
			else
			if (xmlClass.equals(Long.class.getName()))
				result = new Holder<T>((T) new Long(Long.parseLong(content)));
			else
			if (xmlClass.equals(String.class.getName()))
				result =  new Holder<T>((T) content);
			else
			if (xmlClass.equals(Float.class.getName()))
				result =  new Holder<T>((T) new Float(
						Float.parseFloat(content)));
			else
			if (xmlClass.equals(Double.class.getName()))
				result = new Holder<T>((T) new Double(
						Double.parseDouble(content)));
			else
			if (xmlClass.equals(Long.class.getName()))
				result = new Holder<T>((T) new Long(Long.parseLong(content)));
			else
			if (xmlClass.equals(Date.class.getName()))
				result = new Holder<T>((T) new Date(Long.parseLong(content)));
			else
			if (xmlClass.equals(Boolean.class.getName()))
				result = new Holder<T>((T) new Boolean(Boolean.parseBoolean(content)));
			else
			if (xmlClass.equals(Short.class.getName()))
				result = new Holder<T>((T) new Short(Short.parseShort(content)));
			else
			if (xmlClass.equals(Character.class.getName()))
				result = new Holder<T>((T) new Character(content.charAt(0)));
			
			
				

			
			currentElement = (Element) currentElement.getNextSibling();

			if (result == null)
				throw new Exception("Unexpected primitve type");
			
			return result;
			

		} else {

			Holder<T> result = new Holder<T>((T) createObject(xmlClass));

			if (result.value instanceof Serializable) {
				Element tmp = currentElement;
				currentElement = (Element) currentElement.getFirstChild();
				((Serializable) result.value).serialize(this);
				currentElement = (Element) tmp.getNextSibling();
			
			} else {
				// Collections
				
				boolean handled = false;
				
				if (result.value instanceof Set<?>) {
					Element tmp = currentElement;
					
					NodeList elments =  currentElement.getChildNodes();
					for (int i = 0; i<elments.getLength(); i++) {
						currentElement = (Element) elments.item(i);
						((Set)result.value).add(this.serialize(SUBTAG_SET_ELEMENT, value).value);
					}
					
					currentElement = (Element) tmp.getNextSibling();
					handled = true;
				}
				
				else
				if (result.value instanceof List<?>) {
					Element tmp = currentElement;
					
					NodeList elments =  currentElement.getChildNodes();
					for (int i = 0; i<elments.getLength(); i++) {
						currentElement = (Element) elments.item(i);
						((List)result.value).add(this.serialize(SUBTAG_SET_ELEMENT, value).value);
					}
					
					currentElement = (Element) tmp.getNextSibling();
					handled = true;
				}
				else
				if(result.value instanceof Map<?,?>){
					
					Element tmp = currentElement;
					
					NodeList elments =  currentElement.getChildNodes();
					for (int i = 0; i<elments.getLength(); i++) {
						
						currentElement = (Element) elments.item(i);
						if (!currentElement.getTagName().equals(SUBTAG_MAP_ELEMENT))
							throw new Exception("Expected <"+SUBTAG_MAP_ELEMENT+"> but got ");
						
						NodeList keyValueList = currentElement.getChildNodes();
						if (keyValueList.getLength()!=2)
							throw new Exception("Expect two child nodes: <"+SUBSUBTAG_MAP_ELEMENT_KEY+"> and <"+SUBSUBTAG_MAP_ELEMENT_VALUE+"> but got "+keyValueList.getLength()+" node(s)");
						
						currentElement = (Element) keyValueList.item(0);
						Holder<T> k = this.serialize(SUBSUBTAG_MAP_ELEMENT_KEY, value);
						
						currentElement = (Element) keyValueList.item(1);
						Holder<T> v = this.serialize(SUBSUBTAG_MAP_ELEMENT_VALUE, value);
						
						((Map)result.value).put(k.value, v.value);
					}
					
					currentElement = (Element) tmp.getNextSibling();
					handled = true;
					
				}
				else
				if (result.value instanceof Object[]){
					// Arrays
					
				}
				
				if (!handled)
					throw new Exception("A type "+xmlClass+" with the key name "+key+" could not be deserialized, because its not a supported primitive type, nor a supported Collection");
					
				
			}

			return result;
		} // End else

	}

}
