package org.xodia.usai2d;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class ToolTipManager {

	private ConcurrentHashMap<IUserInterface, ToolTip> toolList;
	private ToolTip visibleTool;
	
	private static class Instance { public static final ToolTipManager instance = new ToolTipManager(); }
	private ToolTipManager(){
		toolList = new ConcurrentHashMap<>();
	}
	
	public void addToolTip(IUserInterface parent, ToolTip tool){
		toolList.put(parent, tool);
		tool.setVisible(false);
	}
	
	public void removeToolTip(IUserInterface parent){
		toolList.remove(parent);
	}
	
	public void clearToolTips(){
		visibleTool = null;
		toolList.clear();
	}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy){
		Enumeration<IUserInterface> e = toolList.keys();
		while(e.hasMoreElements()){
			IUserInterface ui = e.nextElement();
			if(ui.isVisible() && !ui.isDisabled()){
				if(newx >= ui.getX() && newx <= ui.getX() + ui.getWidth() &&
					newy >= ui.getY() && newy <= ui.getY() + ui.getHeight()){
					if(visibleTool != null){
						if(visibleTool != toolList.get(ui)){
							visibleTool.setVisible(false);
							visibleTool = toolList.get(ui);
							visibleTool.setVisible(true);
						}
					}else{
						visibleTool = toolList.get(ui);
						visibleTool.setVisible(true);
					}
				}else{
					if(visibleTool == toolList.get(ui)){
						visibleTool.setVisible(false);
						visibleTool = null;
					}
				}
				
				if(visibleTool != null){
					if(newx + visibleTool.getWidth() >= visibleTool.getContainer().getWidth()){
						visibleTool.setPosition(newx - visibleTool.getWidth(), newy);
					}else{
						visibleTool.setPosition(newx, newy);
					}
				}
			}else{
				if(visibleTool == toolList.get(ui)){
					visibleTool.setVisible(false);
					visibleTool = null;
				}
			}
		}
	}
	
	public ToolTip getFocusedToolTip(){
		return visibleTool;
	}
	
	public static ToolTipManager getInstance(){
		return Instance.instance;
	}
	
}
