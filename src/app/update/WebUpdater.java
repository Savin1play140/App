package app.update;

import app.Main;
import app.logger.Logger;
import app.net.File;
import app.utils.Archive;
import app.utils.Localization;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebUpdater implements Updater {
  private String lastRelease = "0.0";
  
  private String lastBuild = "0000";
  
  public boolean hasConnection() {
    boolean connect;
    try {
      URL obj = new URL("http://" + Main.getConfig().get("serverAddr"));
      HttpURLConnection connection = 
        (HttpURLConnection)obj.openConnection();
      connection.setRequestMethod("GET");
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuffer response = new StringBuffer();
      String inputLine;
      while ((inputLine = in.readLine()) != null)
        response.append(inputLine); 
      in.close();
      connect = true;
    } catch (Exception e) {
      connect = false;
    } 
    return connect;
  }
  
  public String sendGet(String url) {
    try {
      URL obj = new URL("http://" + url);
      HttpURLConnection connection = (HttpURLConnection)obj.openConnection();
      connection.setRequestMethod("GET");
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      StringBuffer response = new StringBuffer();
      String inputLine;
      while ((inputLine = in.readLine()) != null)
        response.append(inputLine); 
      in.close();
      return response.toString();
    } catch (Exception e) {
      Logger.error(e.getMessage());
      return e.getMessage();
    } 
  }
  
  public boolean GLR(boolean sendMessage) {
    boolean enable = false;
    String lastRelease = sendGet(Main.getConfig().get("serverAddr") + "/RM.php?get-last-version");
    this.lastRelease = lastRelease;
    double ver = 0.0;
    try {
      ver = Double.parseDouble(lastRelease);
    } catch (NumberFormatException e) {
      System.err.println(e);
    } 
    if (sendMessage)
      Localization.sendText("update.test", "info"); 
    if (hasConnection()) {
      if (ver > Main.version) {
        enable = true;
      } else if (ver < Main.version) {
        if (sendMessage)
          Localization.sendText("update.server-old", "info"); 
        enable = false;
      } else if (ver == Main.version) {
        if (sendMessage)
          Localization.sendText("update.last", "info"); 
        enable = false;
      } else if (sendMessage) {
        Logger.error("ERROR: server has error, release can't be check");
      } 
    } else if (sendMessage) {
      Localization.sendText("update.fail-conn", "warn");
    } 
    return enable;
  }
  
  public boolean GLR() {
    return GLR(false);
  }
  
  public boolean GLB(boolean sendMessage) {
    boolean enable = false;
    String build = sendGet(Main.getConfig().get("serverAddr") + "/RM.php?get-last-build");
    this.lastBuild = build;
    int buildServer = Integer.parseInt(build);
    int buildThis = Integer.parseInt(Main.build);
    if (buildServer > buildThis) {
      enable = true;
    } else if (buildServer < buildThis) {
      if (sendMessage)
        Localization.sendText("update.update-new", "info"); 
      enable = false;
    } else if (buildServer == buildThis) {
      if (sendMessage)
        Localization.sendText("update.no-update", "info"); 
      enable = false;
    } else {
      if (sendMessage)
        Logger.error("ERROR: server has error, build can't be check"); 
      enable = false;
    } 
    return enable;
  }
  
  public boolean GLB() {
    return GLB(false);
  }
  
  public void downloadUpdate(boolean sendMessage) {
    Localization.sendText("update.install", "info");
    try {
      File.downloadFile(Main.getConfig().get("serverAddr") + "/RM.php?get-update", String.valueOf(this.lastRelease) + ".zip");
      Archive.unzip(String.valueOf(this.lastRelease) + ".zip", "./" + this.lastRelease + "_" + this.lastBuild);
      Logger.info(Localization.getText("update.successful") + " \"" + Localization.getText("update.successful") + "_" + this.lastRelease + "\"");
    } catch (IOException e) {
      Logger.error(e.getMessage());
    } 
  }
  
  public void downloadUpdate() {
    downloadUpdate(false);
  }
}
