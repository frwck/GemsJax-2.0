package org.gemsjax.client.experiment.view.impl;


import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.experiment.view.ExperimentLoginView;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;


public class ExperimentLoginViewImpl extends VLayout implements ExperimentLoginView{
	
	private TextItem passwordField;
	private IButton loginButton;
	private Label welcomeLabel;

	private UserLanguage language;
	
	public ExperimentLoginViewImpl(UserLanguage Language)
	{
		super();
		this.language = Language;
				
		this.setWidth100();
		this.setHeight100();
		this.setMembersMargin(0);
		
		
		VStack topSpacer = new VStack();
		VStack bottomSpacer = new VStack();
		VStack eastSpacer = new VStack();
		VStack westSpacer = new VStack();
	
		
		topSpacer.setHeight("*");
		bottomSpacer.setHeight("*");
		eastSpacer.setWidth("*");
		eastSpacer.setHeight("*");
		westSpacer.setWidth("*");
		westSpacer.setHeight("*");
		
		
		HLayout middleLayout = new HLayout();
		middleLayout.setWidth100();
		
		middleLayout.addMember(eastSpacer);
		middleLayout.addMember(createLoginForm(Language));
		middleLayout.addMember(westSpacer);
		
		
		this.addMember(topSpacer);
		this.addMember(middleLayout);
		this.addMember(bottomSpacer);
	
	}
	
	
	
	private VStack createLoginForm(UserLanguage Language)
	{
		// Logo
		Img logo = new Img("/images/logo_dark_200.png");
		logo.setWidth(200);
		logo.setHeight(40);
		logo.setAlign(Alignment.CENTER);
		
		// Welcome label
		welcomeLabel = new Label(Language.LoginTitle());
		welcomeLabel.setAlign(Alignment.CENTER);
		welcomeLabel.setValign(VerticalAlignment.CENTER);
		welcomeLabel.setStyleName("loginWelcomeLabel");
		
		
		String txt = "To participate in the experiment enter the password, that you have specified on the first time you clicked on the experiment invitations e-mail link";
		Label descriptionLabel = new Label(txt);
		descriptionLabel.setWrap(true);

		
		// username and password
		DynamicForm form = new DynamicForm();
		passwordField = new PasswordItem();
		passwordField.setTitle(Language.Password());
		
		passwordField.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter"))
					loginButton.fireEvent(new ClickEvent(loginButton.getJsObj()));
			}
		});
		
		
		form.setFields(passwordField);
		form.setAlign(Alignment.CENTER);
		
		form.draw();
		
		// Login Button
		loginButton = new IButton(Language.Login());
		loginButton.setAlign(Alignment.CENTER);
		loginButton.setWidth100();
	
		
		/*
		// Forgot password
		forgotPasswordLabel = new Label(Language.ForgetPassword());
		forgotPasswordLabel.setStyleName("loginLinkLabel");
		*/
		
		
		VStack layoutStack = new VStack();
		layoutStack.setMembersMargin(0);
		
		layoutStack.setWidth(200);
		layoutStack.setMembersMargin(5);
		
		
		
		// Put them all together
		layoutStack.addMember(logo);
		layoutStack.addMember(welcomeLabel);
		layoutStack.addMember(descriptionLabel);
		layoutStack.addMember(form);
		layoutStack.addMember(loginButton);
		
		layoutStack.setAlign(Alignment.CENTER);
		layoutStack.setAlign(VerticalAlignment.CENTER);
		
		
		
		return layoutStack;
		
	}



	



	@Override
	public String getPassword() {
		return (String) passwordField.getValue();
	}



	



	@Override
	public Widget asWidget() {
		return this;
	}



	@Override
	public void bringToFront() {
		// TODO is it ok to clear the whole rootpanel
		//RootPanel.get().clear();
		//RootPanel.get().add(this);
		super.bringToFront();
		this.show();
		
		
	}





	@Override
	public void showPasswordIncorrectMessage() {
		SC.warn("Wrong password");
	}



	@Override
	public void showError(Throwable t) {
		SC.warn("An unexpected error has occurred");
		Window.Location.reload();
	}



	@Override
	public HasClickHandlers getLoginButton() {
		return loginButton;
	}
	

}
