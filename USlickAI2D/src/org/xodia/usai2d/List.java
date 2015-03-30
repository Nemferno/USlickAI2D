package org.xodia.usai2d;

import org.xodia.usai2d.layout.VerticalLayout;

public class List extends BasicUserInterface {

	private Container c;
	private Panel p;
	
	public List(float x, float y, float w, float h){
		this(x, y, w, h, 10);
	}
	
	public List(float x, float y, float w, float h, int capacity){
		super(x, y, w, h);
		
		c = new Container(0, 0, w, h);
		p = new Panel(w, capacity * (h * 0.25f));
		p.setLayout(new VerticalLayout());
		c.setContent(p);
		addChild(c);
	}
	
	public void addItem(final String item, String desc){
		Label l = new Label(item, 0, 0, p.getWidth(), getHeight() * 0.25f);
		p.addChild(l);
	}
	
	public void addItem(String item){
		addItem(item, null);
	}
	
	public void clearList(){
		p.clearChildren();
	}
	
}
