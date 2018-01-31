package com.klnsyf.battleroyale.configuration;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;

public class Configuration {

	public Configuration() {
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(YamlConfiguration configurationFile, ConfigurationKey key) {
		return (T) configurationFile.get(key.getPath(), key.getDefaultValue());
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(World world, ConfigurationKey key) {
		return (T) BattlefieldHandler.battlefields.get(world).getConfigurationFile().get(key.getPath(), key.getDefaultValue());
	}
}
