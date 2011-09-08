package org.gemsjax.client.admin.presenter;


import java.util.ArrayList;
import java.util.List;

import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.canvas.Anchor;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.MetaConnectionBox;
import org.gemsjax.client.canvas.MetaConnectionDrawable;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.canvas.Moveable;
import org.gemsjax.client.canvas.ResizeArea;
import org.gemsjax.client.canvas.Resizeable;
import org.gemsjax.client.canvas.MetaModelCanvas.EditingMode;
import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.IconLoadEvent;
import org.gemsjax.client.canvas.events.MouseOutEvent;
import org.gemsjax.client.canvas.events.MouseOverEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.PlaceEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.events.PlaceEvent.PlaceEventType;
import org.gemsjax.client.canvas.events.ResizeEvent.ResizeEventType;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.IconLoadHandler;
import org.gemsjax.client.canvas.handler.MouseOutHandler;
import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.PlaceHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.docs.Debugging;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * The Presenter that manages the MetaModelView and the MetaModel.
 * @author Hannes Dorfmann
 *
 */
public class MetaModelPresenter extends Presenter implements ClickHandler,FocusHandler, ResizeHandler, MoveHandler, MouseOverHandler, MouseOutHandler, IconLoadHandler, PlaceHandler{
	
	private MetaModel metaModel;
	private MetaModelView view;

	public MetaModelPresenter(EventBus eventBus, MetaModelView view, MetaModel metaModel) {
		super(eventBus);
		
		this.metaModel = metaModel;
		this.view = view;
		
		TabEnviroment.getInstance().addTab((Tab)view);
		
		bind();
		generateInitialDrawables();
	}
	
	
	private void bind()
	{
		view.getNewInheritanceButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				view.setCanvasEditingMode(EditingMode.CREATE_INHERITANCE);
			}
		});
		
		view.getNewMetaClassButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				view.setCanvasEditingMode(EditingMode.CREATE_CLASS);
			}
		});
		
		view.getNewRelationshipButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				view.setCanvasEditingMode(EditingMode.CREATE_RELATION);
			}
		});
		
		view.getUseMouseButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				view.setCanvasEditingMode(EditingMode.NORMAL);
			}
		});
	}
	
	
	
	private void generateInitialDrawables()
	{
		
		try {
			
			// First add all MetaClasses
			for (MetaClass c: metaModel.getMetaClasses())
			{
				MetaClassDrawable d = new MetaClassDrawable(c);
				d.addFocusHandler(this);
				d.addMoveHandler(this);
				d.addResizeHandler(this);
				d.addMouseOutHandler(this);
				d.addMouseOverHandler(this);
				d.addClickHandler(this);
				d.addIconLoadHandler(this);
				view.addDrawable(d);	
			}
			
			
			// Add all Connections
			for (MetaClass c: metaModel.getMetaClasses())
			{
				for (MetaConnection con: c.getConnections())
				{
					MetaClassDrawable source = (MetaClassDrawable) view.getDrawableOf(con.getSource());
					MetaClassDrawable target = (MetaClassDrawable) view.getDrawableOf(con.getTarget());
					
					
					MetaConnectionDrawable d = new MetaConnectionDrawable(con, source,  target);
					d.addMoveHandler(this);
					d.addResizeHandler(this);
					d.addFocusHandler(this);
					
					// Add PlaceHandler to the AnchorPoints between source and Connection box
					AnchorPoint currentPoint = d.getSourceAnchor().getAnchorPoint();
					Anchor a ;
					
					while (currentPoint!=d.getSourceConnectionBoxAnchor().getAnchorPoint())
					{
						a = d.getAnchor(currentPoint);
						if (a != null)
							a.addPlaceHandler(this);
						else
							SC.logWarn("Error: Anchor not found for AnchorPoint "+currentPoint);
						
						currentPoint = currentPoint.getNextAnchorPoint();
					}
					
					d.getSourceConnectionBoxAnchor().addPlaceHandler(this);
					
					
					// Add PlaceHandler to the AnchorPoints between connection box and target
					currentPoint = d.getTargetConnectionBoxAnchor().getAnchorPoint();
					
					while (currentPoint!=d.getTargetAnchor().getAnchorPoint())
					{
						a = d.getAnchor(currentPoint);
						
						if (a != null)
							a.addPlaceHandler(this);
						else
							SC.logWarn("Error: Anchor not found for AnchorPoint "+currentPoint);
						
						currentPoint = currentPoint.getNextAnchorPoint();
					}
					
					d.getTargetAnchor().addPlaceHandler(this);
					
					
					view.addDrawable(d);
				
				}
			}	
			
			
			view.redrawMetaModelCanvas();
			
			
		} catch (DoubleLimitException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onFocusEvent(FocusEvent event) {
		
		if (event.getSource() instanceof MetaClassDrawable)
			onMetaClassFocusEvent((MetaClass) ((Drawable)event.getSource()).getDataObject(), event);
		else
		if (event.getSource() instanceof MetaConnectionDrawable)
			onMetaConnectionFocusEvent((MetaConnection) ((Drawable)event.getSource()).getDataObject(), event);
	
	}


	@Override
	public void onResize(ResizeEvent event) {
		
		if (event.getSource() instanceof MetaClassDrawable)
			onMetaClassResizeEvent((MetaClass) ((Drawable)event.getSource()).getDataObject(), event);
		else
		if (event.getSource() instanceof MetaConnectionDrawable)
			onMetaConnectionResizeEvent((MetaConnection) ((Drawable)event.getSource()).getDataObject(), event);
	}


	@Override
	public void onMove(MoveEvent e) {
		
		if (e.getSource() instanceof MetaClassDrawable)
			onMetaClassMoveEvent((MetaClass) ((Drawable)e.getSource()).getDataObject(), e);
		else
		if (e.getSource() instanceof MetaConnectionDrawable)
			onMetaConnectionMoveEvent((MetaConnection) ((Drawable)e.getSource()).getDataObject(), e);
		
	}


	@Override
	public void onMouseOver(MouseOverEvent event) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMouseOut(MouseOutEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void onMetaClassFocusEvent(MetaClass metaClass, FocusEvent event)
	{
		if (event.getType()==FocusEventType.GOT_FOCUS)
			metaClass.setSelected(true);
		else
			metaClass.setSelected(false);
		
		view.redrawMetaModelCanvas();
	}
	
	private void onMetaClassMoveEvent(MetaClass metaClass, MoveEvent e)
	{
		// Resize the MetaClass
		MetaClassDrawable d =(MetaClassDrawable) e.getSource();
		d.setX(e.getX()-e.getDistanceToTopLeftX());
		d.setY(e.getY()-e.getDistanceToTopLeftY());

		
		if (e.getType()==MoveEventType.MOVE_FINISHED){
			metaClass.setX(e.getX()-e.getDistanceToTopLeftX());
			metaClass.setY(e.getY()-e.getDistanceToTopLeftY());
			
			// TODO collaborative info websocket
		}
			
		view.redrawMetaModelCanvas();
	}
	
	
	private void onMetaClassResizeEvent(MetaClass metaClass, ResizeEvent event)
	{

		if (event.getWidth()<metaClass.getMinWidth() || event.getHeight()<metaClass.getMinHeight())
			return;
		
			// Its done, when ResizeEventType.TEMP_RESIZE  and  RESIZE_FINISHED
			MetaClassDrawable d =(MetaClassDrawable) event.getSource();
			d.setWidth( event.getWidth());
			d.setHeight(event.getHeight());
				
			if (event.getType()==ResizeEventType.RESIZE_FINISHED )
			{
				metaClass.setWidth(event.getWidth());
				metaClass.setHeight(event.getHeight());
				
				
				// Check for Anchors which position has been changed durrning the resizement
				
				List<AnchorPoint> changedAnchorPoints = new ArrayList<AnchorPoint>();
				
				
				for (Anchor a : d.getDockedAnchors())
				
					if (a.getX()!=a.getAnchorPoint().x || a.getY() != a.getAnchorPoint().y)
					{
						a.getAnchorPoint().x = a.getX();
						a.getAnchorPoint().y = a.getY();
						changedAnchorPoints.add(a.getAnchorPoint());
					}
				
				// TODO collabrative info websocket (also changedAnchorPoints)
				}
			else
			if (event.getType()==ResizeEventType.NOT_ALLOWED)
			{
				SC.logWarn("resize not allowed");
				// TODO If not allowed cause minWidth / minHeight
			}
			
			
			view.redrawMetaModelCanvas();
		
	}

	
	private void onMetaConnectionMoveEvent(MetaConnection connection, MoveEvent e)
	{
		Moveable d = e.getSource();
		d.setX(e.getX()-e.getDistanceToTopLeftX());
		d.setY(e.getY()-e.getDistanceToTopLeftY());

		
		if (e.getType()==MoveEventType.MOVE_FINISHED){
			connection.setConnectionBoxX(e.getX()-e.getDistanceToTopLeftX());
			connection.setConnectionBoxY(e.getY()-e.getDistanceToTopLeftY());
			
			// TODO collabrative info websocket
		}
		
		view.redrawMetaModelCanvas();
	}
	
	
	private void onMetaConnectionResizeEvent(MetaConnection connection, ResizeEvent event)
	{
		
		if (event.getWidth()<connection.getConnectionBoxMinWidth() || event.getHeight()<connection.getConnectionBoxMinHeight())
			return;
		
		MetaConnectionDrawable d = (MetaConnectionDrawable)event.getSource();
		
		

			d.setWidth(event.getWidth());
			d.setHeight(event.getHeight());
			
			
			d.adjustConnectionBoxAnchors();
			
			
			
			if (event.getType() == ResizeEventType.RESIZE_FINISHED)
			{
				connection.setConnectionBoxWidth(event.getWidth());
				connection.setConnectionBoxHeight(event.getHeight());
				
				
				List<AnchorPoint> changedAnchorPoints = new ArrayList<AnchorPoint>();
				
				
				for (Anchor a : d.getDockedAnchors())
				
					if (a.getX()!=a.getAnchorPoint().x || a.getY() != a.getAnchorPoint().y)
					{
						a.getAnchorPoint().x = a.getX();
						a.getAnchorPoint().y = a.getY();
						changedAnchorPoints.add(a.getAnchorPoint());
					}
				
				// TODO collaborative websocket info
				

			}
			
			view.redrawMetaModelCanvas();
		
	}
	
	
	private void onMetaConnectionFocusEvent(MetaConnection connection, FocusEvent event)
	{
		if (event.getType()==FocusEventType.GOT_FOCUS)
			connection.setSelected(true);
		else
			connection.setSelected(false);
		
		view.redrawMetaModelCanvas();
	}

	@Override
	public void onIconLoaded(IconLoadEvent e) {
		
		view.redrawMetaModelCanvas();
	}


	@Override
	public void onPlaceEvent(PlaceEvent event) {
	
		if (event.getSource() instanceof Anchor && event.getParent() instanceof MetaConnectionDrawable)
			onMetaConnectionAnchor((Anchor) event.getSource(), (MetaConnectionDrawable) event.getParent() , event);
		
	}
	
	
	
	private void onMetaConnectionAnchor(Anchor p, MetaConnectionDrawable parent,  PlaceEvent e)
	{
		double x=e.getX() , y=e.getY();
		
		// transform to relative coordinates, if its one of the 4 default anchor points
		if (p == parent.getSourceAnchor())		
		{
			x = e.getX() - parent.getSourceDrawable().getX();
			y = e.getY() - parent.getSourceDrawable().getY();
		}
		else
		if (p == parent.getSourceConnectionBoxAnchor())
		{
			x = e.getX() - parent.getConnectionBox().getX();
			y = e.getY() - parent.getConnectionBox().getY();
		}
		else
		if (p == parent.getTargetConnectionBoxAnchor())
		{
			x = e.getX() - parent.getConnectionBox().getX();
			y = e.getY() - parent.getConnectionBox().getY();
		}
		else
		if (p == parent.getTargetAnchor())
		{
			x = e.getX() - parent.getTargetDrawable().getX();
			y = e.getY() - parent.getTargetDrawable().getY();
		}
		
		
		if (e.getType() == PlaceEventType.TEMP_PLACING) // its just a temporary placing event, so update only the view
		{
			p.setX(x);
			p.setY(y);
			SC.logWarn("Temp placing");
		}	
		else
		if (e.getType()==PlaceEventType.PLACING_FINISHED) // finished, so update the view, model and inform the other collaborative clients
		{
			p.setX(x);
			p.setY(y);
			AnchorPoint ap = p.getAnchorPoint();
			
			ap.x = x;
			ap.y = y;
			

			SC.logWarn("Finished placing");
			//TODO collaborativ websocket information
		}
		else
		if(e.getType() == PlaceEventType.NOT_ALLOWED) // Not Allowed: display a notification, restore the anchors position to the position before the TEMP_PLACING has started
		{
			AnchorPoint ap = p.getAnchorPoint();
			
			p.setX(ap.x);
			p.setY(ap.y);

			SC.logWarn("Not allowed placing");
			
			view.showAnchorPlaceNotAllowed(p);
		}
		
		
		view.redrawMetaModelCanvas();
	}
	

}
