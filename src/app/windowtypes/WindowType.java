package app.windowtypes;

import app.windowtypes.PBType.ProgressBarType;

public interface WindowType {
  void createGUI(String paramString, String[] paramArrayOfString, float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  void addText(String paramString);
  
  void setText(int paramInt, String paramString);
  
  ProgressBarType addProgressBar();
}
