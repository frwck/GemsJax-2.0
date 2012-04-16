package org.gemsjax.shared;

/**
 * <p>
 * FieldVerifier validates that the name the user enters is valid.
 * </p>
 * <p>
 * This class is in the <code>shared</code> packing because we use it in both
 * the client code and on the server. On the client, we verify that the name is
 * valid before sending an RPC request so the user doesn't have to wait for a
 * network round trip to get feedback. On the server, we verify that the name is
 * correct to ensure that the input is correct regardless of where the RPC
 * originates.
 * </p>
 * <p>
 * When creating a class that is used on both the client and the server, be sure
 * that all code is translatable and does not use native JavaScript. Code that
 * is note translatable (such as code that interacts with a database or the file
 * system) cannot be compiled into client side JavaScript. Code that uses native
 * JavaScript (such as Widgets) cannot be run on the server.
 * </p>
 */
public class FieldVerifier {

	/**
	 * The regular expression to check a email address
	 */
	public static final String EMAIL_REGEX = "^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$"; //  "|!|#|\\$|%|&|'|*|\\+|-|/|=|\\?|\\^|_|`|{|\\||}|~"
	
	/**
	 * The regular expression to check usernames
	 */
	public static final String USERNAME_REGEX = "\\w{3,}";
	
	/**
	 * Verifies that the specified name is valid for our service.
	 * 
	 * We require that the name is at least 3
	 * characters and matches the regular expression [a-zA-Z_0-9]+ 
	 * @param name the name to validate
	 * @return true if valid, false if invalid
	 */
	public static boolean isValidUsername(String name) {
		if (name == null || name.equals("")) {
			return false;
		}
		
	
		if (name.length() < 3)
			return  false;
	
		if (!name.matches(USERNAME_REGEX))
			return false;
		
		return true;
	}
	
	
	/**
	 * Checks, if a email address is valid
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email)
	{
		
		if (isEmpty(email))
			return false;
		
		return email.matches(EMAIL_REGEX);
	}
	
	
	/**
	 * Check if a String is not Empty (not null and not the empty String "")
	 * @param toCheck
	 * @return
	 */
	public static boolean isNotEmpty(String toCheck)
	{
		return toCheck!=null && !toCheck.equals("");
	}
	
	/**
	 * Check if a String is not Empty (not null and not the empty String "")
	 * @param toCheck
	 * @return
	 */
	public static boolean isEmpty(String toCheck)
	{
		return toCheck==null || toCheck.equals("");
	}
	
	
	/**
	 * Checks, if the passed string, is a valid name for collaborateable files
	 * @param toCheck
	 * @return
	 */
	public static boolean isValidCollaborateableName(String toCheck){
		
		return true;
	}
	
}
