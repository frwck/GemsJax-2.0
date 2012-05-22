package org.gemsjax.client.admin.view.implementation;

import java.util.Set;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.adminui.TabEnviroment;
import org.gemsjax.client.admin.presenter.event.ShowExperimentRequiredEvent;
import org.gemsjax.client.admin.tabs.LoadingTab;
import org.gemsjax.client.admin.view.AllExperimentsView;
import org.gemsjax.client.admin.widgets.Hyperlink;
import org.gemsjax.client.admin.widgets.OptionButton;
import org.gemsjax.client.admin.widgets.Title;
import org.gemsjax.shared.communication.message.experiment.ExperimentDTO;

import com.google.gwt.event.shared.EventBus;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.HasClickHandlers;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class AllExperimentsViewImpl extends LoadingTab implements AllExperimentsView, ClickHandler{

	private class ExperimentItem extends Hyperlink{
		private ExperimentDTO experiment;
		
		public ExperimentItem(ExperimentDTO ex, ClickHandler ch){
			super(ex.getName());
			this.experiment = ex;
			this.addClickHandler(ch);
		}
		
		public ExperimentDTO getExperiment(){
			return experiment;
		}
		
	}
	
	private VStack content;
	private VStack experimentList;
	private HLayout header;
	private OptionButton createNewButton;
	private OptionButton refreshButton;
	
	private EventBus eventBus;
	
	public AllExperimentsViewImpl(String title, UserLanguage language, EventBus eventBus) {
		super(title, language);
		
		content = new VStack();
		header = new HLayout();
		
		this.eventBus = eventBus;
		
		Title t = new Title("Experiments");
		createNewButton = new OptionButton("new");
		createNewButton.setWidth(40);
		createNewButton.setValign(VerticalAlignment.CENTER);
		t.setWidth("*");
		
		refreshButton = new OptionButton("refresh");
		createNewButton.setWidth(70);
		createNewButton.setValign(VerticalAlignment.CENTER);
		
		header.addMember(t);
		header.addMember(createNewButton);
		header.addMember(refreshButton);
		header.setWidth100();
		header.setMembersMargin(10);
		header.setHeight(50);
		
		
		experimentList = new VStack();
		experimentList.addMember(new Label("No experiment"));
		experimentList.setWidth100();
		
		content.addMember(header);
		content.addMember(experimentList);
		
		content.setWidth100();

		this.setCanClose(true);
		this.setContent(content);
		this.showLoading();
		

		
	}

	@Override
	public HasClickHandlers getCreateNewButton() {
		return createNewButton;
	}

	@Override
	public void showIt() {

		TabEnviroment.getInstance().addTab(this);
	}

	@Override
	public void closeIt() {
		TabEnviroment.getInstance().removeTab(this);
	}

	@Override
	public void setAllExperiments(Set<ExperimentDTO> experiments) {
		
		experimentList.removeMembers(experimentList.getMembers());
		
		if (experiments.isEmpty())
			experimentList.addMember(new Label("No experiment found"));
		else
			for (ExperimentDTO m : experiments)
				experimentList.addMember(new ExperimentItem(m, this));
		
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource() instanceof ExperimentItem){
			int id = ((ExperimentItem)(event.getSource())).getExperiment().getId();
			String name = ((ExperimentItem)(event.getSource())).getExperiment().getName();
			eventBus.fireEvent(new ShowExperimentRequiredEvent(id, name));
		}
	}

	@Override
	public HasClickHandlers getRefreshButton() {
		return refreshButton;
	}

	@Override
	public void showUnexpectedError() {
		SC.warn("An unexcpected Error has occurred. The Tab will be closed now");
	}


}
