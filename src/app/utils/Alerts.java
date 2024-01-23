package app.utils;
import app.Main;
import app.logger.Logger;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

public class Alerts {
	public static void ExitMessage(WindowEvent winEve) {
		Object[] options = { "yes", "no" };
		int n = JOptionPane.showOptionDialog(null, "You are exit?", "Close", 0, 3, null, options, options[0]);
    
		if (n == 0) {
			Logger.warning("closing window...");
			winEve.getWindow().setVisible(false);
			Logger.info("close window");
			Logger.warning("stoping...");
			Main.Exit();
		} 
	}
	public static void sendMessage(String title, String message, int type) {
		JOptionPane.showMessageDialog( null,  title,  message,  type);
	}
  
	public static void RunMessage() {
	  JOptionPane.showMessageDialog(null, "Hello, you run the GMPJW(GMPJarWindow)!", "Hello", -1);
	}
}
