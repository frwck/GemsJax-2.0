package org.gemsjax.server.persistence.dao.exception;

/**
 * This {@link Exception} is the same as {@link IllegalArgumentException}.
 * {@link ArgumentException} is used to say, that the a passed argument is not the expected.
 * But this is not a {@link RuntimeException} like {@link IllegalArgumentException},
 * so this exception must be caught.
 * @author Hannes Dorfmann
 *
 */
public class ArgumentException extends Exception {
	
	public ArgumentException(String msg)
	{
		super(msg);
	}

}
