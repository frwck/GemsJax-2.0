package org.gemsjax.client.util;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class UUIDTest extends TestCase {
	
	
	public void testID()
	{
		
		Map<String, Boolean> idMap = new HashMap<String, Boolean>();
		String id; 
		
		for (int i =0; i<1000000; i++)
		{
			id = UUID.uuid();
			//System.out.println(id);
			assertFalse(idMap.containsKey(id));
			
			idMap.put(id, true);
		}
		
		
	}

}
