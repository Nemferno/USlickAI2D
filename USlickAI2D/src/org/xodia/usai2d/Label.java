package org.xodia.usai2d;

import org.newdawn.slick.GameContainer;

public class Label extends TextField {
	
	public Label(GameContainer gc, float x, float y, float w, float h){
		this(gc, "", x, y, w, h);
	}
	
	public Label(GameContainer gc, String text, float x, float y, float w, float h){
		super(gc, x, y, w, h);
		
		setEditable(false);
		setText(text);
	}
	
	public void setToolTip(String desc){
		ToolTip.createToolTip(this, desc);
	}
	
}
