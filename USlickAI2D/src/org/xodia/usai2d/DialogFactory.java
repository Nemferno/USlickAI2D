package org.xodia.usai2d;

import org.newdawn.slick.GameContainer;
import org.xodia.usai2d.Button.OnClickListener;
import org.xodia.usai2d.layout.BorderLayout;

public class DialogFactory {

	private DialogFactory(){}
	
	public static Dialog createOKDialog(GameContainer gc, String title, String message){
		return createOKDialog(gc, title, message, null);
	}
	
	public static Dialog createOKDialog(GameContainer gc, String title, String message, final OnClickListener ok){
		final Dialog dialog = new Dialog(gc, title, 250, 250, true, false);
		dialog.setLayout(new BorderLayout());
		
		TextArea mLabel = new TextArea(gc, 0, 0, dialog.getWidth(), dialog.getHeight());
		mLabel.setEditable(false);
		mLabel.setText(message);
		dialog.addChild(mLabel, BorderLayout.Direction.CENTER);
		
		Button okB = new Button(gc, "OK", 0, 0, 100, 100, new OnClickListener() {
			public void onClick(int button) {
				DialogManager.getInstance().disposeModal();
				
				if(ok != null)
					ok.onClick(button);
			}
		});
		okB.setTextOption(TextOption.CENTER);
		dialog.addChild(okB, BorderLayout.Direction.SOUTH);
		
		return dialog;
	}
	
	public static Dialog createYesNoDialog(GameContainer gc, String title, String message, final OnClickListener yes){
		return createYesNoDialog(gc, title, message, yes, null);
	}
	
	public static Dialog createYesNoDialog(GameContainer gc, String title, String message, final OnClickListener yes, final OnClickListener no){
		final Dialog dialog = new Dialog(gc, title, 250, 250, true, false);
		dialog.setLayout(new BorderLayout());
		
		TextArea mLabel = new TextArea(gc, 0, 0, dialog.getWidth(), dialog.getHeight());
		mLabel.setEditable(false);
		mLabel.setText(message);
		dialog.addChild(mLabel, BorderLayout.Direction.CENTER);
		
		Button yButton = new Button(gc, "Yes", 0, 0, 0, 0, new OnClickListener(){
			public void onClick(int button){
				DialogManager.getInstance().disposeModal();
				if(yes != null)
					yes.onClick(button);
			}
		});
		yButton.setTextOption(TextOption.CENTER);
		dialog.addChild(yButton, BorderLayout.Direction.SOUTH);
		Button nButton = new Button(gc, "No", 0, 0, 0, 0, new OnClickListener(){
			public void onClick(int button){
				DialogManager.getInstance().disposeModal();
				if(no != null)
					no.onClick(button);
			}
		});
		nButton.setTextOption(TextOption.CENTER);
		dialog.addChild(nButton, BorderLayout.Direction.SOUTH);
		
		return dialog;
	}
	
}
