package org.gemsjax.client.admin.presenter.event;

import org.gemsjax.client.admin.presenter.GlobalSearchPresenter;
import org.gemsjax.client.admin.presenter.handler.DoNewGlobalSearchHandler;

import com.google.gwt.event.shared.GwtEvent;

/**
 * This kind of event, is fired to start a new {@link GlobalSearchPresenter} to do a new global search
 * @author Hannes Dorfmann
 *
 */
public class DoNewGlobalSearchEvent extends GwtEvent<DoNewGlobalSearchHandler> {

	public static Type TYPE = new Type<DoNewGlobalSearchHandler>();
	
	private String searchString;
	
	public DoNewGlobalSearchEvent(String searchString)
	{
		this.searchString = searchString;
	}
	
	
	public String getSearchString()
	{
		return searchString;
	}
	
	
	@Override
	public Type<DoNewGlobalSearchHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DoNewGlobalSearchHandler handler) {
		handler.onDoNewGlobalSerch(this);
	}

}
