package org.gemsjax.shared.communication.message.friend;

/** 
 * This kind of message extends the {@link FriendMessage} to add a simple reference id to those kind of messages,
 * that need a response/answer from the server. <br /><br />
 * 
	 * The reference id is a temporarly unique id that can be randomly choose by the client to reference 
	 * a client request and the corresponding server response.
	 * <br /><br /> 
	 * Example:<br />
	 * The Client will send a {@link NewFriendshipRequestMessage} to the server.
	 * So the client choose randomly a reference id, for example {@link #ATTRIBUTE_REFERENCE_ID}="123", and send the 
	 * {@link NewFriendshipRequestAnswerMessage} with this reference id to the server.
	 * The Server will response with a {@link NewFriendshipRequestAnswerMessage} with the same {@link #ATTRIBUTE_REFERENCE_ID}="123".
	 * That's the way, how the client can determine, which received answer is the correct answer for the sent request.
	 * 
	 * The client must take care, that the reference id is unique as long as no response is received from the client.
	 * For Example: If the client send a {@link NewFriendshipRequestMessage} with {@link #ATTRIBUTE_REFERENCE_ID}="123" to the server  and will send
	 * a second {@link NewFriendshipRequestAnswerMessage} a moment later and the server response/answer for the first sent message was not received so far,
	 * the second {@link NewFriendshipRequestMessage} <b>must</b> have another reference id like the first one ({@link #ATTRIBUTE_REFERENCE_ID}="123").
	 * 
	 * After the client have received the server response / answer for the Message with {@link #ATTRIBUTE_REFERENCE_ID}="123" the client can reuse "123" as {@link #ATTRIBUTE_REFERENCE_ID}
	 * 
	 
 * @author Hannes Dorfmann
 *
 */
public abstract  class ReferenceableFriendMessage  extends FriendMessage{

	/**
	 * The reference id is a temporarly unique id that can be randomly choose by the client to reference 
	 * a client request and the corresponding server response.
	 * <br /><br /> 
	 * Example:<br />
	 * The Client will send a {@link NewFriendshipRequestMessage} to the server.
	 * So the client choose randomly a reference id, for example {@link #ATTRIBUTE_REFERENCE_ID}="123", and send the 
	 * {@link NewFriendshipRequestAnswerMessage} with this reference id to the server.
	 * The Server will response with a {@link NewFriendshipRequestAnswerMessage} with the same {@link #ATTRIBUTE_REFERENCE_ID}="123".
	 * That's the way, how the client can determine, which received answer is the correct answer for the sent request.
	 * 
	 * The client must take care, that the reference id is unique as long as no response is received from the client.
	 * For Example: If the client send a {@link NewFriendshipRequestMessage} with {@link #ATTRIBUTE_REFERENCE_ID}="123" to the server  and will send
	 * a second {@link NewFriendshipRequestAnswerMessage} a moment later and the server response/answer for the first sent message was not received so far,
	 * the second {@link NewFriendshipRequestMessage} <b>must</b> have another reference id like the first one ({@link #ATTRIBUTE_REFERENCE_ID}="123").
	 * 
	 * After the client have received the server response / answer for the Message with {@link #ATTRIBUTE_REFERENCE_ID}="123" the client can reuse "123" as {@link #ATTRIBUTE_REFERENCE_ID}
	 * 
	 * <br /><br />You should not use special characters like " for the reference id, 
	 * since the  {@link #ATTRIBUTE_REFERENCE_ID} is sent a xml attribute and this could lead to parsing errors.
	 * 
	 */
	public static final String ATTRIBUTE_REFERENCE_ID="ref-id";
	
	private String referenceId;
	
	public ReferenceableFriendMessage(String referenceId)
	{
		this.referenceId = referenceId;
	}
	
	/**
	 * Get the {@link #referenceId}
	 * @return
	 * @see #ATTRIBUTE_REFERENCE_ID
	 */
	public String getReferenceId()
	{
		return referenceId;
	}
	
}
