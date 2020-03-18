package com.bartish.oooist.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bartish.oooist.Main;

public class DesktopLauncher {
	public static final int WIDTH = 360;
	public static final int HEIGHT = 640;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = WIDTH;
		config.height = HEIGHT;
		config.title = "111 is 2!";
		new LwjglApplication(new Main(), config);
	}
}
