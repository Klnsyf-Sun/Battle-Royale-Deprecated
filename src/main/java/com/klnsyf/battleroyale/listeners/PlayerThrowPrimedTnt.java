package com.klnsyf.battleroyale.listeners;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.events.PlayerThrowPrimedTntEvent;

public class PlayerThrowPrimedTnt implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;

	public PlayerThrowPrimedTnt() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerInteractTnt(PlayerInteractEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if (event.getMaterial() == Material.TNT) {
					if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
						event.setCancelled(true);
						server.getPluginManager().callEvent(new PlayerThrowPrimedTntEvent(event.getPlayer()));
						event.getPlayer().getInventory().getItemInMainHand()
								.setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerThrowPrimedTnt(PlayerThrowPrimedTntEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getWorld()).getStatue() == 2) {
				Entity e = event.getWorld().spawnEntity(event.getPlayer().getEyeLocation(),
						EntityType.PRIMED_TNT);
				e.setVelocity(event.getPlayer().getLocation().getDirection().normalize());
			}
		}
	}

}
