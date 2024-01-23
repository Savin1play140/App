/*    */ package app.windowtypes;
/*    */ import app.Main;
/*    */ import app.logger.Logger;
/*    */ import app.windowtypes.PBType.SWTPB;
/*    */ import org.eclipse.swt.graphics.Image;
/*    */ import org.eclipse.swt.graphics.Rectangle;
/*    */ import org.eclipse.swt.layout.GridLayout;
/*    */ import org.eclipse.swt.widgets.Composite;
/*    */ import org.eclipse.swt.widgets.Display;
/*    */ import org.eclipse.swt.widgets.Label;
/*    */ import org.eclipse.swt.widgets.Layout;
/*    */ import org.eclipse.swt.widgets.Shell;
/*    */ 
/*    */ public class SWTWindow implements WindowType {
/*    */   private Label label;
/*    */   private Shell shell;
/*    */   private Display display;
/*    */   private String[] texts;
/*    */   
/*    */   public void createGUI(String name, String[] texts, float x, float y, boolean resizable) {
/* 22 */     this.texts = texts;
/* 23 */     this.display = new Display();
/* 24 */     this.shell = new Shell(this.display);
/* 25 */     this.shell.setText(name);
/* 26 */     int x1 = (int)getXAsDisplay(this.display, x);
/* 27 */     int y1 = (int)getYAsDisplay(this.display, y);
/* 28 */     Logger.info("display size: " + (this.display.getBounds()).width + "x" + (this.display.getBounds()).height);
/* 29 */     this.shell.setSize(x1, y1);
/* 30 */     Logger.info("X: " + x1 + ";Y: " + y1);
/* 31 */     this.shell.setLayout((Layout)new GridLayout());
/* 32 */     this.label = new Label((Composite)this.shell, 0);
/*    */     try {
/* 34 */       String path = "./resources/icon1.png";
/* 35 */       Image icon = new Image(display, path);
/* 36 */       this.shell.setImage(icon);
/* 37 */     } catch (Exception e) {
/* 38 */       Logger.error("Icon error: " + e.getMessage());
/*    */     }  byte b; int i; String[] arrayOfString;
/* 40 */     for (i = (arrayOfString = texts).length, b = 0; b < i; ) { String text = arrayOfString[b];
/* 41 */       addText(text); b++; }
/*    */     
/* 43 */     Possit(this.shell, this.display);
/* 44 */     this.shell.open();
/* 45 */     while (!this.shell.isDisposed()) {
/* 46 */       if (!this.display.readAndDispatch()) {
/* 47 */         this.display.sleep();
/*    */       }
/*    */     } 
/* 50 */     this.display.dispose();
/* 51 */     Main.Exit();
/*    */   }
/*    */   public void Possit(Shell s, Display d) {
/* 54 */     Rectangle client = s.getBounds();
/* 55 */     Rectangle screen = d.getBounds();
/* 56 */     client.x = screen.width / 2 - client.width / 2;
/* 57 */     client.y = screen.height / 2 - client.height / 2;
/* 58 */     s.setBounds(client);
/*    */   }
/*    */   private float getXAsDisplay(Display d, float px1) {
/* 61 */     int displayX = (d.getBounds()).width;
/* 62 */     float dpxX = displayX * px1 / 100.0F;
/* 63 */     return dpxX;
/*    */   }
/*    */   private float getYAsDisplay(Display d, float px1) {
/* 66 */     int displayY = (d.getBounds()).height;
/* 67 */     float dpxY = displayY * px1 / 100.0F;
/* 68 */     return dpxY;
/*    */   }
/*    */   public void addText(String text) {
/* 71 */     if (text == null)
/* 72 */       return;  if (this.label == null)
/* 73 */       return;  this.label.setText(String.valueOf(this.label.getText()) + text + "\n");
/* 74 */     this.label.getParent().layout();
/*    */   }
/*    */   public void setText(int strNum, String text) {
/* 77 */     if (text == null)
/* 78 */       return;  if (this.label == null)
/* 79 */       return;  this.texts[strNum] = text;
/* 80 */     Display.getDefault().asyncExec(new Runnable() {
/*    */           public void run() {
/* 82 */             String endText = ""; byte b; int i; String[] arrayOfString;
/* 83 */             for (i = (arrayOfString = SWTWindow.this.texts).length, b = 0; b < i; ) { String t = arrayOfString[b];
/* 84 */               endText = String.valueOf(endText) + t + "\n";
/* 85 */               SWTWindow.this.label.setText(endText);
/*    */               b++; }
/*    */           
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public SWTPB addProgressBar() {
/* 93 */     SWTPB swtpb = new SWTPB();
/* 94 */     swtpb.start();
/* 95 */     return swtpb;
/*    */   }
/*    */ }
