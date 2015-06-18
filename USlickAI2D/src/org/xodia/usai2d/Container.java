package org.xodia.usai2d;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * 
 * @author Jasper Bae
 *
 */
public class Container extends BasicUserInterface {

	private float contentWidth;
	private float contentHeight;
	
	private VerticalScroller vScroller;
	private HorizontalScroller hScroller;
	
	private Panel content;
	
	public Container(GameContainer gc, float x, float y, float w, float h) {
		super(gc, x, y, w, h);
	}
	
	public void render(Graphics g){
		if(isVisible()){
			g.setColor(DEFAULT_BACKGROUND);
			g.fillRect(getX(), getY(), getWidth(), getHeight());
			g.setColor(DEFAULT_BORDER);
			g.drawRect(getX(), getY(), getWidth(), getHeight());
			
			Rectangle oldClip = g.getWorldClip();
			g.setWorldClip(getX(), getY(), getWidth(), getHeight());
			
			float ty = (contentHeight - getHeight()) * (vScroller != null ? vScroller.getValue() : 0);
			float tx = (contentWidth - getWidth()) * (hScroller != null ? hScroller.getValue() : 0);
			g.translate(-tx, -ty);
			if(content != null){
				content.render(g);
			}
			g.translate(tx, ty);
			g.setWorldClip(oldClip);
			
			if(vScroller != null){
				vScroller.render(g);
			}
			
			if(hScroller != null){
				hScroller.render(g);
			}
		}
	}
	
	public void setContent(Panel p){
		contentWidth = p.getWidth();
		contentHeight = p.getHeight();
		
		if(content != null)
			removeChild(content);
		
		if(vScroller != null){
			vScroller.setContentHeight(contentHeight);
		}else{
			if(contentHeight > getHeight()){
				setVerticalScroller();
			}else{
				vScroller = null;
			}
		}
		
		if(hScroller != null){
			hScroller.setContentWidth(contentWidth);
		}else{
			if(contentWidth > getWidth()){
				setHorizontalScroller();
			}else{
				hScroller = null;
			}
		}
		
		content = p;
		addChild(p);
	}
	
	public void setSize(float w, float h) {
		super.setSize(w, h);
		
		if(content != null){
			contentWidth = content.getWidth();
			contentHeight = content.getHeight();
			
			if(vScroller != null){
				vScroller.setContentHeight(contentHeight);
			}else{
				if(contentHeight > getHeight()){
					setVerticalScroller();
				}else{
					vScroller = null;
				}
			}
			
			if(hScroller != null){
				hScroller.setContentWidth(contentWidth);
			}else{
				if(contentWidth > getWidth()){
					setHorizontalScroller();
				}else{
					hScroller = null;
				}
			}
		}
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		if(vScroller != null){
			vScroller.mouseDragged(oldx, oldy, newx, newy);
		}
		
		if(hScroller != null){
			hScroller.mouseDragged(oldx, oldy, newx, newy);
		}
		
		if(content != null){
			content.mouseDragged(oldx, oldy, newx - (int) (hScroller != null ? (contentWidth - getWidth()) * hScroller.getValue() : 0),
					newy - (int) (vScroller != null ? (contentHeight - getHeight()) * vScroller.getValue() : 0));
		}
	}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if(vScroller != null){
			vScroller.mouseMoved(oldx, oldy, newx, newy);
		}
		
		if(hScroller != null){
			hScroller.mouseMoved(oldx, oldy, newx, newy);
		}
		
		if(content != null){
			content.mouseMoved(oldx, oldy, newx - (int) (hScroller != null ? (contentWidth - getWidth()) * hScroller.getValue() : 0),
					newy - (int) (vScroller != null ? (contentHeight - getHeight()) * vScroller.getValue() : 0));
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		// Check if the scrollers have been touched...
		if(vScroller != null){
			if(x >= vScroller.getX() && x <= vScroller.getX() + vScroller.getWidth() &&
				y >= vScroller.getY() && y <= vScroller.getY() + vScroller.getHeight()){
				vScroller.mousePressed(button, x, y);
				return;
			}
		}
		
		if(hScroller != null){
			if(x >= hScroller.getX() && x <= hScroller.getX() + hScroller.getWidth() &&
				y >= hScroller.getY() && y <= hScroller.getY() + hScroller.getHeight()){
				hScroller.mousePressed(button, x, y);
				return;
			}
		}
		
		super.mousePressed(button, 
				x - (int) (hScroller != null ? (contentWidth - content.getWidth()) * hScroller.getValue() : 0), 
				y - (int) (vScroller != null ? (contentHeight - content.getHeight()) * vScroller.getValue() : 0));
	}
	
	public void mouseReleased(int button, int x, int y) {
		if(vScroller != null){
			vScroller.mouseReleased(button, x, y);
		}

		if(hScroller != null){
			hScroller.mouseReleased(button, x, y);
		}
		
		super.mouseReleased(button, 
				x - (int) (hScroller != null ? (contentWidth - getWidth()) * hScroller.getValue() : 0), 
				y - (int) (vScroller != null ? (contentHeight - getHeight()) * vScroller.getValue() : 0));
	}
	
	private void setVerticalScroller(){
		vScroller = new VerticalScroller(container, getWidth() * 0.1f, getHeight());
		vScroller.setPosition((getWidth() - vScroller.getWidth()), 0);
		vScroller.setContentHeight(contentHeight);
		addChild(vScroller);
	}
	
	private void setHorizontalScroller(){
		hScroller = new HorizontalScroller(container, getWidth(), getHeight() * 0.1f);
		hScroller.setPosition(0, (getHeight() - hScroller.getHeight()));
		hScroller.setContentWidth(contentWidth);
		addChild(hScroller);
	}
	
}
