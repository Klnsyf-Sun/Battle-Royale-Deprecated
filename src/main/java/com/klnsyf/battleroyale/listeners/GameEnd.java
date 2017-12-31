package com.klnsyf.battleroyale.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.GameEndEvent;

public class GameEnd implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public GameEnd(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onGameEnd(GameEndEvent event) {
		if (event.getWinner() != null) {
			for (Player player : config.getPlayers()) {
				player.sendTitle("[¡ì6Battle Royale¡ìr]", "-= Winner: ¡ìd" + event.getWinner().getName() + "¡ìr =-", 10, 80,
						10);
			}
		} else {
			Bukkit.broadcastMessage("[¡ì6Battle Royale¡ìr] ¡ìcGame has been forced to terminate");
		}
		battleRoyale.getWorld().setDifficulty(Difficulty.PEACEFUL);
		battleRoyale.setState(0);
		plugin.getServer().getScheduler().cancelAllTasks();
		new ListenersReload().listenersReload(battleRoyale);
		config.roundReset();
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
