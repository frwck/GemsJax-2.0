package org.gemsjax.client.admin.model.metamodel;

/**
 * A Connection is used to represent a connection like association between two {@link MetaClass}es
 * @author Hannes Dorfmann
 *
 */
public class Connection {
	
	/**
	 * The source ("begin") of the {@link Connection}
	 */
	private MetaClass source;
	/**
	 * The target ("end") of the {@link Connection}
	 */
	private MetaClass target;
	
	
	public Connection(MetaClass source, MetaClass target)
	{
		this.source = source;
		this.target = target;
		
	}


	public MetaClass getSource() {
		return source;
	}


	public void setSource(MetaClass source) {
		this.source = source;
	}


	public MetaClass getTarget() {
		return target;
	}


	public void setTarget(MetaClass target) {
		this.target = target;
	}

}
