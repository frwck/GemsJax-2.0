package org.gemsjax.client.admin.tabs;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.canvas.OldBufferedCanvas;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;

public class MetaModelEditorTab2 extends TwoColumnLayoutTab{

	public MetaModelEditorTab2(String title, UserLanguage language) throws CanvasSupportException {
		super(title, language);
		
		IButton button = new IButton("This is a MenuBar");
		this.setLeftColumn(button, true);
		
		BufferedCanvas canvas = new BufferedCanvas();
		this.setRightColumn(canvas, true);
		
		canvas.initCanvasSize();
		canvas.redrawCanvas();
		
		this.getLayout().setOverflow(Overflow.HIDDEN);
		
	
		
		canvas.setOverflow(Overflow.SCROLL);
		
		
		
	}

}
