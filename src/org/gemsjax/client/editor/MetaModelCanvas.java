package org.gemsjax.client.editor;

import javax.swing.text.html.CSS;

import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.model.metamodel.MetaClass;
import org.gemsjax.client.admin.model.metamodel.exception.AttributeNameException;
import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.canvas.ResizeArea;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;

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

/**
 * This is a Canvas, used for editing Meta-Models
 * @author Hannes Dorfmann
 *
 */
public class MetaModelCanvas extends BufferedCanvas implements ClickHandler, MouseMoveHandler, MouseDownHandler, MouseUpHandler, MouseOutHandler{

	/**
	 * This enum will be used by the {@link MetaModelCanvas} to indicate, how the user interactions, like click etc. should be interpreted.
	 * @author Hannes Dorfmann
	 *
	 */
	public enum EditingMode
	{
		/**
		 * Indicates, that you are using this Canvas in a normal way. U can select, resize, move, remove elements on this {@link MetaModelCanvas}
		 */
		NORMAL,
		
		/**
		 * Inicates, that you are now clicking on the Canvas to create there a new meta-class, so the click event should be interpreted to
		 * create on the click event coordinates (x/y) a new meta-class.
		 */
		CREATE_CLASS,
		
		/**
		 * Indicates, that you now should select two meta-classes to create a inheritance between them.
		 * The first selected will be super class.
		 */
		CREATE_INHERITANCE,
		
		/**
		 * Indicates, that the user should now select two meta-model classes to create a relation between them.
		 * The first selected is the "start" point of the relation, the last selected will be the "end" point of this relation.
		 */
		CREATE_RELATION,
		
		/**
		 * That means, that the user is only allowed to display the meta-model, but not to edit it.
		 */
		READ_ONLY
	}
	
	
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


	
	private EditingMode editingMode;
	
	
	public MetaModelCanvas() throws CanvasSupportException {
		super();
		
		isMouseDown = false;
		mouseDownX = -200;
		mouseDownY = -200;
		
		getWrappedCanvas().addMouseMoveHandler(this);
		getWrappedCanvas().addClickHandler(this);
		getWrappedCanvas().addMouseDownHandler(this);
		getWrappedCanvas().addMouseUpHandler(this);
		getWrappedCanvas().addMouseOutHandler(this);
		
		

		//TODO remove sample data
		try {
			// Hardcore performance test
			/*for (int i =0; i<10000;i++)
			{
			*/
				MetaClass d = new MetaClass(100, 200);
				d.setName("Test MetaClass");
				
				d.addAttribute("Attribute1", "Type1");
				d.addAttribute("Attribute2", "Type1");
				d.addAttribute("Attribute3", "Type1");
				
				MetaClassDrawable dr = new MetaClassDrawable(d);
				dr.autoSize();
				
				this.addDrawable(dr);
			/*
			
			new Timer(){

				@Override
				public void run() {
					redrawCanvas();
				}}.scheduleRepeating(2000);
			*/
		} catch (DoubleLimitException e) {
			Window.alert(e.getMessage());
		} catch (AttributeNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * Set the {@link EditingMode} for this canvas
	 * @param mode
	 */
	public void setEditingMode(EditingMode mode)
	{
		editingMode = mode;
	}
	
	
	public EditingMode getEditingMode()
	{
		return editingMode;
	}
	
	
	
	@Override
	public void onClick(ClickEvent event) {


		switch (editingMode)
		{
		
		
			case NORMAL:
				
				// TODO : Canvas: Wrong Focus (ResizeArea) When you move a Drawable in the canvas and will overlap it with another Drawable with the higher Z index, the Drawable  with the highest Z index will get the Focus instead of the drawable, which has been moved
				Drawable previous = selectedDrawable;
		
				selectedDrawable = getDrawableStorage().getDrawableAt(event.getX(), event.getY());
				
				if (previous != null)
					previous.fireFocusEvent(new FocusEvent(previous, FocusEventType.LOST_FOCUS));
		
		
				if (selectedDrawable != null)
				{
					org.gemsjax.client.canvas.events.ClickEvent.MouseButton button = org.gemsjax.client.canvas.events.ClickEvent.MouseButton.LEFT;
					
						if (event.getNativeButton()== com.google.gwt.dom.client.NativeEvent.BUTTON_RIGHT)
							button = org.gemsjax.client.canvas.events.ClickEvent.MouseButton.RIGHT;
						else
							if (event.getNativeButton()== com.google.gwt.dom.client.NativeEvent.BUTTON_MIDDLE)
								button = org.gemsjax.client.canvas.events.ClickEvent.MouseButton.MIDDLE;
					
					selectedDrawable.fireClickEvent(new org.gemsjax.client.canvas.events.ClickEvent(selectedDrawable, event.getX(), event.getY(), event.getScreenX(), event.getScreenY(), button));
					
					// Focus event
					selectedDrawable.fireFocusEvent(new FocusEvent(selectedDrawable, FocusEventType.GOT_FOCUS));
					
					
				}else
					if (previous != null) 
						previous.fireFocusEvent(new FocusEvent(previous, FocusEventType.LOST_FOCUS));
		
				redrawCanvas();
				
				break; // End case Normal
			
			
			
		}// End switch
		
	}


	@Override
	public void onMouseDown(MouseDownEvent event) {

		switch (editingMode)
		{
		
			case NORMAL:
				isMouseDown =true;
				currentMouseDownDrawable = getDrawableStorage().getDrawableAt(event.getX(), event.getY());
		
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
			
			break; // End NORMAL
		}

	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {

		switch (editingMode)
		{
		
			case NORMAL:
				if (currentMouseDownDrawable == null) return;
		
				// Resize, if mouse is on a ResizeArea and Resizeable
				if (currentMouseDownDrawable.isResizeable() && isMouseDown && currentResizeArea != null)
				{
					double width = beforeResizeWidth +  event.getX() - mouseDownX;
					double height = beforeResizeHeight + event.getY() - mouseDownY;
		
					SC.logWarn(" w "+width +" "+(event.getX() - mouseDownX)+ " h "+height);
		
		
					ResizeEvent e = new ResizeEvent(currentMouseDownDrawable, width, height, event.getX(), event.getY(), currentResizeArea);
					currentMouseDownDrawable.fireResizeEvent(e);
		
					redrawCanvas();
		
					return; // Break at this point 
				}
		
		
		
				// Move a Drawable if it is moveable	
				if (currentMouseDownDrawable.isMoveable() && isMouseDown)
				{
		
					MoveEvent e = new MoveEvent(currentMouseDownDrawable, mouseDownX, mouseDownY, event.getX(), event.getY(), mouseDownInitialXDistance, mouseDownInitialYDistance, event.getScreenX(), event.getScreenY(), isMouseDown);
		
					currentMouseDownDrawable.fireMoveEvent(e);
					redrawCanvas();
					return; // Break at this point
				}
				
			break; // END Normal
				
		} // END switch




		/*
		if (isMouseDown && currentMouseDownDrawable != null)
		{
			
			
			
			// Move Drawables	
			if (currentMouseDownDrawable==null || !currentMouseDownDrawable.isMoveable()) return;
			
			
			// TODO	Prevent that a Drawable can be moved outside the Canvasdouble x, double y
			
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
		
		switch (editingMode)
		{
			case NORMAL:
				isMouseDown = false;
				currentMouseDownDrawable = null;
				currentResizeArea = null;
			break; // END editing mode
		
		} // End NORMAL
		
		
	}


	@Override
	public void onMouseOut(MouseOutEvent event) {
		// If you are out of the canvas while Mouse is still down
		onMouseUp(null);

	}
	
	
	

}
