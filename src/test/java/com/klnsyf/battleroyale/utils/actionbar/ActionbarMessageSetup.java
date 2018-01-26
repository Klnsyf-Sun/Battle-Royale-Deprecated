package com.klnsyf.battleroyale.utils.actionbar;

import com.klnsyf.battleroyale.BattleRoyale;

public class ActionbarMessageSetup {
	private BattleRoyale plugin = BattleRoyale.plugin;
	ActionbarMessage actionbarMessage = null;

	public ActionbarMessageSetup() {
		setupActionbar();
	}

	private boolean setupActionbar() {
		String version;
		try {
			version = plugin.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
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
