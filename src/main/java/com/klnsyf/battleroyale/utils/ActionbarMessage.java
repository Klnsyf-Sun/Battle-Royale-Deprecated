package com.klnsyf.battleroyale.utils;

import org.bukkit.entity.Player;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.utils.actionbar.ActionbarMessageSetup;

public class ActionbarMessage {
	private BattleRoyaleSetup plugin;

	public ActionbarMessage(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}

	public void sendActionbarMessage(Player player, String message) {
		new ActionbarMessageSetup(plugin).getActionBarMessage().sendActionbarMessage(player, message);
	}
}
