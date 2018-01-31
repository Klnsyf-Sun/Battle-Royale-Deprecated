package com.klnsyf.battleroyale.utils.autorespawn;

import com.klnsyf.battleroyale.BattleRoyale;

public class AutoRespawnSetup {
	private BattleRoyale plugin = BattleRoyale.plugin;
	AutoRespawn autoRespawn = null;

	public AutoRespawnSetup() {
		setupAutoRespawn();
	}

	private boolean setupAutoRespawn() {
		String version;
		try {
			version = plugin.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		} catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
			return false;
		}
		if (version.equals("v1_12_R1")) {
			autoRespawn = new AutoRespawn_v1_12_R1();
		}
		return autoRespawn != null;
	}

	public AutoRespawn getAutoRespawn() {
		return autoRespawn;
	}
}
