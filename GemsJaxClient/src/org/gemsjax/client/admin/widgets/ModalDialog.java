package org.gemsjax.client.admin.widgets;

import com.smartgwt.client.widgets.Window;

public class ModalDialog extends Window{
	
	public ModalDialog(){
		this.setStyleName("ModalDialog");
		this.setModalMaskStyle("ModalDialogBackground");
		this.setIsModal(true);
		this.setBorder("none");
		this.setShowHeader(false);
		this.setShowStatusBar(false);
		this.setShowTitle(false);
		this.setShowCloseButton(false);
		this.setCanDragResize(false);
		this.setCanDragReposition(false);
		this.setModalMaskOpacity(70);
		this.setShowModalMask(true);  
		this.setEdgeSize(0);
		this.setEdgeOpacity(0);
		this.centerInPage();
	}

}
