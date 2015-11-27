package org.xodia.usai2d.layout;

import java.util.Iterator;

import org.xodia.usai2d.IUserInterface;

public class GridLayout implements ILayout {

	private int row;
	private int col;
	
	public GridLayout(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	public void validateLayout(Iterator<IUserInterface> uiList, float x,
			float y, float w, float h) 
	{
		float newWidth = w / col;
		float newHeight = h / row;
		for(int i = 0; i < row; i++)
		{
			for(int j = 0; j < col && uiList.hasNext(); j++)
			{
				IUserInterface ui = uiList.next();
				ui.setSize(newWidth, newHeight);
				ui.setPosition(x + newWidth * j, y + newHeight * i);
			}
		}
	}
	
}
