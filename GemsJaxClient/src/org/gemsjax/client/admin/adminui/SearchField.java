package org.gemsjax.client.admin.adminui;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.admin.view.QuickSearchView.QuickSearchHanlder;

import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;

public class SearchField extends TextItem
{
	private Set<QuickSearchHanlder> handlers;
	private boolean resetAfterFireSearch;
	
	
	public SearchField()
	{
		super();
		handlers = new LinkedHashSet<QuickSearchHanlder>();
		this.setWidth( 200 );
		final PickerIcon searchIcon = new PickerIcon( PickerIcon.SEARCH );
		this.setIcons(searchIcon);
		this.setTitle("");
		this.setShowTitle(false);
		this.setTitleStyle("header-searchfield-title");
		this.resetAfterFireSearch = true;
		searchIcon.addFormItemClickHandler(new FormItemClickHandler() {
			
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				fireSearch();
			}
		});
		
		this.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter"))
					fireSearch();
			}
		});
		
	}
	
	private void fireSearch()
	{
		for (QuickSearchHanlder h : handlers)
		{
			h.onDoSearch(this.getValueAsString());
		}
		

		if (resetAfterFireSearch)
			this.setValue("");
	}
	
	
	public void setResetAfterFireSearch(boolean reset)
	{
		this.resetAfterFireSearch = reset;
	}
	
	public Set<QuickSearchHanlder> getQuickSearchHandlers()
	{
		return handlers;
	}
}