package org.xodia.usai2d;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * With the new improved {@link BasicUserInterface}, it is easier to develop UIs that
 * have children who also have children!
 * 
 * @author Jasper Bae
 *
 */
public class TextArea extends BasicUserInterface{

	private Container c;
	private Panel p;
	private EditField field;
	
	public TextArea(GameContainer gc, float width, float height){
		super(gc, width, height);
		
		c = new Container(gc, 0, 0, width, height);
		p = new Panel(gc, width, height);
		field = new EditField(gc, 0, 0, width, height){
			public void render(Graphics g) {
				super.render(g);

				if(getTextHeight() >= getHeight()){
					p.setSize(p.getWidth(), getTextHeight());
					p.setPosition(0, 0);
					c.setContent(p);
				}
			}
		};
		field.setWordWrap(true);
		p.addChild(field);
		c.setContent(p);
		addChild(c);
	}
	
	public void setEditable(boolean editable){
		field.setEditable(editable);
	}
	
	public void setMaximumCharacters(int max){
		field.setMaxCharacters(max);
	}
	
	public void setWordWrap(boolean wrap){
		field.setWordWrap(wrap);
	}
	
	public void setText(String text){
		field.setText(text);
	}
	
	public boolean isEditable(){
		return field.isEditable();
	}
	
	public int getMaximumCharacters(){
		return field.getMaxCharacters();
	}
	
	public boolean isWordWrap(){
		return field.isWordWrap();
	}
	
	public String getText(){
		return field.getText();
	}
	
}
