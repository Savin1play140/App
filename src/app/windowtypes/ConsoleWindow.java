package app.windowtypes;
import app.*;
import app.utils.*;

public class ConsoleWindow implements WindowType {
	public void createGUI(String name, String[] texts, int x, int y, boolean resizable) {
		Main.DisableLogging();
		(new Console()).start();
	}
}