package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.view.RegistrationView;
import org.gemsjax.shared.FieldVerifier;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;


/**
 * The implementation as modal window
 * @author Hannes Dorfmann
 *
 */
public class RegistrationViewImpl extends Window implements RegistrationView {

	
	private Button createButton;
	private PasswordItem password, password2;
	private TextItem username, email;
	
	private DynamicForm form;
	
	private UserLanguage language;
	
	/*
	private TextBox usernameBox, emailBox;
	private PasswordTextBox passwordBox, password2Box;
	*/
	
	
	public RegistrationViewImpl(UserLanguage language)
	{
		this.language = language;
		generateForm(language);
		
		this.setWidth(300);
		this.setHeight(300);
		
		this.setModalMaskOpacity(70);
		this.setTitle(language.RegistrationTitle());  
		this.setShowTitle(false);
		this.setShowMinimizeButton(false);  
		this.setModalMaskStyle("loadingViewBackground");
		this.setIsModal(true);  
		this.setShowModalMask(true);  
		this.centerInPage(); 
	}
	
	
	/*
	private Widget genereateStack(UserLanguage lang)
	{
		usernameBox = new TextBox();
		emailBox = new TextBox();
		passwordBox = new PasswordTextBox();
		password2Box = new PasswordTextBox();
		
		
		Grid grid = new Grid(4,2);
		
		grid.add(new Label(lang.RegistrationUsername()));
		grid.add(usernameBox);
		
		grid.add(new Label(lang.RegistrationEmail()));
		grid.add(emailBox);
		
		grid.add(new Label(lang.RegistrationPassword()));
		grid.add(passwordBox);
		
		grid.add(new Label(lang.RegistrationPasswordRepeated()));
		gird
		
		return grid;
	}
	*/
	
	private void generateForm(UserLanguage lang)
	{
		
		form = new DynamicForm();  
		form.setWidth100();
		form.setHeight100();
        
        RegExpValidator usernameValidator = new RegExpValidator();
        usernameValidator.setExpression(FieldVerifier.USERNAME_REGEX);
        usernameValidator.setErrorMessage(lang.RegistrationInvalidUsername());
        
        RegExpValidator emailValidator = new RegExpValidator();
        emailValidator.setExpression(FieldVerifier.EMAIL_REGEX);
        emailValidator.setErrorMessage(lang.RegistrationInvalidEmail());
        
        username = new TextItem();  
        username.setName("username");  
        username.setTitle(lang.RegistrationUsername());  
        username.setRequired(true);  
        username.setDefaultValue(""); 
        username.setValidators(usernameValidator);
        
        
        
        email = new TextItem();  
        email.setName("email");  
        email.setTitle(lang.RegistrationEmail());  
        email.setRequired(true);  
        email.setDefaultValue("");  
        email.setValidators(emailValidator);
           
        MatchesFieldValidator validator = new MatchesFieldValidator();  
        validator.setOtherField("password");  
        validator.setErrorMessage(lang.RegistrationPasswordMismatch());  
          
        password = new PasswordItem();  
        password.setName("password");  
        password.setTitle(lang.RegistrationPassword());  
        password.setRequired(true);  
        
        password2 = new PasswordItem();  
        password2.setName("password2");  
        password2.setTitle(lang.RegistrationPasswordRepeated());  
        password2.setRequired(true);  
        password2.setValidators(validator);  
        
        
        createButton = new Button(lang.RegistrationSubmit());  
        createButton.setWidth100();
        
        form.setFields(username, email, password, password2);  
        
        
        Label titleLabel = new Label(lang.RegistrationTitle());
		titleLabel.setAlign(Alignment.CENTER);
		titleLabel.setValign(VerticalAlignment.CENTER);
		titleLabel.setStyleName("loginWelcomeLabel");
        
        this.addItem(titleLabel);
		this.addItem(form);
		this.addItem(createButton);
		   
    }
    
	
	
	

	@Override
	public String getEmail() {
		return (String)email.getValue();
	}

	@Override
	public String getPassword() {
		return (String) form.getValue("password");
	}

	@Override
	public String getPasswordRepeated() {
		return (String) form.getValue("password2");
	}

	

	@Override
	public String getUsername() {
		return (String)username.getValue();
	}

	@Override
	public void showErrorMessage(String msg) {
		SC.warn(msg);
	}


	@Override
	public HasClickHandlers getSubmitButton() {
		return createButton;
	}


	@Override
	public UserLanguage getCurrentLanguage() {
		return language;
	}


	@Override
	public boolean doGuiValidate() {
		return form.validate();
	}


	@Override
	public void clearForm() {
		username.clearValue();
		email.clearValue();
		password.clearValue();
		password2.clearValue();
	}


	@Override
	public void hideIt() {
		this.hide();
	}


	@Override
	public void showSuccessfulRegistrationMessage() {
		SC.confirm(language.RegistrationSuccessful(), new BooleanCallback() {
			
			@Override
			public void execute(Boolean value) {
				// Nothing to do
			}
		});
	}


	@Override
	public void showUnexpectedError(Throwable t) {
			SC.warn(language.RegistrationUnexpectedError()+"<br /><br />"+t.getMessage());
	}
	
	

}
