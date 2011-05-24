package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.tabs.TwoColumnLayoutTab;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.CanvasSupportException;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VStack;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class MetaModelViewImpl extends TwoColumnLayoutTab implements MetaModelView{

	private ToolStripButton newMetaClassButton;
	private ToolStripButton newConnectionButton;
	private ToolStripButton newInheritanceButton;

	
	public MetaModelViewImpl(String title, UserLanguage language) throws CanvasSupportException {
		super(title, language);
		
		generateToolStrip(language);
		
		BufferedCanvas canvas = new BufferedCanvas();
		this.setRightColumn(canvas, true);
		
		canvas.initCanvasSize();
		canvas.redrawCanvas();
		
		this.getLayout().setOverflow(Overflow.HIDDEN);
		
	
		
		canvas.setOverflow(Overflow.SCROLL);
		
		
		
	}
	
	private void generateToolStrip(UserLanguage language)
	{
		 ToolStrip toolStrip = new ToolStrip();  
	     toolStrip.setVertical(true);  
		
		newMetaClassButton = new ToolStripButton (language.NewMetaClassToolStrip());
		newMetaClassButton.setActionType(SelectionType.RADIO);  
		newMetaClassButton.setRadioGroup("toolstripGroup");  
	    toolStrip.addButton(newMetaClassButton);
	    
	 
	    
	    newInheritanceButton = new ToolStripButton (language.InheritanceToolStrip());
	    newInheritanceButton.setActionType(SelectionType.RADIO);  
	    newInheritanceButton.setRadioGroup("toolstripGroup");  
	    toolStrip.addButton(newInheritanceButton);
	    
	    
	    newConnectionButton = new ToolStripButton(language.ConnectionToolStrip());
	    newConnectionButton.setActionType(SelectionType.RADIO);  
	    newConnectionButton.setRadioGroup("toolstripGroup");  
	    toolStrip.addButton(newConnectionButton);
	    
	    this.setLeftColumn(toolStrip, true);
	    
	}

	@Override
	public ToolStripButton getAddMetaClassButton() {
		return newMetaClassButton;
	}

}
