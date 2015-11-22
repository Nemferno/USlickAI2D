package org.xodia.usai2d;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * TODO: When getting mouse events, must start from the end to the back!
 * 
 * @author Jasper Bae
 *
 */
public class DialogManager {

	private List<Dialog> dialogList;
	private List<Dialog> modalList;
	
	private boolean isFocused;
	
	private DialogManager(){
		dialogList = new CopyOnWriteArrayList<Dialog>();
		modalList = new CopyOnWriteArrayList<Dialog>();
	}
	private static final class Instance { public static final DialogManager instance = new DialogManager();  }
	
	public void addModal(Dialog d){
		try{
			if(!d.isModal()){
				throw new Exception("Dialog MUST be a modal!");
			}else{
				d.setVisible(false);
				modalList.add(d);
				KeyManager.getInstance().add(d);
				if(!isFocused){
					d.setVisible(true);
					isFocused = true;
					d.setFocused(true);
				}else{
					getFocusedDialog().setVisible(false);
					getFocusedDialog().setFocused(false);
					d.setFocused(true);
					d.setVisible(true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Disposes the curent modal that is being focused on.
	 */
	public void disposeModal(){
		Dialog d = getFocusedDialog();
		if(d.isModal()){
			d.setVisible(false);
			if(d.getDialogListener() != null)
				d.getDialogListener().onExit();
			isFocused = false;
			d.setFocused(false);
			KeyManager.getInstance().remove(d);
			modalList.remove(d);
			revalidateFocus();
		}
	}
	
	public void addDialog(Dialog d){
		try{
			if(d.isModal()){
				throw new Exception("Dialog MUST not be a modal!");
			}else{
				dialogList.add(d);
				KeyManager.getInstance().add(d);
				if(!isFocused){
					isFocused = true;
					d.setFocused(true);
					assignFocusedDialogTop();
				}else{
					Dialog focused = getFocusedDialog();
					if(!focused.isModal()){
						focused.setFocused(false);
						d.setFocused(true);
						assignFocusedDialogTop();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setFocused(boolean focus){
		isFocused = focus;
	}
	
	public void clearDialogs(){
		Dialog d = getFocusedDialog();
		if(d != null)
			d.setFocused(false);
		isFocused = false;
		
		dialogList.clear();
		modalList.clear();
	}
	
	public void removeDialog(int index){
		Dialog d = dialogList.remove(index);
		if(d.isFocused()){
			if(d.getDialogListener() != null)
				d.getDialogListener().onExit();
			d.setFocused(false);
			setFocused(false);
		}
		KeyManager.getInstance().remove(d);
	}
	
	public void removeDialog(Dialog d){
		dialogList.remove(d);
		if(d.isFocused()){
			if(d.getDialogListener() != null)
				d.getDialogListener().onExit();
			d.setFocused(false);
			setFocused(false);
		}
		KeyManager.getInstance().remove(d);
		
	}
	
	public boolean isFocused(){
		return isFocused;
	}
	
	public boolean isModalFocused(){
		Dialog d = getFocusedDialog();
		if(d != null){
			if(d.isModal()){
				return true;
			}
		}
		
		return false;
	}
	
	public Iterator<Dialog> getModals(){
		return modalList.iterator();
	}
	
	public Iterator<Dialog> getDialogs(){
		return dialogList.iterator();
	}
	
	public Dialog getFocusedDialog(){
		if(modalList.size() != 0){
			Iterator<Dialog> it = getModals();
			while(it.hasNext()){
				Dialog d = it.next();
				if(d.isFocused()){
					return d;
				}
			}
		}else{
			Iterator<Dialog> it = getDialogs();
			while(it.hasNext()){
				Dialog d = it.next();
				if(d.isFocused()){
					return d;
				}
			}
		}
		
		return null;
	}
	
	public void assignFocusedDialogTop(){
		Dialog d = getFocusedDialog();
		dialogList.remove(d); // Remove it
		dialogList.add(0, d); // Re-add it to the top
	}
	
	private void revalidateFocus(){
		if(modalList.size() > 0){
			modalList.get(modalList.size() - 1).setFocused(true);
			modalList.get(modalList.size() - 1).setVisible(true);
			isFocused = true;
		}
	}
	
	public static DialogManager getInstance(){
		return Instance.instance;
	}
	
	/*public boolean mousePressed(int button, int x, int y){
		// This is only for frames!
		Dialog d = getFocusedDialog();
		if(d != null){
			if(!(x >= d.getX() && x <= d.getX() + d.getWidth() &&
				y >= d.getY() && y <= d.getY() + d.getHeight())){
				if(!d.isModal()){
					Iterator<Dialog> it = getDialogs();
					while(it.hasNext()){
						Dialog c = it.next();
						if(x >= c.getX() && x <= c.getX() + c.getWidth() &&
							y >= c.getY() && y <= c.getY() + c.getHeight()){
							c.setFocused(true);
							d.setFocused(false);
							return true;
						}
					}
				}
			}else{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean mouseReleased(int button, int x, int y){
		Dialog d = getFocusedDialog();
		if(d != null){
			if(x >= d.getX() && x <= d.getX() + d.getWidth() &&
				y >= d.getY() && y <= d.getY() + d.getHeight()){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean mouseMoved(int oldx, int oldy, int newx, int newy){
		Dialog d = getFocusedDialog();
		if(d != null){
			if(newx >= d.getX() && newx <= d.getX() + d.getWidth() &&
				newy >= d.getY() && newy <= d.getY() + d.getHeight()){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean mouseDragged(int oldx, int oldy, int newx, int newy){
		Dialog d = getFocusedDialog();
		if(d != null){
			if(newx >= d.getX() && newx <= d.getX() + d.getWidth() &&
				newy >= d.getY() && newy <= d.getY() + d.getHeight()){
				return true;
			}
		}
		
		return false;
	}*/
	
}
