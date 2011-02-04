package org.gemsjax.client.desktopenviroment;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

import org.gemsjax.client.desktopenviroment.DropDownMenuButton.DropDownMenu;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ResizedEvent;
import com.smartgwt.client.widgets.events.ResizedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;

/**
 * A WindowBar is a GUI Component, that displays open windows.
 * The last Windows in use will be displayed in the {@link WindowTaskBar}.
 * A complete List of all current open windows can be displayed by clicking on the {@link WindowBar#allWindowTaskButton}.
 * @author Hannes Dorfmann
 */

public class WindowBar extends HLayout {

	
	
	
	
	private class OtherWindowTaskManager
	{
		private int marginLeftRight = 2;
		private HStack staticRow;
		
		private VStack layoutStack;
		
		
		public OtherWindowTaskManager() {
		
			layoutStack = new VStack();
			layoutStack.setWidth100();
			layoutStack.setMembersMargin(2);
			layoutStack.setMargin(2);
			adjustHeight();
			
		}
		
		/**
		 * Redraw all {@link WindowTask}s
		 */
		public void redrawAll()
		{
			HStack rows[] =(HStack[]) layoutStack.getMembers();
			for (HStack row : rows)
			{
				for (Canvas c: row.getMembers())
				{
					c.redraw();
				}
				
				row.redraw();
			}
			
			layoutStack.redraw();
		}
		
		
		/**
		 * Calculates how many {@link WindowTask} can be placed in one row.
		 * @return
		 */
		private int calculateWindowTaskElementsPerRow()
		{
			return layoutStack.getWidth() / (WindowTask.getStandardTaskWidth()+marginLeftRight);
			
		}
		
		public Canvas getCanvas()
		{
			return layoutStack;
		}
		
		public void onWindowResize()
		{
			
		}
		
		/**
		 * Set the height according the number of rows * 30px
		 */
		public void adjustHeight()
		{
			layoutStack.setHeight(this.getRowCount()*30);
		}
		
		/**
		 * Get the number of rows
		 * @return
		 */
		private int getRowCount()
		{
			return layoutStack.getMembers().length;
		}
		
		/**
		 * Get the last row. A row is a {@link HStack}.
		 * If there is no row, create a new Row and return the new created row, which is the first and the last
		 * @return
		 */
		private HStack getLastRow()
		{
			Canvas members[] = layoutStack.getMembers();
			
			if (getRowCount()==0)
			{
				HStack newRow = createNewRow();
				layoutStack.addMember(newRow);
				
				return newRow;
			}
			
			
			return (HStack) members[members.length-1];
		}
		
		
		/**
		 * Add a {@link WindowTask}. It creates a new row, if there is not enough space in the existing rows.
		 * 
		 * @param task
		 */
		public void addWindowTask(WindowTask task)
		{
			
			
			HStack row = getLastRow();
			
			Window.alert("Task "+row+ " "+row.getID());
			row.addMember(new Label("Test"));
			//row.addMember(new WindowTask("Neuer"));
			
			
			
			
			
			
			/*
			
			// If there already is no row, create one and add the task
			if (this.getRowCount()==0)
			{
				HStack newRow = createNewRow();
				newRow.addMember(task);
				this.addMember(newRow);
			}
			else
			{
				// Get the last Row.
				// Assumption: all Rows are full, there is no space for this task, except the last row
				
				HStack lastRow = getLastRow();
				
				int currentTaskInLastRow = lastRow.getMembers().length;
				
				// check if there is Space for another Task
				if (currentTaskInLastRow<calculateWindowTaskElementsPerRow())
				{
					// There is enough Space, simply add the task in the last row
					lastRow.addMember(task);
					
				}
				else
				{
					// The last row is full, so create a new Row and add the Task in this new created row
					HStack newRow = createNewRow();
					newRow.addMember(task);
					this.addMember(newRow);
				}
				
			}
			
			
			adjustHeight();
			redrawAll();
			
			*/
			
		}
		
		
		private HStack createNewRow()
		{
			HStack newRow = new HStack();
			newRow.setMembersMargin(marginLeftRight);
			
			
			return newRow;
		}
		
	}
	
	
	/**
	 * The {@link WindowTaskBar} is just a simple graphical component (no logic) that displays the last {@link GWindow}s that are used by the user and are shown in the TopBar
	 * @author Hannes Dorfmann
	 *
	 */
	private class WindowTaskBar extends HStack
	{
		public WindowTaskBar()
		{
			super();
			this.setLayoutRightMargin(2);
			this.setLayoutLeftMargin(2);
			this.setWidth("*");
			this.setAnimateMembers(true);
			
			
			this.addResizedHandler(new ResizedHandler() {
				
				@Override
				public void onResized(ResizedEvent event) {
					onWindowTaskBarHasResized();
					allOtherWindowTaskPanel.onWindowResize();
				}
			});
		}
		
		
	}
	
	/**
	 * This enum are used by
	 * @author Hannes Dorfmann
	 *
	 */
	private enum BeforeRenderOperation
	{
		
		ADD,
		NEW_ACTIVE,
		REMOVE,
		RESIZE
	}
	
	

	private DropDownMenuButton allWindowTaskButton;
	private WindowTaskBar windowTaskBar;
	private WindowTask activeWindowTask;
	/**
	 * This List contains all {@link WindowTask}s. This list is also in the right order, in there they are displayed by the {@link WindowTaskBar} and {@link #allOtherWindowTaskPanel}
	 */
	private LinkedList<WindowTask> windowTaskList;
	
	/**
	 * This Panel displayes all other {@link WindowTask}s, that haven't place in the {@link WindowTaskBar}
	 */
	private OtherWindowTaskManager allOtherWindowTaskPanel;
	
	
	
	
	public WindowBar()
	{
		super();
		
		this.windowTaskList = new LinkedList<WindowTask>();
		
		// styling
		this.setWidth100();
		this.setHeight(30);
		this.setMembersMargin(3);
		this.setLayoutLeftMargin(5);
		
		// Window TaskBar for last used
		this.windowTaskBar = new WindowTaskBar();

		// all WindowTaskMenuButton
		generateAllItemsMenu();
		
		// this.addMember(scrollableWindowTaskBar);
		this.addMember(windowTaskBar);
		this.addMember(allWindowTaskButton);
		
		//TODO Debug console
		SC.showConsole();
		
	}
	
	
	private void generateAllItemsMenu()
	{
		Img icon = new Img("/images/icons/windows.png");
		icon.setAlign(Alignment.CENTER);  
		icon.setLayoutAlign(VerticalAlignment.CENTER);
		icon.setWidth(18);
		icon.setHeight(18);
		
	    
		this.allWindowTaskButton = new DropDownMenuButton(icon);
		this.allWindowTaskButton.setWidth(28);
		this.allWindowTaskButton.setHeight(28);
		this.allWindowTaskButton.setHoverClass("allWindowTasksHover");
		this.allWindowTaskButton.setPadding(5);
		
		DropDownMenu allWindowTasksDropDownMenu = this.allWindowTaskButton.getDropDownMenu();
		allWindowTasksDropDownMenu.setWidth100();
		allWindowTasksDropDownMenu.setPosition(0, 30);
		allWindowTasksDropDownMenu.setStyleName("dropDownMenuAllWindows");
		
		
		
		allOtherWindowTaskPanel = new OtherWindowTaskManager();
		
		
		allWindowTasksDropDownMenu.addMenuItem(allOtherWindowTaskPanel.getCanvas());
		
		
		
		
		
	}


	/**
	 * Add a windowTask in the {@link WindowTaskBar}.<br />
	 * <b>Note:</b> Every new inserted WindowTask will be also the new active one
	 * @param windowTask
	 */
	public void addWindowTask(WindowTask windowTask)
	{
		windowTaskList.add(windowTask);
		/*
		scrollableWindowTaskBar.addMember(windowTask);
		
		if (windowTaskList.size() > calculateNumberOfMaximumTaskBarElements())
		{
			int toScroll = (windowTaskList.size() - calculateNumberOfMaximumTaskBarElements()) * WindowTask.getStandardTaskWidth() * (-1);
			Window.alert("Scroll to "+toScroll);
			scrollableWindowTaskBar.scrollTo(toScroll);
		}
		*/
		
		// set as active
		if (this.activeWindowTask!=null)
			this.activeWindowTask.setAsNormalTask();
		
		this.activeWindowTask = windowTask;
		if (windowTask!=null)
			windowTask.setAsActiveTask();
		
		renderTaskBarPositions(BeforeRenderOperation.ADD, windowTask);
		
	}
	
	
	/**
	 * Set this window as the new active window. This will be done by {@link WindowTask#setAsActiveTask()}, which change the css style.
	 * The old active window will be set to display as a normal window task {@link WindowTask#setAsNormalTask()}, which change the css style.
	 * If the parameter is null, none WindowTask will be set as the active one, so all WindowTasks would be "normal"
	 * @param windowTask The new active {@link WindowTask} or <code>null</code>
	 */
	public void setActiveWindowTask(WindowTask windowTask)
	{
		if (this.activeWindowTask!=null)
			this.activeWindowTask.setAsNormalTask();
		
		this.activeWindowTask = windowTask;
		if (windowTask!=null)
			windowTask.setAsActiveTask();
		
		renderTaskBarPositions(BeforeRenderOperation.NEW_ACTIVE, windowTask);
	}
	
	
	
	/**
	 * Remove a WindowTask
	 * @param windowTask
	 */
	public void removeWindowTask(WindowTask windowTask)
	{
		windowTaskList.remove(windowTask);
		
		renderTaskBarPositions(BeforeRenderOperation.REMOVE, windowTask);
	}
	
	
	private void onWindowTaskBarHasResized()
	{
		renderTaskBarPositions(BeforeRenderOperation.RESIZE, null);
	}
	
	/**
	 * This method is very important. This will set the positions of each WindowTask.
	 * It also decides if a {@link WindowTask} is set in the {@link WindowTaskBar} or in the {@link #allOtherWindowTaskPanel}, which is displayed as a {@link DropDownMenu}
	 * 
	 * @param operation {@link BeforeRenderOperation} that indicated, what happened before this renderTaskBarPositions has been called
	 * @param task The {@link WindowTask} that has been done somthing (added ({@link #addWindowTask(WindowTask)} a new WindowTask, removed ({@link #removeWindowTask(WindowTask)}), or set as the active one ({@link #setActiveWindowTask(WindowTask)}) ). If this method has been called, because the browser window has been resized, this parameter will be <code>null</code>
	 */
	private void renderTaskBarPositions(BeforeRenderOperation operation, WindowTask task)
	{
		int maxTaskBarElements = calculateNumberOfMaximumTaskBarElements();
		
		//check how much Elements are in the WindowTaskBar
		int elementsInTaskBar = windowTaskBar.getMembers().length;
		
		
		switch (operation)
		{
			case ADD: 
				if (elementsInTaskBar < maxTaskBarElements) // if there is space for one more WindowTask, add it
				{	
					windowTaskBar.addMember(task);
					
				}
				else // There is not enough space, add this new WindowTask at first place, and put the last WindowTask (which has not enought space) in the allWindowTasksDropDownMenu
				{
					
					
					
					// search for the last element, that has no longer space and must be placed in the 
					WindowTask elementThatHasNotEnoughtPlaceInWindowTaskBar = windowTaskList.get(maxTaskBarElements-1);
					windowTaskBar.removeMember(elementThatHasNotEnoughtPlaceInWindowTaskBar);
					allOtherWindowTaskPanel.addWindowTask(elementThatHasNotEnoughtPlaceInWindowTaskBar);

					// put the new WindowTask at first place in WindowTaskList
					windowTaskList.remove(elementThatHasNotEnoughtPlaceInWindowTaskBar);
					ListIterator<WindowTask> listIterator = windowTaskList.listIterator();
					listIterator.add(task);
					
					
					//put the new WindowTask at first place in WindowTaskBar
					windowTaskBar.addMember(task,0);
				
				}
				break;
			
		} // END switch
		
		
	}
	
	/**
	 * Get the number of WindowTasks that can be placed in the {@link WindowTaskBar}
	 * @return 
	 */
	private int calculateNumberOfMaximumTaskBarElements()
	{
		
		
		return windowTaskBar.getWidth() / (WindowTask.getStandardTaskWidth()+windowTaskBar.getLayoutRightMargin()+windowTaskBar.getLayoutLeftMargin());
		// TODO  Scrollable ?  return scrollableWindowTaskBar.getWidth() / (WindowTask.getStandardTaskWidth()+scrollableWindowTaskBar.getLayoutRightMargin()+scrollableWindowTaskBar.getLayoutLeftMargin());
	}
	
	

	
	
}
