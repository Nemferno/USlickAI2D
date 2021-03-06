package org.xodia.usai2d;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.xodia.usai2d.layout.BorderLayout;
import org.xodia.usai2d.layout.BorderLayout.Direction;
import org.xodia.usai2d.layout.ILayout;

public class BasicUserInterface implements IUserInterface 
{

	protected List<IUserInterface> children;
	protected GameContainer container;
	private IUserInterface parent;
	protected ILayout layout;
	
	protected Image backgroundImage;
	protected Color backgroundColor;
	protected Font font;
	
	private float x, y;
	private float offsetX, offsetY;
	private float width, height;
	private boolean isKeyFocused;
	private boolean isDisabled;
	private boolean isVisible;
	
	public BasicUserInterface(GameContainer gc)
	{
		this(gc, 0, 0, 0, 0);
	}
	
	public BasicUserInterface(GameContainer gc, float w, float h)
	{
		this(gc, 0, 0, w, h);
	}
	
	public BasicUserInterface(GameContainer gc, float x, float y, float w, float h)
	{
		container = gc;
		font = gc.getDefaultFont();
		children = new CopyOnWriteArrayList<IUserInterface>();
		offsetX = x;
		offsetY = y;
		setPosition(x,y);
		setSize(w, h);
		setVisible(true);
		setDisabled(false);
	}
	
	public void setPosition(float x, float y) 
	{
		this.x = x;
		this.y = y;
		
		validatePosition();
	}

	public void setSize(float w, float h) 
	{
		float oldW = getWidth();
		float oldH = getHeight();
		
		this.width = w;
		this.height = h;
		
		revalidateSize(oldW, oldH);
	}
	
	public void setParent(IUserInterface parent) 
	{
		this.parent = parent;
	}
	
	public void setLayout(ILayout layout)
	{
		this.layout = layout;	
	}
	
	public void setColorBackground(Color color)
	{
		backgroundColor = color;
	}
	
	/**
	 * All of the children must be disabled if the parent is disabled...
	 */
	public void setDisabled(boolean disable) 
	{
		this.isDisabled = disable;
		
		Iterator<IUserInterface> childs = children.iterator();
		while(childs.hasNext()){
			childs.next().setDisabled(disable);
		}
	}

	public void setVisible(boolean visible) 
	{
		this.isVisible = visible;
		
		for(IUserInterface ui : children){
			ui.setVisible(visible);
		}
	}

	public void setKeyFocused(boolean focus) 
	{
		this.isKeyFocused = focus;
		
		for(IUserInterface ui : children){
			ui.setKeyFocused(focus);
		}
	}
	
	public void setBackgroundImage(Image background)
	{
		backgroundImage = background;
		
/*		if(background.getWidth() != getWidth() || background.getHeight() != getHeight()){
			backgroundImage = background.getScaledCopy((int) getWidth(), (int) getHeight());
		}*/
	}
	
	public void addChild(IUserInterface ui) 
	{
		ui.setParent(this);
		ui.setPosition(getX() + ui.getX(), getY() + ui.getY());
		children.add(ui);
		
		if(layout != null)
			layout.validateLayout(getChilds(), getX(), getY(), getWidth(), getHeight());
	}
	
	public void addChild(IUserInterface ui, Direction d)
	{
		if(layout instanceof BorderLayout)
			((BorderLayout) layout).addBorder(ui, d);
		
		addChild(ui);
	}

	public void removeChild(int index) 
	{
		IUserInterface child = children.remove(index);
		if(child != null){
			child.setParent(null);
			
			validateLayout();
		}
	}

	public void removeChild(IUserInterface ui) 
	{
		if(children.remove(ui)){
			ui.setParent(null);
			
			validateLayout();
		}
	}
	
	public void clearChildren()
	{
		children.clear();
	}

	public Iterator<IUserInterface> getChilds() 
	{
		return children.iterator();
	}
	
	public IUserInterface getParent()
	{
		return parent;
	}
	
	public ILayout getLayout()
	{
		return layout;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}
	
	public boolean isKeyFocused() 
	{
		return isKeyFocused;
	}
	
	public boolean isDisabled()
	{
		return isDisabled;
	}
	
	public boolean isVisible()
	{
		return isVisible;
	}
	
	public boolean isParent()
	{
		return parent == null;
	}
	
	public boolean hasParent()
	{
		return parent != null;
	}

	public float getX() 
	{
		return x;
	}

	public float getY() 
	{
		return y;
	}

	public float getWidth() 
	{
		return width;
	}

	public float getHeight() 
	{
		return height;
	}
	
	public float getOffsetX()
	{
		return offsetX;
	}
	
	public float getOffsetY()
	{
		return offsetY;
	}
	
	public GameContainer getContainer()
	{
		return container;
	}
	
	public void validateLayout()
	{
		if(layout != null)
			layout.validateLayout(getChilds(), getX(), getY(), getWidth(), getHeight());
	}
	
	/**
	 * You can view more info about this method at {@link IUserInterface#revalidateSize(float, float)}
	 * You must use this method ONLY when you set the size of the parent.
	 */
	public void revalidateSize(float oldW, float oldH) 
	{
		// We have to get the percentage of shrinkage or enlargement
		// > 0 means shrunk & < 0 means enlargement
		float percentW = ((getWidth() - oldW) / oldW);
		float percentH = ((getHeight() - oldH) / oldH);
		Iterator<IUserInterface> list = getChilds();
		while(list.hasNext())
		{
			IUserInterface child = list.next();
			float oldCW = child.getWidth(), oldCH = child.getHeight();
			// We have to set the new size for the child
			float newCW = oldCW + (oldCW * percentW);
			float newCH = oldCH + (oldCH * percentH);
			child.setSize(newCW, newCH);
		}
		
		revalidatePosition(percentW, percentH);
	}
	
	/**
	 * Different from {@link IUserInterface#revalidatePosition(float, float)}, this takes into the
	 * account of the old width and height instead of old x and y since when resizing the parent's
	 * size, the position will not stay the same!
	 * 
	 * @param oldW
	 * Parent's old width
	 * @param oldH
	 * Parent's old height
	 */
	public void revalidatePosition(float percentW, float percentH)
	{
		Iterator<IUserInterface> list = getChilds();
		while(list.hasNext())
		{
			IUserInterface child = list.next();
			float newX = child.getX() + (child.getX() * percentW);
			float newY = child.getY() + (child.getY() * percentH);
			child.setPosition(getX() + newX, getY() + newY);
		}
	}

	/**
	 * You can view more info about this method at {@link IUserInterface#revalidatePosition(float, float)}
	 * You must use this method ONLY when you set the position of the parent
	 */
	public void validatePosition() 
	{
		// We have to retrieve the child's x & y offset
		Iterator<IUserInterface> list = getChilds();
		while(list.hasNext())
		{
			IUserInterface child = list.next();
			float cX = child.getX();
			float cY = child.getY();
			
			// Now we check if the child is in its correct position
			float correctX = getX() + child.getOffsetX();
			float correctY = getY() + child.getOffsetY();
			// If either axis are not in the correct position, then set it correctly
			if(cX != correctX || cY != correctY)
			{
				child.setPosition(correctX, correctY);
			}
		}
	}

	public void update() 
	{
		
	}

	public void render(Graphics g) 
	{
		if(isVisible){
			if(!isDisabled){
				if(backgroundColor == null)
					g.setColor(DEFAULT_BACKGROUND);
				else
					g.setColor(backgroundColor);
			}
				
			if(backgroundImage != null){
				backgroundImage.draw(getX(), getY());
			}else{
				g.fillRect(getX(), getY(), getWidth(), getHeight());
				g.setColor(DEFAULT_BORDER);
				g.drawRect(getX(), getY(), getWidth(), getHeight());
			}
			
			Iterator<IUserInterface> childs = children.iterator();
			while(childs.hasNext()){
				IUserInterface child = childs.next();
				if(child.isVisible()){
					child.render(g);
				}
			}
			
			if(isDisabled){
				g.setColor(DEFAULT_DISABLED);
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
		}
	}

	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
		if(!isDisabled && isVisible){
			Iterator<IUserInterface> it = children.iterator();
			while(it.hasNext()){
				IUserInterface ui = it.next();
				if(ui.isVisible()){
					if(newx >= ui.getX() && newx <= ui.getX() + ui.getWidth() && 
						newy >= ui.getY() && newy <= ui.getY() + ui.getHeight()){
						ui.mouseMoved(oldx, oldy, newx, newy);
					}
				}
			}
		}
	}

	public void mouseDragged(int oldx, int oldy, int newx, int newy) 
	{
		if(!isDisabled && isVisible){
			Iterator<IUserInterface> it = children.iterator();
			while(it.hasNext()){
				IUserInterface ui = it.next();
				if(ui.isVisible()){
					if(newx >= ui.getX() && newx <= ui.getX() + ui.getWidth() && 
						newy >= ui.getY() && newy <= ui.getY() + ui.getHeight()){
						ui.mouseDragged(oldx, oldy, newx, newy);
					}
				}
			}
		}
	}

	public void mousePressed(int button, int x, int y) 
	{
		if(!isDisabled && isVisible){
			Iterator<IUserInterface> it = children.iterator();
			while(it.hasNext()){
				IUserInterface ui = it.next();
				if(ui.isVisible()){
					if(x >= ui.getX() && x <= ui.getX() + ui.getWidth() && 
						y >= ui.getY() && y <= ui.getY() + ui.getHeight()){
						ui.mousePressed(button, x, y);
					}
				}
			}
		}
	}

	public void mouseReleased(int button, int x, int y) 
	{
		if(!isDisabled && isVisible){
			Iterator<IUserInterface> it = children.iterator();
			while(it.hasNext()){
				IUserInterface ui = it.next();
				if(ui.isVisible()){
					if(x >= ui.getX() && x <= ui.getX() + ui.getWidth() && 
						y >= ui.getY() && y <= ui.getY() + ui.getHeight()){
						ui.mouseReleased(button, x, y);
					}
				}
			}
		}
	}
	
	public void keyPressed(int key, char c) 
	{
		if(!isDisabled && isVisible && isKeyFocused){
			Iterator<IUserInterface> it = children.iterator();
			while(it.hasNext()){
				IUserInterface ui = it.next();
				ui.keyPressed(key, c);
			}
		}
	}

	public void keyReleased(int key, char c) 
	{
		if(!isDisabled && isVisible && isKeyFocused){
			Iterator<IUserInterface> it = children.iterator();
			while(it.hasNext()){
				IUserInterface ui = it.next();
				ui.keyReleased(key, c);
			}
		}
	}

	public String toString() 
	{
		return "[" + getX() + ", " + getY() + ", " + getWidth() + ", " + getHeight() + "]";
	}
	
}
