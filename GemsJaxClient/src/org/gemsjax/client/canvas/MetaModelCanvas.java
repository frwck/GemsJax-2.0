package org.gemsjax.client.canvas;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.PlaceEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.events.PlaceEvent.PlaceEventType;
import org.gemsjax.client.canvas.events.ResizeEvent.ResizeEventType;
import org.gemsjax.client.canvas.events.metamodel.CreateMetaClassEvent;
import org.gemsjax.client.canvas.handler.metamodel.CreateMetaClassHandler;
import org.gemsjax.client.util.Console;
import org.gemsjax.shared.FieldVerifier;
import org.gemsjax.shared.Point;
import org.gemsjax.shared.metamodel.MetaConnection;


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
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;



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
		
		CREATE_CONTAINMENT,
		
		/**
		 * That means, that the user is only allowed to display the meta-model, but not to edit it.
		 */
		READ_ONLY,
		
		REPLAY_MODE
		

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
	 * Fire MoveEvents only, if the corresponding Drawable is selected ({@link Drawable#isSelected()})
	 */
	private boolean moveableOnlyOnSelected = false;


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
	private Object currentClicked;	

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
	
	
	private boolean placing;
	
	/**
	 * Is used to dertermine, which {@link Placeable} is already in use by the mouse cursor.
	 * Normally this is a AnchorPoint
	 */
	private Placeable currentPlaceable;
	
	private EditingMode editingMode;
	
	/**
	 * A little hack to call {@link #onMouseUp(MouseUpEvent)} from {@link #onMouseOut(MouseOutEvent)} since it's not possible to generate the {@link MouseUpEvent}
	 * to with the mouse out properties
	 */
	private MouseOutEvent lastMouseOutEvent;
	
	private String createMetaClassName;
	
	private Set<CreateMetaClassHandler> createClassHandlers;
	private Set<CreateMetaRelationHandler> createRelationHandlers;
	private Set<CreateMetaInheritanceHandler> createInheritanceHandlers;
	
	private MetaClassDrawable createRelationSource;
	private MetaClassDrawable createRelationTarget;
	
	private MetaClassDrawable createInheritanceToExtendClass;
	private MetaClassDrawable createInheritanceSuperClass;
	
	public MetaModelCanvas() throws CanvasSupportException {
		super();
		
		createClassHandlers = new LinkedHashSet<CreateMetaClassHandler>();
		createRelationHandlers = new LinkedHashSet<CreateMetaRelationHandler>();
		createInheritanceHandlers = new LinkedHashSet<CreateMetaInheritanceHandler>();
		
		resizing = false;
		moving = false;
		placing = false;
		
		isMouseDown = false;
		mouseDownX = -200;
		mouseDownY = -200;
		
		getWrappedCanvas().addMouseMoveHandler(this);
		getWrappedCanvas().addClickHandler(this);
		getWrappedCanvas().addMouseDownHandler(this);
		getWrappedCanvas().addMouseUpHandler(this);
		getWrappedCanvas().addMouseOutHandler(this);

	}
	
	
	public void addCreateMetaClassHandler(CreateMetaClassHandler h){
		createClassHandlers.add(h);
	}
	
	public void removeCreateMetaClassHandler(CreateMetaClassHandler h){
		createClassHandlers.remove(h);
	}
	
	public void addCreateMetaInheritanceHandler(CreateMetaInheritanceHandler h){
		createInheritanceHandlers.add(h);
	}
	
	public void removeCreateMetaInheritanceHandler(CreateMetaInheritanceHandler h){
		createInheritanceHandlers.remove(h);
	}
	
	
	/**
	 * Set the {@link EditingMode} for this canvas
	 * @param mode
	 */
	public void setEditingMode(EditingMode mode)
	{
		editingMode = mode;
		
		createRelationSource = null;
		createRelationTarget = null;
		createInheritanceToExtendClass = null;
		createInheritanceSuperClass = null;
		
	}
	
	
	public EditingMode getEditingMode()
	{
		return editingMode;
	}
	
	
	
	@Override
	public void onClick(final ClickEvent event) {


		switch (editingMode)
		{
		
		
			case NORMAL:
				
				Object previous = currentClicked;
				
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
			
				
			case CREATE_CLASS:
				final double x = event.getClientX();
				final double y = event.getClientY();
				SC.askforValue("Create MetaClass", "Please insert the name", new ValueCallback() {
					
					@Override
					public void execute(String value) {
						if (value == null || value.isEmpty())
						{
							NotificationManager.getInstance().showTipNotification(new TipNotification("Insert a valid name", null, 2000, NotificationPosition.BOTTOM_CENTERED), AnimationEffect.FADE);
							createMetaClassName = null;
						}
						
						else
							for(CreateMetaClassHandler h : createClassHandlers)
								h.onCreateMetaClass(new CreateMetaClassEvent(value, x, y));
					}			
				});
				
				break;
				
			case CREATE_RELATION:
				
				Drawable d = getDrawableStorage().getDrawableAt(event.getX(), event.getY());
				
				if (d instanceof MetaClassDrawable){
					if(createRelationSource==null){
						createRelationSource = (MetaClassDrawable) d;
						NotificationManager.getInstance().showTipNotification(new TipNotification("Select the traget", null, 2000, NotificationPosition.BOTTOM_CENTERED));
					}
					else
					if (createRelationTarget == null){
						createRelationTarget = (MetaClassDrawable) d;
					
					
						SC.askforValue("Create MetaRelation", "Please insert the name", new ValueCallback() {
							
							@Override
							public void execute(String value) {
								if (!FieldVerifier.isValidRelationName(value))
								{
									NotificationManager.getInstance().showTipNotification(new TipNotification("Inserted name is not valid", null, 2000, NotificationPosition.BOTTOM_CENTERED), AnimationEffect.FADE);
									createMetaClassName = null;
								}
								
								else
									for(CreateMetaRelationHandler h : createRelationHandlers)
										h.onCreateMetaRelation(value, createRelationSource.getMetaClass(), createRelationTarget.getMetaClass());
								
								
							}			
						});
					}
					
				}
				else
					NotificationManager.getInstance().showTipNotification(new TipNotification("No MetaClass selected", "Please click on a MetaClass", 2000, NotificationPosition.BOTTOM_CENTERED), AnimationEffect.FADE);
				
				
					
				
				break;
				
				
			case CREATE_INHERITANCE:
				
				Drawable selected = getDrawableStorage().getDrawableAt(event.getX(), event.getY());
				
				if (selected instanceof MetaClassDrawable){
					if(createInheritanceToExtendClass==null){
						createInheritanceToExtendClass = (MetaClassDrawable) selected;
						NotificationManager.getInstance().showTipNotification(new TipNotification("Select super class", null, 2000, NotificationPosition.BOTTOM_CENTERED));
					}
					else
					if (createInheritanceSuperClass == null){
						createInheritanceSuperClass = (MetaClassDrawable) selected;
						
						
						for (CreateMetaInheritanceHandler h : createInheritanceHandlers)
							h.onCreateInheritance(createInheritanceToExtendClass.getMetaClass(), createInheritanceSuperClass.getMetaClass());
						
						createInheritanceToExtendClass = null;
						createInheritanceSuperClass = null;
						
					}
					
				}
				else
					NotificationManager.getInstance().showTipNotification(new TipNotification("No MetaClass selected", "Please click on a MetaClass", 2000, NotificationPosition.BOTTOM_CENTERED), AnimationEffect.FADE);
				
				
				break;
				
			
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
				if (currentMouseDownDrawable!=null && currentMouseDownDrawable instanceof Moveable )
				{
					mouseDownInitialXDistance = event.getX() - ((Moveable)currentMouseDownDrawable).getX();
					mouseDownInitialYDistance = event.getY() - ((Moveable)currentMouseDownDrawable).getY();
				}
				
				
				// Has Placeable (normaly used for the AnchorPoints)
				if (currentMouseDownDrawable!=null && currentMouseDownDrawable instanceof HasPlaceable)
				{
					this.currentPlaceable = ((HasPlaceable)currentMouseDownDrawable).hasPlaceableAt(event.getX(), event.getY());
					
					// Handle focus Events for Placeable, if they are also Focusable
					if (currentPlaceable != null)
						currentPlaceable.setSelected(true);
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
					if (resizeableOnlyOnSelected)
					{
						if (currentMouseDownDrawable.isSelected())
						{
							double width = beforeResizeWidth +  event.getX() - mouseDownX;
							double height = beforeResizeHeight + event.getY() - mouseDownY;
							
							Resizeable res = (Resizeable)currentMouseDownDrawable;
							resizing = true;
							
							// Fire only a event if the minWidth and min Height allow this
							if (res.getMinWidth()<width && res.getMinHeight()<height)
							{
								ResizeEvent e = new ResizeEvent((Resizeable) currentMouseDownDrawable, ResizeEventType.TEMP_RESIZE, width, height, event.getX(), event.getY(), currentResizeArea);
								res.fireResizeEvent(e);
							}
						}
					}
					else
					{
						double width = beforeResizeWidth +  event.getX() - mouseDownX;
						double height = beforeResizeHeight + event.getY() - mouseDownY;
						
						Resizeable res = (Resizeable)currentMouseDownDrawable;
						resizing = true;
						
						// Fire only a event if the minWidth and min Height allow this
						if (res.getMinWidth()<width && res.getMinHeight()<height)
						{
							ResizeEvent e = new ResizeEvent((Resizeable) currentMouseDownDrawable, ResizeEventType.TEMP_RESIZE, width, height, event.getX(), event.getY(), currentResizeArea);
							res.fireResizeEvent(e);
						}
					}
				}
				
				else
					
				if (currentMouseDownDrawable != null &&  currentMouseDownDrawable instanceof HasPlaceable && currentPlaceable != null)
				{
					placing = true;
					
					
					if (currentPlaceable.getPlaceableDestination() == null) // can be placed everywhere
					{
						// TODO should be something special be done?
					}
					else
					{
						if (currentPlaceable.getPlaceableDestination().canPlaceableBePlacedAt(event.getX(), event.getY()) != null)
							currentPlaceable.setCanBePlaced(true);
						else
							currentPlaceable.setCanBePlaced(false);
					}
					
					PlaceEvent e = new PlaceEvent(currentPlaceable, PlaceEventType.TEMP_PLACING, event.getX(), event.getY(), (HasPlaceable)currentMouseDownDrawable);
					currentPlaceable.firePlaceEvent(e);
					
				}
				
				else
					
				// MoveEvent
				if (currentMouseDownDrawable instanceof Moveable && isMouseDown)
				{
					if (moveableOnlyOnSelected)
					{
						
						if (currentMouseDownDrawable.isSelected())
						{
						
							moving = true;
							MoveEvent e = new MoveEvent((Moveable)currentMouseDownDrawable, MoveEventType.TEMP_MOVE, mouseDownX, mouseDownY, event.getX(), event.getY(), mouseDownInitialXDistance, mouseDownInitialYDistance, event.getScreenX(), event.getScreenY(), isMouseDown);
					
							((Moveable)currentMouseDownDrawable).fireMoveEvent(e);
						}
					}
					else
					{
						moving = true;
						MoveEvent e = new MoveEvent((Moveable)currentMouseDownDrawable, MoveEventType.TEMP_MOVE, mouseDownX, mouseDownY, event.getX(), event.getY(), mouseDownInitialXDistance, mouseDownInitialYDistance, event.getScreenX(), event.getScreenY(), isMouseDown);
				
						((Moveable)currentMouseDownDrawable).fireMoveEvent(e);
					}
					
				}
				
				
			break; // END Normal
				
		} // END switch

	}


	// REIHENFOLGE: erster kommt MouseUp, dann MouseClick

	@Override
	public void onMouseUp(MouseUpEvent event) {

		double x, y;
		int screenX, screenY;
		
		
		if (event != null)
		{
			x = event.getX();
			y = event.getY();
			screenX = event.getScreenX();
			screenY = event.getScreenY();
			
		}
		else // little hack, because onMouseUp will call this method with null as argument because its not possible to pass the MouseOut properties as an MouseUp event
		{
			x = lastMouseOutEvent.getX();
			y = lastMouseOutEvent.getY();

			screenX = lastMouseOutEvent.getScreenX();
			screenY = lastMouseOutEvent.getScreenY();
		}
		
		
		switch (editingMode)
		{
			case NORMAL:
				
				if (moving && currentMouseDownDrawable instanceof Moveable && isMouseDown)
				{
					//TODO needed to check the moveableOnlyOnSelected field ?
					MoveEvent e = new MoveEvent((Moveable)currentMouseDownDrawable, MoveEventType.MOVE_FINISHED, mouseDownX, mouseDownY, x, y, mouseDownInitialXDistance, mouseDownInitialYDistance, screenX, screenY, isMouseDown);
		
					((Moveable)currentMouseDownDrawable).fireMoveEvent(e);
					
				}
				
				
				if (resizing && currentMouseDownDrawable != null && currentMouseDownDrawable instanceof Resizeable && isMouseDown && currentResizeArea != null)
				{
					if (resizeableOnlyOnSelected && currentMouseDownDrawable.isSelected())
					{
						double width = beforeResizeWidth +  x - mouseDownX;
						double height = beforeResizeHeight + y - mouseDownY;
						
						
						Resizeable res = (Resizeable)currentMouseDownDrawable;
						
						// Fire only a event if the minWidth and min Height allow this
						if (res.getMinWidth()<width && res.getMinHeight()<height)
						{
							ResizeEvent e = new ResizeEvent((Resizeable) currentMouseDownDrawable, ResizeEventType.RESIZE_FINISHED, width, height, x, y, currentResizeArea);
							res.fireResizeEvent(e);
						}
						else
							res.fireResizeEvent(new ResizeEvent(res,  ResizeEventType.NOT_ALLOWED, width, height, x, y, currentResizeArea));
						
					}
				}
				
				
				if (placing && currentMouseDownDrawable instanceof HasPlaceable && currentPlaceable !=null)
				{
					if (currentPlaceable.getPlaceableDestination()== null) // can be placed everywhere
					{
						PlaceEvent e = new PlaceEvent(currentPlaceable, PlaceEventType.PLACING_FINISHED, x, y, (HasPlaceable)currentMouseDownDrawable);
						currentPlaceable.firePlaceEvent(e);
					}
					else
					{
						
						PlaceEvent e;
						
						Drawable newDestination = getDrawableStorage().getSecondDrawableAt(x,y);
						
						// Source / Target MetaConnection Point move
					
						if (newDestination!= null  && newDestination!=currentPlaceable.getPlaceableDestination() 
								&& newDestination instanceof PlaceableDestination && newDestination instanceof MetaClassDrawable && currentMouseDownDrawable instanceof MetaConnectionDrawable){
							
							MetaConnection mc = ((MetaConnectionDrawable) currentMouseDownDrawable).getMetaConnection();
							Anchor anchor = (Anchor) currentPlaceable;
							MetaClassDrawable newDestClass = (MetaClassDrawable) newDestination;
							
							// MetaConnection source
							if (anchor.getAnchorPoint().getID().equals(mc.getSourceRelativePoint().getID())){
								e = new PlaceEvent(currentPlaceable, PlaceEventType.PLACING_FINISHED, 0, newDestClass.getHeight(), (HasPlaceable)currentMouseDownDrawable);
								e.setNewMetaConnectionSourceDestination((PlaceableDestination) newDestination);	
								currentPlaceable.firePlaceEvent(e);
								return;
							}
							else // MetaConnection target
							if (anchor.getAnchorPoint().getID().equals(mc.getTargetRelativePoint().getID())){
								e = new PlaceEvent(currentPlaceable, PlaceEventType.PLACING_FINISHED, 0, newDestClass.getHeight(), (HasPlaceable)currentMouseDownDrawable);
								e.setNewMetaConnectionTargetDestination((PlaceableDestination) newDestination);	
								currentPlaceable.firePlaceEvent(e);
								return;
							}
						}
						
						
						
						
						
						
						// normal anchot point move
						
							Point p = currentPlaceable.getPlaceableDestination().canPlaceableBePlacedAt(x, y);
						
							if (p == null) // Not allowed to be placed there
								e = new PlaceEvent(currentPlaceable, PlaceEventType.NOT_ALLOWED, x, y, (HasPlaceable)currentMouseDownDrawable);
							else
								e = new PlaceEvent(currentPlaceable, PlaceEventType.PLACING_FINISHED, p.x, p.y, (HasPlaceable)currentMouseDownDrawable);
							
							currentPlaceable.firePlaceEvent(e);
						
					}
					
				}
				

				
			break; // END editing mode
		
		} // End NORMAL


		isMouseDown = false;
		moving = false;
		resizing = false;
		placing = false;
		currentMouseDownDrawable = null;
		currentResizeArea = null;
		
		if (currentPlaceable != null) 
		{
			currentPlaceable.setSelected(false);
			currentPlaceable.setCanBePlaced(false);
		}
		
		currentPlaceable = null;
	}


	@Override
	public void onMouseOut(MouseOutEvent event) {
		// If you are out of the canvas while Mouse is still down
		
		// TODO is the current mouse out behavior desired?
		//lastMouseOutEvent = event;
		//onMouseUp(null);

	}
	
	
	public void addCreateMetaRelationHandler(CreateMetaRelationHandler h){
		createRelationHandlers.add(h);
	}
	
	public void removeCreateMetaRelationHandler(CreateMetaRelationHandler h){
		createRelationHandlers.remove(h);
	}
	
	
	

}
