package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.model.metamodel.MetaModel;
import org.gemsjax.client.admin.view.MetaModelView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class MetaModelPresenter extends Presenter {
	
	private MetaModel metaModel;
	private MetaModelView view;

	public MetaModelPresenter(EventBus eventBus, MetaModelView view, MetaModel metaModel) {
		super(eventBus);
		
		this.metaModel = metaModel;
		this.view = view;
		
		bind();
	}
	
	
	private void bind()
	{
		view.getAddMetaClassButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Klicked");
			}
		});
	}

}
