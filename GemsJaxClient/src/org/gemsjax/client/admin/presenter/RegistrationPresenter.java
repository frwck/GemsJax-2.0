package org.gemsjax.client.admin.presenter;

import org.gemsjax.client.admin.view.RegistrationView;

import com.google.gwt.event.shared.EventBus;

public class RegistrationPresenter extends Presenter {

	private RegistrationView view;
	
	
	public RegistrationPresenter(RegistrationView view, EventBus eventBus) {
		super(eventBus);
		
		this.view = view;
		bind();
	}
	
	
	private void bind()
	{
		
	}

}
