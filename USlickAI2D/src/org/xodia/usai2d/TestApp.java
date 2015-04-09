package org.xodia.usai2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class TestApp extends BasicUIGame{

	public TestApp(String title) {
		super(title);
	}

	public void preUpdate(GameContainer gc, int delta) throws SlickException {
		
	}

	public void input(Input input) throws SlickException {
		
	}

	public void preRender(GameContainer gc, Graphics g) throws SlickException {

	}

	public void init(GameContainer gc) throws SlickException {
		TextField field = new TextField(gc, 100, 100, 200, 25);
		addUI(field);
		registerKeyUI(field);
	}

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new TestApp("Test UI Game"));
		app.start();
	}
	
}
