package com.klnsyf.battleroyale.utils;

import org.bukkit.entity.Player;

import com.klnsyf.battleroyale.BattleRoyaleSetup;

public class InventoryHandler {
	private BattleRoyaleSetup plugin;

	public InventoryHandler(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}

	public void clearInventory(Player player) {
		player.getInventory().clear();
	}

	public BattleRoyaleSetup getPlugin() {
		return plugin;
	}

	public void setPlugin(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}
}
