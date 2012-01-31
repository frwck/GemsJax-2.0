package org.gemsjax.client.admin.view;


/**
 * A {@link QuickSearchView} proviedes a simple text input field and a search Button
 * @author Hannes Dorfmann
 *
 */
public interface QuickSearchView {

	public interface QuickSearchHanlder
	{
		public void onDoSearch(String searchString);
	}
	
	
	public void addQuickSearchHandler(QuickSearchHanlder h);
	
	public void removeQuickSearchHandler(QuickSearchHanlder h);
	
}
