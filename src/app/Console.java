package app;
import java.io.*;

public class Console extends Thread {
	public final int width = 120;
	public final int height = 30;

	public boolean started = true;

	public void run() {
		clearConsole();
		String[] screen = new String[width * height + 1];
		screen[width * height] = "\0";
		for (int frame = 0;frame <= 1;frame++) {
			for (int i = 0;i < width;i++) {
				for (int j = 0;j < height;j++) {
					float x = i / width * 2.0f - 1.0f;
					float y = j / height * 2.0f - 1.0f;
					String pixel = " ";
					if (x*x + y*y < 0.5) pixel = "@";
					screen[i + j * width] = pixel;
				}
			}
			System.out.println(String.join("", screen));
		}
		while (started) {
		}
	}
	public static void clearConsole() {
		String ANSI_CLEAR_SEQ = "\u001b[2J";
		System.out.println(ANSI_CLEAR_SEQ);
	}
}