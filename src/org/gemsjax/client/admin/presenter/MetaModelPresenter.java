package org.gemsjax.client.admin.presenter;


import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.exception.DoubleLimitException;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.canvas.MetaConnectionDrawable;
import org.gemsjax.client.canvas.MetaClassDrawable;
import org.gemsjax.client.canvas.MetaModelCanvas.EditingMode;
import org.gemsjax.shared.metamodel.MetaClass;
import org.gemsjax.shared.metamodel.MetaConnection;
import org.gemsjax.shared.metamodel.MetaModel;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.tab.Tab;


public class MetaModelPresenter extends Presenter {
	
	private MetaModel metaModel;
	private MetaModelView view;

	public MetaModelPresenter(EventBus eventBus, MetaModelView view, MetaModel metaModel) {
		super(eventBus);
		
		this.metaModel = metaModel;
		this.view = view;
		
		TabEnviroment.getInstance().addTab((Tab)view);
		
		bind();
		generateDrawables();
	}
	
	
	private void bind()
	{
		view.getNewInheritanceButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.setCanvasEditingMode(EditingMode.CREATE_INHERITANCE);
			}
		});
		
		view.getNewMetaClassButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.setCanvasEditingMode(EditingMode.CREATE_CLASS);
			}
		});
		
		view.getNewRelationshipButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.setCanvasEditingMode(EditingMode.CREATE_RELATION);
			}
		});
		
		view.getUseMouseButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				view.setCanvasEditingMode(EditingMode.NORMAL);
			}
		});
	}
	
	
	
	private void generateDrawables()
	{
		
		try {
			
			// First add all MetaClasses
			for (MetaClass c: metaModel.getMetaClasses())
			{
				view.addDrawable(new MetaClassDrawable(c));	
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
	
	

}
