package app.windowtypes.PBType;

public abstract class ProgressBarType {
  public abstract boolean shutdown();
  
  public final boolean start() {
    return run();
  }
  
  public abstract boolean run();
}
