package app.update;

import app.Loadable;
import app.Main;
import app.logger.Logger;

public class Update extends Loadable {
  private Updater updater;
  
  public void load(Main main) {
    switch (Main.getUpdateType()) {
      case "web":
        this.updater = new WebUpdater();
        break;
      case "java":
        this.updater = new JavaUpdater();
        break;
      default:
        Logger.error(Main.getUpdateType() + " update method not found");
        this.updater = new WebUpdater();
        break;
    } 
    checkUpdate(true);
  }
  
  public void checkUpdate(boolean message) {
    if (this.updater.GLR(message)) {
      this.updater.downloadUpdate(message);
    } else if (this.updater.GLB(message)) {
      this.updater.downloadUpdate(message);
    } 
  }
  
  public void onrun() {}
  
  public void shutdown() {}
  
  public void update(int circle) {
    if (circle > 20)
      checkUpdate(false); 
  }
}
