package app;

public abstract class Loadable extends Thread {
  private boolean runed = true;
  
  public abstract void load(Main paramMain);
  
  public final void run() {
    int circle = 0;
    onrun();
    while (this.runed) {
      circle++;
      update(circle);
    } 
  }
  
  public abstract void onrun();
  
  public final void disable() {
    this.runed = false;
    shutdown();
  }
  
  public abstract void update(int paramInt);
  
  public abstract void shutdown();
  
  public final boolean isActive() {
    return this.runed;
  }
}
