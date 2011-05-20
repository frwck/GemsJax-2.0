package org.gemsjax.client.admin.tabs;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.CanvasSupportException;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.tab.Tab;

// TODO extend it from LoadingTab
public class MetaModelCanvasTab extends Tab {
	
	public MetaModelCanvasTab(String title, UserLanguage language)
	{
		super(title);
		try {
			BufferedCanvas bc = new BufferedCanvas();
			this.setPane(bc);
			bc.initCanvasSize();
			
		} catch (CanvasSupportException e) {
			SC.say(e.getMessage());
		}
	}

}
