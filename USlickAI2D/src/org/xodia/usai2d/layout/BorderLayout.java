package org.xodia.usai2d.layout;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.xodia.usai2d.IUserInterface;

public class BorderLayout implements ILayout {

	public static enum Direction {
		NORTH, SOUTH, EAST, WEST, CENTER
	}
	
	private ConcurrentHashMap<IUserInterface, Direction> borderList;
	private int padding;
	
	public BorderLayout(){
		this(0);
	}
	
	public BorderLayout(int padding){
		this.padding = padding;
		
		borderList = new ConcurrentHashMap<>(6);
	}
	
	public void addBorder(IUserInterface ui, Direction d){
		borderList.put(ui, d);
	}
	
	public void removeBorder(IUserInterface ui){
		borderList.remove(ui);
	}

	/**
	 * This layout has to be more cost efficient and memory effecient!
	 */
	public void validateLayout(Iterator<IUserInterface> uiList, float x,
			float y, float w, float h) {
		int nCount = 0, sCount = 0, wCount = 0, eCount = 0, cCount = 0;
		
		Enumeration<Direction> e = borderList.elements();
		while(e.hasMoreElements()){
			switch(e.nextElement()){
			case CENTER:
				cCount++;
				break;
			case EAST:
				eCount++;
				break;
			case NORTH:
				nCount++;
				break;
			case SOUTH:
				sCount++;
				break;
			case WEST:
				wCount++;
				break;
			}
		}
		
		float westWidth = 0, westHeight = 0;
		float eastWidth = 0, eastHeight = 0;
		float northWidth = 0, northHeight = 0;
		float southWidth = 0, southHeight = 0;
		float centerWidth = 0, centerHeight = 0;
		
		// There are no south and north count, then east, west, and center height is 100%
		if(nCount == 0 && sCount == 0){
			westHeight = eastHeight = centerHeight = h;
			
			// If there are no east count then west and center count increases
			if(eCount == 0 && wCount == 0){
				centerWidth = w - padding * 2;
				centerHeight /= cCount;
				centerHeight -= padding * 2;
				eastWidth = eastHeight = westWidth = westHeight = 0;
			}else if(wCount == 0 && cCount == 0){
				eastWidth = w - padding * 2;
				eastHeight /= eCount;
				eastHeight -= padding * 2;
				centerWidth = centerHeight = westWidth = westHeight = 0;
			}else if(cCount == 0 && eCount == 0){
				westWidth = w - padding * 2;
				westHeight /= wCount;
				westHeight -= padding * 2;
				centerWidth = centerHeight = eastWidth = eastHeight = 0;
			}else{
				centerWidth = w * 0.6f - padding * 2;
				centerHeight /= cCount;
				centerHeight -= padding * 2;
				eastWidth = westWidth = w * 0.2f - padding * 2;
				eastHeight /= eCount;
				eastHeight -= padding *	2;
				westHeight /= wCount;
				westHeight -= padding * 2;
			}
		}else{
			if(nCount == 0){
				westHeight = eastHeight = centerHeight = h * 0.8f;
				
				// If there are no east count then west and center count increases
				if(eCount == 0 && wCount == 0){
					centerWidth = w - padding * 2;
					centerHeight /= cCount;
					centerHeight -= padding * 2;
					eastWidth = eastHeight = westWidth = westHeight = 0;
					southHeight = h * 0.2f;
					southHeight /= sCount;
					southHeight -= padding * 2;
				}else if(wCount == 0 && cCount == 0){
					eastWidth = w - padding * 2;
					eastHeight /= eCount;
					eastHeight -= padding * 2;
					centerWidth = centerHeight = westWidth = westHeight = 0;
					southHeight = h * 0.2f;
					southHeight /= sCount;
					southHeight -= padding * 2;
				}else if(cCount == 0 && eCount == 0){
					westWidth = w - padding * 2;
					westHeight /= wCount;
					westHeight -= padding * 2;
					centerWidth = centerHeight = eastWidth = eastHeight = 0;
					southHeight = h * 0.2f;
					southHeight /= sCount;
					southHeight -= padding * 2;
				}else if(cCount == 0 && eCount == 0 && wCount == 0){
					westWidth = eastWidth = centerWidth = westHeight = eastHeight = centerHeight = 0;
					southHeight = h;
					southHeight /= sCount;
					southHeight -= padding * 2;
				}else{
					centerWidth = w * 0.6f - padding * 2;
					centerHeight /= cCount;
					centerHeight -= padding * 2;
					eastWidth = westWidth = w * 0.2f - padding * 2;
					eastHeight /= eCount;
					eastHeight -= padding *	2;
					westHeight /= wCount;
					westHeight -= padding * 2;
					southHeight = h * 0.2f;
					southHeight /= sCount;
					southHeight -= padding * 2;
				}
				
				southWidth = w - padding * 2;
			}else if(sCount == 0){
				westHeight = eastHeight = centerHeight = h * 0.8f;
				
				// If there are no east count then west and center count increases
				if(eCount == 0 && wCount == 0){
					centerWidth = w - padding * 2;
					centerHeight /= cCount;
					centerHeight -= padding * 2;
					eastWidth = eastHeight = westWidth = westHeight = 0;
					northHeight = h * 0.2f;
				}else if(wCount == 0 && cCount == 0){
					eastWidth = w - padding * 2;
					eastHeight /= eCount;
					eastHeight -= padding * 2;
					centerWidth = centerHeight = westWidth = westHeight = 0;
					northHeight = h * 0.2f;
				}else if(cCount == 0 && eCount == 0){
					westWidth = w - padding * 2;
					westHeight /= wCount;
					westHeight -= padding * 2;
					centerWidth = centerHeight = eastWidth = eastHeight = 0;
					northHeight = h * 0.2f;
				}else if(cCount == 0 && eCount == 0 && wCount == 0){
					westWidth = eastWidth = centerWidth = westHeight = eastHeight = centerHeight = 0;
					northHeight = h;
				}else{
					centerWidth = w * 0.6f - padding * 2;
					centerHeight /= cCount;
					centerHeight -= padding * 2;
					eastWidth = westWidth = w * 0.2f - padding * 2;
					eastHeight /= eCount;
					eastHeight -= padding *	2;
					westHeight /= wCount;
					westHeight -= padding * 2;
					northHeight = h * 0.2f;
				}
				
				northWidth = w;
			}else{
				westHeight = eastHeight = centerHeight = h * 0.6f;
				
				// If there are no east count then west and center count increases
				if(eCount == 0 && wCount == 0){
					centerWidth = w - padding * 2;
					centerHeight /= cCount;
					centerHeight -= padding * 2;
					eastWidth = eastHeight = westWidth = westHeight = 0;
					northHeight = southHeight = h * 0.2f;
					southHeight /= sCount;
					northHeight /= nCount;
					southHeight -= padding * 2;
					northHeight -= padding * 2;
				}else if(wCount == 0 && cCount == 0){
					eastWidth = w - padding * 2;
					eastHeight /= eCount;
					eastHeight -= padding * 2;
					centerWidth = centerHeight = westWidth = westHeight = 0;
					northHeight = southHeight = h * 0.2f - padding * 2;
				}else if(cCount == 0 && eCount == 0){
					westWidth = w - padding * 2;
					westHeight /= wCount;
					westHeight -= padding * 2;
					centerWidth = centerHeight = eastWidth = eastHeight = 0;
					northHeight = southHeight = h * 0.2f - padding * 2;
				}else if(cCount == 0 && eCount == 0 && wCount == 0){
					westWidth = eastWidth = centerWidth = westHeight = eastHeight = centerHeight = 0;
					northHeight = southHeight = h * 0.5f - padding * 2;
				}else{
					centerWidth = w * 0.6f - padding * 2;
					centerHeight /= cCount;
					centerHeight -= padding * 2;
					eastWidth = westWidth = w * 0.2f - padding * 2;
					eastHeight /= eCount;
					eastHeight -= padding *	2;
					westHeight /= wCount;
					westHeight -= padding * 2;
					northHeight = southHeight = h * 0.2f - padding * 2;
				}
				
				northWidth = southWidth = w - padding * 2;
			}
		}
		
		int northCount = nCount, southCount = sCount;//, westCount = wCount, eastCount = eCount, centerCount = cCount;
		nCount = sCount = wCount = eCount = cCount = 0;
		
		while(uiList.hasNext()){
			IUserInterface ui = uiList.next();
			Direction dir = borderList.get(ui);
			
			switch(dir){
			case CENTER:
				ui.setPosition(x + padding + westWidth, y + (northHeight * northCount) + padding + (centerHeight * cCount));
				ui.setSize(centerWidth, centerHeight);
				cCount++;
				break;
			case EAST:
				ui.setPosition(x + w - padding - eastWidth, y + northHeight * northCount + padding + (eastHeight * eCount));
				ui.setSize(eastWidth, eastHeight);
				eCount++;
				break;
			case NORTH:
				ui.setPosition(x + padding, y + padding + (northHeight * nCount));
				ui.setSize(northWidth, northHeight);
				nCount++;
				break;
			case SOUTH:
				ui.setPosition(x + padding, y + h - ((southHeight + padding) * southCount) + (southHeight * sCount));
				ui.setSize(southWidth, southHeight);
				sCount++;
				break;
			case WEST:
				ui.setPosition(x + padding, y + northHeight * northCount + padding + (westHeight * wCount));
				ui.setSize(westWidth, westHeight);
				wCount++;
				break;
			}
		}
	}
	
	
	
}
