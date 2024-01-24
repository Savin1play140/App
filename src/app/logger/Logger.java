package app.logger;

import app.Main;

public final class Logger {
	private static boolean enabled = true;
	
	public static void shutdown() {
		if (Main.isTestBuild) return;
		enabled = false;	
	}

	public static void info(String string) {
		if (!enabled) return;
		System.out.print("[INFO] "+string+"\n");
		if (Main.isTestBuild) Main.gc();
	}

	public static void alert(String string) {
		if (!enabled) return;
		System.out.print("[ALERT] "+string+"\n");
		if (Main.isTestBuild) Main.gc();
	}

	public static void fatal(Exception e) {
		if (!enabled) return;
		System.out.print("[FATAL] ");
		System.err.print(e);
		System.out.print("\n");
		if (Main.isTestBuild) Main.gc();
	}
	public static void fatal(String string) {
		if (!enabled) return;
		fatal(new Exception(string));
		if (Main.isTestBuild) Main.gc();
	}

	public static void error(String message) {
		if (!enabled) return;
		System.err.print("[ERROR] "+message+"\n");
		if (Main.isTestBuild) Main.gc();
	}

	public static void warn(String string) {
		if (!enabled) return;
		System.out.print("[WARNING] "+string+"\n");
		if (Main.isTestBuild) Main.gc();
	}
}
