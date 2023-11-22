package app;
import java.util.*;
import app.logger.*;
import app.net.*;
import app.update.*;

public class Main {
	public static boolean soundsEnable = true;
	public static boolean windowEnable = true;
	public static boolean isResizable = false;
	public static boolean alertsEnable = true;
	public static final double version = 0.1;
	public static final String sub_build = "08";
	public Connect connect;
	private static ArrayList<Loadable> loaded = new ArrayList<Loadable>();

	public static String startSound = "./resources/sounds/test.wav";

	public Loadable loadExternal(Loadable loading) {
		loading.load(this);
		loaded.add(loading);
		return loading;
	}
	public static boolean DisableLogging() {
		Logger.alert("Disabling logger...");
		boolean swithed = Logger.Switch(false);
		return swithed;
	}
	private void Init(String[] args) {
		connect = new Connect();
		connect.load(this);
		for (String arg : args) {
			switch (arg) {
				case "no-sounds":
					soundsEnable = false;
					break;
				case "logger-disable":
					DisableLogging();
					break;
			}
		}
	}
	private Main(String[] args) {
		Init(args);
		if (Main.windowEnable) {
			if (Main.soundsEnable) {
				Logger.info("waiting...");
				Sound.playSound(Main.startSound);
			}
			Window win = new Window();
			this.connect.start();
			loadExternal(new Updater()).start();
			String[] texts = new String[] {
				"Hello user.",
				"This jar file has compiled in release mode.",
				"This project for all.",
				"Version format: v.v.v_b_f.",
				"   v - version",
				"   b - build",
				"   s - sub-build"
			};
			win.createGUI("release", "Window "+Main.version+"_"+Main.sub_build+"[Release]", texts, 500, 500, false);
			Logger.info("Create window");
		}
		Logger.info("started");
	}
	public static void main(String[] args) {
		new Main(args);
	}
	public static boolean sync() {
		if (Main.soundsEnable) {
			Logger.info("waiting...");
			Sound.playSound(startSound);
		}
		Logger.info("complited");
		return true;
	}
	public static void Exit() {
		Logger.info("sync data...");
		boolean complite = sync();
		if (complite) {
			Logger.info("exit...");
			System.exit(-1);
		} else {
			Logger.error("synchronize not sucsessful complited");
		}
	}
	public String getAddr() {
		return "localhost";
	}
	public int getPort() {
		return 8088;
	}
}