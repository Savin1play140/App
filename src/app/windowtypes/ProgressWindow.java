package app.windowtypes;

import app.logger.Logger;
import app.windowtypes.PBType.ProgressBarType;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProgressWindow extends ProgressBarType {
  private JFrame frame;
  
  public boolean run() {
    this.frame = new JFrame();
    this.frame.setTitle("Loading...");
    this.frame.setSize(300, 75);
    this.frame.setLocationRelativeTo((Component)null);
    this.frame.setResizable(false);
    this.frame.dispose();
    this.frame.setCursor(Cursor.getPredefinedCursor(3));
    this.frame.setDefaultCloseOperation(0);
    try {
      String path = "./resources/icon1.png";
      Image icon = (new ImageIcon(path)).getImage();
      this.frame.setIconImage(icon);
    } catch (Exception e) {
      Logger.error("Icon error: " + e.getMessage());
    } 
    JProgressBar progressBar = new JProgressBar();
    progressBar.setIndeterminate(true);
    this.frame.add(progressBar);
    this.frame.setVisible(true);
    return true;
  }
  
  public boolean shutdown() {
    this.frame.setVisible(false);
    return true;
  }
}
