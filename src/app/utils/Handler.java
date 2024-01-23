package app.utils;

public abstract class Handler extends Thread {
	private boolean runA;

	public void run() {
		this.runA = true;
		onrun();
		for (; this.runA; ontick());
	}
	public void exit() {
		this.runA = false;
		onstop();
	}

	public abstract void onrun();

	public abstract void ontick();

	public abstract void onstop();

	public abstract void onTPS(int paramInt);
}
