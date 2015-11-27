package org.xodia.usai2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.xodia.usai2d.Button.OnClickListener;
import org.xodia.usai2d.layout.BorderLayout;
import org.xodia.usai2d.layout.GridLayout;
import org.xodia.usai2d.layout.VerticalLayout;

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
		final Panel p = new Panel(gc, 500, 300);
		p.setLayout(new GridLayout(3, 1));
		p.addChild(new Panel(gc, 100, 10));
		p.addChild(new Panel(gc, 100, 10));
		
		addUI(p);
	}

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new TestApp("Test UI Game"), 800, 600, false);
		app.start();
	}
	
}

class TestStateApp extends StateBasedGame {

	public TestStateApp(String name) {
		super(name);
	}

	public void initStatesList(GameContainer gc) throws SlickException {
		addState(new TestState(0));
	}
	
	class TestState extends BasicUIGameState {

		public TestState(int id) {
			super(id);
		}

		public void init(GameContainer gc, StateBasedGame arg1)
				throws SlickException {
			DialogFactory.createOKDialog(gc, "ERROR!", "YOU DO NOT HAVE ENOUGH PLATINUMS!");
		}

		public void preUpdate(GameContainer gc, int delta)
				throws SlickException {
			
		}

		public void input(Input input) throws SlickException {
			
		}

		public void preRender(GameContainer gc, Graphics g)
				throws SlickException {
			
		}
		
	}
	
}
