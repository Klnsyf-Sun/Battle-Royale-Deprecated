package com.klnsyf.battleroyale.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;

public class PlayerQuit implements Listener {
	private BattleRoyale battleRoyale;

	private BattleRoyaleSetup plugin;

	public PlayerQuit(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (battleRoyale.getState() == 3) {
			event.getPlayer().damage(11037);
		}
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

}
