package org.gemsjax.client.admin.exception;

/**
 * This Exception will be thrown, when with the next incremetation an overfolw occures
 * @author Hannes Dorfmann
 *
 */
public class DoubleLimitException extends Exception{

	//TODO check MIN & MAX value for double in JavaScript
	public static double MAX = 10000000;
	public static double MIN = -10000000;
	
	public DoubleLimitException(String msg)
	{
		super(msg);
	}
}
