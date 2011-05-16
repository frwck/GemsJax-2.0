package org.gemsjax.client.admin.tabs;

import org.gemsjax.client.admin.UserLanguage;
import org.gemsjax.client.canvas.CanvasSupportException;
import org.gemsjax.client.editor.MetaModelEditor;

import com.smartgwt.client.widgets.tab.Tab;

public class MetaModelEditorTab extends LoadingTab{

	public MetaModelEditorTab(String title, UserLanguage language) throws CanvasSupportException {
		super(title, language);
		//super(title);
		this.setContent(new MetaModelEditor());
		this.showContent();
		
	}

}
