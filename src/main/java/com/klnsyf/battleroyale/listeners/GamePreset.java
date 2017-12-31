package com.klnsyf.battleroyale.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.GamePresetEvent;
import com.klnsyf.battleroyale.worldborder.WorldBorderHandler;

public class GamePreset implements Listener {
	private BattleRoyaleSetup plugin;
	private BattleRoyale battleRoyale;
	private Config config;

	public GamePreset(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onGamePreset(GamePresetEvent event) {
		if (battleRoyale.getState() == 0) {
			battleRoyale = event.getBattleRoyale();
			battleRoyale.setWorld(event.getWorld());
			battleRoyale.setState(1);
			config = battleRoyale.getConfig();
			config.setWorldBorderHandler(new WorldBorderHandler(battleRoyale));
			plugin.getServer().getConsoleSender()
					.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaBattlefield ¡ìb" + battleRoyale.getWorld().getName()
							+ "¡ìa has been preseted with configuration file with name "
							+ battleRoyale.getConfig().getConfigName());
			for (Player player : battleRoyale.getWorld().getPlayers()) {
				player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaBattlefield ¡ìb" + battleRoyale.getWorld().getName()
						+ "¡ìa has been preseted");
				player.sendMessage(
						"[¡ì6Battle Royale¡ìr] ¡ìaYou can use command ¡ì6/battleroyale¡ìa join to join into the battlefield");
			}
			new ListenersReload().listenersReload(battleRoyale);
		}
	}

	public BattleRoyaleSetup getPlugin() {
		return plugin;
	}

	public void setPlugin(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

}
