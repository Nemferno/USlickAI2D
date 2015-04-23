package org.xodia.usai2d;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class TextArea extends BasicUserInterface {

	private final int KEY_REPEAT_INTERVAL = 50;
	private final int INIT_KEY_REPEAT_INTERVAL = 400;
	
	private String text;
	private Container cont;
	private Canvas canv;
	
	private boolean isEditable;
	
	private char lastChar;
	private long repeatTimer;
	protected char[][] charPosition;
	private int maxCharacters;
	private int posX, posY; // Position in the Text Area
	private int cpos; // TRUE Position in the String
	private int lastKey;
	
	public TextArea(GameContainer gc, float x, float y, float w, float h){
		super(gc, x, y, w, h);
		
		cont = new Container(gc, 0, 0, w, h);
		addChild(cont);
		canv = new Canvas(gc, w, h);
		cont.setContent(canv);
		
		maxCharacters = 125;
		text = "";
		isEditable = true;
		
		int width = (int) (w / font.getWidth("A")) + 1;
		int height = 0;
		boolean isDone = false;
		int currentChar = 0;
		
		while(!isDone){
			currentChar += width;
			if(currentChar > maxCharacters){
				isDone = true;
			}else{
				height++;
			}
		}
		
		charPosition = new char[1 + height][width];
	}
	
	private void paste(){
		String copy = Sys.getClipboard();
		text = text.substring(0, cpos) + copy + text.substring(cpos, text.length());
		cpos = cpos + copy.length();
		
		if(text.length() > maxCharacters){
			text = text.substring(maxCharacters);
			cpos = maxCharacters;
		}
	}
	
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);
		
		if(isEditable){
			if(key != -1){
				if(key == Input.KEY_V && (container.getInput().isKeyDown(Input.KEY_LCONTROL) || container.getInput().isKeyDown(Input.KEY_RCONTROL))){
					paste();
					return;
				}
				
				if(container.getInput().isKeyDown(Input.KEY_LCONTROL) || container.getInput().isKeyDown(Input.KEY_RCONTROL) || 
				   container.getInput().isKeyDown(Input.KEY_LALT) || container.getInput().isKeyDown(Input.KEY_RALT)){
					return;
				}
			}
			
			if(lastKey != key){
				lastKey = key;
				lastChar = c;
				repeatTimer = System.currentTimeMillis() + INIT_KEY_REPEAT_INTERVAL;
			}else{
				repeatTimer = System.currentTimeMillis() + KEY_REPEAT_INTERVAL;
			}
			
			if(key == Input.KEY_LEFT){
				if(cpos > 0){
					if(posY == 0 ? posX > 0 : posX > 1){
						posX--;
					}else{
						if(posY > 0){
							posY--;
							posX = charPosition[posY].length - 1;
						}
					}
					
					cpos--;
				}
			}else if(key == Input.KEY_RIGHT){
				if(cpos < text.length()){
					if(posX < charPosition[posY].length - 1){
						posX++;
					}else{
						if(posY < charPosition.length - 1){
							posY++;
							posX = 1;
						}
					}
					
					cpos++;
				}
			}else if(key == Input.KEY_UP){
				int cNum = charPosition[0].length - 1;
				if(posY > 0){
					cpos -= cNum;
					posY--;
				}
			}else if(key == Input.KEY_DOWN){
				// Find out the future pos
				// Get the number of chars on current row
				int cNum = charPosition[0].length - 1;
				
				if(cpos + cNum <= text.length()){
					cpos += cNum;
					if(posY < charPosition.length - 1){
						posY++;
					}
				}
			}else if(key == Input.KEY_BACK){
				if(cpos == text.length()){
					if(cpos > 0){
						text = text.substring(0, cpos - 1);
						cpos = text.length();
					}
				}else if(cpos != text.length()){
					text = text.substring(0, cpos - 1) + text.substring(cpos, text.length());
					cpos--;
				}
				
				if(posY == 0 ? posX > 0 : posX > 1){
					posX--;
				}else{
					if(posY > 0){
						posY--;
						posX = charPosition[posY].length - 1;
					}
				}
			}else if(key == Input.KEY_DELETE){
				if(cpos != text.length()){
					text = text.substring(0, cpos) + text.substring(cpos + 1, text.length());
				}
			}else{
				if(text.length() < maxCharacters){
					if(Character.isLetterOrDigit(c) || CharacterUtil.isPunctuation(c)){
						text = text.substring(0, cpos) + c + text.substring(cpos, text.length());
						cpos++;
						
						if(posX < charPosition[posY].length - 1){
							posX++;
						}else{
							if(posY < charPosition.length - 1){
								posY++;
								posX = 1;
							}
						}
					}else if(key == Input.KEY_SPACE){
						if(cpos == text.length()){
							text += " ";
						}else{
							text = text.substring(0, cpos) + " " + text.substring(cpos, text.length());
						}
						
						cpos++;
						
						if(posX < charPosition[posY].length - 1){
							posX++;
						}else{
							if(posY < charPosition.length - 1){
								posY++;
								posX = 1;
							}
						}
					}
				}
			}
		}
		
	}
	
	private int revalidateCharacters(){
		int x = 0, y = 0;
		int textHeight = font.getHeight("A");
		int textWidth = font.getWidth("A");
		int currentWidth = 0;
		int currentHeight = textHeight;
		char[] textCharArray = text.toCharArray();
		int offset = 0;
		charPosition = new char[charPosition.length][charPosition[0].length];
		for(int i = 0; i < textCharArray.length; i++){
			currentWidth += textWidth;
			
			if(currentWidth >= getWidth() || textCharArray[i] == '\n'){
				y++;
				x = 0;
				currentWidth = textWidth;
				currentHeight += textHeight;
				
				if(currentHeight > getHeight()){
					offset = (int) (currentHeight - getHeight());
				}
				
				if(textCharArray[i] == '\n'){
					currentWidth = 0;
					continue;
				}
			}

			charPosition[y][x++] = textCharArray[i];
		}
		
		return offset;
	}
	
	private void resizeCanvas(int offset){
		cont.removeChild(canv);
		canv.setSize(canv.getWidth(), canv.getHeight() + offset);
		cont.setContent(canv);
	}
	
	private class Canvas extends Panel {

		public Canvas(GameContainer gc, float w, float h) {
			super(gc, w, h);
		}
		
		public void render(Graphics g) {
			super.render(g);
			
			if(lastKey != -1){
				if(container.getInput().isKeyDown(lastKey)){
					if(repeatTimer < System.currentTimeMillis()){
						repeatTimer = System.currentTimeMillis() + KEY_REPEAT_INTERVAL;
						TextArea.this.keyPressed(lastKey, lastChar);
					}
				}else{
					lastKey = -1;
				}
			}
			
			int offset = revalidateCharacters();
			if(offset != 0){
				resizeCanvas(offset);
			}
			
			Rectangle oldClip = g.getWorldClip();
			g.setWorldClip(getX() - 1, getY() - 1, getWidth() + 1, getHeight() + 1);
			
			int cposX = g.getFont().getWidth("A") * posX;
			int cposY = g.getFont().getHeight("A") * posY;
			int ty = 0;
			
			if(cposY > getHeight()){
				ty = (int) getHeight() - cposY - g.getFont().getHeight("A");
			}
			
			g.translate(0, ty);
			g.setColor(IUserInterface.DEFAULT_TEXT);
			
			int textWidth = g.getFont().getWidth("A");
			int textHeight = g.getFont().getHeight("A");
			for(int y = 0; y < charPosition.length; y++){
				for(int x = 0; x < charPosition[y].length; x++){
					g.drawString(String.valueOf(charPosition[y][x]), getX() + x * textWidth, getY() + y * textHeight);
					//System.out.print(charPosition[y][x]);
				}
				//System.out.println();
			}
			
			if(isKeyFocused() && isEditable){
				g.drawString("|", getX() + cposX - (g.getFont().getWidth("A") / 2), getY() + cposY);
			}
			
			g.translate(0, -ty);
			g.setWorldClip(oldClip);
		}
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setEditable(boolean editable){
		this.isEditable = editable;
	}
	
	public void setMaxCharacters(int max){
		this.maxCharacters = max;
	}
	
	public String getText(){
		return text;
	}
	
	public int getMaxCharacters(){
		return maxCharacters;
	}
	
	public boolean isEditable(){
		return isEditable;
	}
	
}
