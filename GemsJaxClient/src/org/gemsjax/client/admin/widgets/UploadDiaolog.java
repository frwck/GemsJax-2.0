package org.gemsjax.client.admin.widgets;

import java.util.LinkedHashSet;
import java.util.Set;

import org.gemsjax.client.admin.notification.Notification.NotificationPosition;
import org.gemsjax.client.admin.notification.NotificationManager;
import org.gemsjax.client.admin.notification.TipNotification;
import org.gemsjax.shared.ServletPaths;

import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.layout.VLayout;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;

public class UploadDiaolog extends StyledModalDialog{
	
	public interface SuccessfulHandler{
		public void onUploadSuccessful(String pathToUploadedFile);
	}

	private SingleUploader uploader;
	
	private Set<SuccessfulHandler> handlers;
	
	public UploadDiaolog(String title, SuccessfulHandler handler) {
		super(title);
		
		handlers = new LinkedHashSet<UploadDiaolog.SuccessfulHandler>();
		handlers.add(handler);
		
		uploader = new SingleUploader(FileInputType.BUTTON);
		uploader.setServletPath(ServletPaths.ICON_UPLOAD);
		uploader.setValidExtensions("jpg","png", "gif");
		uploader.setAutoSubmit(true);
		uploader.addOnStartUploadHandler(new IUploader.OnStartUploaderHandler() {
			
			@Override
			public void onStart(IUploader uploader) {
				UploadDiaolog.this.setShowCloseButton(false);
			}
		});
		
		uploader.addOnFinishUploadHandler(new IUploader.OnFinishUploaderHandler(){

			@Override
			public void onFinish(IUploader uploader) {
				
				UploadDiaolog.this.setShowCloseButton(true);
				
				if(uploader.getStatus() == Status.SUCCESS){
					UploadDiaolog.this.destroy();
					NotificationManager.getInstance().showTipNotification(new TipNotification("Upload successful", null, 2000, NotificationPosition.CENTER),AnimationEffect.FADE);
					
					String path = uploader.getServerInfo().message;
					for (SuccessfulHandler h : handlers)
						h.onUploadSuccessful(path);
					
					
					
				}
				else
				if(uploader.getStatus() == Status.CANCELED || uploader.getStatus()==Status.ERROR){
					NotificationManager.getInstance().showTipNotification(new TipNotification("Error", "An error has occrred. Please retry.", 2000, NotificationPosition.CENTER),AnimationEffect.FADE);
					
				}
			}});
		
		
		
				
		VLayout content = new VLayout();
		content.addMember(uploader);
		content.setWidth100();
		
		this.setContent(content);
		
		this.setShowCancelButton(false);
		this.setShowOkButton(false);
		this.setWidth(400);
		this.setHeight(200);
		this.centerInPage();
		
	}

	
	
	
}
