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
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.system.NewRegistrationMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage;
import org.gemsjax.shared.communication.message.system.RegistrationAnswerMessage.RegistrationAnswerStatus;
import org.gemsjax.shared.user.RegisteredUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import junit.framework.*;


public class RegistrationTest {

	private UserDAO userDAO;
	
	private String successUsername = "UsernameTest1";
	private String successPassword = "PasswordTes11";
	
	
    @Before
    public void prepare() {
    	userDAO = new HibernateUserDAO();
    }
    
    
    @After
    public void databaeCleanUp() throws NoSuchAlgorithmException, MoreThanOneExcpetion, NotFoundException, DAOException
    {
    	RegisteredUser u = userDAO.getUserByLogin(successUsername, SHA.generate256(successPassword));
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
	    String url = "http://localhost:8080/servlets/registration";
	    
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpContext localContext = new BasicHttpContext();
	  
	    NewRegistrationMessage rm  = new NewRegistrationMessage(successUsername, successPassword, "EmailSuccessServletTest");
	    
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
    
    public void failUsernameRegistration() throws SAXException, IOException
    {
	    
	    
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
    	
    	String postPar = m.toHttpPost();
    	
    	String nameValueTuple [] = postPar.split("&");
    	
    	String data[];
    	
    	for (String tuple : nameValueTuple)
    	{
    		data = tuple.split("=");
    		// data[0] is the parameter name
    		// data[1] is the value
    		//System.out.println("Setting "+data[0]+" = "+data[1]);
    		parms.add(new BasicNameValuePair(data[0], data[1]));
    	}
    	
    	UrlEncodedFormEntity ret = new UrlEncodedFormEntity(parms, "UTF-8");
    	
    	return ret;
    }
}