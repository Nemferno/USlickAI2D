package org.xodia.usai2d;


public class ToolTip extends TextArea {

	private ToolTip(IUserInterface parent, String text) {
		super(parent.getContainer(), parent.getX(), parent.getY(), 100, 100);
		
		String[] lines = text.split("\n");
		int count = 0;
		for(String line : lines){
			if(line.length() > count)
				count = line.length();
		}
		int width = (int) count + 1;
		int height = 1 + lines.length;
		
		charPosition = new char[1 + height][width];
		
		setText(text);
		setSize(width * font.getWidth("A"), height * font.getHeight("A"));
		ToolTipManager.getInstance().addToolTip(parent, this);
	}
	
	public static void createToolTip(IUserInterface parent, String text){
		new ToolTip(parent, text);
	}
	
}
