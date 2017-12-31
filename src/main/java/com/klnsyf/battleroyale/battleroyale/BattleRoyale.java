package com.klnsyf.battleroyale.battleroyale;

import org.bukkit.World;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.configuration.Config;

public class BattleRoyale {
	private BattleRoyaleSetup plugin;
	private World world;
	private Config config;
	private int state;

	/*
	 * state: 0- idle 1 - preset 2 - load 3 - start
	 */

	public BattleRoyale(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
		this.world = plugin.getServer().getWorlds().get(0);
		this.state = 0;
	}

	public BattleRoyaleSetup getPlugin() {
		return plugin;
	}

	public void setPlugin(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
