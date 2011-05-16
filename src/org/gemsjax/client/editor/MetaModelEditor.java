package org.gemsjax.client.editor;

import org.gemsjax.client.canvas.BufferedCanvas;
import org.gemsjax.client.canvas.CanvasSupportException;

import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class MetaModelEditor extends HLayout{
	
	private BufferedCanvas canvas;
	
	private VStack toolBar;
	
	public MetaModelEditor() throws CanvasSupportException
	{
		
		toolBar = new VStack();
		canvas = new BufferedCanvas();
		//canvas.initCanvasSize();
		canvas.setWidth100();
		canvas.setHeight100();
		
		this.setWidth100();
		this.setHeight100();
		generateToolBar();
		
		this.addChild(canvas);
		this.addChild(toolBar);
		
	}
	
	
	
	private void generateToolBar()
	{
		toolBar.addChild(new IButton("Test"));
	}
	
	
	public BufferedCanvas getCanvas()
	{
		return canvas;
	}

}
