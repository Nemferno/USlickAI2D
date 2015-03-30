package org.xodia.usai2d;

import org.xodia.usai2d.Button.OnClickListener;
import org.xodia.usai2d.layout.BorderLayout;

public class DialogFactory {

	private DialogFactory(){}
	
	public static Dialog createOKDialog(String message){
		return createOKDialog(message, null);
	}
	
	public static Dialog createOKDialog(String message, final OnClickListener ok){
		final Dialog dialog = new Dialog(250, 250, true, false);
		dialog.setLayout(new BorderLayout());
		
		Label mLabel = new Label(message, 0, 0, 0, 0);
		dialog.addChild(mLabel, BorderLayout.Direction.CENTER);
		
		Button okB = new Button("OK", 0, 0, 100, 100, new OnClickListener() {
			public void onClick(int button) {
				DialogManager.getInstance().disposeModal();
				
				if(ok != null)
					ok.onClick(button);
			}
		});
		dialog.addChild(okB, BorderLayout.Direction.SOUTH);
		
		return dialog;
	}
	
	public static Dialog createYesNoDialog(String message, final OnClickListener yes){
		return createYesNoDialog(message, yes, null);
	}
	
	public static Dialog createYesNoDialog(String message, final OnClickListener yes, final OnClickListener no){
		final Dialog dialog = new Dialog(250, 250, true, false);
		dialog.setLayout(new BorderLayout());
		
		Label mLabel = new Label(message, 0, 0, 0, 0);
		dialog.addChild(mLabel, BorderLayout.Direction.CENTER);
		
		Button yButton = new Button("Yes", 0, 0, 0, 0, new OnClickListener(){
			public void onClick(int button){
				DialogManager.getInstance().disposeModal();
				if(yes != null)
					yes.onClick(button);
			}
		});
		dialog.addChild(yButton, BorderLayout.Direction.SOUTH);
		Button nButton = new Button("No", 0, 0, 0, 0, new OnClickListener(){
			public void onClick(int button){
				DialogManager.getInstance().disposeModal();
				if(no != null)
					no.onClick(button);
			}
		});
		dialog.addChild(nButton, BorderLayout.Direction.SOUTH);
		
		return dialog;
	}
	
}
