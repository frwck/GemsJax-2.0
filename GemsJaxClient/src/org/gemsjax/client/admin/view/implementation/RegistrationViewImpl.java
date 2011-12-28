package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.view.RegistrationView;
import org.gemsjax.shared.FieldVerifier;

import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.form.fields.events.HasClickHandlers;

/**
 * The implementation as modal window
 * @author Hannes Dorfmann
 *
 */
public class RegistrationViewImpl extends Window implements RegistrationView {

	
	private ButtonItem createButton;
	private PasswordItem password, password2;
	private TextItem username, email;
	
	private DynamicForm form;
	
	private UserLanguage language;

	
	public RegistrationViewImpl(UserLanguage language)
	{
		this.language = language;
		generateForm(language);
		
		this.setWidth(300);
		this.setHeight(200);
		
		this.setModalMaskOpacity(70);
		this.setTitle(language.RegistrationTitle());  
		this.setShowMinimizeButton(false);  
		this.setModalMaskStyle("loadingViewBackground");
		this.setIsModal(true);  
		this.setShowModalMask(true);  
		this.centerInPage(); 
	}
	
	
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
        //email.validate();
        
        MatchesFieldValidator validator = new MatchesFieldValidator();  
        validator.setOtherField("password");  
        validator.setErrorMessage(lang.RegistrationPasswordMismatch());  
          
        PasswordItem password = new PasswordItem();  
        password.setName("password");  
        password.setTitle(lang.RegistrationPassword());  
        password.setRequired(true);  
        
        PasswordItem password2 = new PasswordItem();  
        password2.setName("password2");  
        password2.setTitle(lang.RegistrationPasswordRepeated());  
        password2.setRequired(true);  
        password2.setValidators(validator);  
        
        
        createButton = new ButtonItem();  
        createButton.setName("createAccount");  
        createButton.setTitle(lang.RegistrationSubmit());  
        
        form.setFields(username, email, password, password2, createButton);  

		this.addItem(form);
		
         
    }
    
	
	
	

	@Override
	public String getEmail() {
		return email.getValueAsString();
	}

	@Override
	public String getPassword() {
		return password.getValueAsString();
	}

	@Override
	public String getPasswordRepeated() {
		return password2.getValueAsString();
	}

	

	@Override
	public String getUsername() {
		return username.getValueAsString();
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
	
	

}
