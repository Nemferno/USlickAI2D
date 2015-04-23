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
		this.textOption = TextOption.LEFT;
	}
	
	public void setToolTip(String desc){
		ToolTip.createToolTip(this, desc);
	}
	
	public void setTestOption(TextOption option){
		this.textOption = option;
	}
	
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		
		if(listener != null)
			listener.onClick(button);
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		g.setColor(DEFAULT_TEXT);
		switch(textOption){
		case CENTER:
			g.drawString(text, getX() + getWidth() / 2 - g.getFont().getWidth(text) / 2, getY() + getHeight() / 2 - g.getFont().getHeight(text) / 2);
			break;
		case LEFT:
			g.drawString(text, getX(), getY() + getHeight() / 2 - g.getFont().getHeight(text) / 2);
			break;
		case RIGHT:
			g.drawString(text, getX() + getWidth() - g.getFont().getWidth(text), getY() + getHeight() / 2 - g.getFont().getHeight(text) / 2);
			break;
		}
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
	
	public TextOption getTextOption(){
		return textOption;
	}
	
	public static interface OnClickListener {
		void onClick(int button);
	}
	
}
