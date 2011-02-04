package org.gemsjax.client.desktopenviroment;

import java.util.LinkedList;
import java.util.List;

import org.gemsjax.client.desktopenviroment.events.GWindowEvent;
import org.gemsjax.client.desktopenviroment.handler.GWindowEventHandler;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.AnimationEffect;
import com.smartgwt.client.types.ClickMaskMode;
import com.smartgwt.client.types.HeaderControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.MinimizeClickEvent;
import com.smartgwt.client.widgets.events.MinimizeClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.layout.VStack;
/**
 * This is the basic super class for the windows.
 * It provides two basic funtions:
 * <ul>
 * <li>show a loading animation and hide the normal content</li>
 * <li>show the normal content</li>
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class GWindow extends Window{
	
	private VLayout loadingPanel;
	private Img loadingImg;
	private Canvas normalContent[];
	
	private List<GWindowEventHandler> gWindowEventHandlers;
	
	
	
	
	
	
	public GWindow(String title, int width, int height)
	{
		super();
		
		
		
		configureWindow(title, width, height);
		
		
		// Loading
		this.loadingPanel = new VLayout();
		this.loadingPanel.setWidth100();
		this.loadingPanel.setHeight100();
		this.loadingPanel.setDefaultLayoutAlign(Alignment.CENTER);
		this.loadingPanel.setDefaultLayoutAlign(VerticalAlignment.CENTER);
		
		Label spacer1 = new Label();
		Label spacer2 = new Label();
		spacer1.setWidth("*");
		spacer1.setHeight("*");
		spacer2.setWidth("*");
		spacer2.setHeight("*");
		
		
		
		this.loadingImg = new Img("/images/loading.gif");
		loadingImg.setAlign(Alignment.CENTER);
		loadingImg.setValign(VerticalAlignment.CENTER);
		loadingImg.setWidth(32);
		loadingImg.setHeight(32);
		
		loadingPanel.addMember(spacer1);
		loadingPanel.addMember(loadingImg);
		loadingPanel.addMember(spacer2);
		
		// GWindowEventHandler
		this.gWindowEventHandlers = new LinkedList<GWindowEventHandler>();
		
		
		showLoading();
		

				
	}
	
	
	
	/**
	 * Configure the window with the standard settings
	 */
	private void configureWindow(String title, int width, int height)
	{
		this.setCanDragReposition(true);  
		this.setCanDragResize(true);  
		this.setKeepInParentRect(true);
		this.setHeaderControls(HeaderControls.HEADER_ICON, HeaderControls.HEADER_LABEL, HeaderControls.MINIMIZE_BUTTON, HeaderControls.MAXIMIZE_BUTTON, HeaderControls.CLOSE_BUTTON);
	
		this.setTitle(title);
		this.setWidth(width);
		this.setHeight(height);
		this.setAutoSize(true);
		this.setShowMaximizeButton(true);
		
		
		this.addMinimizeClickHandler(new MinimizeClickHandler() {
			
			@Override
			public void onMinimizeClick(MinimizeClickEvent event) {
				minimizeClicked();
			}
		});
	}

	private void configureLoading()
	{
		 VLayout vLayout = new VLayout();
		 vLayout.setAutoHeight();
		 vLayout.setAutoWidth();
		 Img i = new Img();
		 i.setSrc("/images/loading2.gif");
		 i.setWidth(32);
		 i.setHeight(32);
		 vLayout.addMember(i);
		 vLayout.showClickMask(null, ClickMaskMode.HARD, null);
		 vLayout.setTop((com.google.gwt.user.client.Window.getClientHeight() / 2) - 32);
		 vLayout.setLeft((com.google.gwt.user.client.Window.getClientWidth() / 2) - 32);
		 Canvas c = new Canvas();
		 c.setWidth100();
		 c.setHeight100();
		 c.setBackgroundColor("#333333");
		 c.setOpacity(60);
		 c.bringToFront();
		 vLayout.bringToFront();
		 c.draw();
		 vLayout.draw();
	}
	
	private void minimizeClicked()
	{
		this.showContent();
		this.setMaximized(true);
	}
	
	
	
	/**
	 * Hide the normal content and show a loading animation (img/loading.gif)
	 */
	public void showLoading()
	{
		// Remove normal content
		this.normalContent = this.getItems();
		
		this.clear();
		
		// add loading animation
		this.addItem(this.loadingPanel);
	}
	
	/**
	 * Shows the normal Content
	 */
	public void showContent()
	{
		this.removeItem(loadingPanel);
		
		for (Canvas c : this.normalContent)
			this.addItem(c);
	}
	
	
	/**
	 * Add a {@link GWindowEventHandler}
	 * @param handler
	 */
	public void addGWindowEventHandler(GWindowEventHandler handler)
	{
		if (!this.gWindowEventHandlers.contains(handler))
			this.gWindowEventHandlers.add(handler);
	}
	
	/**
	 * Remove a {@link GWindowEventHandler}
	 * @param handler
	 */
	public void removeGWindowEventHandler(GWindowEventHandler handler)
	{
		this.gWindowEventHandlers.remove(handler);
	}

	/**
	 * Fire a {@link GWindowEvent} to its {@link GWindowEventHandler}s
	 * @param event {@link GWindowEvent}
	 */
	private void fireEvent(GWindowEvent event)
	{
		for(GWindowEventHandler h : gWindowEventHandlers)
			h.onGWindowEvent(event);
	}
	
	
	/**
	 * Close a Window with a animation
	 */
	public void close()
	{
		//TODO check if destroy() is needed
		this.animateHide(AnimationEffect.FADE);
	}

}
