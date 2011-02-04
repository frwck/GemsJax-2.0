package org.gemsjax.client.desktopenviroment;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.desktopenviroment.events.WindowTaskEvent;
import org.gemsjax.client.desktopenviroment.events.WindowTaskEvent.WindowTaskEventType;
import org.gemsjax.client.desktopenviroment.handler.WindowTaskEventHandler;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.MouseOutEvent;
import com.smartgwt.client.widgets.events.MouseOutHandler;
import com.smartgwt.client.widgets.events.MouseOverEvent;
import com.smartgwt.client.widgets.events.MouseOverHandler;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

/**
 * A WindowTask is a element that will be displayed in the {@link WindowTaskBar}.
 * It represents the {@link GWindow} and also interacts with the GWindow.
 * @author Hannes Dorfmann
 *
 */
public class WindowTask extends Layout // extends Canvas
{
	
	/**
	 * A BlinkTimer is a Timer that schedules the graphical change form of a {@link WindowTask}
	 * @author Hannes Dorfmann
	 *
	 */
	private class BlinkTimer extends Timer
	{
		private boolean running;
		private WindowTask task;
		private boolean inBlinkStyleNext;
		
		public BlinkTimer(WindowTask task)
		{
			super();
			this.running = false;
			this.inBlinkStyleNext = true;
			this.task = task;
		}
		
		public void run()
		{
			running = true;
			if (this.inBlinkStyleNext)
				task.setBlinkStyle();
			else
				task.setNormalStyle();
			
			this.inBlinkStyleNext=!this.inBlinkStyleNext;
			
		}
		
		public void schedule(int delayMillis)
		{
			if (!running)
				super.schedule(delayMillis);
			
		}
		
		
		public void scheduleRepeating(int periodMillis)
		{
			if (!running)
				super.scheduleRepeating(periodMillis);
		}
		
		public void cancel()
		{
			super.cancel();
			running = false;
		}
		
	}
	
	
	private static final int NORMAL_STATE = 1;
	private static final int ACTIVE_STATE = 2;
	//TODO make the width dynamic changeabel, probably via settings ... 
	private static int taskWidth = 200;
	private Label text;
	private BlinkTimer blinkTimer;
	private int currentState;
	
	
	private List<WindowTaskEventHandler> windowTaskHandlers;
	
	
	public WindowTask(String title, String iconUrl)
	{
		this(title);
		this.text.setIcon(iconUrl);
		this.text.setIconAlign("left");
	}
	
	
	public WindowTask(String title){
		super();

		this.windowTaskHandlers = new LinkedList<WindowTaskEventHandler>();
		
		this.setHeight(26);
		this.setWidth(taskWidth);
		
		
		// Text
		this.text = new Label(title);
		this.text.setHeight(26);
		this.text.setAlign(Alignment.LEFT);
		this.text.setValign(VerticalAlignment.CENTER);
		this.text.setOverflow(Overflow.HIDDEN);
		

		// Little Hack to remove the "normal" label css for font-color
		this.text.setStyleName("");
		
		this.addMember(text);
		
		// Blink Timer
		this.blinkTimer = new BlinkTimer(this);
		
		// Context menu
		this.setContextMenu(generateContextMenu());
		
		//Styling
		this.setAlign(Alignment.CENTER);
		
		this.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				onMouseOverButton();
			}
		});
		
		
		this.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				onMouseOutButton();
			}
		});
		
		this.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				onClickOnButton();
			}
		});
		
		setNormalStyle();
		
		
		
	}
	
	/**
	 * Generates the ContextMenu
	 * @return
	 */
	private Menu generateContextMenu()
	{
		Menu menu = new Menu();
		
		// TODO Language
		MenuItem closeMenuItem = new MenuItem("close","/gemsjax/sc/skins/SilverWave/images/Window/close.png");
		MenuItem maximizeMenuItem = new MenuItem("maximize","/gemsjax/sc/skins/SilverWave/images/Window/maximize.png");
		
		closeMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				onContextMenuClose();
			}
		});
		
		
		maximizeMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			
			@Override
			public void onClick(MenuItemClickEvent event) {
				onContextmenuMaximize();
			}
		});
		
		
		menu.addItem(closeMenuItem);
		menu.addItem(maximizeMenuItem);
		
		
		return menu;
	}
	
	/**
	 * Will be called, when the the user has selected close from the context menu
	 */
	private void onContextMenuClose()
	{
		fireEvent(new WindowTaskEvent(WindowTaskEventType.CLOSE, this));
	}
	
	/**
	 * Will be called, when the user has selected maximize from the context menu
	 */
	private void onContextmenuMaximize()
	{
		fireEvent(new WindowTaskEvent(WindowTaskEventType.MAXIMIZE, this));
	}
	
	/**
	 * Set the style (css) like a normal WindowTask
	 */
	private void setNormalStyle()
	{
		this.setStyleName("WindowTask");
		this.currentState = WindowTask.NORMAL_STATE;
	}
	
	/**
	 * Set the style(css) to the blink style.
	 */
	private void setBlinkStyle()
	{
		this.setStyleName("WindowTaskBlink");
	}
	
	/**
	 * Set the style(css) to the active style. That means that this WindowTask is the WindowTask, that corresponds to the actual focused Window 
	 */
	private void setActiveStyle()
	{
		this.setStyleName("WindowTaskActive");
		this.currentState = WindowTask.ACTIVE_STATE;
		
	}
	
	/**
	 * Set this task as the active one. That means that this WindowTask is the WindowTask, that corresponds to the actual focused Window.
	 * It also change the css style of this  WindowTask to active style.
	 * @see #setActiveStyle()
	 */
	public void setAsActiveTask()
	{
		setActiveStyle();
	}
	
	
	/**
	 * Set this task as a normal Task
	 */
	public void setAsNormalTask()
	{
		setNormalStyle();
	}
	
	/**
	 * Set the style (css) to the hover style. The WindowTask will be in hover style, when the mouse is over
	 */
	private void setHoverStyle()
	{
		
		this.setStyleName("WindowTaskHover");
	}
	
	
	/**
	 * Make a graphical notification by make this WindowTask blinking.
	 * This is realized by switching the css style of this WindowTask from normal stlye ({@link WindowTask#setNormalStyle()}) to blink style ({@link WindowTask#setBlinkStyle()})   
	 */
	public void startBlinking()
	{
		this.blinkTimer.scheduleRepeating(750);
	}
	
	/**
	 * Stop the "blink" notification effect.
	 * @see #startBlinking()
	 */
	public void stopBlinking()
	{
		this.blinkTimer.cancel();
		this.setNormalStyle();
	}
	
	
	/**
	 * Will be called by the {@link MouseOverHandler}
	 */
	private void onMouseOverButton()
	{
		if (currentState!=WindowTask.ACTIVE_STATE)
		setHoverStyle();
	}
	
	/**
	 * Will be called by the {@link MouseOutHandler}
	 */
	private void onMouseOutButton()
	{
		if (currentState!=WindowTask.ACTIVE_STATE)
		setNormalStyle();
	}
	
	/**
	 * Will be called by the {@link ClickHandler}
	 */
	private void onClickOnButton()
	{
		fireEvent(new WindowTaskEvent(WindowTaskEventType.FOCUS, this));
	}
	
	/**
	 * Add a {@link WindowTaskEventHandler}
	 * @param handler
	 */
	public void addWindowTaskEventHandler(WindowTaskEventHandler handler)
	{
		if(!windowTaskHandlers.contains(handler))
			windowTaskHandlers.add(handler);
	}
	
	/**
	 * Remove a {@link WindowTaskEventHandler}
	 * @param handler
	 */
	public void removeWindowTaskEventHandler(WindowTaskEventHandler handler)
	{
		windowTaskHandlers.remove(handler);
	}
	
	/**
	 * Fire a {@link WindowTaskEvent}
	 * @param event
	 */
	private void fireEvent(WindowTaskEvent event)
	{
		for (WindowTaskEventHandler h : windowTaskHandlers)
			h.onWindowTaskEvent(event);
	}
	
	
	/**
	 * Return the standard WIDTH for each task element.
	 * This method will be called from the {@link WindowBar} to calculate where to place a {@link WindowTask}
	 * @return The WIDTH in pixel for each {@link WindowTask}
	 */
	public static int getStandardTaskWidth()
	{
		return taskWidth;
	}
	
	
	/**
	 * Set the title of this WindowTask
	 */
	public void setTitle(String title)
	{
		this.text.setContents(title);
		//TODO add "..." if the title is to long
	}
	
	/**
	 * Get the title
	 */
	public String getTitle()
	{
		return this.text.getContents();
	}
	
	
	/**
	 * Get a Copy of this element
	 * @return
	 */
	public WindowTask copy()
	{
		WindowTask copy = new WindowTask(this.getTitle());
		
		for (WindowTaskEventHandler e : windowTaskHandlers)
		copy.addWindowTaskEventHandler(e);
		
		return copy;
	}
	
}