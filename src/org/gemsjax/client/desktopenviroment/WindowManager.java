package org.gemsjax.client.desktopenviroment;

import java.util.ArrayList;
import java.util.List;

import org.gemsjax.client.desktopenviroment.events.GWindowEvent;
import org.gemsjax.client.desktopenviroment.events.WindowTaskEvent;
import org.gemsjax.client.desktopenviroment.handler.GWindowEventHandler;
import org.gemsjax.client.desktopenviroment.handler.WindowTaskEventHandler;

import com.smartgwt.client.util.SC;


/**
 * The WindowManager (singleton) coordinates the {@link GWindow} and the corresponding {@link WindowTask}.
 * This will be implemented with the private inner classes {@link WindowToTaskMapper}
 * @author Hannes Dorfmann
 *
 */
public class WindowManager implements WindowTaskEventHandler, GWindowEventHandler {
	
	/**
	 * This is a little helper class, that is used by the WindowManager.
	 * It helps to coordinate and administrate a {@link GWindow} with it corresponding {@link WindowTask}
	 * @author Hannes Dorfmann
	 *
	 */
	private class WindowToTaskMapper 
	{
		/**
		 * This is a simple class, that saves the connection from {@link GWindow} to {@link WindowTask}
		 * @author Hannes Dorfmann
		 *
		 */
		private class Entry
		{
			private GWindow window;
			private WindowTask task;
			
			public Entry(GWindow window, WindowTask task)
			{
				this.window = window;
				this.task = task;
			}

			public GWindow getGWindow() {
				return window;
			}
			

			public WindowTask getWindowTask() {
				return task;
			}
		}
		
		
		
		private List<Entry> mapperList;
		
		public WindowToTaskMapper()
		{
			super();
			mapperList = new ArrayList<Entry>();
			
		}
		
		/**
		 * Get the corresponding {@link GWindow} of a {@link WindowTask}
		 * @param task
		 * @return The corresponding ellement or <code>null</code> if the {@link WindowTask} has no corresponding {@link GWindow}.
		 * In that case an internal error has occurred, because normally a {@link WindowTask} has a corresponding {@link GWindow}.
		 */
		public GWindow getCorrespondingWindow(WindowTask task)
		{
			
			
		    for (Entry e : mapperList)
		    	if (e.getWindowTask()==task)
		    		return e.getGWindow();
			
			return null;
		}
		
		/**
		 * Add a new Entry 
		 * @param window The {@link GWindow}
		 * @param task the corresponding  {@link WindowTask}
		 */
		public void addNewEntry(GWindow window, WindowTask task)
		{
			mapperList.add(new Entry(window, task));
		}
		
		/**
		 * Remove a Entry by searching for the {@link GWindow}
		 * @param window  the GWindow to search
		 */
		public void removeEntry(GWindow window)
		{
			Entry found = null;
			for (Entry e : mapperList)
				if (e.getGWindow() == window)
				{
					found = e;
					break;
				}
			
			mapperList.remove(found);
		}
		
		
		
		/**
		 * Get the corresponding {@link WindowTask} of a {@link GWindow}
		 * @param window
		 * @return The corresponding element or <code>null</code> if the {@link GWindow} has no corresponding {@link WindowTask}.
		 * In that case an internal error has occurred, because normally a {@link GWindow} has a corresponding {@link WindowTask}.
		 */
		public WindowTask getCorrespondingWindowTask( GWindow window)
		{
			for (Entry e : mapperList)
		    	if (e.getGWindow()==window)
		    		return e.getWindowTask();
			
			return null;
		}
		
		
	}
	
	
	
	private static WindowManager instance;
	private WindowToTaskMapper mapper;
	private WindowBar windowBar;
	
	private WindowManager()
	{
		mapper = new WindowToTaskMapper();
		this.windowBar = DesktopEnviromentUI.getInstance().getTopBar().getWindowBar();
		
	}
	
	
	/**
	 * Get the (singleton) instance
	 * @return
	 */
	public static WindowManager getInstance()
	{
		if (instance == null)
			instance = new WindowManager();
		
		return instance;
	}
	
	
	/**
	 * Add a {@link GWindow}
	 * @param window
	 */
	public static void addWindow(GWindow window)
	{
		
	}


	@Override
	public void onGWindowEvent(GWindowEvent event) {
		
		WindowTask task = mapper.getCorrespondingWindowTask(event.getSourceWindow());
		
		if (task== null)
		{
			SC.warn("The corresponding WindowTask could not be found");
			return;
		}
		
		// TODO add type TITLE CHANGED
		switch (event.getType())
		{
			case CLOSE: windowBar.removeWindowTask(task); break;
			case FOCUS: windowBar.setActiveWindowTask(task); break;
			case MINIMIZE: windowBar.setActiveWindowTask(null); break;
			
		}
	}


	@Override
	public void onWindowTaskEvent(WindowTaskEvent event) {
		
		GWindow window = mapper.getCorrespondingWindow(event.getWindowTaskSource());
		
		if (window== null)
		{
			SC.warn("The corresponding Window could not be found");
			return;
		}
		
		
		switch(event.getType())
		{
			case CLOSE: window.close(); break;
		}
		
		
	}
	
	

}
