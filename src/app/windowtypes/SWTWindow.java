package app.windowtypes;

import app.Main;
import app.logger.Logger;
import app.windowtypes.PBType.ProgressBarType;
import app.windowtypes.PBType.SWTPB;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Shell;

public class SWTWindow implements WindowType {
  private Label label;
  
  private Shell shell;
  
  private Display display;
  
  private String[] texts;
  
  public void createGUI(String name, String[] texts, float x, float y, boolean resizable) {
    this.texts = texts;
    this.display = new Display();
    this.shell = new Shell(this.display);
    this.shell.setText(name);
    int x1 = (int)getXAsDisplay(this.display, x);
    int y1 = (int)getYAsDisplay(this.display, y);
    Logger.info("display size: " + (this.display.getBounds()).width + "x" + (this.display.getBounds()).height);
    this.shell.setSize(x1, y1);
    Logger.info("X: " + x1 + ";Y: " + y1);
    this.shell.setLayout((Layout)new GridLayout());
    this.label = new Label((Composite)this.shell, 0);
    try {
      String path = "./resources/icon1.png";
      Image icon = new Image((Device)this.display, path);
      this.shell.setImage(icon);
    } catch (Exception e) {
      Logger.error("Icon error: " + e.getMessage());
    } 
    byte b;
    int i;
    String[] arrayOfString;
    for (i = (arrayOfString = texts).length, b = 0; b < i; ) {
      String text = arrayOfString[b];
      addText(text);
      b = (byte)(b + 1);
    } 
    Possit(this.shell, this.display);
    this.shell.open();
    while (!this.shell.isDisposed()) {
      if (!this.display.readAndDispatch())
        this.display.sleep(); 
    } 
    this.display.dispose();
    Main.Exit();
  }
  
  public void Possit(Shell s, Display d) {
    Rectangle client = s.getBounds();
    Rectangle screen = d.getBounds();
    client.x = screen.width / 2 - client.width / 2;
    client.y = screen.height / 2 - client.height / 2;
    s.setBounds(client);
  }
  
  private float getXAsDisplay(Display d, float px1) {
    int displayX = (d.getBounds()).width;
    float dpxX = displayX * px1 / 100.0F;
    return dpxX;
  }
  
  private float getYAsDisplay(Display d, float px1) {
    int displayY = (d.getBounds()).height;
    float dpxY = displayY * px1 / 100.0F;
    return dpxY;
  }
  
  public void addText(String text) {
    if (text == null)
      return; 
    if (this.label == null)
      return; 
    this.label.setText(String.valueOf(this.label.getText()) + String.valueOf(this.label.getText()) + "\n");
    this.label.getParent().layout();
  }
  
  public void setText(int strNum, String text) {
    if (text == null)
      return; 
    if (this.label == null)
      return; 
    this.texts[strNum] = text;
    Display.getDefault().asyncExec(new Runnable() {
          public void run() {
            String endText = "";
            byte b;
            int i;
            String[] arrayOfString;
            for (i = (arrayOfString = SWTWindow.this.texts).length, b = 0; b < i; ) {
              String t = arrayOfString[b];
              endText = String.valueOf(endText) + String.valueOf(endText) + "\n";
              SWTWindow.this.label.setText(endText);
              b = (byte)(b + 1);
            } 
          }
        });
  }
  
  public SWTPB addProgressBar() {
    SWTPB swtpb = new SWTPB();
    swtpb.start();
    return swtpb;
  }
}
