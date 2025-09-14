package app;

import app.utils.Localization;
import app.windowtypes.JFrameWindow;
import app.windowtypes.PBType.ProgressBarType;
import app.windowtypes.SWTWindow;
import app.windowtypes.WindowType;

public class Window extends Thread {
  private WindowType win;
  
  private String name;
  
  private String[] texts;
  
  private double x;
  
  private double y;
  
  private boolean resizable;
  
  public boolean createGUI(String type, String name, String[] texts, double x, double y, boolean resizable) {
    switch (type) {
      case "new":
        this.win = new SWTWindow();
        this.name = name;
        this.texts = texts;
        this.x = x;
        this.y = y;
        this.resizable = resizable;
        start();
        return true;
      case "old":
        break;
    } 
    this.win = new JFrameWindow();
    this.name = name;
    this.texts = texts;
    this.x = x;
    this.y = y;
    this.resizable = resizable;
    start();
    return true;
  }
  
  public void run() {
    Localization.sendText("window.init", "info");
    this.win.createGUI(this.name, this.texts, (float)this.x, (float)this.y, this.resizable);
  }
  
  public WindowType getWindow() {
    return this.win;
  }
  
  public static ProgressBarType sendProgressBar(String type) {
    SWTWindow sWTWindow;
    String str;
    switch (type) {
      case "new":
        sWTWindow = new SWTWindow();
        return sWTWindow.addProgressBar();
      case "old":
        break;
    } 
    JFrameWindow jFrameWindow = new JFrameWindow();
    return jFrameWindow.addProgressBar();
  }
}
