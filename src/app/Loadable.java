package app;

public abstract class Loadable extends Thread {
	private boolean runed = true;

	abstract public void load(Main main);
	abstract public void run();
	abstract public void shutdown();

	final public void disable() {
		runed = false;
		shutdown();
	}
	final public boolean isActive() {
		return runed;
	}
}