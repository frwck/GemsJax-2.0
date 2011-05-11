package org.gemsjax.client.admin.model.metamodel;


/**
 * This exeption will indicate, that the desired name for a {@link MetaClass} or {@link Attribute} is not available,
 * because there exists already another MetaClass or Attribute with the same name.
 * @author Hannes Dorfmann
 *
 */
public class NameNotAvailableException extends Exception{
	
	private String notAvailableName;
	
	public NameNotAvailableException(String name)
	{
		notAvailableName = name;
	}
	
	
	
	public String getNotAvailableName()
	{
		return this.notAvailableName;
	}

}
