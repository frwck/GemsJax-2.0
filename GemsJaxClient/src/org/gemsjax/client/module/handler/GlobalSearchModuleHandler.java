package org.gemsjax.client.module.handler;

import org.gemsjax.client.module.GlobalSearchModule;
import org.gemsjax.shared.communication.message.search.GlobalSearchResultSet;
import org.gemsjax.shared.communication.message.search.SearchError;


/**
 * The handler for a {@link GlobalSearchModule}
 * @author Hannes Dorfmann
 *
 */
public interface GlobalSearchModuleHandler {
	
	/**
	 * Called, if the search query with the given criteria has returned a successful result
	 * @param event The result
	 */
	public void onSearchResultReady(GlobalSearchResultSet event);
	
	/**
	 * Called, if the search query has returned an {@link SearchError}
	 * @param error
	 */
	public void onSearchResultErrorResponse(SearchError error);
	
	/**
	 * Called, if an unexpected (low level) error has occurred, like communication broken etc.
	 * @param t
	 */
	public void onUnexpectedSearchError(Throwable t);

}
