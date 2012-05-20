package org.gemsjax.client.admin.presenter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.presenter.event.CollaborateableClosedEvent;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.admin.view.MetaModelView.MetaAttributeManipulationListener.MetaAttributeManipulationEvent.ManipulationType;
import org.gemsjax.client.admin.view.MetaModelView.MetaClassPropertiesListener.MetaClassPropertyEvent.PropertyChangedType;
import org.gemsjax.client.admin.view.MetaModelView.MetaConnectionPropertiesListener.MetaConnectionPropertyEvent.ConnectionPropertyChangedType;
import org.gemsjax.client.canvas.Anchor;
import org.gemsjax.client.canvas.CreateMetaRelationHandler;
import org.gemsjax.client.canvas.DockableAnchor;
import org.gemsjax.client.canvas.Drawable;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.canvas.MetaConnectionDrawable;
import org.gemsjax.client.canvas.MetaInheritanceDrawable;
import org.gemsjax.client.canvas.MetaModelCanvas.EditingMode;
import org.gemsjax.client.canvas.Moveable;
import org.gemsjax.client.canvas.events.ClickEvent;
import org.gemsjax.client.canvas.events.FocusEvent;
import org.gemsjax.client.canvas.events.FocusEvent.FocusEventType;
import org.gemsjax.client.canvas.events.IconLoadEvent;
import org.gemsjax.client.canvas.events.MouseOutEvent;
import org.gemsjax.client.canvas.events.MouseOverEvent;
import org.gemsjax.client.canvas.events.MoveEvent;
import org.gemsjax.client.canvas.events.MoveEvent.MoveEventType;
import org.gemsjax.client.canvas.events.PlaceEvent;
import org.gemsjax.client.canvas.events.PlaceEvent.PlaceEventType;
import org.gemsjax.client.canvas.events.ResizeEvent;
import org.gemsjax.client.canvas.events.ResizeEvent.ResizeEventType;
import org.gemsjax.client.canvas.events.metamodel.CreateMetaClassEvent;
import org.gemsjax.client.canvas.handler.ClickHandler;
import org.gemsjax.client.canvas.handler.FocusHandler;
import org.gemsjax.client.canvas.handler.IconLoadHandler;
import org.gemsjax.client.canvas.handler.MouseOutHandler;
import org.gemsjax.client.canvas.handler.MouseOverHandler;
import org.gemsjax.client.canvas.handler.MoveHandler;
import org.gemsjax.client.canvas.handler.PlaceHandler;
import org.gemsjax.client.canvas.handler.ResizeHandler;
import org.gemsjax.client.canvas.handler.metamodel.CreateMetaClassHandler;
import org.gemsjax.client.module.CollaborationModule;
import org.gemsjax.client.module.handler.CollaborationModuleHandler;
import org.gemsjax.shared.AnchorPoint;
import org.gemsjax.shared.UUID;
import org.gemsjax.shared.collaboration.command.Command;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaClassAbstractCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaClassIconCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionIconsCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ChangeMetaConnectionMultiplicityCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaConnectionAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.CreateMetaConnectionCommand;
import org.gemsjax.shared.collaboration.command.metamodel.DeleteMetaAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.DeleteMetaConnectionAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.EditMetaAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.EditMetaConnectionAttributeCommand;
import org.gemsjax.shared.collaboration.command.metamodel.MoveMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.MoveMetaConnectionAnchorPointCommand;
import org.gemsjax.shared.collaboration.command.metamodel.RenameMetaClassCommand;
import org.gemsjax.shared.collaboration.command.metamodel.RenameMetaConnectionCommand;
import org.gemsjax.shared.collaboration.command.metamodel.ResizeMetaClassCommand;
import org.gemsjax.shared.communication.message.collaboration.Collaborator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.ChangeMetaConnectionMultiplicityCommandInstantiator;
import org.gemsjax.shared.communication.serialisation.instantiators.collaboration.command.MoveMetaConnectionAchnorPointCommandInstantiator;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaInheritance;
import org.gemsjax.shared.metamodel.MetaModel;
import org.gemsjax.shared.metamodel.MetaModelElement;
import org.gemsjax.shared.metamodel.impl.MetaFactory;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.CloseClickHandler;
import com.smartgwt.client.widgets.tab.events.TabCloseClickEvent;

/**
 * The Presenter that manages the MetaModelView and the MetaModel.
 * @author Hannes Dorfmann
 *
 */
public class MetaModelPresenter extends CollaborationPresenter implements ClickHandler,FocusHandler, ResizeHandler, MoveHandler, MouseOverHandler, MouseOutHandler, IconLoadHandler, PlaceHandler, 
																CollaborationModuleHandler, CreateMetaClassHandler,
																MetaModelView.MetaAttributeManipulationListener,
																MetaModelView.MetaClassPropertiesListener,
																MetaModelView.MetaConnectionPropertiesListener,
																CloseClickHandler, 
																CreateMetaRelationHandler{
	
	private MetaModel metaModel;
	private MetaModelView view;
	private CollaborationModule module;

	public MetaModelPresenter(EventBus eventBus, MetaModelView view, MetaModel metaModel, CollaborationModule module) throws IOException {
		super(eventBus);
		
		this.view = view;
		view.showLoading();
		this.metaModel = metaModel;
		this.module = module;
		module.addCollaborationModuleHandler(this);
		module.subscribe();
		
		TabEnviroment.getInstance().addTab((Tab)view);
		
		bind();
		
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
		
		
		view.getNewContainmentButton().addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				view.setCanvasEditingMode(EditingMode.CREATE_CONTAINMENT);
			}
		});
		
		view.addMetaClassPropertiesListener(this);
		view.addMetaConnectionPropertiesListener(this);
		view.addMetaAttributeManipulationListener(this);
		view.addCreateMetaClassHandler(this);
		view.addCreateMetaRelationHandler(this);
		
		view.addCloseClickHandler(this);
	}
	
	
	
	private void generateDrawables()
	{
		/*
		try {
			
			
		MetaBaseType stringType = new MetaBaseTypeImpl("1", "String");
		MetaBaseType dateType = new MetaBaseTypeImpl("2", "Date");
		MetaBaseType booleanType = new MetaBaseTypeImpl("3", "Boolean");
		MetaBaseType intType = new MetaBaseTypeImpl("4", "Integer");
		
		
		MetaClass songMC = new MetaClassImpl("1", "Song");
		songMC.setX(10);
		songMC.setY(10);
		songMC.setIconURL("/metamodel/icons/song.png");
		songMC.setWidth(100);
		songMC.setHeight(50);
		songMC.addAttribute(new MetaAttributeImpl("1-1", "name", stringType));
		songMC.addAttribute(new MetaAttributeImpl("1-2", "trackNumber", intType));
		songMC.autoSize();
		
		
		MetaClass albumMC = new MetaClassImpl("2", "Album");
		albumMC.addAttribute(new MetaAttributeImpl("2-1", "name", stringType));
		albumMC.addAttribute(new MetaAttributeImpl("2-2", "releaseDate", dateType));
		albumMC.setX(10);
		albumMC.setY(10);
		albumMC.setIconURL("/metamodel/icons/albumCD.png");
		albumMC.setWidth(100);
		albumMC.setHeight(50);
		albumMC.autoSize();
		
		
		MetaClass artistMC = new MetaClassImpl("3", "Artist");
		artistMC.addAttribute(new MetaAttributeImpl("3-1", "name", stringType));
		artistMC.addAttribute(new MetaAttributeImpl("3-2", "isBand", booleanType));
		artistMC.setX(10);
		artistMC.setY(10);
		artistMC.setIconURL("/metamodel/icons/artistBlue.png");
		artistMC.setWidth(100);
		artistMC.setHeight(50);
		artistMC.autoSize();
		
		
		MetaConnection partOf = MetaFactory.createMetaConnection("PartOf", songMC, albumMC);
		songMC.addConnection(partOf);
		
		
		MetaConnection sungBy = MetaFactory.createMetaConnection("SungBy", albumMC, artistMC);
		albumMC.addConnection(sungBy);
		
		
		metaModel.addMetaClass(artistMC);
		metaModel.addMetaClass(albumMC);
		metaModel.addMetaClass(songMC);
		
		metaModel.addBaseType(stringType);
		metaModel.addBaseType(dateType);
		metaModel.addBaseType(booleanType);
		metaModel.addBaseType(intType);
		
		
		
		
		} catch (MetaAttributeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MetaConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MetaClassException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MetaBaseTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		*/
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
			
			
			
			for (MetaClass c: metaModel.getMetaClasses())
			{
				// Add all Connections
				for (MetaConnection con: c.getConnections())
				{
					MetaClassDrawable source = (MetaClassDrawable) view.getDrawableOf(con.getSource());
					MetaClassDrawable target = (MetaClassDrawable) view.getDrawableOf(con.getTarget());
					
					
					MetaConnectionDrawable d = new MetaConnectionDrawable(con, source,  target);
					d.addMoveHandler(this);
					d.addResizeHandler(this);
					d.addFocusHandler(this);
					d.addIconLoadHandler(this);
					d.addClickHandler(this);
					
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
				
				
				// All inheritances
				for (MetaInheritance inh : c.getInheritances())
				{
					MetaClassDrawable ownerClass = (MetaClassDrawable) view.getDrawableOf(inh.getOwnerClass());
					MetaClassDrawable superClass = (MetaClassDrawable) view.getDrawableOf(inh.getSuperClass());
					
					
					MetaInheritanceDrawable d = new MetaInheritanceDrawable( inh, ownerClass, superClass);
					d.addFocusHandler(this);
					
					// Add PlaceHandler to the AnchorPoints between source and Connection box
					AnchorPoint currentPoint = d.getOwnerClassAnchor().getAnchorPoint();
					Anchor a ;
					
					while (currentPoint!=d.getSuperClassAnchor().getAnchorPoint())
					{
						a = d.getAnchor(currentPoint);
						if (a != null)
							a.addPlaceHandler(this);
						else
							SC.logWarn("Error: Anchor not found for AnchorPoint "+currentPoint);
						
						currentPoint = currentPoint.getNextAnchorPoint();
					}
					
					d.getSuperClassAnchor().addPlaceHandler(this);
					
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
		if (event.getSource() instanceof MetaClassDrawable)
		{
			view.setMetaClassDetail(((MetaClassDrawable)event.getSource()).getMetaClass());
		}
		else
		if(event.getSource() instanceof MetaConnectionDrawable){
			view.setMetaConnectionDetail(((MetaConnectionDrawable)event.getSource()).getMetaConnection());
		}
		else
			view.clearDetailView();
	}


	@Override
	public void onFocusEvent(FocusEvent event) {
		
		if (event.getSource() instanceof MetaClassDrawable)
			onMetaClassFocusEvent((MetaClass) ((Drawable)event.getSource()).getDataObject(), event);
		else
		if (event.getSource() instanceof MetaConnectionDrawable)
			onMetaConnectionFocusEvent((MetaConnection) ((Drawable)event.getSource()).getDataObject(), event);
		else
		if (event.getSource() instanceof MetaInheritanceDrawable)
			onMetaInheritanceFocusEvent((MetaInheritance) ((Drawable)event.getSource()).getDataObject(), event);
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
			double x = (e.getX()-e.getDistanceToTopLeftX());
			double y = (e.getY()-e.getDistanceToTopLeftY());
		
			try {
				module.sendAndCommitTransaction(new MoveMetaClassCommand(UUID.generate(), metaClass.getID(), x, y, metaClass.getX(), metaClass.getY()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
			d.adjustDockedAnchors();
				
			if (event.getType()==ResizeEventType.RESIZE_FINISHED )
			{
				List<Command> commands =new LinkedList<Command>();
				
				ResizeMetaClassCommand rc = new ResizeMetaClassCommand(UUID.generate(), metaClass.getID(), event.getWidth(), event.getHeight(), metaClass.getWidth(), metaClass.getHeight());
				commands.add(rc);
				
				/*
				metaClass.setWidth(event.getWidth());
				metaClass.setHeight(event.getHeight());
				*/
				
				
				
				// Check for Anchors which position has been changed durrning the resizement
				
				List<AnchorPoint> changedAnchorPoints = new ArrayList<AnchorPoint>();
				
				
				for (Anchor a : d.getDockedAnchors())
				
					
					if (a.getX()!=a.getAnchorPoint().x || a.getY() != a.getAnchorPoint().y)
					{
						MetaModelElement owner = metaModel.getAnchorPointOwner(a.getAnchorPoint());
						
						if (owner instanceof MetaConnection){
							MoveMetaConnectionAnchorPointCommand c = new MoveMetaConnectionAnchorPointCommand(UUID.generate(), (MetaConnection)owner, a.getAnchorPoint(), a.getX()-d.getX(), a.getY()-d.getY());
							commands.add(c);
						}
						/*
						 * a.getAnchorPoint().x = a.getX();
						 	a.getAnchorPoint().y = a.getY();
						 */
						
						changedAnchorPoints.add(a.getAnchorPoint());
					}
				
				
					try {
						module.sendAndCommitTransaction(commands);
					} catch (IOException e) {
						view.showSendError(e);
						e.printStackTrace();
					}
				
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
	
	
	private void onMetaInheritanceFocusEvent(MetaInheritance inheritance, FocusEvent event)
	{
		
		
		if (event.getType()==FocusEventType.GOT_FOCUS)
			inheritance.setSelected(true);
		else
			inheritance.setSelected(false);
		
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
		
		else
		if (event.getSource() instanceof Anchor && event.getParent() instanceof MetaInheritanceDrawable)
			onMetaInheritanceAnchor((Anchor) event.getSource(), (MetaInheritanceDrawable) event.getParent() , event);
		
	}
	
	
	
	private void onMetaInheritanceAnchor(Anchor p, MetaInheritanceDrawable parent, PlaceEvent e)
	{
		
		double x=e.getX() , y=e.getY();
		
		
		if (e.getType() == PlaceEventType.TEMP_PLACING) // its just a temporary placing event, so update only the view
		{
			p.setX(x);
			p.setY(y);
		}	
		else
		if (e.getType()==PlaceEventType.PLACING_FINISHED) // finished, so update the view, model and inform the other collaborative clients
		{
			p.setX(x);
			p.setY(y);
			AnchorPoint ap = p.getAnchorPoint();
			
			if (p instanceof DockableAnchor)
			{
				ap.x = ((DockableAnchor)p).getRelativeX();
				ap.y = ((DockableAnchor)p).getRelativeY();
			}
			else
			{
				ap.x = x;
				ap.y = y;
			}

			//TODO collaborative websocket information
		}
		else
		if(e.getType() == PlaceEventType.NOT_ALLOWED) // Not Allowed: display a notification, restore the anchors position to the position before the TEMP_PLACING has started
		{
			AnchorPoint ap = p.getAnchorPoint();
			
			if (p instanceof DockableAnchor)
			{
				((DockableAnchor) p).setRelativeX(ap.x);
				((DockableAnchor) p).setRelativeY(ap.y);
			}
			else
			{
				p.setX(ap.x);
				p.setY(ap.y);
			}
			
			view.showAnchorPlaceNotAllowed(p);
		}
		
		
		view.redrawMetaModelCanvas();
	}
	
	
	
	private void onMetaConnectionAnchor(Anchor p, MetaConnectionDrawable parent,  PlaceEvent e)
	{
		double x=e.getX() , y=e.getY();
		
		
		if (e.getType() == PlaceEventType.TEMP_PLACING) // its just a temporary placing event, so update only the view
		{
			p.setX(x);
			p.setY(y);
		}	
		else
		if (e.getType()==PlaceEventType.PLACING_FINISHED) // finished, so update the view, model and inform the other collaborative clients
		{
			p.setX(x);
			p.setY(y);
			AnchorPoint ap = p.getAnchorPoint();
			
			if (p instanceof DockableAnchor)
			{
				double newX = ((DockableAnchor)p).getRelativeX();
				double newY = ((DockableAnchor)p).getRelativeY();
				
				MoveMetaConnectionAnchorPointCommand c = new MoveMetaConnectionAnchorPointCommand(UUID.generate(), parent.getMetaConnection(), ap, newX, newY);
				try {
					module.sendAndCommitTransaction(c);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				double newX = x;
				double newY = y;
				
				MoveMetaConnectionAnchorPointCommand c = new MoveMetaConnectionAnchorPointCommand(UUID.generate(), parent.getMetaConnection(), ap, newX, newY);
				try {
					module.sendAndCommitTransaction(c);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	

		}
		else
		if(e.getType() == PlaceEventType.NOT_ALLOWED) // Not Allowed: display a notification, restore the anchors position to the position before the TEMP_PLACING has started
		{
			AnchorPoint ap = p.getAnchorPoint();
			
			if (p instanceof DockableAnchor)
			{
				((DockableAnchor) p).setRelativeX(ap.x);
				((DockableAnchor) p).setRelativeY(ap.y);
			}
			else
			{
				p.setX(ap.x);
				p.setY(ap.y);
			}
			
			view.showAnchorPlaceNotAllowed(p);
		}
		
		
		view.redrawMetaModelCanvas();
	}


	@Override
	public void onCollaborateableUpdated() {
		view.clearDrawables();
		generateDrawables();
	}
	


	@Override
	public void onCollaborateableInitialized() {
		
		view.setMetaBaseTypes(metaModel.getBaseTypes());
		view.showContent();
	}


	@Override
	public void showView() {
		// TODO implement
	}


	@Override
	public void onCollaboratorJoined(Collaborator c) {
		view.addCollaborator(c);
	}


	@Override
	public void onCollaboratorLeft(Collaborator c) {
		view.removeCollaborator(c);
	}


	@Override
	public void onCreateMetaClass(CreateMetaClassEvent e) {

		if (!metaModel.isClassRelationNameAvailable(e.getName()))
			view.showNameAlreadyInUseError(e.getName());
		else
			try {
				CreateMetaClassCommand c = new CreateMetaClassCommand(UUID.generate(), UUID.generate(), e.getName(), e.getX(), e.getY(), 100, 100, metaModel);
				c.setSequenceNumber(1);
				module.sendAndCommitTransaction(c);
				view.setCanvasEditingMode(EditingMode.NORMAL);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}


	@Override
	public void onMetaAttributeManipulated(MetaAttributeManipulationEvent e) {
		try {

			if (e.getMetaClass()!=null){
				if (e.getType()==ManipulationType.NEW){
					CreateMetaAttributeCommand c = new CreateMetaAttributeCommand(UUID.generate(), e.getMetaClass(), UUID.generate(), e.getName(), e.getBaseType());
					module.sendAndCommitTransaction(c);
				}
				else
				if (e.getType()==ManipulationType.MODIFY){
					EditMetaAttributeCommand c = new EditMetaAttributeCommand(UUID.generate(), e.getMetaAttribute().getID(), e.getMetaClass(), e.getName(), e.getMetaAttribute().getName(), e.getBaseType(), e.getMetaAttribute().getType());
					module.sendAndCommitTransaction(c);
				}
				else
				if (e.getType() == ManipulationType.DELETE){
					DeleteMetaAttributeCommand c = new DeleteMetaAttributeCommand(UUID.generate(), e.getMetaClass(), e.getMetaAttribute());
					module.sendAndCommitTransaction(c);
				}
			}
			else
			if (e.getMetaConnection()!=null){
				
				if (e.getType()==ManipulationType.NEW){

					CreateMetaConnectionAttributeCommand c = new CreateMetaConnectionAttributeCommand(UUID.generate(), e.getMetaConnection(), UUID.generate(), e.getName(), e.getBaseType());
					module.sendAndCommitTransaction(c);
				}
				else
				if (e.getType()==ManipulationType.MODIFY){
					EditMetaConnectionAttributeCommand c = new EditMetaConnectionAttributeCommand(UUID.generate(),  e.getMetaConnection(), e.getMetaAttribute(), e.getName(), e.getBaseType());
					module.sendAndCommitTransaction(c);
				}
				else
				if (e.getType() == ManipulationType.DELETE){
					DeleteMetaConnectionAttributeCommand c = new DeleteMetaConnectionAttributeCommand(UUID.generate(), e.getMetaConnection(), e.getMetaAttribute());
					module.sendAndCommitTransaction(c);
				}
				
			}
			
		} catch (IOException ex) {
			view.showSendError(ex);
			ex.printStackTrace();

		}
	}


	@Override
	public void onMetaClassPropertyChanged(MetaClassPropertyEvent e) {
		try {
			if (e.getType() == PropertyChangedType.RENAME){
				RenameMetaClassCommand c = new RenameMetaClassCommand(UUID.generate(), e.getMetaClass(), e.getName());
				module.sendAndCommitTransaction(c);
			}
			else
			if (e.getType() == PropertyChangedType.CHANGE_ICON){
				ChangeMetaClassIconCommand c = new ChangeMetaClassIconCommand(UUID.generate(), e.getMetaClass(), e.getIconUrl());
				module.sendAndCommitTransaction(c);
			}
			else
			if (e.getType() == PropertyChangedType.ABSTRACT){
				ChangeMetaClassAbstractCommand c = new ChangeMetaClassAbstractCommand(UUID.generate(), e.getMetaClass(), e.isAbstract());
				module.sendAndCommitTransaction(c);
			}
		} catch (IOException e1) {
			view.showSendError(e1);
			e1.printStackTrace();
		}
		
	}


	@Override
	public void onCloseClick(TabCloseClickEvent event) {
		
		try {
			module.unsubscribe();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO memory cleanup
		eventBus.fireEvent(new CollaborateableClosedEvent(metaModel.getId()));
		
	}


	@Override
	public void onCreateMetaRelation(String name, MetaClass source,
			MetaClass target) {
		
		view.setCanvasEditingMode(EditingMode.NORMAL);
		
		
		MetaConnection mc = MetaFactory.createMetaConnection(name, source, target);
		
		CreateMetaConnectionCommand c = new CreateMetaConnectionCommand(UUID.generate(), mc);
		try {
			module.sendAndCommitTransaction(c);
		} catch (IOException e) {
			view.showSendError(e);
			e.printStackTrace();
		}
		
		
	}


	@Override
	public void onMetaConnectionPropertyChanged(MetaConnectionPropertyEvent e) {
		
		try {
			if (e.getType() == ConnectionPropertyChangedType.RENAME){
				RenameMetaConnectionCommand c = new RenameMetaConnectionCommand(UUID.generate(), e.getConnection(), e.getName());
				module.sendAndCommitTransaction(c);
			}
			else
			if(e.getType() == ConnectionPropertyChangedType.MULTIPLICITY){
				ChangeMetaConnectionMultiplicityCommand c = new ChangeMetaConnectionMultiplicityCommand(UUID.generate(), e.getConnection(), e.getLowerBound(), e.getUpperBound());
				module.sendAndCommitTransaction(c);
			}
			else
			if(e.getType() == ConnectionPropertyChangedType.SOURCE_ICON ) {
				ChangeMetaConnectionIconsCommand c = new ChangeMetaConnectionIconsCommand(UUID.generate(), e.getConnection(), e.getSourceIcon(), true);
				module.sendAndCommitTransaction(c);
			}
			else
			if( e.getType() == ConnectionPropertyChangedType.TARGET_ICON ) {
				ChangeMetaConnectionIconsCommand c = new ChangeMetaConnectionIconsCommand(UUID.generate(), e.getConnection(), e.getTargetIcon(), false);
				module.sendAndCommitTransaction(c);
			}
			
			
			
		} catch (IOException ex) {
			view.showSendError(ex);
			ex.printStackTrace();
		}
		
	}
	

}
