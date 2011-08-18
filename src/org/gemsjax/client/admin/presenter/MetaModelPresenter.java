package org.gemsjax.client.admin.presenter;


import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.view.MetaModelView;
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
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.events.ResizeEvent.ResizeEventType;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.IconLoadHandler;
import org.gemsjax.client.canvas.handler.MouseOutHandler;
import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
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
public class MetaModelPresenter extends Presenter implements ClickHandler,FocusHandler, ResizeHandler, MoveHandler, MouseOverHandler, MouseOutHandler, IconLoadHandler{
	
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
		if (event.getSource() instanceof MetaConnectionBox)
			onMetaConnectionResizeEvent((MetaConnection) ((Drawable)event.getSource()).getDataObject(), event);
	}


	@Override
	public void onMove(MoveEvent e) {
		
		if (e.getSource() instanceof MetaClassDrawable)
			onMetaClassMoveEvent((MetaClass) ((Drawable)e.getSource()).getDataObject(), e);
		else
		if (e.getSource() instanceof MetaConnectionBox)
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
			
			// TODO collabrative info websocket
		}
			
		view.redrawMetaModelCanvas();
	}
	
	
	private void onMetaClassResizeEvent(MetaClass metaClass, ResizeEvent event)
	{

		if (event.getWidth()>metaClass.getMinWidth() && event.getHeight()>metaClass.getMinHeight())
		{
		
			// Its done, when ResizeEventType.TEMP_RESIZE  and  RESIZE_FINISHED
			MetaClassDrawable d =(MetaClassDrawable) event.getSource();
			d.setWidth( event.getWidth());
			d.setHeight(event.getHeight());
				
			if (event.getType()==ResizeEventType.RESIZE_FINISHED)
			{
				metaClass.setWidth(event.getWidth());
				metaClass.setHeight(event.getHeight());
				
				// TODO collabrative info websocket
			}
			
			
			
			view.redrawMetaModelCanvas();
			
		}
	}

	
	private void onMetaConnectionMoveEvent(MetaConnection connection, MoveEvent e)
	{
		Moveable d = e.getSource();
		d.setX(e.getX()-e.getDistanceToTopLeftX());
		d.setY(e.getY()-e.getDistanceToTopLeftY());

		SC.logWarn("Move Temp "+d.getX()+" "+d.getY()+"     "+connection.getConnectionBoxX()+" "+connection.getConnectionBoxY());
		
		if (e.getType()==MoveEventType.MOVE_FINISHED){
			connection.setConnectionBoxX(e.getX()-e.getDistanceToTopLeftX());
			connection.setConnectionBoxY(e.getY()-e.getDistanceToTopLeftY());
			
			// TODO collabrative info websocket
			SC.logWarn("Move FINISHED "+d.getX()+" "+d.getY()+"     "+connection.getConnectionBoxX()+" "+connection.getConnectionBoxY());
		}
		
		view.redrawMetaModelCanvas();
	}
	
	
	private void onMetaConnectionResizeEvent(MetaConnection connection, ResizeEvent event)
	{
		Resizeable d = event.getSource();
		
		if (event.getWidth()>d.getMinWidth() && event.getHeight()>d.getMinHeight())
		{

			d.setWidth(event.getWidth());
			d.setHeight(event.getHeight());
			
			
			
			
			
			if (event.getType() == ResizeEventType.RESIZE_FINISHED)
			{
				connection.setConnectionBoxWidth(event.getWidth());
				connection.setConnectionBoxHeight(event.getHeight());
				
				// TODO collaborative websocket info
				
	/*
				// Adjust connections coordinate
				if (connection.getSourceConnectionBoxRelativeX()>event.getWidth())
					connection.setSourceConnectionBoxRelativeX(event.getWidth());
				
				if (connection.getSourceConnectionBoxRelativeY()>event.getHeight())
					connection.setSourceConnectionBoxRelativeY(event.getHeight());
				
				if (connection.getTargetConnectionBoxRelativeX()>event.getWidth())
					connection.setTargetConnectionBoxRelativeX(event.getWidth());
				
				if (connection.getTargetConnectionBoxRelativeY()>event.getHeight())
					connection.setTargetConnectionBoxRelativeY(event.getHeight());
					*/
			}
			
			/* TODO needed?
			if (autoAdjustNameBoxRatio)
			{
				if (connection.getANameBoxRelativeX()>event.getWidth())
					connection.setANameBoxRelativeX(event.getWidth());
				
				if (connection.getANameBoxRelativeY()>event.getHeight())
					connection.setANameBoxRelativeY(event.getHeight());
				
				if (connection.getBNameBoxRelativeX()>event.getWidth())
					connection.setBNameBoxRelativeX(event.getWidth());
				
				if (connection.getBNameBoxRelativeY()>event.getHeight())
					connection.setBNameBoxRelativeY(event.getHeight());
			}
			*/ 
			
			view.redrawMetaModelCanvas();
		}
		
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
	

}
