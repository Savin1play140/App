/*    */ package app.windowtypes;
/*    */ 
/*    */ import app.logger.Logger;
/*    */ import app.windowtypes.PBType.ProgressBarType;
/*    */ import java.awt.Component;
/*    */ import java.awt.Cursor;
/*    */ import java.awt.Image;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JProgressBar;
/*    */ 
/*    */ public class ProgressWindow extends ProgressBarType {
/*    */   private JFrame frame;
/*    */   
/*    */   public boolean run() {
/* 16 */     this.frame = new JFrame();
/* 17 */     this.frame.setTitle("Loading...");
/* 18 */     this.frame.setSize(300, 75);
/* 19 */     this.frame.setLocationRelativeTo((Component)null);
/* 20 */     this.frame.setResizable(false);
/* 21 */     this.frame.dispose();
/* 22 */     this.frame.setCursor(Cursor.getPredefinedCursor(3));
/* 23 */     this.frame.setDefaultCloseOperation(0);
/*    */     try {
/* 25 */       String path = "./resources/icon1.png";
/* 26 */       Image icon = (new ImageIcon(path)).getImage();
/* 27 */       this.frame.setIconImage(icon);
/* 28 */     } catch (Exception e) {
/* 29 */       Logger.error("Icon error: " + e.getMessage());
/*    */     } 
/* 31 */     JProgressBar progressBar = new JProgressBar();
/* 32 */     progressBar.setIndeterminate(true);
/* 33 */     this.frame.add(progressBar);
/* 34 */     this.frame.setVisible(true);
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shutdown() {
/* 40 */     this.frame.setVisible(false);
/* 41 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Downloads\App.jar!\app\windowtypes\ProgressWindow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */