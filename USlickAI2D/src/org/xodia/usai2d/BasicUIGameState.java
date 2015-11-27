package org.xodia.usai2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public abstract class BasicUIGameState extends BasicGameState {

	private List<IUserInterface> uiList;
	private KeyManager kManager;
	private DialogManager dManager;
	private ToolTipManager tManager;
	private boolean isOnDialog;
	private boolean isDragOnDialog;
	private boolean isOnUI;
	private int id;
	
	public BasicUIGameState(int id){
		this.id = id;
		
		uiList = new ArrayList<IUserInterface>();
		kManager = KeyManager.getInstance();
		dManager = DialogManager.getInstance();
		tManager = ToolTipManager.getInstance();
	}
	
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.leave(container, game);
		
		uiList.clear();
		kManager.clearKeys();
		dManager.clearDialogs();
		tManager.clearToolTips();
		isOnDialog = false;
		isDragOnDialog = false;
		isOnUI = false;
	}
	
	public void update(GameContainer gc , StateBasedGame sg, int delta)
		throws SlickException {
		tManager.update();
		
		if(!dManager.isModalFocused() || isOnUI())
		{
			input(gc.getInput());
		}
		
		preUpdate(gc, delta);
		StateManager.check(sg);
	}
	
	public void render(GameContainer gc, StateBasedGame sg, Graphics g)
		throws SlickException {
		preRender(gc, g);
		
		for(IUserInterface ui : uiList){
			if(ui.isVisible())
				ui.render(g);
		}
		
		Iterator<Dialog> it = dManager.getDialogs();
		while(it.hasNext()){
			Dialog d = it.next();
			if(d != dManager.getFocusedDialog()){
				d.render(g);
			}
		}
		
		if(dManager.isFocused()){
			Dialog d = dManager.getFocusedDialog();
			if(d.isModal()){
				g.setColor(new Color(84, 84, 84, 150));
				g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
				
				d.render(g);
			}else{
				d.render(g);
			}
		}
		
		if(tManager.getFocusedToolTip() != null){
			tManager.getFocusedToolTip().render(g);
		}
	}
	
	public void registerKeyUI(IUserInterface ui){
		kManager.add(ui);
	}
	
	public void unregisterKeyUI(IUserInterface ui){
		kManager.remove(ui);
	}
	
	public void addUI(IUserInterface ui){
		uiList.add(ui);
	}
	
	public void removeUI(int index){
		uiList.remove(index);
	}
	
	public void removeUI(IUserInterface ui){
		uiList.remove(ui);
	}
	
	public boolean isOnUI(){
		return isOnUI;
	}
	
	public boolean isKeyOnUI(){
		return kManager.isKeyFocused();
	}
	
	public int getID(){
		return id;
	}
	
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		super.mouseDragged(oldx, oldy, newx, newy);
		
		if(!dManager.isModalFocused()){
			// We have to make sure if it is on a dialog
			Iterator<Dialog> it = dManager.getDialogs();
			while(it.hasNext()){
				Dialog d = it.next();
				if(newx >= d.getX() && newx <= d.getX() + d.getWidth() &&
					newy >= d.getY() && newy <= d.getY() + d.getHeight()){
					isDragOnDialog = true;
					break;
				}
			}
			
			if(!isDragOnDialog){
				for(IUserInterface ui : uiList){
					if(newx >= ui.getX() && newx <= ui.getX() + ui.getWidth() &&
						newy >= ui.getY() && newy <= ui.getY() + ui.getHeight()){
						ui.mouseDragged(oldx, oldy, newx, newy);
					}
				}
			}else{
				Dialog focus = dManager.getFocusedDialog();
				if(focus != null)
					focus.mouseDragged(oldx, oldy, newx, newy);
			}
		}else{
			dManager.getFocusedDialog().mouseDragged(oldx, oldy, newx, newy);
		}
		
		return;
	}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		super.mouseMoved(oldx, oldy, newx, newy);
		
		tManager.mouseMoved(oldx, oldy, newx, newy);
		
		if(!dManager.isModalFocused()){
			Dialog focus = dManager.getFocusedDialog();
			if(focus != null)
				focus.mouseMoved(oldx, oldy, newx, newy);
			
			for(IUserInterface ui : uiList){
				if(newx >= ui.getX() && newx <= ui.getX() + ui.getWidth() &&
					newy >= ui.getY() && newy <= ui.getY() + ui.getHeight()){
					ui.mouseMoved(oldx, oldy, newx, newy);
				}
			}
		}else{
			dManager.getFocusedDialog().mouseMoved(oldx, oldy, newx, newy);
		}
		
		return;
	}
	
	public void mousePressed(int button, int x, int y) {
		super.mousePressed(button, x, y);
		
		kManager.mousePressed(button, x, y);	
		
		if(!dManager.isModalFocused()){
			Iterator<Dialog> it = dManager.getDialogs();
			while(it.hasNext()){
				Dialog d = it.next();
				if(x >= d.getX() && x <= d.getX() + d.getWidth() &&
					y >= d.getY() && y <= d.getY() + d.getHeight()){
					if(!d.isFocused()){
						if(dManager.isFocused()){
							// Reinstate focus
							Dialog focus = dManager.getFocusedDialog();
							focus.setFocused(false);
							d.setFocused(true);
							dManager.assignFocusedDialogTop();
						}else{
							d.setFocused(true);
							dManager.setFocused(true);
							dManager.assignFocusedDialogTop();
						}
					}
					
					isOnDialog = true;
					
					break;
				}
			}
			
			if(!isOnDialog){
				for(IUserInterface ui : uiList){
					if(x >= ui.getX() && x <= ui.getX() + ui.getWidth() &&
						y >= ui.getY() && y <= ui.getY() + ui.getHeight()){
						isOnUI = true;
						ui.mousePressed(button, x, y);
					}
				}
			}else{
				Dialog d = dManager.getFocusedDialog();
				if(d != null)
					d.mousePressed(button, x, y);
			}
		}else{
			dManager.getFocusedDialog().mousePressed(button, x, y);
			isOnUI = true;
		}
		
		return;
	}
	
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
		
		if(!dManager.isModalFocused()){
			Dialog focus = dManager.getFocusedDialog();
			if(focus != null)
				focus.mouseReleased(button, x, y);
			
			for(IUserInterface ui : uiList){
				if(x >= ui.getX() && x <= ui.getX() + ui.getWidth() &&
					y >= ui.getY() && y <= ui.getY() + ui.getHeight()){
					ui.mouseReleased(button, x, y);
				}
			}
			
			isOnUI = false;
			isOnDialog = false;
			isDragOnDialog = false;
		}else{
			dManager.getFocusedDialog().mouseReleased(button, x, y);
			isOnUI = false;
		}
		
		return;
	}
	
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		
		if(key == Input.KEY_ESCAPE){
			if(dManager.isFocused()){
				Dialog d = dManager.getFocusedDialog();
				if(d.canExitOnESC()){
					if(d.isModal()){
						dManager.disposeModal();
					}else{
						d.dispose();
					}
				}
			}
		}
		
		kManager.keyPressed(key, c);
	}
	
	public void keyReleased(int key, char c) {
		super.keyReleased(key, c);
		
		kManager.keyReleased(key, c);
	}
	
	public abstract void preUpdate(GameContainer gc, int delta) throws SlickException;
	public abstract void input(Input input) throws SlickException;
	public abstract void preRender(GameContainer gc, Graphics g) throws SlickException;
	
}
