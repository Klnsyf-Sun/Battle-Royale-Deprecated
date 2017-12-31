package com.klnsyf.battleroyale.utils;

import com.klnsyf.battleroyale.BattleRoyaleSetup;

public class WorldHandler {
	private BattleRoyaleSetup plugin;

	public WorldHandler(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}

	public BattleRoyaleSetup getPlugin() {
		return plugin;
	}

	public void setPlugin(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}
}
