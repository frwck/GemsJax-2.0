package org.gemsjax.client.canvas;

import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * This is a Buffered Canvas. So it use another Canvas as the back buffer.
 * This class wrapps the {@link Canvas} with an Smart GWT {@link VLayout}.<br />
 * Use the {@link #redrawCanvas()} method to redraw the html5 canvas <b>(Attention: the {@link #redraw()} method is SmartGWT stuff and not a member of the html5 canvas element)</b>
 * 
 * @author Hannes Dorfmann
 *
 */
public class BufferedCanvas extends VLayout implements ClickHandler, MouseMoveHandler, MouseDownHandler, MouseUpHandler, MouseOutHandler{

	/**
	 * The {@link Canvas} element which displayes the elements
	 */
	private Canvas canvas;

	/**
	 * The back buffer used to draw on the {@link #canvas}
	 */
	private Canvas backBuffer;

	/**
	 * This color is used to make a small smooth "fade" while drawing the content from the {@link #backBuffer} to the front {@link #canvas}
	 */
	private CssColor redrawColor;


	private DrawableStorage drawableStorage;

	private Context2d canvasContext;
	private Context2d backBufferContext;

	private int canvasWidth;
	private int canvasHeight;


	private boolean isMouseDown;


	/**
	 * 
	 */
	private double mouseDownX;
	private double mouseDownY;
	private double mouseDownInitialXDistance;
	private double mouseDownInitialYDistance;

	/**
	 * Is used to calculate the new width durring resizing (MouseMoveEvent) a {@link Drawable}
	 */
	private double beforeResizeWidth;

	/**
	 * Is used to calculate the new height durring resizing (MouseMoveEvent) a {@link Drawable}
	 */
	private double beforeResizeHeight;



	/**
	 * Is used to save, which object is now in use while a mouseDown action, for example, to move an Object
	 */
	private Drawable currentMouseDownDrawable;

	/**
	 * Is used, to say which drawable is now markt as selected
	 */
	private Drawable selectedDrawable;	

	/**
	 * Is used to say, which {@link ResizeArea} has startet the resizing progress.
	 * If currentResizeArea is null, than there is currently no resizing on going.
	 */
	private ResizeArea currentResizeArea;



	public BufferedCanvas() throws CanvasSupportException
	{
		canvas = Canvas.createIfSupported();
		backBuffer=Canvas.createIfSupported();

		if (canvas == null || backBuffer == null)
			throw new CanvasSupportException("Can not create a HTML5 <canvas> element. <canvas> is not supported by this browser");

		isMouseDown = false;
		mouseDownX = -200;
		mouseDownY = -200;


		// TODO remove debug
		SC.showConsole();




		drawableStorage = new DrawableStorage();

		
		//TODO remove sample data
		try {
			// Hardcore performance test
			/*for (int i =0; i<10000;i++)
			{
			*/
				MetaClassDrawable d = new MetaClassDrawable(100, 200);
				d.setBackgroundColor("blue");
				drawableStorage.add(d);
			/*
			
			new Timer(){

				@Override
				public void run() {
					redrawCanvas();
				}}.scheduleRepeating(2000);
			*/
		} catch (DoubleLimitException e) {
			Window.alert(e.getMessage());
		}

	   


		canvas.setWidth("100%");
		canvas.setHeight("100%");

		redrawColor = CssColor.make("#FFFFFF");

		canvasContext = canvas.getContext2d();
	    backBufferContext = backBuffer.getContext2d();


	    initHandlers();

		this.addMember(canvas);

		this.setWidth100();
		this.setHeight100();

		int width = this.getWidth();
		int height = this.getHeight();
		setCanvasSize(width, height);



	}


	/**
	 * <b>Important:</b> This must be called to set the correct size of the html canvas
	 * @param width
	 * @param height
	 */
	public void initCanvasSize()
	{
		int width = this.getWidth();
		int height = this.getHeight();
		setCanvasSize(width, height);


		canvas.setCoordinateSpaceWidth(width);
	    canvas.setCoordinateSpaceHeight(height);
	    backBuffer.setCoordinateSpaceWidth(width);
	    backBuffer.setCoordinateSpaceHeight(height);

	    redrawCanvas();
	}


	private void setCanvasSize(int width, int height)
	{
		this.canvasWidth = width;
		this.canvasHeight = height;

		canvas.setWidth(width+"px");
		canvas.setHeight(height+"px");

	}


	private void initHandlers() 
	{
		canvas.addMouseMoveHandler(this);
		canvas.addClickHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
		canvas.addMouseOutHandler(this);

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
		// TODO To increase the performance just redraw the part on the canvas, that has been changed
		// Clear the backBuffer
		 backBufferContext.setFillStyle(redrawColor);
		 backBufferContext.fillRect(0, 0, canvasWidth, canvasHeight);

		 drawObjects();

		 // draw from Backbuffer to the Front Canvas
		 canvasContext.drawImage(backBufferContext.getCanvas(), 0, 0);

	}


	private void drawObjects()
	{
		for (Drawable d: drawableStorage.getAllElements())
			if (d.isSelected())
				d.drawOnSelected(backBufferContext);
			else
				if (d.isMouseOver())
					d.drawOnMouseOver(backBufferContext);
				else
					d.draw(backBufferContext);
	}


	@Override
	public void onClick(ClickEvent event) {

		// TODO : Canvas: Wrong Focus (ResizeArea) When you move a Drawable in the canvas and will overlap it with another Drawable with the higher Z index, the Drawable  with the highest Z index will get the Focus instead of the drawable, which has been moved


		Drawable previous = selectedDrawable;

		selectedDrawable = drawableStorage.getDrawableAt(event.getX(), event.getY());

		if (previous != null)
			previous.setSelected(false);


		if (selectedDrawable != null)
		{
			selectedDrawable.setSelected(true);

		}else
			if (previous != null) 
				previous.setSelected(false);


		redrawCanvas();
	}


	@Override
	public void onMouseDown(MouseDownEvent event) {

		isMouseDown =true;
		currentMouseDownDrawable = drawableStorage.getDrawableAt(event.getX(), event.getY());

		if (currentMouseDownDrawable==null) return;


		// check for Resizing by checking if there is a ResizeArea at the current mouse position
		this.currentResizeArea = currentMouseDownDrawable.isResizerAreaAt(event.getX(), event.getY());
		if (currentResizeArea != null)
		{
			beforeResizeWidth = currentMouseDownDrawable.getWidth();
			beforeResizeHeight = currentMouseDownDrawable.getHeight();
		}


		mouseDownX = event.getX();
		mouseDownY = event.getY();
		mouseDownInitialXDistance = event.getX() - currentMouseDownDrawable.getX();
		mouseDownInitialYDistance = event.getY() - currentMouseDownDrawable.getY();

	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {

		if (currentMouseDownDrawable== null) return;

		// Resize, if mouse is on a ResizeArea and Resizeable
		if (currentMouseDownDrawable.isResizeable() && isMouseDown && currentResizeArea != null)
		{
			double width = beforeResizeWidth +  event.getX() - mouseDownX;
			double height = beforeResizeHeight + event.getY() - mouseDownY;

			SC.logWarn(" w "+width +" "+(event.getX() - mouseDownX)+ " h "+height);


			ResizeEvent e = new ResizeEvent(width, height, event.getX(), event.getY(), currentResizeArea);
			for (ResizeHandler r : currentMouseDownDrawable.getResizeHandlers())
				r.onResize(e);

			redrawCanvas();

			return; // Break at this point 
		}



		// Move a Drawable if it is moveable	
		if (currentMouseDownDrawable.isMoveable() && isMouseDown)
		{

			MoveEvent e = new MoveEvent(mouseDownX, mouseDownY, event.getX(), event.getY(), mouseDownInitialXDistance, mouseDownInitialYDistance, event.getScreenX(), event.getScreenY(), isMouseDown);

			for (MoveHandler h : currentMouseDownDrawable.getMoveHandlers())
			{
				h.onMove(e);
			}

			redrawCanvas();
			return; // Break at this point
		}





		/*
		if (isMouseDown && currentMouseDownDrawable != null)
		{
			
			
			
			// Move Drawables	
			if (currentMouseDownDrawable==null || !currentMouseDownDrawable.isMoveable()) return;
			
			
			// TODO	Prevent that a Drawable can be moved outside the Canvas
			
			/*
			double distanceToLeft, distanceToRight, distanceToTop, distanceToBottom;
			
			distanceToLeft =event.getX() - currentMouseDownDrawable.getX();
			distanceToRight = currentMouseDownDrawable.getX() - event.getX();
			distanceToTop = event.getY() - currentMouseDownDrawable.getY();
			distanceToBottom = currentMouseDownDrawable.getY() - event.getY();
			
			
			
			//if (event.getX()>=distanceToLeft )
			for (MoveHandler h : currentMouseDownDrawable.getMoveHandlers())
				h.onMove(mouseDownInitialDrawableX+(event.getX()-mouseDownX), mouseDownInitialDrawableY+(event.getY()-mouseDownY));
			
			redrawCanvas();
		}
		*/
	}


	// REIHENFOLGE: erster kommt MouseUp, dann MouseClick

	@Override
	public void onMouseUp(MouseUpEvent event) {

			isMouseDown = false;
			currentMouseDownDrawable = null;
			currentResizeArea = null;


	}


	@Override
	public void onMouseOut(MouseOutEvent event) {
		// If you are out of the canvas while Mouse is still down
		onMouseUp(null);

	}




}
