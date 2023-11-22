package app;
import app.windowtypes.*;

public class Window {
	// format: x.x.x_y_z - version_build_Sub-build

	public void createGUI(String type, String name, String[] texts, int x, int y, boolean resizable) {
		WindowType win;
		switch (type) {
			case "console":
				win = new ConsoleWindow();
				break;
			case "release":
			default:
				win = new JFrameWindow();
				break;
		}
		win.createGUI(name, texts, x, y, resizable);
	}
	public void createGUI(WindowType type) {
		String[] texts = new String[] {
			"Hello user.",
			"This jar file has compiled in release mode.", "This project for all.",
			"Version format: v.v_s - version_build."
		};
		type.createGUI("Window "+Main.version+"[Release]", texts, 500, 500, false);
	}
}