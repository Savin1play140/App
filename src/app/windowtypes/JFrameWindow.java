package app.windowtypes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import app.logger.*;
import app.*;
import app.utils.*;

public class JFrameWindow extends JFrame implements WindowType {
	private static final long serialVersionUID = 1L;

	// format: x.x.x_y_z - version_bild_Fix-bild
	public static String version = "0.0.1_1_17";

	public void createGUI(String name, String[] texts, int x, int y, boolean resizable) {
		setTitle(name);
		setSize(x+7, y+31);// +7, +31
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(resizable);

		try {
			String path = "./resources/icon1.png";
			Image icon = new ImageIcon(path).getImage();
			setIconImage(icon);
		} catch (Exception e) {
			Logger.error("Icon error: "+e.getMessage());
		}

		addWindowListener(new WindowListener() {
			public void windowActivated(WindowEvent event) {}
			public void windowClosed(WindowEvent event) {}
			public void windowClosing(WindowEvent event) {
				Logger.info("User click on close button");
				if (Main.alertsEnable) {
					Alerts.ExitMessage(event);
				} else {
					event.getWindow().setVisible(false);
					Main.Exit();
				}
			}
			public void windowDeactivated(WindowEvent event) {}
			public void windowDeiconified(WindowEvent event) {}
			public void windowIconified(WindowEvent event) {}
			public void windowOpened(WindowEvent event) {
				if (Main.alertsEnable) {
					Alerts.RunMessage();
				}
			}
		});

		JPanel panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				int yText = 10;
				super.paintComponent(g);
				g.setColor(Color.RED);
				g.fillOval(x/4, x/4, x/2, y/2);
				//g.setColor(Color.GREEN);
				g.drawRect(0,1,x,y);
				g.drawRect(1,2,x-1,y-1);
				g.drawRect(2,3,x-3,y-3);
				g.drawRect(3,4,x-5,y-5);
				g.drawRect(4,5,x-7,y-7);
				g.drawRect(5,6,x-9,y-9);
				g.drawRect(6,7,x-11,y-11);
				g.drawRect(6,7,x-12,y-12);
				//g.drawLine(0,0,x,y);
				//g.drawLine(0,y,x,0);
				g.setColor(Color.BLACK);
				for (String text : texts) {
					g.drawString(text, 0, yText);
					yText += 13;
				}
			}
		};
		setPreferredSize(new Dimension(390, 135));
		add(panel);
		setVisible(true);
	}
	protected static ImageIcon createIcon(String path) {
		URL imgURL = JFrameWindow.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("File not found " + path);
			return null;
		}
	}
}