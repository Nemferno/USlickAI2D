package org.xodia.usai2d;

public class HorizontalScroller extends BasicUserInterface implements IScroller{

	private Handle h;
	private float contentWidth;
	private boolean canDrag;
	
	public HorizontalScroller(float w, float h) {
		super(w, h);
		
		this.h = new Handle(w, h);
		addChild(this.h);
	}
	
	public void setValue(float v){
		
	}
	
	public void setContentWidth(float w){
		contentWidth = w;
		
		if(contentWidth <= getWidth()){
			setDisabled(true);
		}else{
			setDisabled(false);
			
			float percent = (contentWidth - getWidth()) / contentWidth;
			
			if(percent > 0.9f){
				percent = 0.9f;
			}
			
			this.h.setSize(getWidth() - (getWidth() * percent), this.h.getHeight());
		}
	}
	
	public float getValue(){
		float x = getX() - h.getX();
		float val = Math.abs(x / (getWidth() - h.getWidth()));
		
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
				if(newx <= getX()){
					h.setPosition(getX(), h.getY());
				}else if(newx + h.getWidth() >= getX() + getWidth()){
					h.setPosition(getX() + getWidth() - h.getWidth(), getY());
				}else{
					h.setPosition(newx, h.getY());
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
