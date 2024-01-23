package app.utils;

import app.net.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


public class Config {
	private ArrayList<String> keys = new ArrayList<String>();
	private ArrayList<String> values = new ArrayList<String>();
	private String configFile = "./app.conf";
	public Config() {
		loadConfig();
	}
	private void loadConfig() {
		File.CINF(configFile);
		String[] strs = File.readFile(configFile);
		for (String str : strs) {
			if (str == null) continue;
			String[] str0 = str.split(":", 2);
			str0[0] = str0[0].replace(" ", "");
			str0[1] = str0[1].replace(" ", "");
			str0[0] = str0[0].replace("\"", "");
			str0[1] = str0[1].replace("\"", "");
			str0[0] = str0[0].replace("\'", "");
			str0[1] = str0[1].replace("\'", "");
			keys.add(str0[0]);
			values.add(str0[1]);
		}
	}
	public String get(String key) {
		return values.get(keys.indexOf(key));
	}
	public void defaultSetParam(String key, String value) {
		if (values.indexOf(value) == -1 && keys.indexOf(key) == -1) {
			keys.add(key);
			values.add(value);
			Path path = Paths.get(configFile);
	        try {
				Files.writeString(path, "");
			} catch (IOException e) {
				e.printStackTrace();
			}
	        for (String value0 : values) {
	        	int key0 = values.indexOf(value0);
	    		File.writeFile(configFile, keys.get(key0)+": "+value0);
	        }
		}
	}
}
