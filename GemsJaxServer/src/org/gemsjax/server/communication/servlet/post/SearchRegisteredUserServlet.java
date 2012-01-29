package org.gemsjax.server.communication.servlet.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gemsjax.server.communication.HttpCommunicationConnection;
import org.gemsjax.server.communication.channel.RegistrationChannel;
import org.gemsjax.server.communication.channel.SearchRegisteredUserChannel;
import org.gemsjax.server.communication.servlet.HttpPostServlet;
import org.gemsjax.shared.communication.CommunicationConnection;

public class SearchRegisteredUserServlet extends HttpPostServlet{
	
	public SearchRegisteredUserServlet()
	{
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		CommunicationConnection connection = new HttpCommunicationConnection(request, response);
		SearchRegisteredUserChannel rc = new SearchRegisteredUserChannel(connection);
		connection.connect();
	}

}
