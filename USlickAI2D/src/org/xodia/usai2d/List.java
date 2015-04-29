package org.xodia.usai2d;

import org.newdawn.slick.GameContainer;
import org.xodia.usai2d.Button.OnClickListener;
import org.xodia.usai2d.layout.VerticalLayout;

public class List extends BasicUserInterface {

	private int capacity;
	private int numOfChilds;
	
	private Container c;
	private Panel p;
	
	private String sString;
	private OnChangedListener listener;
	
	public List(GameContainer gc, float x, float y, float w, float h){
		this(gc, x, y, w, h, 10);
	}
	
	public List(GameContainer gc, float x, float y, float w, float h, int capacity){
		this(gc, x, y, w, h, capacity, null);
	}
	
	public List(GameContainer gc, float x, float y, float w, float h, int capacity, OnChangedListener listener){
		super(gc, x, y, w, h);
		
		this.capacity = capacity;
		this.listener = listener;
		
		c = new Container(gc, 0, 0, w, h);
		p = new Panel(gc, w, capacity * font.getHeight("A"));
		p.setLayout(new VerticalLayout());
		c.setContent(p);
		addChild(c);
	}
	
	public void addDivider(String item){
		if(numOfChilds < capacity){
			Label l = new Label(container, item, 0, 0, p.getWidth(), font.getHeight("A"));
			p.addChild(l);
			numOfChilds++;
		}
	}
	
	public void addItem(String item, String desc){
		if(numOfChilds < capacity){
			final Button b = new Button(container, item, 0, 0, p.getWidth(), font.getHeight("A"));
			b.setOnClickListener(new OnClickListener() {
				public void onClick(int button) {
					sString = b.getText();
					
					if(listener != null)
						listener.changedSelection(sString);
				}
			});
			p.addChild(b);
			numOfChilds++;
		}
	}
	
	public void addItem(String item){
		addItem(item, null);
	}
	
	public String getSelectedString(){
		return sString;
	}
	
	public void clearList(){
		p.clearChildren();
		numOfChilds = 0;
	}
	
}
