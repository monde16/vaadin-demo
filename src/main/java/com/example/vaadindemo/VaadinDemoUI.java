package com.example.vaadindemo;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.Label;
import com.vaadin.annotations.Theme;

@Theme("VaadinDemo")
public class VaadinDemoUI extends UI{
	
	@Override
	protected void init(VaadinRequest request){
		Label lbl = new Label("Hello vaadin");
		setContent(lbl);
	}
}
