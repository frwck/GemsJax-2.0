package org.gemsjax.client.admin.presenter;

import java.io.IOException;

import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent;
import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent.CriticalErrorType;
import org.gemsjax.client.admin.view.NotificationRequestShortInfoView;
import org.gemsjax.client.admin.view.NotificationRequestView;
import org.gemsjax.client.module.NotificationRequestModule;
import org.gemsjax.client.module.handler.NotificationRequestModuleHandler;
import org.gemsjax.shared.communication.message.notification.LiveNotificationMessage;
import org.gemsjax.shared.communication.message.notification.Notification;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.Request;
import org.gemsjax.shared.communication.message.request.RequestError;

import com.google.gwt.event.shared.EventBus;

public class NotificationRequestPresenter extends Presenter implements NotificationRequestModuleHandler{

	private NotificationRequestView view;
	private NotificationRequestShortInfoView shortView;
	private NotificationRequestModule module;
	
	public NotificationRequestPresenter(NotificationRequestModule module, NotificationRequestView view, NotificationRequestShortInfoView shortView, EventBus eventBus) {
		super(eventBus);
		
		this.view = view;
		this.shortView = shortView;
		this.module = module;
		bind();
	}
	
	
	
	public void start(){
		
		try {
			view.showLoading();
			module.doGetAllRequests();
			module.doGetAllNotifications();
		} catch (IOException e) {
			eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.INITAIL_NOTIFICATION_REQUEST));
		}
		
		
	}
	
	
	private void bind(){
		module.addNotificationRequestModuleHandler(this);
	}
	

	@Override
	public void onLiveNotificationReceived(LiveNotificationMessage msg) {
		shortView.showShortNotification(msg);	
	}


	@Override
	public void onLiveRequestReceived(LiveRequestMessage msg) {
		shortView.showShortRequestNotification(msg);
		
	}


	@Override
	public void onUpdated() {
		shortView.setUnreadNotificationRequest(module.getUneradUnansweredCount());
		
	}


	@Override
	public void onGetAllRequestSuccessfull() {
		
		if (module.isInitializedWithGetAll())
		{
			
			view.setAdministrateExperimentRequests(module.getExperimentRequests());
			view.setCollaborationRequests(module.getCollaborationRequests());
			view.setFriendshipRequests(module.getFriendshipRequests());
			view.setNotifications(module.getNotifications());
			view.showContent();
		}
		
	}


	@Override
	public void onGetAllRequestFailed(RequestError error) {
		
		view.showInitializeError();
		
	}


	@Override
	public void onRequestAnsweredSuccessfully(Request r) {
		
	}


	@Override
	public void onRequestAnsweredFail(Request r, RequestError error) {
		view.showRequestError(r, error);
	}


	@Override
	public void onUnexpectedError(Throwable t) {
		view.showInitializeError();
	}


	@Override
	public void onGetAllNotificationFailed(NotificationError error) {
		view.showInitializeError();
		
	}


	@Override
	public void onGetAllNotificationSuccessful() {
		if (module.isInitializedWithGetAll())
		{
			
			view.setAdministrateExperimentRequests(module.getExperimentRequests());
			view.setCollaborationRequests(module.getCollaborationRequests());
			view.setFriendshipRequests(module.getFriendshipRequests());
			view.setNotifications(module.getNotifications());
			view.showContent();
		}
	}


	@Override
	public void onNotificationDeleteError(Notification n, NotificationError error) {
		view.showNotificationError(n, error);
	}


	@Override
	public void onNotificationDeletedSuccessfully(Notification n) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNotificationMarkedAsReadError(Notification n,
			NotificationError error) {

		view.setNotificationAsRead(n, false);
		view.showNotificationError(n, error);
	}


	@Override
	public void onNotificationMarkedAsReadSuccessfully(Notification n) {
		view.setNotificationAsRead(n,true);
		
	}


	
	

}
