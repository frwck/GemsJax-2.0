package org.gemsjax.client.canvas.events.metamodel;

public class CreateMetaClassEvent {
	
	private String name;
	private double x;
	private double y;
	
	public CreateMetaClassEvent(String name, double x, double y) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	

}
