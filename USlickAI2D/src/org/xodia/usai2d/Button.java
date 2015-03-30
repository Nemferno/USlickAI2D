package org.xodia.usai2d;

import org.newdawn.slick.Graphics;

public class Button extends BasicUserInterface {
	
	private String text;
	private OnClickListener listener;
	
	public Button(float x, float y, float w, float h){
		this("Button", x, y, w, h);
	}
	
	public Button(String text, float x, float y, float w, float h) {
		this(text, x, y, w, h, null);
	}
	
	public Button(String text, float x, float y, float w, float h, OnClickListener listener){
		super(x, y, w, h);
		
		this.text = text;
		this.listener = listener;
	}
	
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		
		if(listener != null)
			listener.onClick(button);
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		g.setColor(DEFAULT_TEXT);
		g.drawString(text, getX() + getWidth() / 2 - g.getFont().getWidth(text) / 2, getY() + getHeight() / 2 - g.getFont().getHeight(text) / 2);
	}
	
	public void setOnClickListener(OnClickListener l){
		this.listener = l;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
	
	public static interface OnClickListener {
		void onClick(int button);
	}
	
}
