package org.xodia.usai2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.xodia.usai2d.Button.OnClickListener;
import org.xodia.usai2d.layout.BorderLayout;

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
		TextArea area = new TextArea(gc, 100, 100, 200, 200);
		addUI(area);
		registerKeyUI(area);
		area.setText("HELLO WORLD I AM HERE TO ANNOUNCE YOUR ARRIVAL TO BUENOS AIRES YEAH YEAH YEAH!");
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
			final Dialog dialog = new Dialog(gc, 250, 250, true, false);
			dialog.setLayout(new BorderLayout());
			
			final TextField edit = new TextField(gc, 0, 0, 0, 0);
			dialog.addChild(edit, BorderLayout.Direction.CENTER);
			
			Label label = new Label(gc, "Enter new username:", 0, 0, 0, 0);
			dialog.addChild(label, BorderLayout.Direction.NORTH);
			
			Button button = new Button(gc, "Enter!", 0, 0, 0, 0, new OnClickListener() {
				public void onClick(int button) {
					if(!edit.getText().trim().equals("")){
						if(edit.getText().contains(" ")){
						}else{
						}
					}else{
					}
				}
			});
			dialog.addChild(button, BorderLayout.Direction.SOUTH);
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
