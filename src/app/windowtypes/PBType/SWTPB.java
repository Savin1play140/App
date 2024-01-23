/*    */ package app.windowtypes.PBType;
/*    */ 
/*    */ import org.eclipse.swt.layout.GridLayout;
/*    */ import org.eclipse.swt.widgets.Display;
/*    */ import org.eclipse.swt.widgets.Layout;
/*    */ import org.eclipse.swt.widgets.Shell;
/*    */ 
/*    */ public class SWTPB
/*    */   extends ProgressBarType
/*    */ {
/*    */   private Shell s;
/*    */   
/*    */   public boolean shutdown() {
/* 14 */     if (!this.s.isDisposed()) this.s.dispose(); 
/* 15 */     return this.s.isDisposed();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean run() {
/* 20 */     this.s = new Shell(new Display());
/* 21 */     this.s.setText("Loading...");
/* 22 */     this.s.setLayout((Layout)new GridLayout());
/*    */     
/* 24 */     this.s.setSize(200, 65);
/* 25 */     this.s.open();
/* 26 */     return !this.s.isDisposed();
/*    */   }
/*    */ }


/* Location:              D:\Downloads\App.jar!\app\windowtypes\PBType\SWTPB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */