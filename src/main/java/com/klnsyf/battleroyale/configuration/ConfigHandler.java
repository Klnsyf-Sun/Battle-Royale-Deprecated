package com.klnsyf.battleroyale.configuration;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.klnsyf.battleroyale.battleroyale.BattleRoyale;

public class ConfigHandler {
	private BattleRoyale battleRoyale;
	private Config config;
	private String configName;

	public ConfigHandler(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.config = battleRoyale.getConfig();
		this.configName = config.getConfigName();
	}

	public void loadConfig() {
		FileConfiguration configFile = battleRoyale.getPlugin().getConfig();
		// player-genetic-option
		config.setMaxHealth(configFile.getInt(configName + ".player-genetic-option.maxHealth"));
		// player-spread-option
		config.setMinRange(configFile.getInt(configName + ".player-spread-option.range.min"));
		config.setMaxRange(configFile.getInt(configName + ".player-spread-option.range.max"));
		config.setSpreadDistance(configFile.getInt(configName + ".player-spread-option.distance"));
		config.setSpreadSpace(configFile.getInt(configName + ".player-spread-option.space"));
		config.setAttempt(configFile.getInt(configName + ".player-spread-option.attempt"));
		// worldborder-option
		config.setCenterX(configFile.getInt(configName + ".worldborder-option.center.x"));
		config.setCenterZ(configFile.getInt(configName + ".worldborder-option.center.z"));
		config.setDamageAmount(configFile.getDouble(configName + ".worldborder-option.damage.amount"));
		config.setDamageBuffer(configFile.getInt(configName + ".worldborder-option.damage.buffer"));
		config.setWarningDistance(configFile.getInt(configName + ".worldborder-option.warning.distance"));
		// game-option
		config.setHideName(configFile.getBoolean(configName + ".game-option.hide-name"));
		config.setAutoMelt(configFile.getBoolean(configName + ".game-option.ore-option.auto-melt"));
		config.setProtectAnimal(configFile.getBoolean(configName + ".game-option.protect-animal"));
		config.setGameLoadLatencyTime(configFile.getInt(configName + ".game-option.gameload-option.latency-time"));
		config.setPraticleAmount(configFile.getInt(configName + ".game-option.gameload-option.praticle.amount"));
		config.setMaxRadius(configFile.getInt(configName + ".game-option.worldborder-option.radius.max"));
		config.setMinRadius(configFile.getInt(configName + ".game-option.worldborder-option.radius.min"));
		config.setShrinkSpeed(configFile.getDouble(configName + ".game-option.worldborder-option.shrink.speed"));
		config.setCompassMode(configFile.getBoolean(configName + ".game-option.compass-option.mode"));
		config.setCompassColdDown(configFile.getInt(configName + ".game-option.compass-option.cold-down"));
		config.setAccelerateSpeed(configFile.getDouble(configName + ".game-option.accelerate-shrink-option.speed"));
		config.setAccelerateTicks(configFile.getInt(configName + ".game-option.accelerate-shrink-option.ticks"));
		config.setAccelerateItem(
				Material.getMaterial(configFile.getString(configName + ".game-option.accelerate-shrink-option.item")));
		config.setAccelerateColdDown(configFile.getInt(configName + ".game-option.accelerate-shrink-option.cold-down"));
		config.setAccelerateProtectTime(
				configFile.getInt(configName + ".game-option.accelerate-shrink-option.protect-time"));
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

}
