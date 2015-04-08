package org.xodia.usai2d;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ToggleButton extends Button {

	private boolean isToggled;
	
	public ToggleButton(GameContainer gc, float x, float y, float w, float h) {
		super(gc, x, y, w, h);
	}
	
	public ToggleButton(GameContainer gc, String text, float x, float y, float w, float h){
		super(gc, text, x, y, w, h);
	}
	
	public ToggleButton(GameContainer gc, String text, float x, float y, float w, float h, OnClickListener listener){
		super(gc, text, x, y, w, h, listener);
	}
	
	public void render(Graphics g) {
		if(!isToggled){
			g.setColor(DEFAULT_BACKGROUND);
		}else{
			g.setColor(Color.darkGray);
		}
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(DEFAULT_BORDER);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		g.setColor(DEFAULT_TEXT);
		switch(getTextOption()){
		case CENTER:
			g.drawString(getText(), getX() + getWidth() / 2 - g.getFont().getWidth(getText()) / 2, getY() + getHeight() / 2 - g.getFont().getHeight(getText()) / 2);
			break;
		case LEFT:
			g.drawString(getText(), getX(), getY() + getHeight() / 2 - g.getFont().getHeight(getText()) / 2);
			break;
		case RIGHT:
			g.drawString(getText(), getX() + getWidth() - g.getFont().getWidth(getText()), getY() + getHeight() / 2 - g.getFont().getHeight(getText()) / 2);
			break;
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		if(!isToggled)
			isToggled = true;
		else
			isToggled = false;
		
		super.mousePressed(button, x, y);
	}
	
	public void setToggled(boolean toggle){
		isToggled = toggle;
	}
	
	public boolean isToggled(){
		return isToggled;
	}

}
