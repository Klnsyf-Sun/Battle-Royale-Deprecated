package com.klnsyf.battleroyale.messages;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.settings.Settings;

public class Messages {

	public static File language = new File(BattleRoyale.dataFolder.getPath() + "\\language\\" + Settings.language + ".yml");

	Messages() {
	}

	public static String getMessage(MessageKey key, String... replacements) {
		new YamlConfiguration();
		YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(language);
		String message = yamlConfiguration.getString(key.getKey());
		String[] tags = key.getTags();
		if (replacements.length == tags.length) {
			for (int i = 0; i < tags.length; ++i) {
				message = message.replace(tags[i], replacements[i]);
			}
			return message;
		} else {
			throw new IllegalArgumentException();
		}
	}
}
