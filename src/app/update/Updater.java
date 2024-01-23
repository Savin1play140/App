package app.update;
import app.Loadable;
import app.Main;
import app.logger.Logger;
import app.net.File;
import app.utils.Archive;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater extends Loadable {
	private String lastVersion = "0.0";
	private String lastBuild = "0000";

	public String sendGet(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection)obj.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			StringBuffer response = new StringBuffer(); String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} catch (Exception e) {
			Logger.error(e.getMessage());
			return e.getMessage();
		} 
	}

	public void load(Main main) {
		String url = Main.getConfig().get("serverHttp")+"/RM.php?get-last-version";
		if (hasConnection()) {
			this.lastVersion = sendGet(url);
			start();
		} else {
			Logger.warning("You have not connnection or server is not connectable");
			Logger.warning("Cannot check updates");
		} 
	}

	public void onrun() { checkUpdate(true); }
	private void update() {
		Logger.info("Installing update...");
		try {
			File.downloadFile(Main.getConfig().get("serverHttp")+"/RM.php?get-update", String.valueOf(lastVersion)+".zip");
			Archive.unzip(String.valueOf(lastVersion)+".zip", "./"+lastVersion+"_"+lastBuild);
			Logger.info("The update was successfully installed in \""+lastVersion+"_"+lastBuild+"\" dir");
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}
	public void checkBuild(boolean sendMessage) {
		String build = sendGet(Main.getConfig().get("serverHttp")+"/RM.php?get-last-build");
		this.lastBuild = build;
		int buildServer = Integer.parseInt(build);
		int buildThis = Integer.parseInt(Main.build);
		if (buildServer > buildThis) {
			update();
		} else if (buildServer < buildThis) {
			if (sendMessage) Logger.info("Updating to a server older than installed");
		} else if (buildServer == buildThis) {
			if (sendMessage) Logger.info("Updates not found, is already instaled");
		}
	}
	public void checkUpdate(boolean sendMessage) {
		String build = sendGet(Main.serverHttp+"/RM.php?get-last-build");
		this.lastBuild = build;
		double ver = 0.0;
		try {
			ver = Double.parseDouble(this.lastVersion);
		} catch (NumberFormatException e) {
			System.err.println(e);
		} 
		if (sendMessage) Logger.info("Test connection, server: "+Main.getConfig().get("serverHttp"));
		if (hasConnection()) {
			if (ver > Main.version) {
				update();
			} else if (ver < Main.version) {
				if (sendMessage) Logger.info("The server version has not yet been updated");
			} else if (ver == Main.version) {
				if (sendMessage) Logger.info("Latest version is already installed");
				if (sendMessage) Logger.info("Getting updates...");
				checkBuild(sendMessage);
			} 
		} else {
			if (sendMessage) Logger.warning("You have not connnection or server is not available");
			return;
		} 
	}
	public void checkUpdate() { checkUpdate(false); }
	private boolean hasConnection() {
		boolean connect;
		try {
			URL obj = new URL(Main.getConfig().get("serverHttp"));
			HttpURLConnection connection = (HttpURLConnection)
			obj.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			StringBuffer response = new StringBuffer(); String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			connect = true;
		} catch (Exception e) {
			connect = false;
		} 
		return connect;
	}

	public void shutdown() {}
	public void update(int circle) {
		if (circle == 15) checkUpdate(); 
	}
}
