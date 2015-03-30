package org.xodia.usai2d.layout;

import java.util.Iterator;

import org.xodia.usai2d.IUserInterface;

public class VerticalLayout implements ILayout{

	private float spacing;
	
	public VerticalLayout(){
		this(0);
	}
	
	public VerticalLayout(float space){
		spacing = space;
	}
	
	public void validateLayout(Iterator<IUserInterface> uiList, float x,
			float y, float w, float h) {
		float offsetY = 0;
		while(uiList.hasNext()){
			IUserInterface child = uiList.next();
			child.setPosition(x, y + offsetY + spacing);
			offsetY += child.getHeight();
		}
	}

}
