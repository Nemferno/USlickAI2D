package org.xodia.usai2d;

import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * 
 * The IUserInterface defines the buildings blocks for all
 * user interfaces that the programmer wishes to make.
 * 
 * @author Jasper Bae
 * @since 3/22/15
 * @version 1
 *
 */
public interface IUserInterface {


	public static final int EXITED_ON_PRESSED = 1,
							EXITED_ON_RELEASED = 2,
							EXITED_ON_MOVED = 3,
							EXITED_ON_DRAGGED = 4;
	
	Color DEFAULT_BORDER = Color.black;
	Color DEFAULT_BACKGROUND = Color.gray;
	Color DEFAULT_TEXT = Color.white;
	Color DEFAULT_DISABLED = Color.darkGray;
	
	/**
	 * Sets the x and y position of the interface
	 * @param x
	 * New x position
	 * @param y
	 * New y position
	 */
	void setPosition(float x, float y);
	/**
	 * Sets the size of the interface
	 * @param w
	 * New width
	 * @param h
	 * New height
	 */
	void setSize(float w, float h);
	/**
	 * Sets the parent of the interface
	 * @param parent
	 * Parent of the interface
	 */
	void setParent(IUserInterface parent);
	/**
	 * Different from @see {@link IUserInterface#setVisible(boolean)}, you do not necessarily
	 * have to turn off the interface disability in order to take out the input events and update events
	 * of the interface.
	 * @param disable
	 * No longer updates or listens to events
	 */
	void setDisabled(boolean disable);
	/**
	 * Different from @see {@link IUserInterface#setDisabled(boolean)}, you can turn off the
	 * visibility of the user interface and the interface will no longer listen to input events
	 * and update itself
	 * @param visible
	 * No longer renderable
	 */
	void setVisible(boolean visible);
	/**
	 * Is the user interface's keys are focused and listened to?
	 * @param focus
	 * Is the user interface being focused
	 */
	void setKeyFocused(boolean focus);
	/**
	 * Is the user interface's mouse inputs focused and listened to?
	 * @param focus
	 * Is the user interface being focused
	 */
	void setMouseFocused(boolean focus);
	/**
	 * Assigns the Image to the Background Image
	 * @param background
	 * The image for the background of the UI
	 */
	void setBackgroundImage(Image image);
	
	/**
	 * Adds the child to the user interface. The child's x & y position is set by adding it upon
	 * the origin, the parent's x & y positions.
	 * @param ui
	 * The new child of the user interface
	 */
	void addChild(IUserInterface ui);
	/**
	 * Removes the child from the index
	 * @param index
	 * The position of the child in the list
	 */
	void removeChild(int index);
	/**
	 * Removes the child from the reference object
	 * @param ui
	 * Reference object of the child
	 */
	void removeChild(IUserInterface ui);
	/**
	 * Returns the list of the children
	 * @return
	 * Returns an iterator for the list containing the parent's children
	 */
	Iterator<IUserInterface> getChilds();
	/**
	 * Returns if the interface is focused
	 * @return
	 * Is the interface focused?
	 */
	boolean isKeyFocused();
	/**
	 * Returns if the interface is focused
	 * @return
	 * Is the interface focused?
	 */
	boolean isMouseFocused();
	/**
	 * Returns if the user interface is disabled
	 * @return
	 * Is disabled?
	 */
	boolean isDisabled();
	/**
	 * Returns if the user interface is visible
	 * @return
	 * Is visible?
	 */
	boolean isVisible();
	/**
	 * Returns the parent of the user interface
	 * @return
	 * Parent of the user interface
	 */
	IUserInterface getParent();
	/**
	 * Returns the x position
	 * @return
	 * x position
	 */
	float getX();
	/**
	 * Returns the y position
	 * @return
	 * y position
	 */
	float getY();
	/**
	 * Returns the width
	 * @return
	 * width
	 */
	float getWidth();
	/**
	 * Returns the height
	 * @return
	 * height
	 */
	float getHeight();
	/**
	 * Different from the revalidate(float, float, float, float), a previous version of the method.
	 * revalidate(float, float, float, float) is split into two. One is for the change in size (@see {@link IUserInterface#revalidateSize(float, float)}) and
	 * the other is for the change in position (@see {@link IUserInterface#revalidatePosition(float, float)}).
	 * The method will change will the child's size without worrying about the position. It
	 * sets the child's new width and height based by the percentage of how big or small of
	 * an impact is has in terms of the proportion.
	 * @param oldW
	 * the parent's old width
	 * @param oldH
	 * the parent's old height
	 */
	void revalidateSize(float oldW, float oldH);
	/**
	 * Different from the revalidate(float, float, float, float), a previous version of the method.
	 * revalidate(float, float, float, float) is split into two. One is for the change in size (@see {@link IUserInterface#revalidateSize(float, float)}) and
	 * the other is for the change in position (@see {@link IUserInterface#revalidatePosition(float, float)}).
	 * The method will change will the child's size without worrying about the position. It
	 * sets the child's new width and height based by the percentage of how big or small of
	 * an impact is has in terms of the proportion.
	 * @param oldW
	 * the parent's old width
	 * @param oldH
	 * the parent's old height
	 */
	void revalidatePosition(float oldX, float oldY);
	/**
	 * New functionality from the old one. This update is for when the user interface requires
	 * to check a flag or a condition. The old library had to use events and listeners to do it
	 * for you, but this is more effective.
	 */
	void update();
	/**
	 * Renders the user interface and its children
	 * @param g
	 * Graphics object
	 */
	void render(Graphics g);
	
	void mouseMoved(int oldx, int oldy, int newx, int newy);
	void mouseDragged(int oldx, int oldy, int newx, int newy);
	void mousePressed(int button, int x, int y);
	void mouseReleased(int button, int x, int y);
	void mouseExited(int type);
	void keyPressed(int key, char c);
	void keyReleased(int key, char c);
}
