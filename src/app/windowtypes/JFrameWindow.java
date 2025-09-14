package app.windowtypes;

import app.Main;
import app.logger.Logger;
import app.utils.Math;
import app.windowtypes.PBType.ProgressBarType;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JFrameWindow extends JFrame implements WindowType {
  private int yText = 10;
  
  private static final long serialVersionUID = 1L;
  
  private String[] texts = new String[Math.S(1024, 2)];
  
  private JPanel panel;
  
  public void createGUI(String name, final String[] texts, float x, float y, boolean resizable) {
    this.texts = texts;
    setTitle(name);
    setSize((int)getXAsDisplay(x), (int)getYAsDisplay(y));
    setPreferredSize(new Dimension((int)getXAsDisplay(x), (int)getYAsDisplay(y)));
    setDefaultCloseOperation(0);
    setLocationRelativeTo((Component)null);
    setResizable(resizable);
    try {
      String path = "./resources/icon1.png";
      Image icon = (new ImageIcon(path)).getImage();
      setIconImage(icon);
    } catch (Exception e) {
      Logger.error("Icon error: " + e.getMessage());
    } 
    addWindowListener(
        new WindowListener() {
          public void windowActivated(WindowEvent event) {}
          
          public void windowClosed(WindowEvent event) {}
          
          public void windowClosing(WindowEvent event) {
            event.getWindow().setVisible(false);
            Main.Exit();
          }
          
          public void windowDeactivated(WindowEvent event) {}
          
          public void windowDeiconified(WindowEvent event) {}
          
          public void windowIconified(WindowEvent event) {}
          
          public void windowOpened(WindowEvent event) {}
        });
    this.panel = new JPanel() {
        private static final long serialVersionUID = 1L;
        
        public void paintComponent(Graphics g) {
          JFrameWindow.this.yText = 10;
          super.paintComponent(g);
          g.setColor(Color.BLACK);
          byte b;
          int i;
          String[] arrayOfString;
          for (i = (arrayOfString = texts).length, b = 0; b < i; ) {
            String text = arrayOfString[b];
            if (text == null)
              return; 
            g.drawString(text, 0, JFrameWindow.this.yText);
            JFrameWindow.this.yText += 13;
            b = (byte)(b + 1);
          } 
        }
      };
    add(this.panel);
    setVisible(true);
  }
  
  private float getXAsDisplay(float px1) {
    int displayX = (Toolkit.getDefaultToolkit().getScreenSize()).width;
    float dpxX = displayX * px1 / 100.0F;
    return dpxX;
  }
  
  private float getYAsDisplay(float px1) {
    int displayY = (Toolkit.getDefaultToolkit().getScreenSize()).height;
    float dpxY = displayY * px1 / 100.0F;
    return dpxY;
  }
  
  protected static ImageIcon createIcon(String path) {
    URL imgURL = JFrameWindow.class.getResource(path);
    if (imgURL != null)
      return new ImageIcon(imgURL); 
    System.err.println("File not found " + path);
    return null;
  }
  
  public void addText(final String text) {
    if (text == null)
      return; 
    this.panel = new JPanel() {
        private static final long serialVersionUID = 1L;
        
        public void paintComponent(Graphics g) {
          int yText = 10;
          super.paintComponent(g);
          g.setColor(Color.BLACK);
          g.drawString(text, 0, yText);
        }
      };
    add(this.panel);
  }
  
  public ProgressWindow addProgressBar() {
    ProgressWindow win = new ProgressWindow();
    win.run();
    return win;
  }
  
  public void setText(int strNum, String string) {
    this.texts[strNum] = string;
    remove(this.panel);
    this.panel = new JPanel() {
        private static final long serialVersionUID = 1L;
        
        public void paintComponent(Graphics g) {
          int yText = 10;
          super.paintComponent(g);
          g.setColor(Color.BLACK);
          byte b;
          int i;
          String[] arrayOfString;
          for (i = (arrayOfString = JFrameWindow.this.texts).length, b = 0; b < i; ) {
            String text = arrayOfString[b];
            if (text == null)
              return; 
            g.drawString(text, 0, yText);
            yText += 13;
            b = (byte)(b + 1);
          } 
        }
      };
    add(this.panel);
    remove(this.panel);
  }
}
