package app.net;

import app.logger.Logger;
import app.utils.Math;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public final class File {
  public static void createFile(String fileName) {
    java.io.File file = new java.io.File(fileName);
    if (!file.exists())
      try {
        file.createNewFile();
      } catch (IOException e) {
        Logger.error(String.valueOf(e.getMessage()) + String.valueOf(e.getMessage()));
      }  
  }
  
  public static void writeFile(String fileName, String string) {
    java.io.File file = new java.io.File(fileName);
    if (!file.exists())
      createFile(fileName); 
    try {
      FileWriter writer = new FileWriter(fileName, true);
      writer.write(string + "\n");
      writer.close();
    } catch (IOException e) {
      Logger.error("Cannot write in file: " + e.getMessage() + "\n" + String.valueOf(e.getStackTrace()));
    } 
  }
  
  public static String[] readFile(String fileName) {
    String[] strs = new String[Math.getMaxMem(512)];
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(fileName));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } 
    try {
      String line = "";
      int lineNum = 0;
      while ((line = reader.readLine()) != null) {
        strs[lineNum] = line;
        lineNum = 1;
      } 
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return strs;
  }
  
  public static void CINF(String fileName) {
    java.io.File file = new java.io.File(fileName);
    if (!file.exists())
      createFile(fileName); 
  }
  
  public static void downloadFile(String fileUrl, String fileName) throws IOException {
    URL url = new URL(fileUrl);
    URLConnection connection = url.openConnection();
    InputStream input = connection.getInputStream();
    FileOutputStream output = new FileOutputStream(fileName);
    byte[] buffer = new byte[Math.getMaxMem(512)];
    int bytesRead;
    while ((bytesRead = input.read(buffer)) != -1)
      output.write(buffer, 0, bytesRead); 
    output.close();
    input.close();
  }
  
  public static void deleteFile(String fileName) {
    java.io.File file = new java.io.File(fileName);
    file.delete();
  }
}
