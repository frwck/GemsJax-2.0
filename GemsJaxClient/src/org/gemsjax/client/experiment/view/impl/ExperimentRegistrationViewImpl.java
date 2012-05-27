package org.gemsjax.client.experiment.view.impl;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.experiment.view.ExperimentRegistrationView;

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


public class ExperimentRegistrationViewImpl extends VLayout implements ExperimentRegistrationView{
	
	private TextItem passwordField;
	private TextItem passwordRepeatedField;
	private TextItem displayNameField;
	private IButton loginButton;
	private Label welcomeLabel;

	private UserLanguage language;
	
	public ExperimentRegistrationViewImpl(UserLanguage Language)
	{
		super();
		this.language = Language;
		
		  /* 
         this.setTitle("Login");  
         this.setShowMinimizeButton(false);
         this.setShowCloseButton(false);
         this.setShowMaximizeButton(false);
         this.setIsModal(true);  
         this.setShowModalMask(true);  
		*/
				
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
		
		String txt = "Hello, its the first time you try to participate in this experiment. " +
				"Please specify a displayed name (visible to the other pariticipants) and a password." +
				"This password is needed for a later login. Notice, that this password is associated to your received experiment invitation e-mail and can not be recovered.";
		Label descriptionLabel = new Label(txt);
		descriptionLabel.setWrap(true);

		
		// username and password
		DynamicForm form = new DynamicForm();
		
		displayNameField = new TextItem();
		displayNameField.setTitle("Displayed name");
		displayNameField.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter"))
					loginButton.fireEvent(new ClickEvent(loginButton.getJsObj()));
			}
		});
		
		
		passwordField = new PasswordItem();
		passwordField.setTitle(Language.Password());
		
		passwordField.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter"))
					loginButton.fireEvent(new ClickEvent(loginButton.getJsObj()));
			}
		});
		
		passwordRepeatedField = new PasswordItem();
		passwordRepeatedField.setTitle(Language.Password());
		
		passwordRepeatedField.addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter"))
					loginButton.fireEvent(new ClickEvent(loginButton.getJsObj()));
			}
		});
		
		
		
		
		form.setFields(displayNameField,  passwordField, passwordRepeatedField);
		form.setAlign(Alignment.CENTER);
		
		form.draw();
		
		// Login Button
		loginButton = new IButton(Language.Login());
		loginButton.setAlign(Alignment.CENTER);
		loginButton.setWidth100();
	
	
		
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
	public HasClickHandlers getSubmitButton() {

		return loginButton;
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
		
		super.bringToFront();
		this.show();
		
		
	}



	@Override
	public String getDisplayName() {
		return displayNameField.getValueAsString();
	}



	@Override
	public String getPasswordRepeated() {
		return passwordRepeatedField.getValueAsString();
	}



	@Override
	public void showPasswordMissmatch() {
		SC.warn("Password missmatch. Check yout input.");
	}



	@Override
	public void showUnexpectedError() {
		SC.warn("An unexpected Error has occrred.");
		Window.Location.reload();
	}



	@Override
	public void showDisplayedNameAlreadyUsed() {
		SC.warn("Your desired displayed name is already used by another participant. To avoid confusion this name must be unique. Please choose another name");
	}



	@Override
	public void showDisplayNameNotValid() {
		SC.warn("Displayed name is not valid.");
	}



	@Override
	public void showSuccessful() {
		SC.say("Successful");
		Window.Location.reload();
	}

}
