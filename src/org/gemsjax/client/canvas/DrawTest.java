package org.gemsjax.client.canvas;

import com.google.gwt.canvas.dom.client.Context2d;
/**
 * A little test class 
 * @author Hannes Dorfmann
 *
 */
public class DrawTest implements Drawable{

	
	private double x, y,z;
	
	public DrawTest() {
		 x = 20;
		 y = 100;
	}
	
	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public void setX(double x) {
		this.x = x;
		
	}

	@Override
	public void setY(double y) {
		this.y=y;
	}

	@Override
	public void setZ(double z) {
		this.z=z;
	}

	@Override
	public boolean isCoordinateOfThis(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void draw(Context2d context) {
		
		int width = 100;
		int height = 200;
		context.setFillStyle("red");
		context.fillRect(x, y, width, height);
		
	}

	@Override
	public void drawOnMouseOver(Context2d context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawOnSelected(Context2d context) {
		// TODO Auto-generated method stub
		
	}

}
