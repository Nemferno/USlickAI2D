package org.xodia.usai2d;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

public class EditField extends BasicUserInterface{
	
	private final int KEY_REPEAT_INTERVAL = 150;
	private final int INIT_KEY_REPEAT_INTERVAL = 50;
	
	private long repeatTimer;
	
	private String text;
	private String wrappedText;
	
	private boolean isEditable;
	private boolean wordWrap;
	
	private int cPosition;
	private int maxChars;
	private int row;
	private int stringHeight;
	private int lastKey;
	
	private char lastChar;
	
	public EditField(GameContainer gc, float x, float y, float w, float h) {
		super(gc, x, y, w, h);
		
		setText("");
		setMaxCharacters(32);
		setEditable(true);
		setWordWrap(false);
	}
	
	public void keyPressed(int key, char c) {
		super.keyPressed(key, c);

		if(isEditable){
			if(lastKey != key){
				lastKey = key;
				lastChar = c;
				repeatTimer = System.currentTimeMillis() + INIT_KEY_REPEAT_INTERVAL;
			}else{
				repeatTimer = System.currentTimeMillis() + KEY_REPEAT_INTERVAL;
			}
			
			if(key == Input.KEY_BACK){
				if(cPosition != 0){
					if(cPosition == text.length()){
						text = text.substring(0, cPosition - 1);
						cPosition = text.length();
					}else if(cPosition != text.length()){
						text = text.substring(0, cPosition - 1) + text.substring(cPosition, text.length());
						cPosition--;
					}
				}
			}else if(key == Input.KEY_DELETE){
				if(cPosition != text.length()){
					text = text.substring(0, cPosition + 1) + text.substring(cPosition + 2, text.length());
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
						text = text.substring(0, cPosition) + c + text.substring(cPosition, text.length());
						cPosition++;
					}else if(key == Input.KEY_SPACE){
						if(cPosition == text.length()){
							text += " ";
						}else{
							text = text.substring(0, cPosition) + " " + text.substring(cPosition, text.length());
						}
						
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
		if(wordWrap){
			wordWrap(g.getFont());
			g.drawString(wrappedText, getX() + 1, getY() + 1);
		}else{
			Rectangle oldClip = g.getWorldClip();
			g.setWorldClip(getX(), getY(), getWidth(), getHeight());
			String temp = "";
			for(int i = 0; i < text.length(); i++){
				char c = text.charAt(i);
				if(!Character.isLetterOrDigit(c)){
					temp += "_";
				}else{
					temp += c;
				}
			}
			
			int cpos = g.getFont().getWidth(temp.substring(0, cPosition));
			int tx = 0;
			
			if(cpos > getWidth())
				tx = (int) getWidth() - cpos - g.getFont().getWidth("|");
			
			g.translate(tx, 0);
			g.drawString(text, getX() + 1, getY() + 1);
			
			if(isKeyFocused())
				g.drawString("|", getX() + cpos - 5, getY() + 1);
			g.translate(-tx, 0);
			
			g.setWorldClip(oldClip);
		}
	}
	
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
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setEditable(boolean editable){
		this.isEditable = editable;
	}
	
	public void setWordWrap(boolean wordWrap){
		this.wordWrap = wordWrap;
	}
	
	public void setMaxCharacters(int maxC){
		this.maxChars = maxC;
	}
	
	public String getText(){
		return text;
	}
	
	public String getWrappedText(){
		return wrappedText;
	}
	
	public int getMaxCharacters(){
		return maxChars;
	}
	
	public boolean isEditable(){
		return isEditable;
	}
	
	public boolean isWordWrap(){
		return wordWrap;
	}
	
	public int getTextHeight(){
		return stringHeight;
	}

}
