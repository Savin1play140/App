package app;
import app.logger.Logger;
import app.utils.Localization;
import app.windowtypes.JFrameWindow;
import app.windowtypes.SWTWindow;
import app.windowtypes.WindowType;
import app.windowtypes.PBType.ProgressBarType;

public class Window extends Thread {
   private WindowType win;
   private String name;
   private String[] texts;
   private double x;
   private double y;
   private boolean resizable;

	public boolean createGUI(String type, String name, String[] texts, double x, double y, boolean resizable) {
	   String str;
	   switch ((str = type).hashCode()) {
	   		case 108960:
	   			if (!str.equals("new")) break; 
	   			this.win = (WindowType)new SWTWindow();
	   			this.name = name;
	   			this.texts = texts;
	   			this.x = x;
	   			this.y = y;
	   			this.resizable = resizable;
	   			start();
	   			return true;
			case 110119:
				if (!str.equals("old"));
				break;
		}
		this.win = (WindowType)new JFrameWindow();
		this.name = name;
		this.texts = texts;
		this.x = x;
		this.y = y;
		this.resizable = resizable;
		start();
		return true;
	}
   public void run() {
	   Logger.info(Localization.getText("window.init"));
     this.win.createGUI(this.name, this.texts, (float)this.x, (float)this.y, this.resizable);
   }
   public WindowType getWindow() {
     return this.win;
   }
	public static ProgressBarType sendProgressBar(String type) {
		SWTWindow sWTWindow;
		//WindowType w = null;
		String str;
		switch ((str = type).hashCode()) {
			case 108960:
				if (!str.equals("new")) break; 
				sWTWindow = new SWTWindow();
				return sWTWindow.addProgressBar();
			case 110119:
				if (!str.equals("old"));
				break;
		}
		JFrameWindow jFrameWindow = new JFrameWindow();
		return jFrameWindow.addProgressBar();
	}
}
