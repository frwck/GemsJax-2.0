package org.gemsjax.client.admin.widgets;
import org.gemsjax.client.admin.widgets.BigMenuButton.BigMenuButtonChangedEvent;
import org.gemsjax.client.admin.widgets.BigMenuButton.BigMenuButtonChangedEventHandler;

import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.VStack;


/**
 * This is a GUI widget where you can add {@link BigMenuButton}s. This {@link BigMenuButton} will be managed by this {@link VerticalBigMenuButtonBar},
 *  so only one {@link BigMenuButton} can be set as the active one ( {@link BigMenuButton#setActive(boolean)} at the same time.<br />
 *  This component should be used to contain and display a set of {@link BigMenuButton}s, but since its a {@link VStack} you can add also any widget/canvas you want
 * @see #addMember(BigMenuButton)
 * @author Hannes Dorfmann
 *
 */
public class VerticalBigMenuButtonBar extends VStack implements BigMenuButtonChangedEventHandler
{
	/**
	 * This canvas is used to realize a space at the top
	 * @see #setTopSpace(int)
	 */
	private Canvas topToMenuSpacer;
	/**
	 * To store the information which Button is already the active one
	 */
	private BigMenuButton activeBigMenuButton;
	
	
	public VerticalBigMenuButtonBar()
	{
		super();
		//this.setWidth(200);
	}
	
	
	
	
	public VerticalBigMenuButtonBar(int memberMargin)
	{
		this();
		this.setMembersMargin(memberMargin);
	}
	
	

	public VerticalBigMenuButtonBar(int width, int memberMargin, int topSpace)
	{
		this(memberMargin);
		// TODO smartgwt bug? only sets width correctly when its done in the constructor via setWidth()
		this.setWidth(width);
		
		if (topSpace>0)
			setTopSpace(topSpace);
	}
	
	
	/**
	 * Realize a Space to at the top.
	 * @param space in pixels (int)
	 */
	public void setTopSpace(int space)
	{
		
		if (topToMenuSpacer==null && space >0)
		{
			topToMenuSpacer = new Canvas();
		}
		
		if (space >0)
		{
			topToMenuSpacer.setHeight(space);
			
			if (!this.contains(topToMenuSpacer))
				this.addMember(topToMenuSpacer, 0);
		}
	}
	
	
	/**
	 * Use this method to add a {@link BigMenuButton}.
	 * As this is a {@link VStack}, so you can also simply add any other component ({@link Canvas}) you want by calling {@link #addMember(Canvas)}
	 * @param button
	 */
	public void addMember(BigMenuButton button)
	{
		button.addBigMenuButtonChangedEventHandler(this);
		checkNewToAddButtonForActive(button);
		super.addMember(button);
	}
	
	/**
	 * Check if another {@link BigMenuButton} is already set as active.
	 * If this new button, which should be added is set as active, you have to ensure 
	 * that there is no other {@link BigMenuButton} set as active. If there is another, 
	 * you must set them to NOT active {@link BigMenuButton#setActive(boolean)}
	 * @param button
	 */
	private void checkNewToAddButtonForActive(BigMenuButton button)
	{
		// if new button is active, set all others as NOT active
		if (button.isActive())
		{
			for (Widget w: this.getMembers())
				if (w instanceof BigMenuButton && ((BigMenuButton)w).isActive())
					((BigMenuButton)w).setActive(false);
		
			this.activeBigMenuButton = button;
		}
	}

	/**
	 * Use this method to add a {@link BigMenuButton} at a certain position. The index for the position starts with the index 0 (also if a top spacer has been set)
	 * As this is a {@link VStack}, so you can also simply add any other component ({@link Canvas}) you want by calling {@link #addMember(Canvas)}
	 * @param button
	 */
	public void addMember(BigMenuButton button, int position)
	{
		// If a space at the top was set, the Canvas topToMenuSpacer must allways be at position one
		if (topToMenuSpacer!=null && this.contains(topToMenuSpacer))
			position++;
		button.addBigMenuButtonChangedEventHandler(this);
		
		checkNewToAddButtonForActive(button);
		super.addMember(button, position);	
	}
	
	@Override
	public void addMember(Canvas canvas, int position)
	{
		if (topToMenuSpacer!=null && this.contains(topToMenuSpacer))
			position++;
		
		super.addMember(canvas, position);
	}
	
	@Override
	public void addMember(Widget widget, int position)
	{
		if (topToMenuSpacer!=null && this.contains(topToMenuSpacer))
			position++;
		
		super.addMember(widget, position);
	}


	@Override
	public void onBigMenuButtonChanged(BigMenuButtonChangedEvent event) {
		
		if (event.getType()==BigMenuButton.BigMenuButtonChangedEventType.ACTIVE && this.activeBigMenuButton!=event.getSource())
		{
			if (this.activeBigMenuButton!= null )
			activeBigMenuButton.setActive(false);
			
			this.activeBigMenuButton = event.getSource();
		}
	}
	
	
}
