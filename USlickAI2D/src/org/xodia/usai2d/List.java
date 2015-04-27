package org.xodia.usai2d;

import org.newdawn.slick.GameContainer;
import org.xodia.usai2d.layout.VerticalLayout;

public class List extends BasicUserInterface {

	private int capacity;
	private int numOfChilds;
	
	private Container c;
	private Panel p;
	
	public List(GameContainer gc, float x, float y, float w, float h){
		this(gc, x, y, w, h, 10);
	}
	
	public List(GameContainer gc, float x, float y, float w, float h, int capacity){
		super(gc, x, y, w, h);
		
		this.capacity = capacity;
		
		c = new Container(gc, 0, 0, w, h);
		p = new Panel(gc, w, capacity * font.getHeight("A"));
		p.setLayout(new VerticalLayout());
		c.setContent(p);
		addChild(c);
	}
	
	public void addItem(final String item, String desc){
		if(numOfChilds < capacity){
			Label l = new Label(container, item, 0, 0, p.getWidth(), font.getHeight("A"));
			
			if(desc != null)
				l.setToolTip(desc);
			
			p.addChild(l);
			capacity++;
		}
	}
	
	public void addItem(String item){
		addItem(item, null);
	}
	
	public void clearList(){
		p.clearChildren();
		numOfChilds = 0;
	}
	
}
