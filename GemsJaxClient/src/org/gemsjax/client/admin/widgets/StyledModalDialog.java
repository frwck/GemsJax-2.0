package org.gemsjax.client.admin.widgets;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class StyledModalDialog extends ModalDialog {
	
	private OptionButton close;
	private OptionButton okButton;
	private OptionButton cancelButton;
	
	private VLayout contentContainer;
	private HLayout header;
	private HLayout footer;
	
	private boolean showClose=true;
	private boolean showCancelButton =true;
	private boolean showOkButton = true;
	
	private Canvas content;
	
	private Title title;
	
	public StyledModalDialog(String title){
		
		close = new OptionButton("close");
		close.setWidth(55);
		close.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				destroy();
			}
		});
		
		this.title = new Title(title);
		this.title.setWidth("*");
		
		header = new HLayout();
		header.setMembersMargin(20);
		header.addMember(this.title);
		header.addMember(close);
		header.setWidth100();
		header.setHeight(30);
		header.setAnimateMembers(true);
		
		
		
		footer = new HLayout();
		footer.setMembersMargin(20);
		footer.setWidth100();
		footer.setHeight(30);
		footer.setAnimateMembers(true);
		
		
		okButton = new OptionButton("Ok");
		okButton.setWidth(40);
		cancelButton = new OptionButton("cancel");
		cancelButton.setWidth(40);
		
		buildFooter();
		
		contentContainer = new VLayout();
		contentContainer.setWidth100();
		contentContainer.setHeight100();
		
		contentContainer.addMember(header);
		contentContainer.addMember(footer);
		
		this.addItem(contentContainer);
		
	}
	
	
	private void buildFooter(){
	
		footer.removeMembers(footer.getMembers());
		
		if (showCancelButton)
			footer.addMember(cancelButton);
		
		if(showOkButton)
			footer.addMember(okButton);
	}
	
	
	private void buildHeader(){
		
		header.removeMember(close);
		
		if(showClose)
			header.addMember(close);
	}
	
	
	public void setShowCloseButton(boolean show){
		this.showClose = show;
		buildHeader();
	}
	
	public void setShowCancelButton(boolean show){
		this.showCancelButton = show;
		buildFooter();
	}
	
	
	public void setShowOkButton(boolean show){
		this.showOkButton = show;
		buildFooter();
	}
	
	
	public void setTitle(String title){
		this.title.setContents(title);
	}
	
	
	public HasClickHandlers getCancelButton(){
		return cancelButton;
	}
	
	public HasClickHandlers getOkButton(){
		return okButton;
	}
	
	
	public void setContent(Canvas newContent){
		if (this.content!=null)
			contentContainer.removeMember(this.content);
		contentContainer.addMember(newContent, 1);
		
		this.content = newContent;
	}
	
	
	public void setFooterVisible(boolean visible){
		footer.setVisible(visible);
	}
	
}
