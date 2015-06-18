package org.xodia.usai2d;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

/**
 * 
 * Creates a frame that prevents any other user interface and update methods
 * to work until the dialog is deleted. It can be modal or not. Being modal
 * will do the following things previously mentioned.
 * 
 * There's two different kinds of managers that manages the dialog
 * FrameManager and ModalManager.
 * 
 * @author Jasper Bae
 *
 */
public class Dialog extends BasicUserInterface {

	public static final Color DEFAULT_BAR = Color.darkGray;
	public static final Color DEFAULT_DRAG = Color.red;
	
	private boolean isFocused;
	private boolean isDragged;
	private boolean canExitOnESC;
	private boolean isDraggable;
	private boolean isModal;
	
	private float dragOffsetX;
	private float dragOffsetY;
	
	private String title;
	
	private Rectangle draggableBounds;
	private Rectangle draggedBounds;
	
	private DialogListener listener;
	
	public static interface DialogListener {
		void onExit();
	}
	
	public Dialog(GameContainer gc, float w, float h){
		this(gc, "", w, h);
	}
	
	public Dialog(GameContainer gc, String title, float w, float h){
		this(gc, title, w, h, true, false);
	}
	
	public Dialog(GameContainer gc, String title, float w, float h, boolean isModal, boolean isDraggable){
		super(gc, w, h);
		
		this.isDraggable = isDraggable;
		this.isModal = isModal;
		this.title = title;
		setPosition(gc.getWidth() / 2 - w / 2, gc.getHeight() / 2 - h / 2);
		
		if(isModal){
			DialogManager.getInstance().addModal(this);
		}else{
			DialogManager.getInstance().addDialog(this);
		}
		
		//if(isDraggable){
			draggableBounds = new Rectangle(getX(), getY(), w, h * 0.1f);
			setSize(w, h + h * 0.1f);
			draggedBounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
		//}
	}
	
	public void addChild(IUserInterface ui) {
		ui.setParent(this);
		//if(isDraggable){
			ui.setPosition(getX() + ui.getX(), (getY() + draggableBounds.getY()) + ui.getY());
		//}else{
			//ui.setPosition(getX() + ui.getX(), getY() + ui.getY());
		//}
		children.add(ui);
		
		if(getLayout() != null){
			//if(isDraggable)
				getLayout().validateLayout(getChilds(), getX(), getY() + draggableBounds.getHeight(), getWidth(), getHeight() - draggableBounds.getHeight());
			//else
				//getLayout().validateLayout(getChilds(), getX(), getY(), getWidth(), getHeight());
		}
	}
	
	public void removeChild(int index) {
		IUserInterface child = children.remove(index);
		if(child != null){
			child.setParent(null);
			
			if(getLayout() != null){
				//if(isDraggable)
					getLayout().validateLayout(getChilds(), getX(), getY() + draggableBounds.getHeight(), getWidth(), getHeight() - draggableBounds.getHeight());
				//else
					//getLayout().validateLayout(getChilds(), getX(), getY(), getWidth(), getHeight());
			}
		}
	}
	
	public void removeChild(IUserInterface ui) {
		if(children.remove(ui)){
			ui.setParent(null);
			
			if(getLayout() != null){
				//if(isDraggable)
					getLayout().validateLayout(getChilds(), getX(), getY() + draggableBounds.getHeight(), getWidth(), getHeight() - draggableBounds.getHeight());
				//else
					//getLayout().validateLayout(getChilds(), getX(), getY(), getWidth(), getHeight());
			}
		}
	}
	
	public void setDialogListener(DialogListener listener){
		this.listener = listener;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setFocused(boolean focus){
		this.isFocused = focus;
	}
	
	public void setExitOnESC(boolean exit){
		canExitOnESC = exit;
	}
	
	public void dispose(){
		if(!isModal())
			DialogManager.getInstance().removeDialog(this);
	}
	
	public DialogListener getDialogListener(){
		return listener;
	}
	
	public String getTitle(){
		return title;
	}
	
	public boolean isFocused(){
		return isFocused;
	}
	
	public boolean isModal(){
		return isModal;
	}
	
	public boolean isDraggable(){
		return isDraggable;
	}
	
	public boolean canExitOnESC(){
		return canExitOnESC;
	}

	public void render(Graphics g){
		super.render(g);
		
		//if(isDraggable){
			draggableBounds.setLocation(getX(), getY());
			g.setColor(DEFAULT_BAR);
			g.fill(draggableBounds);
			
			g.setColor(Color.white);
			Font oldFont = g.getFont();
			g.setFont(font);
			g.drawString(title, getX() + 1, getY() + 1);
			g.setFont(oldFont);
			
			if(isDragged){
				g.setColor(DEFAULT_DRAG);
				g.draw(draggedBounds);
			}
		//}
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		super.mouseDragged(oldx, oldy, newx, newy);
		
		if(isDraggable){
			if(!isDragged){
				if(oldx >= draggableBounds.getX() && oldx <= draggableBounds.getX() + draggableBounds.getWidth() &&
					oldy >= draggableBounds.getY() && oldy <= draggableBounds.getY() + draggableBounds.getHeight()){
					dragOffsetX = getX() - oldx;
					dragOffsetY = getY() - oldy;
					isDragged = true;
				}
			}else{
				draggedBounds.setLocation(newx + dragOffsetX, newy + dragOffsetY);
			}
		}
	}
	
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		
		if(button == Input.MOUSE_LEFT_BUTTON){
			if(isDraggable){
				if(isDragged){
					setPosition(draggedBounds.getX(), draggedBounds.getY());
					draggableBounds.setLocation(getX(), getY());
					isDragged = false;
				}
			}
		}
	}
	
}
