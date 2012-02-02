package org.gemsjax.client.admin.tabs;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.layout.HLayout;

/**
 * This Tab provides a Layout, that devide the whole tab pane into two columns:
 * <ul>
 * <li>Left column: which should be used to realize a menu and can be accessed via {@link #setLeftColumn(Canvas)} and {@link #getLeftColumn()} </li>
 * <li>Right column: which should be used to display the main content {@link #setRightColumn(Canvas, boolean)} and {@link #getRightColumn()}
 * </ul>
 * @author Hannes Dorfmann
 *
 */
public class TwoColumnLayout extends HLayout{
	
	private Canvas leftColumnCanvas;
	private Canvas rightColumnCanvas;
	
	
	
	public TwoColumnLayout()
	{
		this.setWidth100();
		this.setHeight100();
		this.setMembersMargin(25);
	}
	
	
	/**
	 * Set the margin between the left column (normally a menu) and the right column (the normal content) 
	 * @param margin
	 */
	public void setMarginBetweenColumns(int margin)
	{
		this.setMargin(margin);
		this.draw();
	}
	
	
	/**
	 * Set the right column. Remember: you must set the width of the right or / and the left column, otherwise the tabpane will be separated in the half (50% left and 50% right column) 
	 * @param canvas The content of the right Column
	 * @param withAnimation
	 */
	public void setRightColumn(Canvas canvas, boolean withAnimation)
	{
		
		this.setAnimateMembers(withAnimation);
		
		// remove previous content
		if (rightColumnCanvas != null)
			this.removeMember(rightColumnCanvas);
		
		rightColumnCanvas = canvas;
		this.addMember(canvas);
		
		
		this.setAnimateMembers(false);
	}
	
	/**
	 * Get the content / canvas of right column
	 * @return
	 */
	public Canvas getRightColumn()
	{
		return rightColumnCanvas;
	}
	
	/**
	 * Get the content / canvas of left column
	 * @return
	 */
	public Canvas getLeftColumn()
	{
		return leftColumnCanvas;
	}
	
	
	/**
	 * Set the content (normally a Menu) which is displayed in the left column.
	 * Remember: you must set the width of the right or / and the left column, otherwise the tabpane will be separated in the half (50% left and 50% right column)
	 * @param canvas the content of the left column
	 * @param withAnimation
	 */
	public void setLeftColumn(Canvas canvas, boolean withAnimation)
	{
		this.setAnimateMembers(withAnimation);
		
		if (leftColumnCanvas != null)
			this.removeMember(rightColumnCanvas);
		
		leftColumnCanvas = canvas;
		this.addMember(canvas, 0);
		
		//TODO maybe remove this animation in here and do this in an own method
		this.setAnimateMembers(false);
	}
	
	
}
