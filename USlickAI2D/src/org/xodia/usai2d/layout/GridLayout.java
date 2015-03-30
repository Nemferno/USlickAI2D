package org.xodia.usai2d.layout;

import java.util.Iterator;

import org.xodia.usai2d.IUserInterface;

public class GridLayout implements ILayout {

	private int padding;
	private int row;
	private int col;
	
	public GridLayout(int row, int col){
		this(row, col, 0);
	}
	
	public GridLayout(int row, int col, int padding){
		this.row = row;
		this.col = col;
		this.padding = padding;
	}

	public void validateLayout(Iterator<IUserInterface> uiList, float x,
			float y, float w, float h) {
		float newWidth = w / col - padding;
		float newHeight = h / row - padding;
		for(int i = 0; i < row; i++){
			for(int j = 0; j < col && uiList.hasNext(); j++){
				IUserInterface ui = uiList.next();
				ui.setPosition(x + newWidth * j + padding, y + newHeight * i + padding);
				ui.setSize(newWidth, newHeight);
			}
		}
	}
	
}
