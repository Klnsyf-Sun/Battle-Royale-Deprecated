package com.klnsyf.battleroyale.listeners;

import java.util.ArrayList;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;

public class PlayerBreakBlock implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Configuration configuration = new Configuration();

	public PlayerBreakBlock() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void onPlayerBreakStone(BlockBreakEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				ArrayList<Map<String, ArrayList<Map<String, Integer>>>> dropList = configuration.getValue(
						event.getPlayer().getWorld(),
						ConfigurationKey.BATTLE_MISC_DROP_LIST);
				for (Map<String, ArrayList<Map<String, Integer>>> blockDropList : dropList) {
					if (event.getBlock().getType() == Material.getMaterial((String) blockDropList.keySet().toArray()[0])) {
						if (event.getBlock().getDrops().size() == 0
								|| event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand()).size() > 0) {
							for (Map<String, Integer> item : (ArrayList<Map<String, Integer>>) blockDropList.values()
									.toArray()[0]) {
								if (Math.random() * 10000 < (int) item.values().toArray()[0]) {
									event.getPlayer().getWorld().dropItem(event.getBlock().getLocation(),
											new ItemStack(Material.getMaterial((String) item.keySet().toArray()[0])));
								}
							}
						}
					}
				}
			}
		}
	}

}
