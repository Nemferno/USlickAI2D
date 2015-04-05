package org.xodia.usai2d;

import org.newdawn.slick.GameContainer;

public class VerticalScroller extends BasicUserInterface implements IScroller{

	private Handle h;
	private float contentHeight;
	private float pressedY;
	private boolean canDrag;
	
	public VerticalScroller(GameContainer gc, float w, float h){
		super(gc, w, h);
		
		this.h = new Handle(gc, w, h);
		addChild(this.h);
	}
	
	public void setValue(float v) {
		if(v < 0)
			v = 0;
		else if(v > 1)
			v = 1;
		
		float newy = v * (getHeight() - h.getHeight());
		float y = -newy + getY();
		h.setPosition(h.getX(), getY() + -y);
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
				if(newx >= h.getX() && newx <= h.getX() + h.getWidth() &&
					newy >= h.getY() && newy <= h.getY() + h.getHeight()){
					
					h.setPosition(h.getX(), newy - pressedY);

					if(h.getY() <= getY()){
						h.setPosition(h.getX(), getY());
					}else if(h.getY() + h.getHeight() >= getY() + getHeight()){
						h.setPosition(h.getX(), getY() + getHeight() - h.getHeight());
					}
				}
			}
		}
	}
	
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		
		canDrag = false;
		pressedY = 0;
	}
	
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		
		if(!(x >= h.getX() && x <= h.getX() + h.getWidth() &&
				y >= h.getY() && y <= h.getY() + h.getHeight())){
			if(y < h.getY()){
				setValue(getValue() - 0.05f);
			}else if(y > h.getY() + h.getHeight()){
				setValue(getValue() + 0.05f);
			}
		}
		
		pressedY = y - h.getY();
		canDrag = true;
	}
	
}
