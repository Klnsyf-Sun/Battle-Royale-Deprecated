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
	private YamlConfiguration configurationFile;
	private ArrayList<Double> spreadLocations;
	public ArrayList<Player> players;

	public BattlefieldConfiguration(YamlConfiguration configurationFile) {
		this.configurationFile = configurationFile;
		this.statue = 0;
		this.spreadLocations = new ArrayList<Double>();
		this.players = new ArrayList<Player>();
	}

	public YamlConfiguration getConfigurationFile() {
		return configurationFile;
	}

	public void setConfigurationFile(YamlConfiguration configurationFile) {
		this.configurationFile = configurationFile;
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

	public void setSpreadLocations(ArrayList<Double> spreadLocations) {
		this.spreadLocations = spreadLocations;
	}

}
