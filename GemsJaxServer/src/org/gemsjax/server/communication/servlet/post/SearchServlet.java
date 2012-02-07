package org.gemsjax.server.communication.servlet.post;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.gemsjax.server.communication.HttpCommunicationConnection;
import org.gemsjax.server.communication.channel.SearchChannel;
import org.gemsjax.server.communication.servlet.HttpPostServlet;
import org.gemsjax.shared.communication.CommunicationConnection;

public class SearchServlet extends HttpPostServlet{
	
	public SearchServlet()
	{
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		CommunicationConnection connection = new HttpCommunicationConnection(request, response);
		SearchChannel rc = new SearchChannel(connection, request.getSession());
		connection.connect();
	}

}
