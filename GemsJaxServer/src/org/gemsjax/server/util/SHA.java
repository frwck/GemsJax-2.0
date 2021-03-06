package org.gemsjax.server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * A simple util to generate SHA hashes
 * @author Hannes Dorfmann
 *
 */
public class SHA {
	
	/**
	 * Generates a SHA 2 - 256 hash
	 * @param toHash
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	public static String generate256(String toHash) throws NoSuchAlgorithmException
	{
		 MessageDigest md = MessageDigest.getInstance("SHA-256");
	     md.update(toHash.getBytes());
	 
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	 
	       
	        //convert the byte to hex format method 2
	        StringBuffer hexString = new StringBuffer();
	    	for (int i=0;i<byteData.length;i++) {
	    		String hex=Integer.toHexString(0xff & byteData[i]);
	   	     	if(hex.length()==1) hexString.append('0');
	   	     	hexString.append(hex);
	    	}
	    	
	    	
	    	return hexString.toString();

	}

}
