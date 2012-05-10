package org.gemsjax.shared.communication.serialisation.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;


public class Person implements Serializable {
	
	public enum Grade {
		A,
		B,
		C,
		D,
		E
		;
	}
	
	
	public String firstName;
	public String lastName;
	public Date date;
	public Other other;
	public List<String> list;
	public Map<Integer, String> map;
	public Grade g;

	@Override
	public void serialize(Archive a) throws Exception {
		firstName = a.serialize("firstName", firstName).value;
		lastName = a.serialize("lastName", lastName).value;
		date = a.serialize("date", date).value;
		other = a.serialize("other", other).value;
		list = a.serialize("list", list).value;
		map = a.serialize("map", map).value;
		String gs = a.serialize("g", g!=null?g.toString():null).value;
		g = gs==null?null:Grade.valueOf(gs);
	}
	
	
	@Override
	public String toString(){
		return firstName+" "+lastName+" "+date+" "+other+" "+g;
	}

}