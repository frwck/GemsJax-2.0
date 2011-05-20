package org.gemsjax.client.admin.view.implementation;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.admin.tabs.TwoColumnLayoutTab;
import org.gemsjax.client.admin.view.MetaModelView;
import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.CanvasSupportException;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;

public class MetaModelViewImpl extends TwoColumnLayoutTab implements MetaModelView{

	public MetaModelViewImpl(String title, UserLanguage language) throws CanvasSupportException {
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
