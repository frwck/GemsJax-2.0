package org.gemsjax.shared.communication.serialisation.test;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.shared.communication.serialisation.Archive;
import org.gemsjax.shared.communication.serialisation.Serializable;


public class Other implements Serializable {
	public int variable1;
	public int variable2;
	public Set<Float> set;
	public Other o;

	@Override
	public void serialize(Archive a) throws Exception {
		variable1 = a.serialize("variable1", variable1).value;
		variable2 = a.serialize("variable2", variable2).value;
		set = a.serialize("set", set).value;
		o = a.serialize("o", o).value;
	}
	
	@Override
	public String toString(){
		
		return ""+variable1+" "+variable2+" "+o; 
	}

}
