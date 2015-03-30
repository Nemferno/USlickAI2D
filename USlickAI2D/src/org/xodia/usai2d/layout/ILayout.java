package org.xodia.usai2d.layout;

import java.util.Iterator;

import org.xodia.usai2d.IUserInterface;

public interface ILayout {

	void validateLayout(Iterator<IUserInterface> uiList, float x, float y, float w, float h);
	
}
