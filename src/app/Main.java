package app;
import java.io.IOException;
import java.util.ArrayList;

import app.logger.Logger;
import app.net.File;
import app.sound.SoundManager;
import app.update.Updater;
import app.utils.Archive;
import app.utils.Config;
import app.utils.Localization;
import app.windowtypes.PBType.ProgressBarType;

public class Main {
	public static boolean soundsEnable = true;
	public static boolean isResizable = false;
	public static boolean resourceEnable = true;

	public static final double version = 0.7;
	public static final String build = "0006";
	public static final boolean isTestBuild = false;

	public static final String wt = "old";
	private static ArrayList<Loadable> loadeds = new ArrayList<>();

	private static Config cnf;

	public static final String startSound = "./resources/sounds/test.wav";
	public static final String serverHttp = "http://0.0.0.0/";

	public Loadable loadExternal(Loadable loading) {
		loading.load(this);
		loadeds.add(loading);
		return loading;
	}
	public static Config getConfig() {
		return cnf;
	}
	private void Init(String[] args) {
		for (String arg : args) {
			switch (arg) {
				case "logger-disable":
					Logger.shutdown();
					break;
				case "no-sounds":
					soundsEnable = false;
					break;
			}
		}
		cnf = new Config();
		cnf.defaultSetParam("serverHttp", serverHttp);
		cnf.defaultSetParam("language", "en_us");
		Localization.setLang(cnf.get("language"));
		
		Logger.info(Localization.getText("config.server"));
	}

	public static void main(String[] args) {
		Init(args);
		boolean exist = resourcesCheck(false);
		ProgressBarType win = null;
		if (!exist) win = Window.sendProgressBar("old");
		int try_num = 0;
		while (!exist && try_num < 5) {
			try_num++;
			try {
				Thread.sleep(750L);
				exist = resourcesCheck(true);
			} catch (InterruptedException e) {
				Logger.fatal(e);
			} 
			if (exist) {
				File.deleteFile("resources.zip");
				win.shutdown();
			}
		}
		resourceEnable = exist;
		Main main = new Main();
		main.run(args);
	}
	private void run(String[] args) {
		if (resourceEnable) SoundManager.PlaySound(startSound);
		Window win = new Window();
		loadExternal((Loadable)new Updater());
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
		java.io.File destDir = new java.io.File("resources");
		if (!((java.io.File) destDir).exists()) {
			exist = false;
			java.io.File zip = new java.io.File("resources.zip");
			if (((java.io.File) zip).exists()) {
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
				Logger.info(Localization.getText("zip.unpack"));
				Archive.unzip("resources.zip", "./");
			} catch (IOException e) {
				Logger.error(e.getMessage());
			} 
		} else {
			try {
				Logger.info(Localization.getText("zip.download"));
				File.downloadFile(Main.getConfig().get("serverHttp")+"resources.zip", "resources.zip");
				try {
					Logger.info(Localization.getText("zip.unpack"));
					Archive.unzip("resources.zip", "./");
				} catch (IOException e) {
					Logger.error(e.getMessage());
				} 
			} catch (IOException e) {
				Logger.fatal(Localization.getText("zip.error")+e.getMessage());
				soundsEnable = false;
			}
		} 
	}
	public static synchronized boolean sync(boolean loging) {
		if (loging) Logger.info(Localization.getText("gc.start")); 
		gc();
		if (loging) Logger.info(Localization.getText("main.successful")); 
		return true;
	}
	public static boolean gc() {
		System.gc();
		return true;
	}
	public static boolean shutdownAllExternal() {
		for (Loadable loaded : loadeds) {
			loaded.disable();
		}
		return true;
	}

	public static void Exit() {
		SoundManager.PlaySound(startSound);
		Logger.info(Localization.getText("main.sync"));
		boolean complite = sync(true);
		if (complite) {
			shutdownAllExternal();
			Logger.info(Localization.getText("main.exit"));
			System.exit(-1);
		} else {
			Logger.error("synchronize not complited");
		} 
	}
}
