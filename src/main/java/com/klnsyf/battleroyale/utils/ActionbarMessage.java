package com.klnsyf.battleroyale.utils;

import org.bukkit.entity.Player;

import com.klnsyf.battleroyale.utils.actionbar.ActionbarMessageSetup;

public class ActionbarMessage {

	public ActionbarMessage() {
	}

	public void sendActionbarMessage(Player player, String message) {
		new ActionbarMessageSetup().getActionBarMessage().sendActionbarMessage(player, message);
	}
}
