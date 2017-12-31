package com.klnsyf.battleroyale.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;

public class OreAutoMelt implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public OreAutoMelt(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (battleRoyale.getState() == 3) {
			if (config.isAutoMelt()) {
				if (event.getBlock().getType() == Material.IRON_ORE
						|| event.getBlock().getType() == Material.GOLD_ORE) {
					event.setCancelled(true);
					Location l = event.getBlock().getLocation();
					Material m = event.getBlock().getType();
					event.getBlock().setType(Material.AIR);
					if (m == Material.IRON_ORE) {
						battleRoyale.getWorld().dropItemNaturally(l, new ItemStack(Material.IRON_INGOT, 1));
					} else if (m == Material.GOLD_ORE) {
						battleRoyale.getWorld().dropItemNaturally(l, new ItemStack(Material.GOLD_INGOT, 1));
					}
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
