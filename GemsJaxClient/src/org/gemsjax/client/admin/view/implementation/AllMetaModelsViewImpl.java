package org.gemsjax.client.admin.view.implementation;

import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.presenter.event.ShowMetaModelRequiredEvent;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.view.AllMetaModelsView;
import org.gemsjax.client.admin.widgets.OptionButton;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.shared.metamodel.MetaModel;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class AllMetaModelsViewImpl extends LoadingTab implements AllMetaModelsView, ClickHandler{

	private class MetaModelItem extends Label{
		private MetaModel metaModel;
		
		public MetaModelItem(MetaModel mm, ClickHandler ch){
			super(mm.getName());
			this.metaModel = mm;
			this.addClickHandler(ch);
			this.setHeight(20);
		}
		
		public MetaModel getMetaModel(){
			return metaModel;
		}
		
	}
	
	private VStack content;
	private VStack metamodelList;
	private HLayout header;
	private OptionButton createNewButton;
	private OptionButton refreshButton;
	
	private EventBus eventBus;
	
	public AllMetaModelsViewImpl(String title, UserLanguage language, EventBus eventBus) {
		super(title, language);
		
		content = new VStack();
		header = new HLayout();
		
		this.eventBus = eventBus;
		
		Title t = new Title(language.AllMetaModelsTitle());
		createNewButton = new OptionButton(language.AllMetaModelsNew());
		createNewButton.setWidth(40);
		createNewButton.setValign(VerticalAlignment.CENTER);
		t.setWidth("*");
		
		refreshButton = new OptionButton(language.AllMetaModelsRefresh());
		createNewButton.setWidth(70);
		createNewButton.setValign(VerticalAlignment.CENTER);
		
		header.addMember(t);
		header.addMember(createNewButton);
		header.addMember(refreshButton);
		header.setWidth100();
		header.setMembersMargin(10);
		header.setHeight(50);
		
		
		metamodelList = new VStack();
		metamodelList.addMember(new Label(language.AllMetaModelsEmpty()));
		metamodelList.setWidth100();
		
		content.addMember(header);
		content.addMember(metamodelList);
		
		content.setWidth100();

		this.setCanClose(true);
		this.setContent(content);
		this.showContent();
		
	}

	@Override
	public HasClickHandlers getCreateNewButton() {
		return createNewButton;
	}

	@Override
	public void showIt() {
		TabEnviroment.getInstance().addTab(this);
		TabEnviroment.getInstance().setSelectedTab(this);
		TabEnviroment.getInstance().redraw();
	}

	@Override
	public void closeIt() {
		TabEnviroment.getInstance().removeTab(this);
	}

	@Override
	public void setAllMetaModels(Set<MetaModel> metaModels) {
		
		metamodelList.removeMembers(metamodelList.getMembers());
		
		if (metaModels.isEmpty())
			metamodelList.addMember(new Label(language.AllMetaModelsEmpty()));
		else
			for (MetaModel m : metaModels)
				metamodelList.addMember(new MetaModelItem(m, this));
		
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() instanceof MetaModelItem){
			int id = ((MetaModelItem)(event.getSource())).getMetaModel().getId();
			String name = ((MetaModelItem)(event.getSource())).getMetaModel().getName();
			eventBus.fireEvent(new ShowMetaModelRequiredEvent(id, name));
		}
	}

	@Override
	public HasClickHandlers getRefreshButton() {
		return refreshButton;
	}

	@Override
	public void showUnexpectedError() {
		SC.warn("An unexcpected Error has occurred. The Tab will be closed now");
	}


}
