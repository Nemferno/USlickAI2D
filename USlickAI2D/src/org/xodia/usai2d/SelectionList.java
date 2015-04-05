package org.xodia.usai2d;

import org.newdawn.slick.GameContainer;
import org.xodia.usai2d.Button.OnClickListener;
import org.xodia.usai2d.layout.VerticalLayout;

public class SelectionList extends BasicUserInterface {

	private Container c;
	private Panel p;
	private Button expandButton;

	private String sString;
	private OnChangedListener listener;
	
	public SelectionList(GameContainer gc, float x, float y, float w, float h){
		this(gc, x, y, w, h, 10);
	}
	
	public SelectionList(GameContainer gc, float x, float y, float w, float h, int capacity) {
		this(gc, x, y, w, h, capacity, null);
	}

	public SelectionList(GameContainer gc, float x, float y, float w, final float h, int capacity, OnChangedListener listener){
		super(gc, x, y, w, h);
		
		this.listener = listener;
		
		float tempH = 0;
		
		if(capacity * (h * 0.25f) < h * 2.5f){
			tempH = capacity * (h * 0.25f);
		}else{
			tempH = h * 2.5f;
		}
		
		c = new Container(gc, 0, h, w, tempH);
		c.setVisible(false);
		p = new Panel(gc, w, capacity * (h * 0.25f));
		p.setLayout(new VerticalLayout());
		c.setContent(p);
		expandButton = new Button(gc, "Select", 0, 0, w, h, new OnClickListener(){
			public void onClick(int button) {
				if(c.isVisible()){
					// Remove it
					c.setVisible(false);
					removeChild(c);
					c.setPosition(0, getHeight());
					removeChild(expandButton);
					expandButton.setPosition(0, 0);
					setSize(getWidth(), h);
					addChild(expandButton);
				}else{
					c.setVisible(true);
					removeChild(expandButton);
					expandButton.setPosition(0, 0);
					removeChild(c);
					c.setPosition(0, getHeight());
					setSize(getWidth(), getHeight() + c.getHeight());
					addChild(expandButton);
					addChild(c);
				}
			}
		});
		addChild(expandButton);
		addChild(c);
	}
	
	public void addItem(final String item, String desc){
		Button b = new Button(container, item, 0, 0, p.getWidth(), getHeight() * 0.25f, new OnClickListener(){
			public void onClick(int button){
				sString = item;
				expandButton.setText(item);
				listener.changedSelection(item);
				c.setVisible(false);
				removeChild(c);
				c.setPosition(0, getHeight());
				removeChild(expandButton);
				expandButton.setPosition(0, 0);
				setSize(getWidth(), getHeight() - c.getHeight());
				addChild(expandButton);
			}
		});
		p.addChild(b);
	}
	
	public void addItem(String item){
		addItem(item, null);
	}
	
	public void clearList(){
		p.clearChildren();
	}
	
	public void setOnChangedListener(OnChangedListener listener){
		this.listener = listener;
	}
	
	public String getSelected(){
		return sString;
	}
	
}
