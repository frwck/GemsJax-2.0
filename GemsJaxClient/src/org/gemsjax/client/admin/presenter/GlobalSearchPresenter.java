package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.view.GlobalSearchResultView;

import com.google.gwt.event.shared.EventBus;

public class GlobalSearchPresenter extends Presenter {

	private GlobalSearchResultView view;
	
	public GlobalSearchPresenter(EventBus eventBus, GlobalSearchResultView view) {
		super(eventBus);
	
		this.view = view;
	}

}
