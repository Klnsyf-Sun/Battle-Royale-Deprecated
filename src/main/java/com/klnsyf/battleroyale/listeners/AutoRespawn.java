package com.klnsyf.battleroyale.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.utils.autorespawn.AutoRespawnSetup;

public class AutoRespawn implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;

	public AutoRespawn(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void autoRespawn(PlayerDeathEvent event) {
		new AutoRespawnSetup(plugin).getAutoRespawn().autoRespawn(event.getEntity(), event.getEntity().getLocation());
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}
}
