package app.update;

import app.Main;
import app.logger.Logger;
import app.utils.Localization;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class JavaUpdater implements Updater {
  private Socket client;
  
  private double lastRelease = 0.0;
  
  private String lastBuild = "0000";
  
  private boolean initSuccessful = true;
  
  private void Init() {
    try {
      this.client = new Socket(Main.getConfig().get("serverAddr"), 1140);
    } catch (Exception e) {
      Logger.error(e.getMessage());
      this.initSuccessful = false;
    } 
  }
  
  private boolean tryInit() {
    int tryNum = 0;
    while (!this.initSuccessful) {
      if (tryNum >= 20)
        return false; 
      Init();
      tryNum++;
    } 
    return this.initSuccessful;
  }
  
  public String sendGet(String quary) {
    if (!this.initSuccessful) {
      boolean successful = tryInit();
      if (!successful)
        return "0"; 
    } 
    String ret = "";
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
      BufferedWriter out = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
      out.write(quary);
      out.flush();
      ret = in.readLine();
      Logger.info("(DEBUG) " + ret);
    } catch (Exception e) {
      Logger.error(e.getMessage());
      return "0";
    } 
    return ret;
  }
  
  public boolean hasConnection() {
    return true;
  }
  
  public boolean GLR(boolean sendMessage) {
    boolean enable = false;
    double ver = Double.parseDouble(sendGet("get-last-version"));
    this.lastRelease = ver;
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
    String build = sendGet("get-last-build");
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
    if (this.client == null)
      Init(); 
    try {
      InputStream in = this.client.getInputStream();
      OutputStream out = this.client.getOutputStream();
      out.write("download-update".getBytes());
      out.flush();
      FileOutputStream fileOut = new FileOutputStream("update.zip");
      byte[] buffer = new byte[4096];
      int bytesRead;
      while ((bytesRead = in.read(buffer)) != -1)
        fileOut.write(buffer, 0, bytesRead); 
      fileOut.close();
    } catch (IOException e) {
      Logger.error(e.getMessage());
    } 
  }
  
  public void downloadUpdate() {
    downloadUpdate(true);
  }
  
  public double getLastRelease() {
    return this.lastRelease;
  }
  
  public String getLastBuild() {
    return this.lastBuild;
  }
}
