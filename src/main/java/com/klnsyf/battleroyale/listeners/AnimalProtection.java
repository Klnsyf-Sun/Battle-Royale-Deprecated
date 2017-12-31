package com.klnsyf.battleroyale.listeners;

import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;

public class AnimalProtection implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public AnimalProtection(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onAnimalDamage(EntityDamageEvent event) {
		if (battleRoyale.getState() == 3) {
			if (config.isProtectAnimal()) {
				if (event.getEntity() instanceof Animals) {
					event.setCancelled(true);
				}
			}
		}
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

}