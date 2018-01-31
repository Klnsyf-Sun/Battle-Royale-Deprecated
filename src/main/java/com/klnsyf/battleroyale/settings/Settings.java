package com.klnsyf.battleroyale.settings;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.klnsyf.battleroyale.BattleRoyale;

public class Settings {

	public static String language;
	public static boolean isCheckUpdate;

	public static final String DEFAULT_LANGUAGE = "en_US";
	private static final boolean DEFAULT_ISCHECKUPDATE = false;

	public Settings() {
		File configFile = new File(BattleRoyale.dataFolder, "config.yml");

		new YamlConfiguration();
		YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);
		Settings.language = yamlConfiguration.isString("language")
				? yamlConfiguration.getString("language")
				: DEFAULT_LANGUAGE;
		Settings.isCheckUpdate = yamlConfiguration.isBoolean("enable-update-check")
				? yamlConfiguration.getBoolean("enable-update-check")
				: DEFAULT_ISCHECKUPDATE;
	}

}
