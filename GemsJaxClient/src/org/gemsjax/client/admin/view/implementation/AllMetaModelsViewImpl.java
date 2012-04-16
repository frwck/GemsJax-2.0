package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.view.AllMetaModelsView;
import org.gemsjax.client.admin.widgets.BigMenuButton;
import org.gemsjax.client.admin.widgets.OptionButton;
import org.gemsjax.client.admin.widgets.Title;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class AllMetaModelsViewImpl extends LoadingTab implements AllMetaModelsView{

	private VStack content;
	private VStack metamodelList;
	private HLayout header;
	private OptionButton createNewButton;
	
	public AllMetaModelsViewImpl(String title, UserLanguage language) {
		super(title, language);
		
		content = new VStack();
		header = new HLayout();
		
		
		Title t = new Title(language.AllMetaModelsTitle());
		createNewButton = new OptionButton(language.AllMetaModelsNew());
		createNewButton.setWidth(40);
		createNewButton.setValign(VerticalAlignment.CENTER);
		t.setWidth("*");
		
		
		header.addMember(t);
		header.addMember(createNewButton);
		header.setWidth100();
		header.setMembersMargin(20);
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
	}

	@Override
	public void closeIt() {
		TabEnviroment.getInstance().removeTab(this);
	}

}
