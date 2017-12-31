package com.klnsyf.battleroyale.utils.autorespawn;

import org.bukkit.Bukkit;

import com.klnsyf.battleroyale.BattleRoyaleSetup;

public class AutoRespawnSetup {
	private BattleRoyaleSetup plugin = null;
	AutoRespawn autoRespawn = null;

	public AutoRespawnSetup(BattleRoyaleSetup plugin) {
		if (this.plugin == null) {
			this.plugin = plugin;
			setupAutoRespawn();
		}
	}

	private boolean setupAutoRespawn() {
		String version;
		try {
			version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
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
