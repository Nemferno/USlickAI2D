package org.xodia.usai2d.layout;

import java.util.Iterator;

import org.xodia.usai2d.IUserInterface;

public class HorizontalLayout implements ILayout {

	private float spacing;
	
	public HorizontalLayout(){
		this(0);
	}
	
	public HorizontalLayout(float space){
		spacing = space;
	}

	public void validateLayout(Iterator<IUserInterface> uiList, float x,
			float y, float w, float h) {
		float offsetX = 0;
		while(uiList.hasNext()){
			IUserInterface child = uiList.next();
			child.setPosition(x + offsetX + spacing, y);
			offsetX += child.getWidth();
		}
	}
	
}
