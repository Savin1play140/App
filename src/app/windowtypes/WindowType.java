package app.windowtypes;

import app.windowtypes.PBType.ProgressBarType;

public interface WindowType {
  void createGUI(String paramString, String[] paramArrayOfString, float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  void addText(String paramString);
  
  void setText(int paramInt, String paramString);
  
  ProgressBarType addProgressBar();
}


/* Location:              D:\Downloads\App.jar!\app\windowtypes\WindowType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */