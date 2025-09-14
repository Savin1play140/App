package app.utils;

import app.logger.Logger;
import app.net.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Config {
  private ArrayList<String> keys = new ArrayList<>();
  
  private ArrayList<String> values = new ArrayList<>();
  
  private String configFile = "./app.conf";
  
  public Config() {
    loadConfig();
  }
  
  private void loadConfig() {
    File.CINF(this.configFile);
    String[] strs = File.readFile(this.configFile);
    byte b;
    int i;
    String[] arrayOfString1;
    for (i = (arrayOfString1 = strs).length, b = 0; b < i; ) {
      String str = arrayOfString1[b];
      if (str != null) {
        String[] str0 = str.split(":", 2);
        str0[0] = str0[0].replace(" ", "");
        str0[1] = str0[1].replace(" ", "");
        str0[0] = str0[0].replace("\"", "");
        str0[1] = str0[1].replace("\"", "");
        str0[0] = str0[0].replace("'", "");
        str0[1] = str0[1].replace("'", "");
        this.keys.add(str0[0]);
        this.values.add(str0[1]);
      } 
      b++;
    } 
  }
  
  public String get(String key) {
    return this.values.get(this.keys.indexOf(key));
  }
  
  public void set(String key, String value) {
    if (this.keys.indexOf(key) == -1) {
      this.keys.add(key);
      this.values.add(this.keys.indexOf(key), value);
    } else {
      this.keys.set(this.keys.indexOf(key), key);
      this.values.set(this.keys.indexOf(key), value);
    } 
    try {
      try {
        Path path = Paths.get(this.configFile, new String[0]);
        Files.writeString(path, "", new java.nio.file.OpenOption[0]);
      } catch (IOException e) {
        e.printStackTrace();
      } 
      for (String value0 : this.values) {
        int key0 = this.values.indexOf(value0);
        File.writeFile(this.configFile, (String)this.keys.get(key0) + ": " + (String)this.keys.get(key0));
      } 
    } catch (IndexOutOfBoundsException e) {
      Logger.fatal(e);
    } 
  }
  
  public void defaultSetParam(String key, String value) {
    if (this.values.indexOf(value) == -1 && this.keys.indexOf(key) == -1)
      set(key, value); 
  }
}
