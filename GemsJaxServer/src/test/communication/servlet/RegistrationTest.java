package test.communication.servlet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.gemsjax.server.persistence.dao.UserDAO;
import org.gemsjax.server.persistence.dao.exception.DAOException;
import org.gemsjax.server.persistence.dao.exception.MoreThanOneExcpetion;
import org.gemsjax.server.persistence.dao.exception.NotFoundException;
import org.gemsjax.server.persistence.dao.hibernate.HibernateUserDAO;
import org.gemsjax.server.util.SHA;
import org.gemsjax.shared.ServletPaths;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import org.gemsjax.shared.user.RegisteredUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import junit.framework.*;


public class RegistrationTest {

	private static UserDAO userDAO;
	
	private static String url = "http://localhost:8081"+ServletPaths.REGISTRATION;
	
	private static String username = "UsernameSucTest1";
	private static String password = "PasswordSucTes11";
	private static String email = "SucEmail1@mail.me";
	
	
    @BeforeClass
    public static void prepare() {
    	userDAO = new HibernateUserDAO();
    }
    
    
    @AfterClass
    public static void databaeCleanUp() throws NoSuchAlgorithmException, MoreThanOneExcpetion, NotFoundException, DAOException
    {
    	RegisteredUser u = userDAO.getUserByLogin(username, SHA.generate256(password));
    	userDAO.deleteRegisteredUser(u);
    }
    
    /**
     * The Registration should be successful
     * @throws SAXException
     * @throws IOException
     */
    @Test
    public void successRegistration() throws SAXException, IOException
    {
	    
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpContext localContext = new BasicHttpContext();
	  
	    NewRegistrationMessage rm  = new NewRegistrationMessage(username, password, email);
	    
	    HttpPost httpPost = new HttpPost(url);
	    UrlEncodedFormEntity entity = setParametersOf(rm);
	    
	    httpPost.setEntity(entity);
	    
	    HttpResponse response = httpclient.execute(httpPost, localContext);
	    String responseContent = getResponseContent(response);
	    System.out.println(responseContent);
	    
	    RegistrationAnswerMessage expectedResponseMsg = new RegistrationAnswerMessage(RegistrationAnswerStatus.OK);
	    
	    assertTrue(checkStatusCode(response, 200));
	    assertEquals(expectedResponseMsg.toXml(), responseContent);
	    
	} 
    
    

    /**
     * The registration should fail, because the Username is not available
     * @throws SAXException
     * @throws IOException
     */
    @Test
    public void failDuplicateUsernameRegistration() throws SAXException, IOException
    {
    	HttpClient httpclient = new DefaultHttpClient();
 	    HttpContext localContext = new BasicHttpContext();
 	  
 	    NewRegistrationMessage rm  = new NewRegistrationMessage(username, password, email);
 	    
 	    HttpPost httpPost = new HttpPost(url);
 	    UrlEncodedFormEntity entity = setParametersOf(rm);
 	    
 	    httpPost.setEntity(entity);
 	    
 	    HttpResponse response = httpclient.execute(httpPost, localContext);
 	    String responseContent = getResponseContent(response);
 	    System.out.println(responseContent);
 	    
 	    RegistrationAnswerMessage expectedResponseMsg = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_USERNAME, username);
 	    
 	    assertTrue(checkStatusCode(response, 200));
 	    assertEquals(expectedResponseMsg.toXml(), responseContent);
	    
	}
    
    
    
    /**
     * The registration should fail, because the Email is not available
     * @throws SAXException
     * @throws IOException
     */
    @Test
    public void failDuplicateEmailRegistration() throws SAXException, IOException
    {
    	HttpClient httpclient = new DefaultHttpClient();
 	    HttpContext localContext = new BasicHttpContext();
 	  
 	    NewRegistrationMessage rm  = new NewRegistrationMessage(username+"123other", password, email);
 	    
 	    HttpPost httpPost = new HttpPost(url);
 	    UrlEncodedFormEntity entity = setParametersOf(rm);
 	    
 	    httpPost.setEntity(entity);
 	    
 	    HttpResponse response = httpclient.execute(httpPost, localContext);
 	    String responseContent = getResponseContent(response);
 	    System.out.println(responseContent);
 	    
 	    RegistrationAnswerMessage expectedResponseMsg = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_EMAIL, email);
 	    
 	    assertTrue(checkStatusCode(response, 200));
 	    assertEquals(expectedResponseMsg.toXml(), responseContent);
	    
	}
    
    
    
    /**
     * The registration should fail, because the Email is not valid
     * @throws SAXException
     * @throws IOException
     */
    @Test
    public void failInvalidEmailRegistration() throws SAXException, IOException
    {
    	
    	String invalidEmail ="Test@not";
    	
    	HttpClient httpclient = new DefaultHttpClient();
 	    HttpContext localContext = new BasicHttpContext();
 	  
 	    NewRegistrationMessage rm  = new NewRegistrationMessage(username+"123", password, invalidEmail);
 	    
 	    HttpPost httpPost = new HttpPost(url);
 	    UrlEncodedFormEntity entity = setParametersOf(rm);
 	    
 	    httpPost.setEntity(entity);
 	    
 	    HttpResponse response = httpclient.execute(httpPost, localContext);
 	    String responseContent = getResponseContent(response);
 	    System.out.println(responseContent);
 	    
 	    RegistrationAnswerMessage expectedResponseMsg = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_INVALID_EMAIL, invalidEmail);
 	    
 	    assertTrue(checkStatusCode(response, 200));
 	    assertEquals(expectedResponseMsg.toXml(), responseContent);
	    
	}
 
    
    
    
    /**
     * The registration should fail, because the username is not valid
     * @throws SAXException
     * @throws IOException
     */
    @Test
    public void failInvalidUsernameRegistration() throws SAXException, IOException
    {
    	
    	String invalidUsername ="q";
    	
    	HttpClient httpclient = new DefaultHttpClient();
 	    HttpContext localContext = new BasicHttpContext();
 	  
 	    NewRegistrationMessage rm  = new NewRegistrationMessage(invalidUsername, password, "123"+email);
 	    
 	    HttpPost httpPost = new HttpPost(url);
 	    UrlEncodedFormEntity entity = setParametersOf(rm);
 	    
 	    httpPost.setEntity(entity);
 	    
 	    HttpResponse response = httpclient.execute(httpPost, localContext);
 	    String responseContent = getResponseContent(response);
 	    System.out.println(responseContent);
 	    
 	    RegistrationAnswerMessage expectedResponseMsg = new RegistrationAnswerMessage(RegistrationAnswerStatus.FAIL_INVALID_USERNAME, invalidUsername);
 	    
 	    assertTrue(checkStatusCode(response, 200));
 	    assertEquals(expectedResponseMsg.toXml(), responseContent);
	    
	}
    
    
    
    
    private boolean checkStatusCode(HttpResponse r, int expectedCode)
    {
    	return r.getStatusLine().getStatusCode() == expectedCode;
    }
    
    
    private String getResponseContent(HttpResponse res) throws IllegalStateException, IOException
    {
    	
    	HttpEntity e = res.getEntity();
    	
    	String ret="";
    	BufferedReader reader = new BufferedReader(new InputStreamReader(e.getContent()));
      
    	String line;
    	
    	while( (line = reader.readLine())!=null)
    		ret+=line;
    	
    	return ret;
    }
    
    
    private UrlEncodedFormEntity setParametersOf(Message m) throws UnsupportedEncodingException
    {
    	List<NameValuePair> parms = new ArrayList<NameValuePair>();
    	parms.add(new BasicNameValuePair(Message.POST_PARAMETER_NAME, m.toXml()));
    	
    	
    	UrlEncodedFormEntity ret = new UrlEncodedFormEntity(parms, "UTF-8");
    	
    	return ret;
    }
}