package org.gemsjax.server.communication;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.gemsjax.shared.ServletPaths;

public class MailSender {

	private static String username;
	private static String password;
	
	
	
	public static void send( String recipient,  String subject, String message, String from )
	throws MessagingException
	{
	
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.uibk.ac.at");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,password);
				}
			});
		
		Message msg = new MimeMessage( session );
		InternetAddress addressFrom = new InternetAddress( from );
		msg.setFrom( addressFrom );
		InternetAddress addressTo = new InternetAddress( recipient );
		msg.setRecipient( Message.RecipientType.TO, addressTo );
		msg.setSubject( subject );
		msg.setContent( message, "text/html" );
		Transport.send( msg );
	}


	public static void setUsername(String username) {
		MailSender.username = username;
	}


	public static void setPassword(String password) {
		MailSender.password = password;
	}
	
	
	
	public static void sendExperimentInvitation(String receiver, String verificationCode) throws MessagingException{
		String url = ServletPaths.SERVER_URL+ServletPaths.EXPERIMENT+"/"+verificationCode;
		String link ="<a href=\""+url+"\">" +url+ "</a>";
		MailSender.send(receiver, "Experiment Invitation", "Hello,<br /> you have been invited to participate on a GemsJax modelling experiment. <br /><br />To participate click on this link: <br />"+link, "noreply@GemsJax.org");
	}
	
}
