package org.gemsjax.client.tests;

import com.smartgwt.client.widgets.Label;

public class FailTestLabel extends Label{

	public FailTestLabel(String name){
		super(name+": fail");
		this.setHeight(20);
		this.setBackgroundColor("red");
	}
}
