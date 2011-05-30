package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.tabs.TwoColumnLayoutTab;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.admin.widgets.BigMenuButton;
import org.gemsjax.client.admin.widgets.VerticalBigMenuButtonBar;
import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.editor.MetaModelCanvas;

import com.smartgwt.client.types.Overflow;

public class MetaModelViewImpl extends TwoColumnLayoutTab implements MetaModelView{

	private BigMenuButton mouseButton, newClassButton, newRelationButton, newInheritanceButton;
	
	public MetaModelViewImpl(String title, UserLanguage language) throws CanvasSupportException {
		super(title, language);
		
		generateToolStrip(language);
		
		BufferedCanvas canvas = new MetaModelCanvas();
		this.setRightColumn(canvas, true);
		
		canvas.initCanvasSize();
		canvas.redrawCanvas();
		
		this.getLayout().setOverflow(Overflow.HIDDEN);
		
	
		
		canvas.setOverflow(Overflow.SCROLL);
		
		
		
	}
	
	private void generateToolStrip(UserLanguage language)
	{
		
		VerticalBigMenuButtonBar toolbar = new VerticalBigMenuButtonBar(120,10);
		//toolbar.setMargin(5);
		toolbar.setMembersMargin(10);
		
		mouseButton = new BigMenuButton("Use Mouse","/images/icons/mouse_black.png"); 
		mouseButton.setActive(true);
		
		
		
		newClassButton = new BigMenuButton("Meta-Class","/images/icons/class.png"); 
		newRelationButton = new BigMenuButton("Relation","/images/icons/relation.png"); 
		newInheritanceButton = new BigMenuButton("Inheritance","/images/icons/inheritance.png"); 	
		
		
		mouseButton.setHeight(100);
		newClassButton.setHeight(100);
		newRelationButton.setHeight(100);
		newInheritanceButton.setHeight(100);
		
		
		
		toolbar.addMember(mouseButton);
		toolbar.addMember(newClassButton);
		toolbar.addMember(newRelationButton);
		toolbar.addMember(newInheritanceButton);
		
		
	    this.setLeftColumn(toolbar, true);
	    
	    
	}

	@Override
	public com.smartgwt.client.widgets.events.HasClickHandlers getAddMetaClassButton() {
		return mouseButton;
	}
	

	
}
