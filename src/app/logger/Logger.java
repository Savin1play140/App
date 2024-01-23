package app.logger;

import app.Main;

public class Logger {
	private static boolean thisEnabled = true;

	static {
	  info("Logger used");
	}
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
		} catch (Throwable e) {
			System.out.println("[ERROR] " + e.getMessage());
			System.out.println("  " + e.getStackTrace());
			Main.Exit();
		} 
	}
	public static void alert(String message) {
		try {
			message(LogLevel.ALERT, message);
		} catch (Throwable e) {
			System.out.println("[ERROR] " + e.getMessage());
			System.out.println("  " + e.getStackTrace());
			Main.Exit();
		} 
	}
	public static void warning(String message) {
		try {
			message(LogLevel.WARNING, message);
		} catch (Throwable e) {
			System.out.println("[ERROR] " + e.getMessage());
			System.out.println("  " + e.getStackTrace());
			Main.Exit();
		} 
	}
	public static void error(String message) {
		try {
			message(LogLevel.ERROR, message);
		} catch (Throwable e) {
			System.out.println("[ERROR] " + e.getMessage());
			System.out.println("  " + e.getStackTrace());
		} 
	}
	public static void fatal(String message) {
		try {
			message(LogLevel.FATAL, message);
		} catch (Throwable e2) {
			fatal(e2);
			Main.Exit();
		} 
	}
	public static void fatal(Throwable e1) {
		try {
			e1.printStackTrace();
		} catch (Throwable e2) {
			fatal(String.valueOf(e2.getMessage()) + e2.getStackTrace());
			Main.Exit();
		} 
	}
	private static void message(LogLevel lL, String message) throws Throwable {
		String level = "";
		switch (lL) {
		case INFO:
			level = "[INFO]";
			break;
		case ALERT:
			level = "[ALERT]";
			break;
		case WARNING:
			level = "[WARNING]";
			break;
		case ERROR:
			level = "[ERROR]";
			break;
		case FATAL:
			throw new Error(message);
		default:
			level = "[UNKNOWN{ERROR_ON_SWITCH}]";
			throw new Exception("Unknown LogLevel given!");
		} 
		if (thisEnabled) System.out.println(String.valueOf(level) + " " + message); 
	}
}
