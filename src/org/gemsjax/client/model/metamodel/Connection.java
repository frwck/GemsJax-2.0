package org.gemsjax.client.model.metamodel;

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
	
	
	private double sourceRelativeX;
	private double sourceRelativeY;
	private double targetRelativeX;
	private double targetRelativeY;
	
	
	
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


	public double getSourceRelativeX() {
		return sourceRelativeX;
	}


	public void setSourceRelativeX(double sourceRelativeX) {
		this.sourceRelativeX = sourceRelativeX;
	}


	public double getSourceRelativeY() {
		return sourceRelativeY;
	}


	public void setSourceRelativeY(double sourceRelativeY) {
		this.sourceRelativeY = sourceRelativeY;
	}


	public double getTargetRelativeX() {
		return targetRelativeX;
	}


	public void setTargetRelativeX(double targetRelativeX) {
		this.targetRelativeX = targetRelativeX;
	}


	public double getTargetRelativeY() {
		return targetRelativeY;
	}


	public void setTargetRelativeY(double targetRelativeY) {
		this.targetRelativeY = targetRelativeY;
	}

}
