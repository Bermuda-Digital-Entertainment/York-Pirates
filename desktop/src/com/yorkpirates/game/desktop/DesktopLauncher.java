package com.yorkpirates.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.yorkpirates.game.YorkPirates;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("York Pirates");
    config.setMaximized(true);
		new Lwjgl3Application(new YorkPirates(), config);
	}
}
