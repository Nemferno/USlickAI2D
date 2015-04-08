package org.xodia.usai2d;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.xodia.usai2d.Button.OnClickListener;

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
		TextField edit = new TextField(gc, 100, 100, 100, 50);
		addUI(edit);
		registerKeyUI(edit);
		Button testButton = new ToggleButton(gc, "HELLO", 32, 32, 100, 100);
		testButton.setOnClickListener(new OnClickListener() {
			public void onClick(int button) {
				System.out.println("Test One");
			}
		});
		//addUI(testButton);
		
		//Container c = new Container(200, 200, 100, 100);
		//c.setContent(new Panel(120, 120));
		//addUI(c);
		
		TextArea area = new TextArea(gc, 200, 100);
		area.setPosition(350, 200);
		addUI(area);
		registerKeyUI(area);
		
		List list = new List(gc, 300, 0, 100, 100, 6);
		list.addItem("HI");
		list.addItem("HI");
		list.addItem("HI");
		list.addItem("HI");
		list.addItem("HI");
		list.addItem("HI");
		addUI(list);
		//list.setValue(0.75f);
		
		//SelectionList list = new SelectionList(300, 0, 125, 50, 10, new OnChangedListener() {
		//	public void changedSelection(String newSelection) {
		//		System.out.println("Selected: " + newSelection);
		//	}
		//});
		//list.addItem("William");
		//list.addItem("Brian");
		//list.addItem("Jasper");
		//list.addItem("Kanako");
		//list.addItem("Robby");
		//list.addItem("William");
		//list.addItem("Jasper");
		//list.addItem("Kanako");
		//list.addItem("Robby");
		//list.addItem("Luc");
		//addUI(list);
		
		/*DialogFactory.createYesNoDialog("TESTING ! @ #", new OnClickListener() {
			public void onClick(int button) {
				System.out.println("KNOB!");
			}
		}).setPosition(gc.getWidth() / 2 - 125, gc.getHeight() / 2 - 125);*/
	}

	public static void main(String[] args) throws SlickException{
		AppGameContainer app = new AppGameContainer(new TestApp("Test UI Game"));
		app.start();
	}
	
}
