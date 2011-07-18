package org.gemsjax.client.canvas;

import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.model.metamodel.MetaClass;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
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
public class BufferedCanvas extends VLayout {

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




	public BufferedCanvas() throws CanvasSupportException
	{
		canvas = Canvas.createIfSupported();
		backBuffer=Canvas.createIfSupported();

		if (canvas == null || backBuffer == null)
			throw new CanvasSupportException("Can not create a HTML5 <canvas> element. <canvas> is not supported by this browser");

		/*
		isMouseDown = false;
		mouseDownX = -200;
		mouseDownY = -200;
		 */


		drawableStorage = new DrawableStorage();



		canvas.setWidth("100%");
		canvas.setHeight("100%");

		redrawColor = CssColor.make("#FFFFFF");

		canvasContext = canvas.getContext2d();
	    backBufferContext = backBuffer.getContext2d();


	    initHandlers();

		this.addMember(canvas);

		this.setWidth100();
		this.setHeight100();
		
		// TODO needed?
/*
		int width = 100;
		Window.alert("Width:" + this.getWidth());
		int height = this.getHeight();
		setCanvasSize(width, height);
*/


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

		/*
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



	/**
	 * Get the {@link DrawableStorage}
	 * @return
	 */
	protected DrawableStorage getDrawableStorage()
	{
		return drawableStorage;
	}


	/**
	 * Add a {@link Drawable} to this Canvas (to the {@link DrawableStorage}) to be painted on the canvas whenever {@link #redrawCanvas()} has been called
	 * @param drawable
	 * @throws DoubleLimitException
	 */
	public void addDrawable(Drawable drawable) throws DoubleLimitException
	{
		drawableStorage.add(drawable);
	}

	/**
	 * Remove a {@link Drawable}
	 * @param drawable
	 * @see #addDrawable(Drawable)
	 */
	public void removeDrawable(Drawable drawable)
	{
		drawableStorage.remove(drawable);
	}

	/**
	 * Since this is also a wrapper class for smart gwt integration, this method can be called to get the HTML 5 Canvas element.
	 * <b> However this method should be used only to add Handlers (ClickHandler, MouseMoveHandler, etc.) to the wrapped canvas and
	 * not to draw directly elements on the canvas.</b> To draw elements use {@link Drawable} and
	 * @return
	 */
	protected Canvas getWrappedCanvas()
	{
		return canvas;
	}




}
