package org.gemsjax.client.admin;

import org.gemsjax.shared.FieldVerifier;

public interface UserLanguage extends com.google.gwt.i18n.client.Constants{
	
	  // Login
	  @DefaultStringValue("Login")
	  String Login();
	  
	  @DefaultStringValue("Logout")
	  String Logout(); 
	  
	  @DefaultStringValue("Username")
	  String Username();  
	  
	  @DefaultStringValue("Password")
	  String Password();
	  
	  
	  @DefaultStringValue("Sign In")
	  String LoginTitle();  
	  
	  @DefaultStringValue("Forget password?")
	  String ForgetPassword();

	  @DefaultStringValue("New User")
	  String NewUser();  
	  
	  /**
	   * If you try to login, but the password field was empty
	   * @return
	   */
	  @DefaultStringValue("The password field can not be empty. You must enter a password.")
	  String PasswordIsEmptyMessage();
	  
	  /**
	   * If you try to login, but the username field is not valid 
	   * @see FieldVerifier#isValidUsername(String)
	   * @return
	   */
	  @DefaultStringValue("The Input is not a valid username.")
	  String IsNotValidUsernameMessage();
	  
	  // registered user menu
	  @DefaultStringValue("settings")
	  String SettingsMenuItem();

	  @DefaultStringValue("experiments")
	  String ExperimentsMenuItem();    
	  
	  @DefaultStringValue("notifications")
	  String NotificationsMenuItem();
	  
	  @DefaultStringValue("meta-models")
	  String MetaModelsMenuItem();
	  
	  
	  
	  // Experiments

	  @DefaultStringValue("Create Experiment")
	  String CreateExperiment();
	  
	  @DefaultStringValue("Description")
	  String ExperimenteDescritption();  
	  
}
