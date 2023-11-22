package app.logger;

import app.*;

public class Logger {
	private static boolean thisEnabled = true;

	public static boolean Switch(boolean enable) {
		if (!enable) {
			warning("Logger disabled");
		}
		if (enable) {
			info("Logger enabled");
		}
		thisEnabled = enable;
		return true;
	}

	public static void info(String message) {
		try {
			message(LogLevel.INFO, message);
		} catch (Exception|Error e) {
			System.out.println("[ERROR] "+e.getMessage());
			System.out.println("  "+e.getStackTrace());
			Main.Exit();
		}
	}
	public static void alert(String message) {
		try {
			message(LogLevel.ALERT, message);
		} catch (Exception|Error e) {
			System.out.println("[ERROR] "+e.getMessage());
			System.out.println("  "+e.getStackTrace());
			Main.Exit();
		}
	}
	public static void warning(String message) {
		try {
			message(LogLevel.WARNING, message);
		} catch (Exception|Error e) {
			System.out.println("[ERROR] "+e.getMessage());
			System.out.println("  "+e.getStackTrace());
			Main.Exit();
		}
	}
	public static void error(String message) {
		try {
			message(LogLevel.ERROR, message);
		} catch (Exception|Error e) {
			System.out.println("[ERROR] "+e.getMessage());
			System.out.println("  "+e.getStackTrace());
			Main.Exit();
		}
	}
	public static void fatal(String message) {
		try {
			message(LogLevel.FATAL, message);
		} catch (Exception|Error e) {
			System.out.println("[ERROR] "+e.getMessage());
			System.out.println("  "+e.getStackTrace());
			Main.Exit();
		}
	}
	private static void message(LogLevel lL, String message) throws Exception, Error {
		String level = "";
		if (lL == LogLevel.INFO) {
			level = "[INFO]";
		} else if (lL == LogLevel.ALERT) {
			level = "[ALERT]";
		} else if (lL == LogLevel.WARNING) {
			level = "[WARNING]";
		} else if (lL == LogLevel.ERROR) {
			level = "[ERROR]";
		} else if (lL == LogLevel.FATAL) {
			throw new Error(message);
		} else {
			level = "[UNKNOWN{ERROR_ON_SWITCH}]";
			throw new Exception("Unknown LogLevel given!");
		}
		if (thisEnabled) System.out.println(level+" "+message);
	}
}