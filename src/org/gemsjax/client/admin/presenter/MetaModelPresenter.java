package org.gemsjax.client.admin.presenter;

<<<<<<< HEAD
import org.gemsjax.client.admin.model.metamodel.MetaModel;
import org.gemsjax.client.admin.view.MetaModelView;

import com.google.gwt.event.shared.EventBus;
=======
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.model.metamodel.MetaModel;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.editor.MetaModelCanvas.EditingMode;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.tab.Tab;
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248

public class MetaModelPresenter extends Presenter {
	
	private MetaModel metaModel;
	private MetaModelView view;

	public MetaModelPresenter(EventBus eventBus, MetaModelView view, MetaModel metaModel) {
		super(eventBus);
		
		this.metaModel = metaModel;
		this.view = view;
<<<<<<< HEAD
	}
=======
		
		TabEnviroment.getInstance().addTab((Tab)view);
		
		bind();
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
	
	
>>>>>>> 6e39b6f63e9e5ce0f0ec81e97e49f26d82589248

}
