package org.gemsjax.client.admin.presenter;


import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.MetaConnectionDrawable;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.canvas.ResizeArea;
import org.gemsjax.client.canvas.MetaModelCanvas.EditingMode;
import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.MouseOutEvent;
import org.gemsjax.client.canvas.events.MouseOverEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.MouseOutHandler;
import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.widgets.tab.Tab;

/**
 * The Presenter that manages the MetaModelView and the MetaModel.
 * @author Hannes Dorfmann
 *
 */
public class MetaModelPresenter extends Presenter implements ClickHandler,FocusHandler, ResizeHandler, MoveHandler, MouseOverHandler, MouseOutHandler{
	
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
				view.addDrawable(d);	
			}
			
			
			// Add all Connections
			for (MetaClass c: metaModel.getMetaClasses())
			{
				for (MetaConnection con: c.getConnections())
				{
					MetaClassDrawable source = (MetaClassDrawable) view.getDrawableOf(con.getSource());
					MetaClassDrawable target = (MetaClassDrawable) view.getDrawableOf(con.getTarget());
					
					view.addDrawable(new MetaConnectionDrawable(con, source,  target));
				
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
	}


	@Override
	public void onResize(ResizeEvent event) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMove(MoveEvent e) {
		if (e.getSource() instanceof MetaClassDrawable)
			onMetaClassMoveEvent((MetaClass) ((Drawable)e.getSource()).getDataObject(), e);
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
	}
	
	private void onMetaClassMoveEvent(MetaClass metaClass, MoveEvent e)
	{
		double oldX = metaClass.getX();
		double oldY = metaClass.getY();
		
		metaClass.setX(e.getX()-e.getDistanceToTopLeftX());
		metaClass.setY(e.getY()-e.getDistanceToTopLeftY());


		MetaClassDrawable d =(MetaClassDrawable) e.getSource();
		
		// Set the Position of the ResizeAreas
		for (ResizeArea ra : d.getResizeAreas())
		{
			ra.setX(ra.getX() + (metaClass.getX()-oldX));
			ra.setY(ra.getY() + (metaClass.getY()-oldY));
		}
	}
	

}
