package com.klnsyf.battleroyale.listeners;

import org.bukkit.DyeColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.material.Dye;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;

public class AutoLapis implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;

	public AutoLapis() {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if (event.getInventory().getType() == InventoryType.ENCHANTING) {
					fillLapis(event.getInventory());
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if (event.getInventory().getType() == InventoryType.ENCHANTING) {
					event.getInventory().clear(1);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getWhoClicked().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getWhoClicked().getWorld()).getStatue() == 2) {
				if (event.getInventory().getType() == InventoryType.ENCHANTING) {
					if (event.getSlotType() == SlotType.CRAFTING) {
						if (event.getSlot() == 1) {
							event.setCancelled(true);
						} else if (event.getSlotType() == SlotType.CRAFTING) {
							fillLapis(event.getInventory());
						}
					}
				}
			}
		}
	}

	private void fillLapis(Inventory inventory) {
		Dye dye = new Dye();
		dye.setColor(DyeColor.BLUE);
		inventory.setItem(1, dye.toItemStack(3));
	}

}
