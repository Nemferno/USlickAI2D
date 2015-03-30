package org.xodia.usai2d;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for all UIs that need a key input...
 * 
 * @author Jasper Bae
 *
 */
public class KeyManager {

	private List<IUserInterface> keyList;
	private boolean isKeyFocused;
	
	private KeyManager(){ keyList = new ArrayList<>(); }
	private static class Instance { public static KeyManager instance = new KeyManager(); }
	
	public void keyPressed(int key, char c){
		for(IUserInterface ui : keyList){
			if(ui.isKeyFocused()){
				ui.keyPressed(key, c);
				break;
			}
		}
	}
	
	public void keyReleased(int key, char c){
		for(IUserInterface ui : keyList){
			if(ui.isKeyFocused()){
				ui.keyReleased(key, c);
				break;
			}
		}
	}
	
	public void mousePressed(int button, int x, int y){
		for(IUserInterface ui : keyList){
			if(x >= ui.getX() && x <= ui.getX() + ui.getWidth() &&
				y >= ui.getY() && y <= ui.getY() + ui.getHeight()){
				if(!isKeyFocused && !ui.isKeyFocused()){
					isKeyFocused = true;
					ui.setKeyFocused(true);
				}
			}else{
				if(isKeyFocused && ui.isKeyFocused()){
					isKeyFocused = false;
					ui.setKeyFocused(false);
				}
			}
		}
	}
	
	public void add(IUserInterface ui){
		keyList.add(ui);
	}
	
	public void remove(IUserInterface ui){
		if(ui.isKeyFocused()){
			isKeyFocused = false;
			ui.setKeyFocused(false);
		}
		
		keyList.remove(ui);
	}
	
	public IUserInterface getFocusedKey(){
		for(IUserInterface ui : keyList){
			if(ui.isKeyFocused()){
				return ui;
			}
		}
		
		return null;
	}
	
	public void clearKeys(){
		IUserInterface key = getFocusedKey();
		if(key != null){
			key.setKeyFocused(false);
		}
		isKeyFocused = false;
		keyList.clear();
		isKeyFocused = false;
	}
	
	public IUserInterface get(int i){
		return keyList.get(i);
	}
	
	public boolean isKeyFocused(){
		return isKeyFocused;
	}
	
	public static KeyManager getInstance(){
		return Instance.instance;
	}
	
}
