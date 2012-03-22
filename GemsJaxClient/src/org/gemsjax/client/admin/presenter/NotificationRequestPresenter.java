package org.gemsjax.client.admin.presenter;

import java.io.IOException;

import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent;
import org.gemsjax.client.admin.presenter.event.CriticalErrorEvent.CriticalErrorType;
import org.gemsjax.client.admin.presenter.event.ShowNotificationRequestCenterRequiredEvent;
import org.gemsjax.client.admin.presenter.handler.ShowNotificationRequestCenterHandler;
import org.gemsjax.client.admin.view.NotificationRequestShortInfoView;
import org.gemsjax.client.admin.view.NotificationRequestView;
import org.gemsjax.client.admin.view.NotificationRequestView.AnswerRequestHandler;
import org.gemsjax.client.admin.view.NotificationRequestView.ChangeNotificationHandler;
import org.gemsjax.client.module.NotificationRequestModule;
import org.gemsjax.client.module.handler.NotificationRequestModuleHandler;
import org.gemsjax.shared.communication.message.notification.LiveNotificationMessage;
import org.gemsjax.shared.communication.message.notification.Notification;
import org.gemsjax.shared.communication.message.notification.NotificationError;
import org.gemsjax.shared.communication.message.request.LiveRequestMessage;
import org.gemsjax.shared.communication.message.request.Request;
import org.gemsjax.shared.communication.message.request.RequestError;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

public class NotificationRequestPresenter extends Presenter implements NotificationRequestModuleHandler, AnswerRequestHandler, ChangeNotificationHandler, ShowNotificationRequestCenterHandler{

	private NotificationRequestView view;
	private NotificationRequestShortInfoView shortView;
	private NotificationRequestModule module;
	
	public NotificationRequestPresenter(NotificationRequestModule module, NotificationRequestView view, NotificationRequestShortInfoView shortView, EventBus eventBus) {
		super(eventBus);
		
		this.view = view;
		this.shortView = shortView;
		this.module = module;
		bind();
		start();
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
		eventBus.addHandler(ShowNotificationRequestCenterRequiredEvent.TYPE, this);
		module.addNotificationRequestModuleHandler(this);
		view.addAnswerRequestHandler(this);
		view.addChangeNotificationHandler(this);
		
		view.getReInitializeButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onRestartClicked();
			}
		});
	}
	
	
	private void onRestartClicked(){
		start();
	}
	

	@Override
	public void onLiveNotificationReceived(LiveNotificationMessage msg) {
		shortView.showShortNotification(msg);	
		shortView.setUnreadNotificationRequest(module.getUneradUnansweredCount());
		view.addNotification(msg.getNotification());
		
	}


	@Override
	public void onLiveRequestReceived(LiveRequestMessage msg) {
		shortView.showShortRequestNotification(msg);
		shortView.setUnreadNotificationRequest(module.getUneradUnansweredCount());
		view.addRequest(msg.getRequest());
	}


	@Override
	public void onUpdated() {
		shortView.setUnreadNotificationRequest(module.getUneradUnansweredCount());
		view.setCount(module.getUnreadNotificationCount(), module.getFriendshipRequests().size(), module.getExperimentRequests().size(), module.getCollaborationRequests().size());
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
		
		if (error == RequestError.AUTHENTICATION)
			eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.AUTHENTICATION));
		
		view.showInitializeError();
		
	}


	@Override
	public void onRequestAnsweredSuccessfully(Request r) {
		
	}


	@Override
	public void onRequestAnsweredFail(Request r, RequestError error) {
		if (error == RequestError.AUTHENTICATION)
			eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.AUTHENTICATION));
		
		view.showRequestError(r, error);
		view.addRequest(r);
	}


	@Override
	public void onUnexpectedError(Throwable t) {
		view.showInitializeError();
	}


	@Override
	public void onGetAllNotificationFailed(NotificationError error) {
		if (error == NotificationError.AUTHENTICATION)
			eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.AUTHENTICATION));

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
		if (error == NotificationError.AUTHENTICATION)
			eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.AUTHENTICATION));
		
		view.showDeleteError(n, error);
		view.addNotification(n);	// Because the Notification has been removed previosly on the gui
	}


	@Override
	public void onNotificationDeletedSuccessfully(Notification n) {
		
	}


	@Override
	public void onNotificationMarkedAsReadError(Notification n,
			NotificationError error) {
		
		if (error == NotificationError.AUTHENTICATION)
			eventBus.fireEvent(new CriticalErrorEvent(CriticalErrorType.AUTHENTICATION));

		view.setNotificationAsRead(n, false);
		view.showMarkAsReadError(n, error);
	}


	@Override
	public void onNotificationMarkedAsReadSuccessfully(Notification n) {
		view.setNotificationAsRead(n,true);
		
	}



	@Override
	public void onNotificationAsRead(Notification notification) {
		try {
			module.markNotificationAsRead(notification);
		} catch (IOException e) {
			view.showNotificationError(notification, NotificationError.DATABASE);
			view.setNotificationAsRead(notification, false);
		}
		
	}



	@Override
	public void onDeleteNotification(Notification notification) {
		try {
			module.deleteNotification(notification);
		} catch (IOException e) {
			view.addNotification(notification);
			view.showNotificationError(notification, NotificationError.PARSING);
		}
	}



	@Override
	public void onRequestAnswer(Request request, boolean accepted) {
		
		try {
			
		
			if (accepted)
			{
				module.doAcceptRequest(request);
			}
			else
			{
				module.doRejectRequest(request);
			}
		
		} catch (IOException e) {
			view.addRequest(request);
			view.showRequestError(request, RequestError.PARSING);
		}
		
	}



	@Override
	public void onShowNotificationRequestCenterRequired(ShowNotificationRequestCenterRequiredEvent event) {
		view.showIt(true);
	}
	


}
