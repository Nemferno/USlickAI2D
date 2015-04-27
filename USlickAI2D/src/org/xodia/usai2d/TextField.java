package org.xodia.usai2d;

import org.lwjgl.Sys;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class TextField extends BasicUserInterface{
	
	private final int KEY_REPEAT_INTERVAL = 50;
	private final int INIT_KEY_REPEAT_INTERVAL = 400;
	
	private long repeatTimer;
	
	private StringBuffer text;
	//private String wrappedText;
	
	private boolean isEditable;
	//private boolean wordWrap;
	
	private int cPosition;
	private int maxChars;
	//private int row;
	//private int stringHeight;
	private int lastKey;
	
	private char lastChar;
	
	public TextField(GameContainer gc, float x, float y, float w, float h) {
		super(gc, x, y, w, h);
		
		text = new StringBuffer();
		setMaxCharacters(32);
		setEditable(true);
		//setWordWrap(false);
	}
	
	private void paste(){
		String copy = Sys.getClipboard();
		//text = text.substring(0, cPosition) + copy + text.substring(cPosition, text.length());
		text.insert(cPosition, copy);
		cPosition = cPosition + copy.length();
		
		if(text.length() > maxChars){
			//text = text.substring(maxChars);
			String temp = text.substring(maxChars);
			text.setLength(0);
			text.append(temp);
			cPosition = maxChars;
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
			
			if(key == Input.KEY_BACK){
				if(cPosition == text.length()){
					if(cPosition > 0){
						//text = text.substring(0, cPosition - 1);
						text.deleteCharAt(cPosition - 1);
						cPosition = text.length();
					}
				}else if(cPosition != text.length()){
					//text = text.substring(0, cPosition - 1) + text.substring(cPosition, text.length());
					text.deleteCharAt(cPosition - 1);
					cPosition--;
				}
			}else if(key == Input.KEY_DELETE){
				if(cPosition != text.length()){
					//text = text.substring(0, cPosition) + text.substring(cPosition + 1, text.length());
					text.deleteCharAt(cPosition);
				}
			}else if(key == Input.KEY_LEFT){
				if(cPosition > 0){
					cPosition--;
				}
			}else if(key == Input.KEY_RIGHT){
				if(cPosition < text.length()){
					cPosition++;
				}
			}else{
				if(text.length() < maxChars){
					if(Character.isLetterOrDigit(c) || CharacterUtil.isPunctuation(c)){
						//text = text.substring(0, cPosition) + c + text.substring(cPosition, text.length());
						text.insert(cPosition, c);
						cPosition++;
					}else if(key == Input.KEY_SPACE){
						//if(cPosition == text.length()){
							//text += " ";
							text.insert(cPosition, ' ');
						/*}else{
							//text = text.substring(0, cPosition) + " " + text.substring(cPosition, text.length());
						}*/
						
						cPosition++;
					}
				}
			}
		}
	}
	
	public void render(Graphics g) {
		super.render(g);
		
		if(lastKey != -1){
			if(container.getInput().isKeyDown(lastKey)){
				if(repeatTimer < System.currentTimeMillis()){
					repeatTimer = System.currentTimeMillis() + KEY_REPEAT_INTERVAL;
					keyPressed(lastKey, lastChar);
				}
			}else{
				lastKey = -1;
			}
		}
		
		g.setColor(DEFAULT_TEXT);
		Rectangle oldClip = g.getWorldClip();
		g.setWorldClip(getX(), getY(), getWidth(), getHeight());
		StringBuffer temp = new StringBuffer();
		for(int i = 0; i < text.length(); i++){
			char c = text.charAt(i);
			if(!Character.isLetterOrDigit(c)){
				temp.append("_");
			}else{
				temp.append(c);
			}
		}
		
		//System.out.println(temp.length());
		int cpos = font.getWidth(temp.substring(0, cPosition));
		int tx = 0;
		
		if(cpos > getWidth()){
			tx = (int) getWidth() - cpos - font.getWidth("|");
		}
			
		g.translate(tx, 0);
		g.drawString(getText(), getX() + 1, getY() + 1);
	
		if(isKeyFocused() && isEditable)
			g.drawString("|", getX() + cpos - 5, getY() + 1);
		g.translate(-tx, 0);
		
		g.setWorldClip(oldClip);
		//}
	}/*
	
	private void wordWrap(Font f){
		boolean startsWithSpace = false, endsWithSpace = false;
		wrappedText = text;
		
		if(wrappedText.startsWith(" "))
			startsWithSpace = true;
		if(wrappedText.endsWith(" "))
			endsWithSpace = true;
		
		// Jasper Bae is awesome
		// 4 words
		// 3 spaces in between
		String[] words = wrappedText.split(" ");
		int numOfSpaces = words.length - 1;
		int currentNumSpaces = 0;
		wrappedText = "";
		String temp = "";
		
		if(startsWithSpace){
			temp += " ";
		}
		
		for(String s : words){
			if(f.getWidth(temp) >= getWidth()){
				wrappedText += temp + '\n';
				temp = "";
			}
			
			String oldTemp = temp;
			temp += s;
			
			// Check if the temp has reached its width max!
			// If it did, add a \n and then erase it and then add it to tempString
			// which will add it to the wrappedText...
			if(f.getWidth(temp) >= getWidth()){
				wrappedText += oldTemp + '\n';
				temp = s;
			}
			
			if(currentNumSpaces++ < numOfSpaces){
				temp += " "; 
			}
			
			if(f.getWidth(temp) >= getWidth()){
				wrappedText += temp + '\n';
				temp = "";
			}
		}
		
		if(endsWithSpace){
			temp += " ";
		}
		
		// Also make sure that this space doesn't go over the width!
		if(f.getWidth(temp) >= getWidth()){
			wrappedText += temp + '\n';
			temp = "";
		}else{
			wrappedText += temp;
		}
		
		row = 1;
		int numNs = 0;
		
		// Get how many rows of lines we have...
		for(int i = 0; i < wrappedText.length(); i++){
			if(wrappedText.charAt(i) == '\n'){
				numNs++;
				row++;
				continue;
			}
		}
		
		stringHeight = row * f.getLineHeight();
		
		if(row <= numNs){
			row = numNs + 1;
		}
	}*/
	
	public void setText(String text){
		this.text.setLength(0);
		this.text.append(text);
		cPosition = this.text.length();
	}
	
	public void setEditable(boolean editable){
		this.isEditable = editable;
	}
	
	/*public void setWordWrap(boolean wordWrap){
		this.wordWrap = wordWrap;
	}*/
	
	public void setMaxCharacters(int maxC){
		this.maxChars = maxC;
	}
	
	public String getText(){
		return text.toString();
	}
	
	/*public String getWrappedText(){
		return wrappedText;
	}*/
	
	public int getMaxCharacters(){
		return maxChars;
	}
	
	public boolean isEditable(){
		return isEditable;
	}
	
	/*public boolean isWordWrap(){
		return wordWrap;
	}
	
	public int getTextHeight(){
		return stringHeight;
	}*/

}
