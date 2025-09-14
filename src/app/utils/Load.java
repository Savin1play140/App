package app.utils;

import app.logger.Logger;

public class Load {
  public Load() {
    Logger.alert("(" + String.valueOf(Load.class) + "-TEST) " + getPbyF(25, 100));
  }
  
  public int getPbyF(int files, int maxProcents) {
    try {
      return Math.max(maxProcents, files) / Math.min(files, maxProcents);
    } catch (ArithmeticException e) {
      Logger.fatal(e);
      return 0;
    } 
  }
}
