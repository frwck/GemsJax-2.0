package org.gemsjax.shared.notification;

public interface QuickNotification extends Notification{
	
	public enum QuickNotificationType{
		EXPERIMENT_DELETED,
		COLLABORATEABLE_DELETED,
		FRIENDSHIP_CANCELED;
		
		public Integer toConstant()
		{
			switch(this)
			{
			case EXPERIMENT_DELETED: return 0;
			case COLLABORATEABLE_DELETED: return 1;
			case FRIENDSHIP_CANCELED: return 2;
			}
			
			return null;
		}
		
		public static QuickNotificationType fromConstant(int constant)
		{
			if (constant == 0)
				return EXPERIMENT_DELETED;
			if (constant == 1)
				return COLLABORATEABLE_DELETED;
			if (constant == 2)
				return FRIENDSHIP_CANCELED;
			
			
			return null;
		}
	}
	

	
	
	/**
	 * A optional text message. The content of this optional message depends on the type of this {@link Notification} (
	 * @return
	 */
	public String getOptionalMessage();
	public void setOptionalMessage(String message);
	
	/**
	 * Get the code number.
	 * For every code number there is a text message in the current users language available.
	 * So this code number is submitted to the client, and the client will map this code number to a text in his current language and display it on screen
	 * @return
	 */
	public QuickNotificationType getQuickNotificationType();
	public void setQuickNotificationType(QuickNotificationType type);

}
