package app.update;

import app.Loadable;
import app.Main;
import app.logger.Logger;
import app.utils.Archive;

import java.io.*;
import java.net.*;

public class Updater extends Loadable {
	private String lastVersion = "";
	
	private String dataServer = "http://localhost/";
	public String sendGet(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
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

	@Override
	public void load(Main main) {
		String url = dataServer+"RM.php?get-last-version=true";
		lastVersion = sendGet(url);
		Logger.info("{Server} Version: "+lastVersion);
	}
	private static void downloadFile(String fileUrl, String fileName) throws IOException {
		URL url = new URL(fileUrl);
		URLConnection connection = url.openConnection();
		try (
			BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
			FileOutputStream fileOutputStream= new FileOutputStream(fileName)
		) {
			byte[] dataBuffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}
		}
	}

	@Override
	public void run() {
		double ver = 0.0;
		try {
			ver = Integer.parseInt(lastVersion);
		} catch (NumberFormatException e) {
			//Logger.error(e.getMessage());
		}
		Logger.info("{Local} Version: "+Main.version);
		if (!hasConnection()) {
			Logger.error("You not have connection or server is not connectable");
			return;
		}
		if (ver >= Main.version) {
			Logger.info("Installing update...");
			try {
				downloadFile(dataServer+"RM.php?get-update", lastVersion+".zip");
				Archive.unzip(lastVersion+".zip", "./update");
				Logger.info("The update was successfully installed in \"update\" dir");
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
		} else {
			Logger.info("Last version installed or not found");
		}
	}
	private boolean hasConnection() {
		boolean connect;
		try {
			URL obj = new URL(dataServer);
			HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
			connection.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
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

	@Override
	public void shutdown() {

	}
}
