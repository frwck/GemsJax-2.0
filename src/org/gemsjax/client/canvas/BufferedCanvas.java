package org.gemsjax.client.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * This is a Buffered Canvas. So it use another Canvas as the back buffer.
 * This class wrapps the {@link Canvas} with an Smart GWT {@link VLayout}.<br />
 * Use the {@link #redrawCanvas()} method to redraw the html5 canvas <b>(Attention: the {@link #redraw()} method is SmartGWT stuff and not a member of the html5 canvas element)</b>
 * 
 * @author Hannes Dorfmann
 *
 */
public class BufferedCanvas extends VLayout implements ClickHandler, MouseMoveHandler, MouseDownHandler, MouseUpHandler{
	
	/**
	 * The {@link Canvas} element which displayes the elements
	 */
	private Canvas canvas;
	
	/**
	 * The back buffer used to draw on the {@link #canvas}
	 */
	private Canvas backBuffer;
	
	
	private Context2d canvasContext;
	private Context2d backBufferContext;
	

	
	public BufferedCanvas() throws CanvasSupportException
	{
		canvas = Canvas.createIfSupported();
		backBuffer=Canvas.createIfSupported();
		
		if (canvas == null || backBuffer == null)
			throw new CanvasSupportException("Can not create a HTML5 <canvas> element. <canvas> is not supported by this browser");
		
		
		canvas.setWidth("100%");
		canvas.setHeight("100%");
		
		// TODO needed?
		/*
		canvas.setCoordinateSpaceWidth(width);
	    canvas.setCoordinateSpaceHeight(height);
	    backBuffer.setCoordinateSpaceWidth(width);
	    backBuffer.setCoordinateSpaceHeight(height);
	    */
		
		canvasContext = canvas.getContext2d();
	    backBufferContext = backBuffer.getContext2d();
		
		this.addMember(canvas);
		initHandlers();
		
	}
	
	
	private void initHandlers() 
	{
		canvas.addMouseMoveHandler(this);
		canvas.addClickHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
		

		    
		    // TODO add Support for touchscreen devices
		    /*
		    canvas.addMouseOutHandler(new MouseOutHandler() {
		      public void onMouseOut(MouseOutEvent event) {
		        mouseX = -200;
		        mouseY = -200;
		      }
		    });

		    canvas.addTouchMoveHandler(new TouchMoveHandler() {
		      public void onTouchMove(TouchMoveEvent event) {
		        event.preventDefault();
		        if (event.getTouches().length() > 0) {
		          Touch touch = event.getTouches().get(0);
		          mouseX = touch.getRelativeX(canvas.getElement());
		          mouseY = touch.getRelativeY(canvas.getElement());
		        }
		        event.preventDefault();
		      }
		    });

		    canvas.addTouchEndHandler(new TouchEndHandler() {
		      public void onTouchEnd(TouchEndEvent event) {
		        event.preventDefault();
		        mouseX = -200;
		        mouseY = -200;
		      }
		    });

		    canvas.addGestureStartHandler(new GestureStartHandler() {
		      public void onGestureStart(GestureStartEvent event) {
		        event.preventDefault();
		      }
		    });
		*/
	 }
	 
	 
	
	 
	/**
	 * This is the method to make a redraw for the html5 canvas element ({@link #canvas}).<br />
	 * <b>The {@link #redraw()} method is Smart GWT stuff, so don't get confused.</b>
	 */
	public void redrawCanvas()
	{
		
	}


	@Override
	public void onClick(ClickEvent event) {
		// TODO Canvas: click
		Window.alert("Click: ");
	}


	@Override
	public void onMouseDown(MouseDownEvent event) {
		// TODO Canvas: mouse is pressed (down)
		
	}


	@Override
	public void onMouseMove(MouseMoveEvent event) {
		// TODO Canvas: mouse is moved
		
	}


	@Override
	public void onMouseUp(MouseUpEvent event) {
		// TODO Canvas: mouse is released up

		Window.alert("MouseUp: ");
	}
	

}
