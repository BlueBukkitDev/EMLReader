package dev.blue.eml;

public class App implements Runnable {
	
	private boolean running;
	private Thread thread;
	
	private Window window;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public App() {
		running = false;
		thread = new Thread();
	}

	@Override
	public void run() {
		//loop
	}
	
	public synchronized void start() {
		thread.start();
		running = true;
		window = new Window();
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Window getWindow () {
		return window;
	}
	
	public MouseManager getMouseManager () {
		return mouseManager;
	}
	
	public KeyManager getKeyManager () {
		return keyManager;
	}
}
