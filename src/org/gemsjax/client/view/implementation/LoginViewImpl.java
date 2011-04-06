package org.gemsjax.client.view.implementation;

import org.gemsjax.client.UserLanguage;
import org.gemsjax.client.view.LoginView;

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
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class LoginViewImpl extends Window implements LoginView{
	
	private TextItem usernameField;
	private TextItem passwordField;
	private IButton loginButton;
	private Label welcomeLabel;
	private Label forgotPasswordLabel;
	private Label newRegistrationLabel;

	public LoginViewImpl(UserLanguage Language)
	{
		super();
		
		   
         this.setTitle("Login");  
         this.setShowMinimizeButton(false);
         this.setShowCloseButton(false);
         this.setShowMaximizeButton(false);
         this.setIsModal(true);  
         this.setShowModalMask(true);  
         
		
		createLoginForm();
		
		
		
		this.setWidth(500);
		this.setHeight(400);
		this.setMembersMargin(0);
		this.centerInPage();  
	}
	
	
	
	private void createLoginForm()
	{
		
		// Logo
		Img logo = new Img("/images/logo-big.png");
		
		// Welcome label
		welcomeLabel = new Label();
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
		loginButton = new IButton();
		
		
		// Forgot password
		forgotPasswordLabel = new Label();
		forgotPasswordLabel.setStyleName("loginLinkLabel");
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
		newRegistrationLabel.setStyleName("loginLinkLabel");
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
		
		
		// Put them all together
		layoutStack.addMember(logo);
		layoutStack.addMember(welcomeLabel);
		layoutStack.addMember(form);
		layoutStack.addMember(loginButton);
		layoutStack.addMember(bottomLabels);
		
		
		this.addItem(layoutStack);
		
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



	

}
