package org.xodia.usai2d;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Button extends BasicUserInterface {
	
	private TextOption textOption;
	private String text;
	private OnClickListener listener;
	
	public Button(GameContainer gc, float x, float y, float w, float h){
		this(gc, "Button", x, y, w, h);
	}
	
	public Button(GameContainer gc, String text, float x, float y, float w, float h) {
		this(gc, text, x, y, w, h, null);
	}
	
	public Button(GameContainer gc, String text, float x, float y, float w, float h, OnClickListener listener){
		super(gc, x, y, w, h);
		
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
