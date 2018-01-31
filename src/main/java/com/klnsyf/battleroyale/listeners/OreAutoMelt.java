package com.klnsyf.battleroyale.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;

public class OreAutoMelt implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final Configuration configuation = new Configuration();

	public OreAutoMelt() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onOreBlockBreak(BlockBreakEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getBlock().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getBlock().getWorld()).getStatue() == 2) {
				if ((boolean) configuation.getValue(event.getBlock().getWorld(), ConfigurationKey.BATTLE_MISC_ORE_AUTO_MELT)) {
					if (event.getBlock().getType() == Material.IRON_ORE
							|| event.getBlock().getType() == Material.GOLD_ORE) {
						event.setCancelled(true);
						Location l = event.getBlock().getLocation();
						Material m = event.getBlock().getType();
						event.getBlock().setType(Material.AIR);
						if (m == Material.IRON_ORE) {
							event.getBlock().getWorld().dropItemNaturally(l, new ItemStack(Material.IRON_INGOT, 1));
						} else if (m == Material.GOLD_ORE) {
							event.getBlock().getWorld().dropItemNaturally(l, new ItemStack(Material.GOLD_INGOT, 1));
						}
					}
				}
			}
		}
	}

}
