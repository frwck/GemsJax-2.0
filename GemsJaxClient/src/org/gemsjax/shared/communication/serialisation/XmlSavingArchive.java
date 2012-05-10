package org.gemsjax.shared.communication.serialisation;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Serializes an Object in xml form.
 * Use the method {@link #serialize(Object)} and <b>not</b> 
 * {@link #serialize(String, Object)}.
 * 
 * it suppots {@link Set}, {@link List}, {@link Map}
 * @author Hannes Dorfmann
 *
 */
public class XmlSavingArchive implements XmlArchive {

	private String xmlResult;
	
	public XmlSavingArchive() {
		
	}
	
	public <T> Holder<T> serialize(T object) throws Exception{
		return serialize(ROOT_TAG_NAME, object);
	}
	

	@Override
	public <T> Holder<T> serialize(String key, T value) throws Exception {
		
		if (xmlResult == null)
			xmlResult = "";
		
		
		if (value instanceof Serializable) {
			
			xmlResult+="<"+key+ " "+ATTRIBUTE_CLASS+"=\""+value.getClass().getName() +"\">";

			Serializable cls = (Serializable) value;
			cls.serialize(this);
			
			xmlResult+="</"+key+">";
			
		} else if (value instanceof Set<?>) {
			
			xmlResult+="<"+key+ " "+ATTRIBUTE_CLASS+"=\""+value.getClass().getName() +"\">"; 


			for (Object elem : (Set) value) {
				this.serialize(SUBTAG_SET_ELEMENT, elem);
			}

			xmlResult+="</"+key+">";
		}
		else if (value instanceof List<?>){
		
			xmlResult+="<"+key+ " "+ATTRIBUTE_CLASS+"=\""+value.getClass().getName() +"\">"; 


			for (Object elem : (List) value) {
				this.serialize(SUBTAG_LIST_ELEMENT, elem);
			}

			xmlResult+="</"+key+">";
			
			
		}
		else if (value instanceof Map<?,?>){
			
			xmlResult+="<"+key+ " "+ATTRIBUTE_CLASS+"=\""+value.getClass().getName() +"\">";
			
			Map<?,?> m = (Map<?,?>) value;
			
			for (Map.Entry<?, ?> entry : m.entrySet()){
				xmlResult+="<"+SUBTAG_MAP_ELEMENT+">";
				this.serialize(SUBSUBTAG_MAP_ELEMENT_KEY, entry.getKey());
				this.serialize(SUBSUBTAG_MAP_ELEMENT_VALUE, entry.getValue());
				xmlResult+="</"+SUBTAG_MAP_ELEMENT+">";
			}
			
			xmlResult+="</"+key+">";
			
		}
		else {
			
			if (value != null) {
				if (value instanceof Date)
					xmlResult+="<"+key+ " "+ATTRIBUTE_CLASS+"=\""+value.getClass().getName() +"\">" + ((Date)value).getTime()+"</"+key+">";
				else
				xmlResult+="<"+key+ " "+ATTRIBUTE_CLASS+"=\""+value.getClass().getName() +"\">" + value.toString()+"</"+key+">";
			}
			else // value == null
			{
				xmlResult+="<"+key+ " "+ATTRIBUTE_NULL_VALUE+"=\"true\"></"+key+">";
			}

		}

		
		
		return new Holder<T>(value);
	}


	public String toXml(){
		if (xmlResult== null || xmlResult.isEmpty())
			return null;
		else
		 return xmlResult;
	}
	
}