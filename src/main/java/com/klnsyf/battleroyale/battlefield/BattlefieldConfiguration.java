package com.klnsyf.battleroyale.battlefield;

import java.util.ArrayList;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class BattlefieldConfiguration {

	// Statue:
	// - 0: Presetted
	// - 1: Loading
	// - 2: Running
	private int statue;
	public ArrayList<Player> players;
	public ArrayList<Player> alivePlayers;
	private boolean isWorldBorderShrinking;
	private double shrinkSpeed;

	private YamlConfiguration configurationFile;
	private ArrayList<Double> spreadLocations;
	private int maxPlayer;
	private boolean autoStart;

	public BattlefieldConfiguration(YamlConfiguration configurationFile) {
		this.statue = 0;
		this.players = new ArrayList<Player>();
		this.alivePlayers = new ArrayList<Player>();
		this.isWorldBorderShrinking = false;

		this.configurationFile = configurationFile;
		this.spreadLocations = new ArrayList<Double>();
		this.maxPlayer = Integer.MAX_VALUE;
		this.autoStart = false;

	}

	public YamlConfiguration getConfigurationFile() {
		return configurationFile;
	}

	public int getStatue() {
		return statue;
	}

	public void setStatue(int statue) {
		this.statue = statue;
	}

	public ArrayList<Double> getSpreadLocations() {
		return spreadLocations;
	}

	public int getMaxPlayer() {
		return maxPlayer;
	}

	public void setConfigurationFile(YamlConfiguration configurationFile) {
		this.configurationFile = configurationFile;
	}

	public void setSpreadLocations(ArrayList<Double> spreadLocations) {
		this.spreadLocations = spreadLocations;
	}

	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}

	public boolean isAutoStart() {
		return autoStart;
	}

	public void setAutoStart(boolean autoStart) {
		this.autoStart = autoStart;
	}

	public boolean isWorldBorderShrinking() {
		return isWorldBorderShrinking;
	}

	public void setWorldBorderShrinking(boolean isWorldBorderShrinking) {
		this.isWorldBorderShrinking = isWorldBorderShrinking;
	}

	public double getShrinkSpeed() {
		return shrinkSpeed;
	}

	public void setShrinkSpeed(double shrinkSpeed) {
		this.shrinkSpeed = shrinkSpeed;
	}

}
