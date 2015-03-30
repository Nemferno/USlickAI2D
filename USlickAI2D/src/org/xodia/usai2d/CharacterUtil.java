package org.xodia.usai2d;

public class CharacterUtil {

	private CharacterUtil(){}
	
	public static boolean isPunctuation(char c){
		if(c == ',' ||
			c == '.' ||
			c == ';' ||
			c == ':' ||
			c == '!' ||
			c == '?' ||
			c == '/' ||
			c == '\\'){
			return true;
		}else{
			return false;
		}
	}
	
}
