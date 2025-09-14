package app.update;

public interface Updater {
  String sendGet(String paramString);
  
  boolean hasConnection();
  
  boolean GLR(boolean paramBoolean);
  
  boolean GLR();
  
  boolean GLB(boolean paramBoolean);
  
  boolean GLB();
  
  void downloadUpdate(boolean paramBoolean);
  
  void downloadUpdate();
}
