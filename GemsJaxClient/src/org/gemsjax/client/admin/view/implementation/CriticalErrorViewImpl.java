package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.view.CriticalErrorView;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.VStack;

public class CriticalErrorViewImpl extends com.smartgwt.client.widgets.Window implements CriticalErrorView {

	private Label errorLabel;
	private Label reloadButton;
	
	public CriticalErrorViewImpl()
	{
		// Label
		errorLabel = new Label("Error");
		errorLabel.setAlign(Alignment.CENTER);
		errorLabel.setStyleName("criticalErrorLabel");
		errorLabel.setContents("Error");
		
		errorLabel.setValign(VerticalAlignment.TOP);
		errorLabel.setAlign(Alignment.CENTER);
		
		reloadButton = new Label("Click here to reload site");
		reloadButton.setAlign(Alignment.CENTER);
		reloadButton.setStyleName("criticalErrorReloadLabel");
		reloadButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.reload();
			}
		});
		
		
		VStack stack = new VStack();
		stack.addMember(errorLabel);
		stack.addMember(reloadButton);
		
		stack.setAlign(Alignment.CENTER);
		stack.setMembersMargin(20);
		
		this.addItem(stack);
		
		
		this.setIsModal(true);
		this.setWidth100();
		this.setHeight100();
		this.setOpacity(100);
		this.setModalMaskOpacity(50);
		this.setShowModalMask(true);
		this.setModalMaskStyle("loadingViewBackground");
		this.setBorder("none");
		this.setBackgroundColor("black");
		this.setShowHeader(false);
		this.setShowStatusBar(false);
		this.setShowTitle(false);
		this.setShowCloseButton(false);
		this.setCanDragResize(false);
		this.setCanDragReposition(false);
		this.setEdgeSize(0);
		this.setEdgeOpacity(0);
		
	}
	
	
	@Override
	public void show() {
		bringToFront();
		super.show();
	}
	
	@Override
	public void setErrorText(String text) {
		errorLabel.setContents(text);
	}

}
