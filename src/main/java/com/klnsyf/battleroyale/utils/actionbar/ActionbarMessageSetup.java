package com.klnsyf.battleroyale.utils.actionbar;

import org.bukkit.Bukkit;

import com.klnsyf.battleroyale.BattleRoyaleSetup;

public class ActionbarMessageSetup {
	private BattleRoyaleSetup plugin = null;
	ActionbarMessage actionbarMessage = null;

	public ActionbarMessageSetup(BattleRoyaleSetup plugin) {
		if (this.plugin == null) {
			this.plugin = plugin;
			setupActionbar();
		}
	}

	private boolean setupActionbar() {
		String version;
		try {
			version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		} catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
			return false;
		}
		if (version.equals("v1_12_R1")) {
			actionbarMessage = new ActionbarMessage_v1_12_R1();
		}
		return actionbarMessage != null;
	}

	public ActionbarMessage getActionBarMessage() {
		return actionbarMessage;
	}
}
