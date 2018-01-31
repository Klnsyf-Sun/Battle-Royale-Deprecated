package com.klnsyf.battleroyale.listeners;

import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;

public class BattlefieldProtect implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;

	public BattlefieldProtect() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getBlock().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getBlock().getWorld()).getStatue() != 2) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).getStatue() != 2) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() != 2) {
				event.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getBlock().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getBlock().getWorld()).getStatue() != 2) {
				event.setCancelled(true);
			}
		}
	}

}
