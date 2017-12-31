package com.klnsyf.battleroyale;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.commands.BattleRoyaleCommand;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.GameEndEvent;
import com.klnsyf.battleroyale.utils.UpdateChecker;

public class BattleRoyaleSetup extends JavaPlugin {
	public static BattleRoyaleSetup instance;
	public static Plugin plugin;
	private BattleRoyale battleRoyale;

	@Override
	public void onEnable() {
		instance = this;
		plugin = this;
		saveDefaultConfig();

		battleRoyale = new BattleRoyale(this);
		battleRoyale.setConfig(new Config(battleRoyale, "default"));

		for (String str : this.getDescription().getCommands().keySet()) {
			getCommand(str).setExecutor(new BattleRoyaleCommand(battleRoyale));
		}

		if (this.getConfig().getBoolean("check-update")) {
			new UpdateChecker(this).updateCheck(Bukkit.getConsoleSender());
		}

		plugin.getServer().getPluginManager().callEvent(new GameEndEvent());
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

}
