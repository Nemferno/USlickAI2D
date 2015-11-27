package org.xodia.usai2d.layout;

import java.util.Iterator;

import org.xodia.usai2d.IUserInterface;

public class VerticalLayout implements ILayout
{

	private float spacing;
	
	public VerticalLayout()
	{
		this(0);
	}
	
	public VerticalLayout(float space)
	{
		this.spacing = space;
	}
	
	public void validateLayout(Iterator<IUserInterface> uiList, float parentX,
			float parentY, float parentWidth, float parentHeight) 
	{
		float offsetY = spacing;
		while(uiList.hasNext())
		{
			IUserInterface child = uiList.next();
			child.setPosition(parentX, parentY + offsetY + spacing);
			offsetY += child.getHeight();
		}
	}

}
