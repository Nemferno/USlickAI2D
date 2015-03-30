package org.xodia.usai2d;

public class Label extends EditField {
	
	public Label(String text, float x, float y, float w, float h){
		super(x, y, w, h);
		
		setEditable(false);
		setWordWrap(true);
		setText(text);
	}
	
}
