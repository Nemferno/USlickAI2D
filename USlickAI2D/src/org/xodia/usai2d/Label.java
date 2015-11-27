package org.xodia.usai2d;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Label extends BasicUserInterface 
{
	
	private String text;
	private TextOption option;
	
	public Label(GameContainer gc, float x, float y, float w, float h)
	{
		this(gc, "", x, y, w, h);
	}
	
	public Label(GameContainer gc, String text, float x, float y, float w, float h)
	{
		super(gc, x, y, w, h);
		
		this.text = text;
		this.option = TextOption.LEFT;
	}
	
	public void render(Graphics g)
	{
		super.render(g);
		
		g.setColor(DEFAULT_TEXT);
		Font oldFont = g.getFont();
		g.setFont(font);
		switch(option){
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
		g.setFont(oldFont);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setTextOption(TextOption option){
		this.option = option;
	}
	
	public String getText(){
		return text;
	}
	
	public TextOption getTextOption(){
		return option;
	}
	
	public void setToolTip(String desc){
		ToolTip.createToolTip(this, desc);
	}
	
}
