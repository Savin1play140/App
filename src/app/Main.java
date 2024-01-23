package app;
import java.io.IOException;
import java.util.ArrayList;

import app.logger.Logger;
import app.net.File;
import app.sound.SoundManager;
import app.update.Updater;
import app.utils.Archive;
import app.utils.Handler;
import app.utils.Config;
import app.windowtypes.PBType.ProgressBarType;

public class Main {
	public static boolean soundsEnable = true;
	public static boolean windowEnable = true;
	public static boolean isResizable = false;
	public static boolean alertsEnable = true;
	public static final double version = 0.6;
	public static final String build = "0014";
	public static final String wt = "old";
	private static ArrayList<Loadable> loadeds = new ArrayList<>();
	private static ArrayList<Handler> handlers = new ArrayList<>();

	private static Handler tpshandler;
	private static Config cnf;

	public static final String startSound = "./resources/sounds/test.wav";
	public static final String serverHttp = "http://0.0.0.0/";

	public Loadable loadExternal(Loadable loading) {
		loading.load(this);
		loadeds.add(loading);
		return loading;
	}
	public static boolean DisableLogging() {
		Logger.alert("Disabling logger...");
		return Logger.Switch(false);
	}
	public static Config getConfig() {
		return cnf;
	}
	private void Init(String[] args) {
		for (String arg : args) {
			switch (arg) {
				case "logger-disable":
					DisableLogging();
					break;
				case "no-sounds":
					soundsEnable = false;
					break;
				}
			}
		InitTPSHandler();
		cnf = new Config();
		cnf.defaultSetParam("serverHttp", serverHttp);
		Logger.info("Server host: "+cnf.get("serverHttp"));
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

		if (windowEnable) {
			SoundManager.PlaySound(startSound);
			Window win = new Window();
			loadExternal((Loadable)new Updater());
			String[] texts = new String[25];
			texts[0] = "Hello user.";
			texts[1] = "This jar file has compiled in release mode.";
			texts[2] = "This project for all.";
			texts[3] = "Version format: v.v_b";
			texts[4] = "   v - version";
			texts[5] = "   b - build";
			win.createGUI("old", "Window "+version+"_"+build+"[Release]", texts, 36.65, 65.2, false);
		} 
		StartTPSHandler();
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
				Logger.info("Unziping resources.zip...");
				Archive.unzip("resources.zip", "./");
			} catch (IOException e) {
				Logger.error(e.getMessage());
			} 
		} else {
			try {
				Logger.info("Download resources.zip...");
				File.downloadFile(Main.getConfig().get("serverHttp")+"resources.zip", "resources.zip");
				try {
					Logger.info("Unziping resources.zip...");
					Archive.unzip("resources.zip", "./");
				} catch (IOException e) {
					Logger.error(e.getMessage());
				} 
			} catch (IOException e) {
				Logger.fatal("Directory and zip file not exist: " + e.getMessage());
				soundsEnable = false;
			}
		} 
	}
	public static synchronized boolean sync(boolean loging) {
		if (loging) Logger.info("GC starting..."); 
		gc();
		if (loging) Logger.info("complited"); 
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
	public static boolean stopTPS() {
		tpshandler.exit();
		return true;
	}

	private static void InitTPSHandler() {
		tpshandler = new Handler() {
			public boolean tpsEnabled;
			private int tick = 0;

			public void ontick() {
				while (this.tpsEnabled) {
					if (this.tick > 30) {
						this.tick++;
					} else {
						this.tick = 0;
					} 
					for (Handler h : Main.handlers) h.onTPS(this.tick);
					try {
						Thread.sleep(50L);
						} catch (InterruptedException e) {
							Logger.fatal(e);
						}
				}
			}
			public void onrun() {
				this.tpsEnabled = true;
			}
			public void onTPS(int tick) {} public void onstop() {
				this.tpsEnabled = false;
			}
		};
	}
	private static void StartTPSHandler() {
		tpshandler.start();
	}
	public static Handler AddHandler(Handler handler) {
		handler.start();
		handlers.add(handler);
		return handler;
	}

	public static void Exit() {
		SoundManager.PlaySound(startSound);
		Logger.info("sync data...");
		boolean complite = sync(true);
		if (complite) {
			stopTPS();
			shutdownAllExternal();
			Logger.info("exit...");
			System.exit(-1);
		} else {
			Logger.error("synchronize not complited");
		} 
	}
}
