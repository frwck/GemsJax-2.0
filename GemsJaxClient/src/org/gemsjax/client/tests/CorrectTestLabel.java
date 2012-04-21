package org.gemsjax.client.tests;

import com.smartgwt.client.widgets.Label;

public class CorrectTestLabel extends Label {
	
	public CorrectTestLabel(String name){
		super(name+": ok");
		this.setHeight(20);
		this.setBackgroundColor("green");
	}

}
