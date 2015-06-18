package org.xodia.usai2d;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Handle extends BasicUserInterface {

	public Handle(GameContainer gc, float w, float h) {
		super(gc, w, h);
	}
	
	public void render(Graphics g) {
		if(isVisible()){
			g.setColor(Color.blue);
			g.fillRect(getX(), getY(), getWidth(), getHeight());
			g.setColor(DEFAULT_BORDER);
			g.drawRect(getX(), getY(), getWidth(), getHeight());
		}
	}

}
