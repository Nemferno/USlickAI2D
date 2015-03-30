package org.xodia.usai2d;

public class VerticalScroller extends BasicUserInterface implements IScroller{

	private Handle h;
	private float contentHeight;
	private boolean canDrag;
	
	public VerticalScroller(float w, float h){
		super(w, h);
		
		this.h = new Handle(w, h);
		addChild(this.h);
	}
	
	public void setValue(float v) {
		
	}
	
	public void setContentHeight(float h){
		contentHeight = h;
		
		if(contentHeight <= getHeight()){
			setDisabled(true);
		}else{
			setDisabled(false);

			float percent = (contentHeight - getHeight()) / contentHeight;
			
			if(percent > 0.9f){
				percent = 0.9f;
			}
			
			this.h.setSize(this.h.getWidth(), getHeight() - (getHeight() * percent));
		}
	}

	public float getValue() {
		float y = getY() - h.getY();
		float val = Math.abs(y / (getHeight() - h.getHeight())); 
		
		if(Float.isNaN(val)){
			val = 0;
		}
		
		return val;
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		super.mouseDragged(oldx, oldy, newx, newy);
		
		if(newx >= getX() && newx <= getX() + getWidth() &&
			newy >= getY() && newy <= getY() + getHeight()){
			if(canDrag){
				if(newy <= getY()){
					h.setPosition(h.getX(), getY());
				}else if(newy + h.getHeight() >= getY() + getHeight()){
					h.setPosition(h.getX(), getY() + getHeight() - h.getHeight());
				}else{
					h.setPosition(h.getX(), newy);
				}
			}
		}
	}
	
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		
		canDrag = false;
	}
	
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		
		canDrag = true;
	}
	
}
