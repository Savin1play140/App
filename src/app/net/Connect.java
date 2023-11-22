package app.net;
import java.io.*;
import java.net.Socket;
import app.*;
import app.logger.*;

public class Connect extends Loadable {
	private boolean connected = true;
	private Socket socket;
	private BufferedWriter out;
	private BufferedReader in;
	private BufferedReader reader;
	public void load(Main main) {
		try {
			socket = new Socket(main.getAddr(), main.getPort());
			reader = new BufferedReader(new InputStreamReader(System.in));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
            Logger.error(e.getMessage());
            connected = false;
        }
	}
	public void run() {
		if (!connected) return;
		try {
			this.out.write("client version: "+Main.version+"\n");
			this.out.flush();
			String serverWord = this.in.readLine();
			Logger.info(serverWord);
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
		while (isActive()) {
			try {
//				String word = reader.readLine();
//				this.out.write("Test message" + "\n");
//				this.out.flush();
				String serverWord = this.in.readLine();
				Logger.info(serverWord);
			} catch (IOException e) {
				Logger.error(e.getMessage());
			}
		}
	}
	public void shutdown() {
		try {
			Logger.info("client closing...");
			this.socket.close();
			this.in.close();
			this.out.close();
			Logger.info("client closed");
		} catch (IOException e) {
			Logger.error(e.getMessage());
		}
	}
}