package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.view.LoginView;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class LoginViewImpl extends VLayout implements LoginView{
	
	private TextItem usernameField;
	private TextItem passwordField;
	private IButton loginButton;
	private Label welcomeLabel;
	private Label forgotPasswordLabel;
	private Label newRegistrationLabel;

	public LoginViewImpl(UserLanguage Language)
	{
		super();
		
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
		middleLayout.addMember(createLoginForm());
		middleLayout.addMember(westSpacer);
		
		
		this.addMember(topSpacer);
		this.addMember(middleLayout);
		this.addMember(bottomSpacer);
	}
	
	
	
	private VStack createLoginForm()
	{
		// Logo
		Img logo = new Img("/images/logo_dark_200.png");
		logo.setWidth(200);
		logo.setHeight(40);
		logo.setAlign(Alignment.CENTER);
		
		// Welcome label
		welcomeLabel = new Label("Sign in");
		welcomeLabel.setAlign(Alignment.CENTER);
		welcomeLabel.setValign(VerticalAlignment.CENTER);
		welcomeLabel.setStyleName("loginWelcomeLabel");

		
		// username and password
		DynamicForm form = new DynamicForm();
		usernameField = new TextItem();
		usernameField.setTitle("Username");
		passwordField = new PasswordItem();
		passwordField.setTitle("Password");
		form.setFields(new FormItem[]{usernameField, passwordField});
		form.setAlign(Alignment.CENTER);
		
		form.draw();
		
		// Login Button
		loginButton = new IButton("Login");
		loginButton.setAlign(Alignment.CENTER);
		loginButton.setWidth100();
	
		
		
		// Forgot password
		forgotPasswordLabel = new Label();
		//forgotPasswordLabel.setStyleName("loginLinkLabel");
		forgotPasswordLabel.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				forgotPasswordLabel.setStyleName("loginLinkLabelHover");
			}
		});
		
		forgotPasswordLabel.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				forgotPasswordLabel.setStyleName("loginLinkLabel");
			}
		});
		
		
		
		
		// new Registration
		newRegistrationLabel = new Label();
		//newRegistrationLabel.setStyleName("loginLinkLabel");
		newRegistrationLabel.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				forgotPasswordLabel.setStyleName("loginLinkLabelHover");
			}
		});
		
		newRegistrationLabel.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				forgotPasswordLabel.setStyleName("loginLinkLabel");
			}
		});
		
		
		HStack bottomLabels = new HStack();
		bottomLabels.setMembersMargin(50);
		bottomLabels.addMember(forgotPasswordLabel);
		bottomLabels.addMember(newRegistrationLabel);
	
		
		VStack layoutStack = new VStack();
		layoutStack.setMembersMargin(0);
		
		layoutStack.setWidth(200);
		layoutStack.setMembersMargin(5);
		
		
		
		// Put them all together
		layoutStack.addMember(logo);
		layoutStack.addMember(welcomeLabel);
		layoutStack.addMember(form);
		layoutStack.addMember(loginButton);
		layoutStack.addMember(bottomLabels);
		
		layoutStack.setAlign(Alignment.CENTER);
		layoutStack.setAlign(VerticalAlignment.CENTER);
		
		
		
		return layoutStack;
		
	}



	@Override
	public HasClickHandlers getLoginButton() {

		return loginButton;
	}



	@Override
	public HasClickHandlers getNewRegistrationButton() {
		return newRegistrationLabel;
	}



	@Override
	public HasClickHandlers getForgotPasswordButton() {
		return forgotPasswordLabel;
	}



	@Override
	public String getUsername() {
		
		return (String) usernameField.getValue();
	}



	@Override
	public String getPassword() {
		return (String) passwordField.getValue();
	}



	@Override
	public void setUsername(String username) {
		usernameField.setValue(username);
	}



	@Override
	public Widget asWidget() {
		return this;
	}



	@Override
	public void bringToFront() {
		// TODO is it ok to clear the whole rootpanel
		RootPanel.get().clear();
		RootPanel.get().add(this);
		
		
	}



	@Override
	public void hide() {
		RootPanel.get().remove(this);
		
	}



	

}
