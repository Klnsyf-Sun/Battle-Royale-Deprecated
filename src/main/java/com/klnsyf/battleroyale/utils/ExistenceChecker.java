package com.klnsyf.battleroyale.utils;

import java.io.File;

import org.bukkit.Server;
import org.bukkit.World;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;

public class ExistenceChecker {
	private final static Server server = BattleRoyale.server;

	public ExistenceChecker() {
	}

	public static boolean worldExist(String worldName) {
		return server.getWorld(worldName) != null ? true : false;
	}

	public static boolean configExist(String configName) {
		return (new File(BattleRoyale.dataFolder.getPath() + "\\configuration\\" + configName + ".yml")).exists() ? true
				: false;
	}

	public static boolean worldPreset(World world) {
		return BattlefieldHandler.battlefields.containsKey(world) ? true : false;
	}

}
