package app;

import app.logger.Logger;
import app.net.File;
import app.sound.SoundManager;
import app.update.Update;
import app.utils.Archive;
import app.utils.Config;
import app.utils.Localization;
import app.windowtypes.PBType.ProgressBarType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
  public static boolean soundsEnable = true;
  
  public static boolean isResizable = false;
  
  public static final double version = 0.8;
  
  public static final String build = "0012";
  
  public static final boolean isTestBuild = false;
  
  public static final String wt = "old";
  
  private static String updateType = "web";
  
  private static ArrayList<Loadable> loadeds = new ArrayList<>();
  
  private static Config cnf;
  
  public static final String startSound = "./resources/sounds/test.wav";
  
  public Loadable loadExternal(Loadable loading) {
    loading.load(this);
    loadeds.add(loading);
    return loading;
  }
  
  public static Config getConfig() {
    return cnf;
  }
  
  public void Init(String[] args) {
    for (int i = 0; i < args.length; i++) {
        String arg = args[i];
        switch (arg) {
            case "logger-disable":
                Logger.shutdown();
                break;
            case "no-sounds":
                soundsEnable = false;
                break;
            case "Beta-funcs":
                updateType = "java";
                break;
        }
    }

    Main.cnf = new Config();
    Main.cnf.defaultSetParam("language", "ru_ru");
    Main.cnf.defaultSetParam("serverAddr", "127.0.0.1");
    Main.cnf.defaultSetParam("updateMethod", updateType);
    
    setLang(Main.cnf.get("language"));
    updateType = Main.cnf.get("updateMethod");
    
    sendText("config.server", "info");
  }

  public static void main(String[] args) {
    boolean exist = resourcesCheck(false);
    ProgressBarType win = null;
    if (!exist) win = Window.sendProgressBar("old"); 
    while (!exist) {
      try {
        Thread.sleep(750L);
        exist = resourcesCheck(true);
      } catch (InterruptedException e) {
        Logger.fatal(e);
      } 
      File.deleteFile("resources.zip");
      if (exist) win.shutdown();
    } 
    Main main = new Main();
    main.run(args);
  }
  
  private void run(String[] args) {
    Init(args);
    SoundManager.PlaySound(startSound);
    Window win = new Window();
    loadExternal(new Update());
    String[] texts = new String[25];
    texts[0] = Localization.getText("text.0");
    texts[1] = Localization.getText("text.1");
    texts[2] = Localization.getText("text.2");
    texts[3] = Localization.getText("text.3");
    texts[4] = Localization.getText("text.4");
    texts[5] = Localization.getText("text.5");
    win.createGUI("old", "Window "+version+"_"+build+"[Release]", texts, 36.65, 65.2, false);
  }
  
  private static synchronized boolean resourcesCheck(boolean loadR) {
    boolean exist = true;
    File destDir = new File("resources");
    if (!destDir.exists()) {
      exist = false;
      File zip = new File("resources.zip");
      if (zip.exists()) {
        exist = false;
        if (loadR) loadResources(true); 
      } else {
        exist = false;
        if (loadR) loadResources(false); 
      } 
    } 
    return exist;
  }
  
  public static synchronized void loadResources(boolean zipExist) {
    if (zipExist) {
      try {
        Localization.sendText("zip.unpack", "info");
        Archive.unzip("resources.zip", "./");
      } catch (IOException e) {
        Logger.error(e.getMessage());
      } 
    } else {
      try {
        Localization.sendText("zip.download", "info");
        File.downloadFile(getConfig().get("serverAddr") + "resources.zip", "resources.zip");
        try {
          Localization.sendText("zip.unpack", "info");
          Archive.unzip("resources.zip", "./");
        } catch (IOException e) {
          Logger.error(e.getMessage());
        } 
      } catch (IOException e) {
        Logger.fatal(Localization.getText("zip.error") + Localization.getText("zip.error"));
        soundsEnable = false;
      } 
    } 
  }
  
  public static synchronized boolean sync(boolean loging) {
    if (loging) Localization.sendText("gc.start", "info"); 
    gc();
    if (loging) Localization.sendText("main.successful", "info"); 
    return true;
  }
  
  public static boolean gc() {
    System.gc();
    return true;
  }
  
  public static boolean shutdownAllExternal() {
    for (Loadable loaded : loadeds)
      loaded.disable(); 
    return true;
  }
  
  public static void Exit() {
    SoundManager.PlaySound(startSound);
    Localization.sendText("main.sync", "info");
    boolean complite = sync(true);
    if (complite) {
      shutdownAllExternal();
      cnf.set("shotdown", "yes");
      Localization.sendText("main.exit", "info");
      System.exit(-1);
    } else {
      Logger.error("synchronize not complited");
    } 
  }
  
  public static String getUpdateType() {
    return updateType;
  }
}
