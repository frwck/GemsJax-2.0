package org.gemsjax.client.admin.presenter;

import java.io.IOException;

import org.gemsjax.client.admin.view.GlobalSearchResultView;
import org.gemsjax.client.module.GlobalSearchModule;
import org.gemsjax.client.module.handler.GlobalSearchModuleHandler;
import org.gemsjax.shared.communication.message.search.GlobalSearchResultSet;
import org.gemsjax.shared.communication.message.search.SearchError;

import com.google.gwt.event.shared.EventBus;

public class GlobalSearchPresenter extends Presenter implements GlobalSearchModuleHandler {

	private GlobalSearchResultView view;
	private GlobalSearchModule searchModule;
	
	public GlobalSearchPresenter(EventBus eventBus, GlobalSearchResultView view, GlobalSearchModule searchModule) {
		super(eventBus);
	
		this.view = view;
		this.searchModule = searchModule;
		this.searchModule.addGlobalSearchModuleHandler(this);
	}
	
	private void doSearch(String search)
	{
		try {
			view.showLoading();
			searchModule.doNewSearch(search);
		} catch (IOException e) {
			
			view.showUnexpectedErrorMessage(e);
			
		}
	}

	@Override
	public void onSearchResultReady(GlobalSearchResultSet result) {
		
		view.setExperimentResult(result.getExperimentResults());
		view.setFriendResult(result.getFriendResults());
		view.setPublicMetaModelResult(result.getMetaModelResults());
		view.setUsersMetaModelResult(result.getUsersMetaModelsResults());
		view.setPublicModelresult(result.getModelResults());
		view.setUsersModelResult(result.getUsersModelResults());
		view.setUserResult(result.getUserResults());
		
		view.showContent();
		
		
	}

	@Override
	public void onSearchResultErrorResponse(SearchError error) {
		view.showErrorMessage(error);
	}

	@Override
	public void onUnexpectedSearchError(Throwable t) {
		view.showUnexpectedErrorMessage(t);
		
	}
	
	
	public void start(String searchString)
	{
		doSearch(searchString);
	}

}
