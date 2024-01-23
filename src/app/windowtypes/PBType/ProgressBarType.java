/*   */ package app.windowtypes.PBType;
/*   */ 
/*   */ public abstract class ProgressBarType {
/*   */   public abstract boolean shutdown();
/*   */   
/*   */   public final boolean start() {
/* 7 */     return run();
/*   */   }
/*   */   
/*   */   public abstract boolean run();
/*   */ }


/* Location:              D:\Downloads\App.jar!\app\windowtypes\PBType\ProgressBarType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */