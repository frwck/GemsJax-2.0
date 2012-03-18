package org.gemsjax.client.admin.presenter;

import java.io.IOException;

import org.gemsjax.client.admin.presenter.event.ManageFriendshipEvent;
import org.gemsjax.client.admin.view.GlobalSearchResultView;
import org.gemsjax.client.admin.view.QuickSearchView.QuickSearchHanlder;
import org.gemsjax.client.admin.view.handlers.FriendshipHandler;
import org.gemsjax.client.admin.view.handlers.ShowCollaborationHandler;
import org.gemsjax.client.admin.view.handlers.ShowExperimentHandler;
import org.gemsjax.client.module.GlobalSearchModule;
import org.gemsjax.client.module.handler.GlobalSearchModuleHandler;
import org.gemsjax.shared.communication.message.search.GlobalSearchResultSet;
import org.gemsjax.shared.communication.message.search.SearchError;

import com.google.gwt.event.shared.EventBus;

public class GlobalSearchPresenter extends Presenter implements GlobalSearchModuleHandler, QuickSearchHanlder, FriendshipHandler, ShowCollaborationHandler, ShowExperimentHandler {

	private GlobalSearchResultView view;
	private GlobalSearchModule searchModule;
	
	public GlobalSearchPresenter(EventBus eventBus, GlobalSearchResultView view, GlobalSearchModule searchModule) {
		super(eventBus);
	
		this.view = view;
		this.searchModule = searchModule;
		this.searchModule.addGlobalSearchModuleHandler(this);
		bind();
	}
	
	private void bind()
	{
		this.view.addQuickSearchHandler(this);
		view.addFriendshipHandler(this);
		view.addShowCollaborateableHandler(this);
		view.addShowExperimentHandler(this);
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
		view.setMetaModelResult(result.getUsersMetaModelsResults(), result.getMetaModelResults());
		view.setModelResult(result.getUsersModelResults(), result.getModelResults());
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

	@Override
	public void onShowExperimentRequired(int experimentId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShowCollaborationRequired(int collaboration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnfriendRequired(int friendId) {
		eventBus.fireEvent(new ManageFriendshipEvent(ManageFriendshipEvent.ManageFriendshipType.UNFRIEND, friendId));
	}

	@Override
	public void onNewFriendshipRequired(int userId) {
		eventBus.fireEvent(new ManageFriendshipEvent(ManageFriendshipEvent.ManageFriendshipType.NEW_FRIENDSHIP, userId));
	}

	@Override
	public void onDoSearch(String searchString) {
		view.setTitle(searchString);
		doSearch(searchString);
	}

}
