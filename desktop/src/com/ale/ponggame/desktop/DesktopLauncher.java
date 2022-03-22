package com.ale.ponggame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ale.ponggame.PongGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Math IA";
		config.width = 1080;
		config.height = 720;
		config.foregroundFPS=60;
		config.fullscreen = true;

		new LwjglApplication(new PongGame(), config);
	}
}
