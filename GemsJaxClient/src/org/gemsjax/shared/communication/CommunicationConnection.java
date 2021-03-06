package org.gemsjax.shared.communication;

import java.io.IOException;

import org.gemsjax.shared.communication.channel.InputChannel;
import org.gemsjax.shared.communication.channel.OutputChannel;
import org.gemsjax.shared.communication.message.Message;
import org.gemsjax.shared.communication.message.MessageType;

/**
 * Is the abstract API interface, that provides communication with the underlying network object like a socket.
 * With this additional class over the plain network communication object, the programmer gets the ability to
 * change the type of the underlying communication object at any time (for example, from WebSocket to Comet), without have to
 * change any other part in the application layer above.<br />
 * <b>Important is to understand the conecpt of sending and receiving data:</b>
 * <ul>
 * <li> <u>To send data:</u> Don't send data direct via {@link #send(String)}.<b>Use a {@link OutputChannel} and {@link Message}s</b>
 * A {@link Message} wraps the communication protocol into a object and via {@link OutputChannel#send(Message)} a {@link Message} can 
 * be send. Internally the {@link OutputChannel} will transform a {@link Message} object in a String (specified in the protocol) by calling {@link Message#toXml()}
 * and than send this generated String by calling {@link CommunicationConnection#send(String)}
 * </li>
 * 
 * <li>
 * <u>To receive data:</u> Data can only be received by registering a {@link InputChannel}. <b>Note that "data" means</b> in this case a <b>string representation of a {@link Message} specified by the communication protocol</b>
 * The incomming data-sting is forwarded to every registered {@link InputChannel}, which matches the {@link InputChannel#isMatchingFilter()}.
 * Then the {@link InputChannel} is responsible to parse the incoming data-string {@link InputChannel#onMessageReceived(String)} into a {@link Message} object.
 * </li>
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public interface CommunicationConnection {
	
	
	
	
	/**
	 * A simple listener, that listens to the {@link CommunicationConnection} and get a info (by calling {@link #onEstablished()}),
	 * when the connection is established correctly and ready to use
	 * @author Hannes Dorfmann
	 *
	 */
	public interface EstablishedListener {
		
		/**
		 * Called, if the CommunicationConnection has been established completely and is from now on ready to use
		 */
		public void onEstablished();

	}
	
	
	
	/**
	 * This is a simple Listener, that listen on a {@link CommunicationConnection} for close events.
	 * This close events are thrown by 
	 * @author Hannes Dorfmann
	 *
	 */
	public interface ClosedListener {
		
		/**
		 * Is called, if the {@link CommunicationConnection} has been closed
		 */
		public void onClose(CommunicationConnection connection);
		

	}
	
	
	/**
	 * This is a listener, that listens on sending/receiving errors events on a {@link CommunicationConnection}
	 * @author Hannes Dorfmann
	 *
	 */
	public interface ErrorListener
	{
		public void onError(Throwable t);
	}
	
	
	
	
	
	/**
	 * Closes the connection and also the underlying network object like a socket.
	 * Once a {@link CommunicationConnection} has been closed, it is not available for further networking use (i.e. can't be reconnected or rebound). 
	 * A new {@link CommunicationConnection} needs to be created.
	 * @throws IOException
	 */
	public abstract void close() throws IOException;
	
	
	public abstract boolean isClosed();
	
	
	public abstract void connect() throws IOException;
	
	/**
	 * Returns the remote port to which this socket is connected.
	 * @return the remote port number to which this socket is connected, or 0 if the socket is not connected yet.
	 */
	public abstract int getPort();
	
	/**
	 * Checks, if the browser supports the underling network object like a WebSocket
	 * @return
	 */
	public abstract boolean isSupported();
	
	/**
	 * Returns the address of the endpoint this socket is connected to, or null if it is unconnected.
	 * @return a SocketAddress reprensenting the remote endpoint of this socket, or null if it is not connected yet.
	 */
	public abstract String getRemoteAddress();
	
	
	public abstract boolean isConnected();
	
	
	public abstract void setKeepAlive(boolean  keepAlive);
	
	public abstract boolean isKeepAlive();
	
	/**
	 * Transform the {@link Message} which should be send in a adequate format and send the data
	 * via the underlying socket/connection.
	 * 
	 * @param message The message which should be send
	 * @throws IOException
	 */
	public abstract void send(Message message) throws IOException;
	
	
	
	/**
	 * Register a {@link InputChannel} to this {@link CommunicationConnection}.
	 * By registering a {@link InputChannel} to this {@link CommunicationConnection}, every incomming message
	 * (from the underlying netwórk object like a socket) is forwarded to the {@link InputChannel}, if the {@link InputChannel#isMatchingFilter()} matches
	 * to the incoming message.<br />
	 * <b>So registering a {@link InputChannel} to a {@link CommunicationConnection} is the only way to receive {@link Message}s</b>
	 * @param c
	 */
	@Deprecated
	public void registerInputChannel(InputChannel c);
	
	
	public void registerInputChannel(InputChannel c, MessageType<?> type);
	
	
	/**
	 * Deregister a {@link InputChannel}
	 * @param c
	 * @see #registerInputChannel(InputChannel)
	 */
	public void deregisterInputChannel(InputChannel c);
	
	
	/**
	 * Add a {@link ClosedListener} to deliver close events by calling {@link ClosedListener#onClose()}
	 * @param listener
	 */
	public void addCloseListener(ClosedListener listener);
	
	/**
	 * @see #addCloseListener(ClosedListener)
	 * @param listener
	 */
	public void removeCloseListener(ClosedListener listener);
	
	
	/**
	 * Add a {@link EstablishedListener} to deliver established events by calling {@link EstablishedListener#onEstablished()}
	 * @param listener
	 */
	public void addEstablishedListener(EstablishedListener listener);
	
	/**
	 * @see #addEstablishedListener(EstablishedListener)
	 * @param listener
	 */
	public void removeEstablishedListener(EstablishedListener listener);
	
	
	/**
	 * Add a {@link ErrorListener}.
	 * So every with this method registered {@link ErrorListener} will be informed by calling {@link ErrorListener#onError(Throwable)} every time a unexpected error has occurred.
	 * @param listener
	 */
	public void addErrorListener(ErrorListener listener);
	
	/**
	 * @see #addErrorListener(ErrorListener)
	 * @param listener
	 */
	public void removeErrorListener(ErrorListener listener);
	
}
