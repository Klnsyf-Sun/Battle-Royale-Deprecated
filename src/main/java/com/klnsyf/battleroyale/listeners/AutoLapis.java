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

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;

public class AutoLapis implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;

	public AutoLapis(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent evt) {
		if (evt.getInventory().getType() == InventoryType.ENCHANTING) {
			fillLapis(evt.getInventory());
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent evt) {
		if (evt.getInventory().getType() == InventoryType.ENCHANTING) {
			evt.getInventory().clear(1);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent evt) {
		if (evt.getInventory().getType() == InventoryType.ENCHANTING) {
			if (evt.getSlotType() == SlotType.CRAFTING) {
				if (evt.getSlot() == 1) {
					evt.setCancelled(true);
				} else if (evt.getSlotType() == SlotType.CRAFTING) {
					fillLapis(evt.getInventory());
				}
			}
		}
	}

	private void fillLapis(Inventory inv) {
		Dye dye = new Dye();
		dye.setColor(DyeColor.BLUE);
		inv.setItem(1, dye.toItemStack(3));
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}
}
