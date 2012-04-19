package org.gemsjax.client.admin.view;

import java.util.Set;

import org.gemsjax.shared.metamodel.MetaModel;

import com.smartgwt.client.widgets.events.HasClickHandlers;

public interface AllMetaModelsView {
	
	public HasClickHandlers getCreateNewButton();
	public HasClickHandlers getRefreshButton();
	
	public void showIt();
	public void showLoading();
	public void showContent();
	
	public void setAllMetaModels(Set<MetaModel> metaModels);
	
	public void closeIt();
	
	public void showUnexpectedError();
	

}
