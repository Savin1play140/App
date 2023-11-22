package app.utils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import app.*;
import app.logger.*;

public class Alerts {
	public static void ExitMessage(WindowEvent winEve) {
		Object[] options = {"yes", "no"};
		int n = JOptionPane.showOptionDialog(
			null,
			"You are exit?", "Close",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0]
		);
		if (n == 0) {
			Logger.warning("closing window...");
			winEve.getWindow().setVisible(false);
			Logger.info("close window");
			Logger.warning("stoping...");
			Main.Exit();
		}
	}
	public  static void sendMessage(String title, String message, int type) {
		JOptionPane.showMessageDialog(
			null,
			title,
			message,
			type
		);
	}
	public static void RunMessage() {
		JOptionPane.showMessageDialog(
			null,
			"Hello, you run the GMPJW(GMPJarWindow)!",
			"Hello",
			-1
		);
	}
}