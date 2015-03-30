package org.xodia.usai2d;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * 
 * Manages what scenes to enter
 * 
 * @author Jasper Bae
 *
 */
public class StateManager {

	private static AtomicBoolean isTransitioning;
	private static AtomicInteger transitionID;
	
	static {
		isTransitioning = new AtomicBoolean();
		transitionID = new AtomicInteger();
	}
	
	public static void check(StateBasedGame sg) throws SlickException {
		if(isTransitioning.get()){
			sg.enterState(transitionID.get());
			isTransitioning.set(false);
		}
	}
	
	public static void enterState(int stateID){
		isTransitioning.set(true);
		transitionID.set(stateID);
	}
	
}
