package org.gemsjax.client.canvas;

import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.events.ResizeEvent.ResizeEventType;
import org.gemsjax.shared.metamodel.MetaModelElement;


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
	 * The x coordinate where the mouse down has began
	 */
	private double mouseDownX;
	
	/**
	 * The y coordinate where the mouse down has began
	 */
	private double mouseDownY;
	
	/**
	 * The initial distance between the Movables x coordinate and the mouse cursor on mouse down event. This  is used to calculate the movement for Moveables
	 */
	private double mouseDownInitialXDistance;
	
	/**
	 * The initial distance between the Movables y coordinate and the mouse cursor on mouse down event. This  is used to calculate the movement for Moveables
	 */
	private double mouseDownInitialYDistance;
	
	/**
	 * Fire ResizeEvents only, if the corresponding Drawable is selected ({@link Drawable#isSelected()})
	 */
	private boolean resizeableOnlyOnSelected = true;


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
	 * Is used, to say which drawable is has the focus now
	 */
	private Drawable currentClicked;	

	/**
	 * Is used to say, which {@link ResizeArea} has started the resizing progress.
	 * If currentResizeArea is null, than there is currently no resizing on going.
	 */
	private ResizeArea currentResizeArea;

	/**
	 * Is set to true or false, whenever the user is doing any resizing on a {@link Resizeable} {@link Drawable}.
	 * Is used to detremine, which event ( {@link ResizeEvent}, {@link MoveEvent}, etc.) should be fired {@link #onMouseUp(MouseUpEvent)}
	 */
	private boolean resizing;
	
	/**
	 * Is set to true or false, whenever the user is doing any movement of a {@link Moveable} {@link Drawable}.
	 * Is used to detremine, which event ( {@link ResizeEvent}, {@link MoveEvent}, etc.) should be fired {@link #onMouseUp(MouseUpEvent)}
	 */
	private boolean moving;
	
	private EditingMode editingMode;
	
	
	public MetaModelCanvas() throws CanvasSupportException {
		super();
		
		resizing = false;
		moving = false;
		
		isMouseDown = false;
		mouseDownX = -200;
		mouseDownY = -200;
		
		getWrappedCanvas().addMouseMoveHandler(this);
		getWrappedCanvas().addClickHandler(this);
		getWrappedCanvas().addMouseDownHandler(this);
		getWrappedCanvas().addMouseUpHandler(this);
		getWrappedCanvas().addMouseOutHandler(this);

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
				
				Drawable previous = currentClicked;
				
				currentClicked = getDrawableStorage().getDrawableAt(event.getX(), event.getY());
				
				// ClickEvent
				if (currentClicked instanceof Clickable)
				{
					org.gemsjax.client.canvas.events.ClickEvent.MouseButton button = org.gemsjax.client.canvas.events.ClickEvent.MouseButton.LEFT;
					
					if (event.getNativeButton()== com.google.gwt.dom.client.NativeEvent.BUTTON_RIGHT)
						button = org.gemsjax.client.canvas.events.ClickEvent.MouseButton.RIGHT;
					else
						if (event.getNativeButton()== com.google.gwt.dom.client.NativeEvent.BUTTON_MIDDLE)
							button = org.gemsjax.client.canvas.events.ClickEvent.MouseButton.MIDDLE;
				
					((Clickable)currentClicked).fireClickEvent(new org.gemsjax.client.canvas.events.ClickEvent((Clickable)currentClicked, event.getX(), event.getY(), event.getScreenX(), event.getScreenY(), button));
				}
				
				// TODO : Canvas: Wrong Focus (ResizeArea) When you move a Drawable in the canvas and will overlap it with another Drawable with the higher Z index, the Drawable  with the highest Z index will get the Focus instead of the drawable, which has been moved
				
				
				
				
				// FocusEvent
				
				if (previous != null && previous instanceof Focusable)
					((Focusable)previous).fireFocusEvent(new FocusEvent((Focusable)previous, FocusEventType.LOST_FOCUS));
		
		
				if (currentClicked != null && currentClicked instanceof Focusable)
				{
					((Focusable)currentClicked).fireFocusEvent(new FocusEvent((Focusable)currentClicked, FocusEventType.GOT_FOCUS));
				}
				
				else	// clicking somewhere in the white (not on a drawable focusable)
					if (previous != null && previous instanceof Focusable) 
						((Focusable)previous).fireFocusEvent(new FocusEvent((Focusable)previous, FocusEventType.LOST_FOCUS));
				
				
				
				break; // End case Normal
			
			
			
		}// End switch
		
	}


	@Override
	public void onMouseDown(MouseDownEvent event) {

		switch (editingMode)
		{
		
			case NORMAL:
				
				isMouseDown =true;
				
				mouseDownX = event.getX();
				mouseDownY = event.getY();
				
				
				currentMouseDownDrawable = getDrawableStorage().getDrawableAt(event.getX(), event.getY());
				
				// Resizeable
				if (currentMouseDownDrawable!=null && currentMouseDownDrawable instanceof Resizeable)
				{
					// check for Resizing by checking if there is a ResizeArea at the current mouse position
					this.currentResizeArea = ((Resizeable)currentMouseDownDrawable).isResizerAreaAt(event.getX(), event.getY());
				
					if (currentResizeArea != null )
					{
						beforeResizeWidth = ((Resizeable)currentMouseDownDrawable).getWidth();
						beforeResizeHeight = ((Resizeable)currentMouseDownDrawable).getHeight();
					}		
			
				}
				
				
				//  Moveable				
				if (currentMouseDownDrawable!=null && currentMouseDownDrawable instanceof Moveable)
				{
					mouseDownInitialXDistance = event.getX() - ((Moveable)currentMouseDownDrawable).getX();
					mouseDownInitialYDistance = event.getY() - ((Moveable)currentMouseDownDrawable).getY();
				}
				 
			break; // End NORMAL
		}

	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {

		switch (editingMode)
		{
		
			case NORMAL:
				
				
				
				// Resize, if mouse is on a ResizeArea and Resizeable
				if (currentMouseDownDrawable != null && currentMouseDownDrawable instanceof Resizeable && isMouseDown && currentResizeArea != null)
				{
					if (resizeableOnlyOnSelected && currentMouseDownDrawable.isSelected())
					{
						double width = beforeResizeWidth +  event.getX() - mouseDownX;
						double height = beforeResizeHeight + event.getY() - mouseDownY;
						
						resizing = true;
						
						ResizeEvent e = new ResizeEvent((Resizeable) currentMouseDownDrawable, ResizeEventType.TEMP_RESIZE, width, height, event.getX(), event.getY(), currentResizeArea);
						((Resizeable)currentMouseDownDrawable).fireResizeEvent(e);
			
						return; // Break at this point 
					}
				}
		
				
		
				// MoveEvent
				if (currentMouseDownDrawable instanceof Moveable && isMouseDown)
				{
					moving = true;
					MoveEvent e = new MoveEvent((Moveable)currentMouseDownDrawable, MoveEventType.TEMP_MOVE, mouseDownX, mouseDownY, event.getX(), event.getY(), mouseDownInitialXDistance, mouseDownInitialYDistance, event.getScreenX(), event.getScreenY(), isMouseDown);
		
					((Moveable)currentMouseDownDrawable).fireMoveEvent(e);
					
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
			
			
		}
		*/
	}


	// REIHENFOLGE: erster kommt MouseUp, dann MouseClick

	@Override
	public void onMouseUp(MouseUpEvent event) {

		
		switch (editingMode)
		{
			case NORMAL:
				
				if (moving && currentMouseDownDrawable instanceof Moveable && isMouseDown)
				{
		
					MoveEvent e = new MoveEvent((Moveable)currentMouseDownDrawable, MoveEventType.MOVE_FINISHED, mouseDownX, mouseDownY, event.getX(), event.getY(), mouseDownInitialXDistance, mouseDownInitialYDistance, event.getScreenX(), event.getScreenY(), isMouseDown);
		
					((Moveable)currentMouseDownDrawable).fireMoveEvent(e);
					
				}
				
				
				if (resizing && currentMouseDownDrawable != null && currentMouseDownDrawable instanceof Resizeable && isMouseDown && currentResizeArea != null)
				{
					if (resizeableOnlyOnSelected && currentMouseDownDrawable.isSelected())
					{
						double width = beforeResizeWidth +  event.getX() - mouseDownX;
						double height = beforeResizeHeight + event.getY() - mouseDownY;
						
						ResizeEvent e = new ResizeEvent((Resizeable) currentMouseDownDrawable, ResizeEventType.RESIZE_FINISHED, width, height, event.getX(), event.getY(), currentResizeArea);
						((Resizeable)currentMouseDownDrawable).fireResizeEvent(e);
					}
				}
				
			break; // END editing mode
		
		} // End NORMAL


		isMouseDown = false;
		moving = false;
		resizing = false;
		currentMouseDownDrawable = null;
		currentResizeArea = null;
		
	}


	@Override
	public void onMouseOut(MouseOutEvent event) {
		// If you are out of the canvas while Mouse is still down
		onMouseUp(null);

	}
	
	
	

}
