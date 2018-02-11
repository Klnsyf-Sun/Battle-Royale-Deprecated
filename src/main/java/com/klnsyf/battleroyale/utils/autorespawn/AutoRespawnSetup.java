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
			version = plugin.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit.", "");
		} catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
			return false;
		}
		switch (version) {
			case "v1_9_R1":
				autoRespawn = new AutoRespawn_v1_9_R1();
				break;
			case "v1_9_R2":
				autoRespawn = new AutoRespawn_v1_9_R2();
				break;
			case "v1_10_R1":
				autoRespawn = new AutoRespawn_v1_10_R1();
				break;
			case "v1_11_R1":
				autoRespawn = new AutoRespawn_v1_11_R1();
				break;
			case "v1_12_R1":
				autoRespawn = new AutoRespawn_v1_12_R1();
				break;
			default:
				autoRespawn = null;
		}
		return autoRespawn != null;
	}

	public AutoRespawn getAutoRespawn() {
		return autoRespawn;
	}
}
