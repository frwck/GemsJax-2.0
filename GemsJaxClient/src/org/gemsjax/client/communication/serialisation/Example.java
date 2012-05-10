package org.gemsjax.client.communication.serialisation;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import org.gemsjax.shared.communication.serialisation.ObjectFactory;
import org.gemsjax.shared.communication.serialisation.XmlSavingArchive;
import org.gemsjax.shared.communication.serialisation.instantiators.LinkedHashMapInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.LinkedHashSetInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.LinkedListInstantiator;
import org.gemsjax.shared.communication.serialisation.test.Other;
import org.gemsjax.shared.communication.serialisation.test.OtherInstantiator;
import org.gemsjax.shared.communication.serialisation.test.Person;
import org.gemsjax.shared.communication.serialisation.test.PersonInstatiator;



public class Example {
	
	public static void main(String[] args) {
		//System.out.println(getExampleSerialized());
		
		Integer i = new Integer(29);
		i = 1;
		System.out.print(i);
	}
	
	
	public static String getExampleSerialized(){
		Person person = new Person();
		person.date = new Date();

		person.firstName = "Vorname";
		person.lastName = "Nachname";
		person.list = new LinkedList<String>();
		person.list.add("first");
		person.list.add("second");
		person.g = null;
		
		person.map = new LinkedHashMap<Integer, String>();
		person.map.put(10, "ten");
		person.map.put(20, "twenty");
		person.map.put(30, "thirty");
		
		
		person.other = new Other();
		person.other.o = null;
		person.other.variable1 = 10;
		person.other.variable2 = 20;
		person.other.set = new LinkedHashSet<Float>();
		person.other.set.add(1.0f);
		person.other.set.add(2.0f);
		person.other.set.add(3.0f);

		
		
		
		try {
			XmlSavingArchive saveArchive = new XmlSavingArchive();
			saveArchive.serialize(person);
			
			return saveArchive.toXml();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	
	
	public static Person getExampleDeserialized(String xmlRepresentation){
		
		ObjectFactory factory = new ObjectFactory();
		factory.register(Person.class.getName(), new PersonInstatiator());
		factory.register(Other.class.getName(), new OtherInstantiator());
		factory.register(LinkedHashSet.class.getName(), new LinkedHashSetInstantiator());
		factory.register(LinkedList.class.getName(), new LinkedListInstantiator());
		factory.register(LinkedHashMap.class.getName(), new LinkedHashMapInstantiator());
		
		try {
			GwtXmlLoadingArchive a = new GwtXmlLoadingArchive(xmlRepresentation, factory);
			return (Person) a.deserialize();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
		
	}
	
	
	

}
