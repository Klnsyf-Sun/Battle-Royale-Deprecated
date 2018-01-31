package com.klnsyf.battleroyale.listeners;

import org.bukkit.Server;
import org.bukkit.entity.Animals;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntityDamageEvent;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;

public class AnimalProtection implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final Configuration configuation = new Configuration();

	public AnimalProtection() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onAnimalDamage(EntityDamageEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).getStatue() == 2) {
				if (event.getEntity() instanceof Animals && (boolean) configuation.getValue(event.getEntity().getWorld(),
						ConfigurationKey.BATTLE_MISC_PROTECT_ANIMAL)) {
					event.setCancelled(true);
				}
			}
		}
	}
}
