package org.gemsjax.client.admin.widgets;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.Window;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * This is a class to realize a simple wizard, to do some step by step input with step by step validation.
 * For security reasons this class doesen't extends a GUI element like a {@link VStack} directly, but uses internal a VStack ({@link #wizardStack} to display the content. <br />
 * <b>So you must use the method {@link #getDisplayableContent()} to get the whole StepByStepWizard as {@link Canvas} element. </b><br />
 * That's the way how you can add a StepWizzardPanel to another Canvas via {@link Canvas#addChild(Canvas)} or layout {@link VLayout#addMember(Canvas)}.
 * <br /><br />
 * <u>Example:</u><br /><br />
 * <code>
 * StepByStepWizard stepWizard = new StepByStepWizard();<br />
 * VLayout layout = new VLayout();<br />
 *  ...<br />
 * layout.addMember(stepWizard.getDisplayableContent());<br />
 * </code> 
 * @author Hannes Dorfmann
 *
 */
public class StepByStepWizard {
	
	public interface WizardHandler{
		/**
		 * Called if the finish has been reached.
		 */
		public void onFinishReached();
	}
	
	
	/**
	 * This is a helper class, that maps a Step-Section to its children-content
	 * @author Hannes Dorfmann
	 *
	 */
	private class StepSection
	{
		private Canvas stepSection;
		private List<Canvas> contentList;
		private int currentChildIndex;
		
		public StepSection(Canvas stepSection, Canvas ...content)
		{
			this.stepSection = stepSection;
			contentList = castArrayToList(content);
			currentChildIndex = 0;
		}
		
		private List<Canvas> castArrayToList(Canvas ...canvas)
		{
			List<Canvas> cList = new LinkedList<Canvas>();
			for (Canvas c: canvas)
				cList.add(c);
			
			return cList;
		}
	}
	
	
	/**
	 * The GUI element, that displays the whole {@link StepByStepWizard}
	 */
	private VStack wizardStack;
	
	private List<StepSection> stepSectionList;
		
	private int currentStepSectionIndex;
	
	private HLayout stepSectionLayout;
	
	private int notActiveSectionStepOpacity;
	
	private Canvas currentContent;
	
	private Set<WizardHandler> handlers;
	
	public StepByStepWizard() {
	
		stepSectionList= new LinkedList<StepSection>();
		currentStepSectionIndex = 0;
		notActiveSectionStepOpacity = 30;
		handlers = new LinkedHashSet<StepByStepWizard.WizardHandler>();
		
		wizardStack= new VStack();
		stepSectionLayout = new HLayout();
		stepSectionLayout.setWidth100();
		stepSectionLayout.setHeight(30);
		
		wizardStack.addMember(stepSectionLayout);
		wizardStack.setAnimateMembers(false);
	
		
	}
	
	public void setStepSectionHeight(int height){
		stepSectionLayout.setHeight(height);
	}
	
	public void addWizardHandler(WizardHandler h){
		handlers.add(h);
	}
	
	public void removeWizardHandler(WizardHandler h){
		handlers.remove(h);
	}
	
	/**
	 * Add a Step, section
	 * @param stepSection
	 * @param contentCanvas
	 */
	public void addStepSection(Canvas stepSection, Canvas ...contentCanvas)
	{
		stepSectionList.add(new StepSection(stepSection, contentCanvas));
		stepSectionLayout.addMember(stepSection);
		
		if (stepSectionList.size()>0) 
			stepSection.setOpacity(notActiveSectionStepOpacity);
		else
		{
			// this is the first element, which is added, so start with this step-section
			setContent(contentCanvas[0]);
		}
		
		
	}
	
	
	/**
	 * Start the wizard from the first step-section , with the first children content
	 */
	public void startFromBegin()
	{
		for(int i =0; i<stepSectionList.size(); i++)
		{
			StepSection s = stepSectionList.get(i);
			
			if (i == 0) 
			{
				s.stepSection.setOpacity(100);
				setContent(s.contentList.get(0));
			}
			
			s.currentChildIndex = 0;
		}
	}
	
	
	public void next()
	{
		StepSection currentStepSection = stepSectionList.get(currentStepSectionIndex);
		
		// if the current step-section has more children, display the next children.
		if (currentStepSection.currentChildIndex<currentStepSection.contentList.size()-1)
		{
			currentStepSection.currentChildIndex++;
			setContent(currentStepSection.contentList.get(currentStepSection.currentChildIndex));
		}
		else
		{
			// the last children of the current step-section has been reached, so the next step-section should be displayed (if one more exists).
			if (currentStepSectionIndex<stepSectionList.size()-1)
			{	
				// set the opacity of the old one
				currentStepSection.stepSection.setOpacity(notActiveSectionStepOpacity);
				
				currentStepSectionIndex++;
				currentStepSection = stepSectionList.get(currentStepSectionIndex);
				// set the opacity of the (new) current step-section
				currentStepSection.stepSection.setOpacity(100);
				
				// set the content to the first child of the (new) current step-section
				setContent(currentStepSection.contentList.get(currentStepSection.currentChildIndex));
				
			}
			else
			{
			// otherwise the end has been reached
				for (WizardHandler h : handlers)
					h.onFinishReached();
			}
		}
			
			
	}
	
	public void previous()
	{
		StepSection currentStepSection = stepSectionList.get(currentStepSectionIndex);
		
		// if the current step-section has more children, display the next children.
		if (currentStepSection.currentChildIndex>0)
		{
			currentStepSection.currentChildIndex--;
			setContent(currentStepSection.contentList.get(currentStepSection.currentChildIndex));
		}
		else
		{
			// the last children of the current step-section has been reached, so the next step-section should be displayed (if one more exists).
			if (currentStepSectionIndex>0)
			{	
				// set the opacity of the old one
				currentStepSection.stepSection.setOpacity(notActiveSectionStepOpacity);
				
				currentStepSectionIndex--;
				currentStepSection = stepSectionList.get(currentStepSectionIndex);
				// set the opacity of the (new) current step-section
				currentStepSection.stepSection.setOpacity(100);
				
				// set the content to the first child of the (new) current step-section
				setContent(currentStepSection.contentList.get(currentStepSection.currentChildIndex));
				
			}
//			else
//			{
			 // First element has been reached
//			}
		}
	}
	
	private void setContent(Canvas content)
	{
		if (currentContent != null)
			wizardStack.removeMember(currentContent);
		
		currentContent = content;
		
		wizardStack.addMember(content);
	}
	
	/**
	 * Set the opacity for the not active step-sections
	 * @param opacity A integer between 0 and 100
	 */
	public void setNotActiveStepSectionOpacity(int opacity)
	{
		this.notActiveSectionStepOpacity = opacity;
		
		// set the opacity of all not active step-sections
		int i = 0;
		for (StepSection s : stepSectionList)
		{
			if (i!=currentStepSectionIndex)
				s.stepSection.setOpacity(opacity);
			
			i++;
		}
	}
	
	
	/**
	 * Set the width
	 * @param width
	 */
	public void setWidth(String width)
	{
		wizardStack.setWidth(width);
	}
	
	
	/**
	 * Set the height
	 * @param height
	 */
	public void setHeight(String height)
	{
		wizardStack.setHeight(height);
		
	}


	/**
	 * Get the displayable Canvas elemente of this class.
	 * <b>Use this method to access the GUI component of this StepByStepWizard</b>
	 * @return
	 */
	public Canvas getDisplayableContent()
	{
		return wizardStack;
		
	}
	
	
	/**
	 * Set the space (margin) between the Step-Section and the main content
	 */
	public void setMarginBetweenSectionAndContent(int margin)
	{
		wizardStack.setMembersMargin(margin);
	}
	
	/**
	 * Get the space (margin) between the Step-Section and the main content
	 * @return
	 */
	public int getMarginBetweenSectionAndContent()
	{
		return wizardStack.getMembersMargin();
	}
	
	/**
	 * Set the margin between each step-section
	 * @param margin
	 */
	public void setStepSectionMargin(int margin)
	{
		stepSectionLayout.setMargin(margin);
	}
	
	
	/**
	 * Set the margin between each step-section
	 * @param margin
	 */
	public int getStepSectionMargin()
	{
		return stepSectionLayout.getMargin();
	}
	
	
	public void setWidth100()
	{
		wizardStack.setWidth100();
	}
	
	public void setHeight100()
	{
		wizardStack.setHeight100();
	}
	
	public void setWidth(int w)
	{
		wizardStack.setWidth(w);
	}
	
	public void setHeight(int h){
		wizardStack.setHeight(h);
	}
	

}
