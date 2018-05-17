package com.klnsyf.battleroyale.listeners.battlefieldGUI;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldConfiguration;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.events.PlayerJoinBattlefieldEvent;
import com.klnsyf.battleroyale.events.PlayerOpenBattlefieldGUIEvent;

public class PlayerOpenBattlefieldGUI implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;
	private final Configuration configuation = new Configuration();

	public PlayerOpenBattlefieldGUI() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerUseBattlefieldBook(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Bukkit.broadcastMessage("Check Point 01");
			ItemStack itemStack = event.getItem();
			Bukkit.broadcastMessage("Check Point 02");
			if (itemStack != null) {
				if (itemStack.getType() == Material.BOOK) {
					Bukkit.broadcastMessage("Check Point 03");
					if (itemStack.hasItemMeta()) {
						Bukkit.broadcastMessage("Check Point 04");
						ItemMeta itemMeta = itemStack.getItemMeta();
						Bukkit.broadcastMessage("Check Point 05");
						if (itemMeta.getDisplayName().equalsIgnoreCase("¡ìr¡ì6Battlefield Book")
								&& itemMeta.getLore().equals(Arrays.asList("¡ìrRight Click to Open Battlefield GUI"))) {
							Bukkit.broadcastMessage("Check Point 06");
							server.getPluginManager().callEvent(new PlayerOpenBattlefieldGUIEvent(event.getPlayer()));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerOpenBattlefieldGUI(PlayerOpenBattlefieldGUIEvent event) {
		Bukkit.broadcastMessage("Check Point 07");
		Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST, "-= Battlefield GUI =-");
		HashMap<World, BattlefieldConfiguration> currentBattlefield = BattlefieldHandler.battlefields;
		Bukkit.broadcastMessage("Check Point 08");
		for (int index = 0; index < Math.min(currentBattlefield.size(), 27); index++) {
			if (index > currentBattlefield.size() - 1) {
				break;
			} else {
				ItemStack battlefieldItem;
				String statue;
				switch (currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getStatue()) {
					case 0:
						battlefieldItem = new Wool(DyeColor.GREEN).toItemStack(1);
						statue = "Preseted";
						break;
					case 1:
						battlefieldItem = new Wool(DyeColor.YELLOW).toItemStack(1);
						statue = "Loading";
						break;
					case 2:
						battlefieldItem = new Wool(DyeColor.RED).toItemStack(1);
						statue = "Running";
						break;
					default:
						battlefieldItem = new Wool(DyeColor.BLACK).toItemStack(1);
						statue = "ERROR";
						break;
				}
				ItemMeta battlefieldItemMeta = battlefieldItem.getItemMeta();
				battlefieldItemMeta.setDisplayName(
						"¡ìrBattlefield: ¡ìb" + ((World) (currentBattlefield.keySet().toArray()[index])).getName());
				battlefieldItemMeta.setLore(Arrays.asList(
						"¡ìrPlayer: ¡ìd" + currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).players.size()
								+ "/"
								+ currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getMaxPlayer(),
						"¡ìrStatue: ¡ìb" + statue));
				if (currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getStatue() == 0) {
					List<String> lore = battlefieldItemMeta.getLore();
					lore.add("¡ìr¡ìaClick to join");
					battlefieldItemMeta.setLore(lore);
				}
				battlefieldItem.setItemMeta(battlefieldItemMeta);
				inventory.setItem(index, battlefieldItem);
			}
		}
		Bukkit.broadcastMessage("Check Point 09");
		event.getPlayer().openInventory(inventory);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory().getName() == "-= Battlefield GUI =-") {
			event.getInventory().clear();
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getName() == "-= Battlefield GUI =-") {
			event.setCancelled(true);
			if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta() != null
					&& event.getCurrentItem().getItemMeta().hasLore()) {
				if (event.getCurrentItem().getItemMeta().getLore().contains("¡ìr¡ìaClick to join")) {
					server.getPluginManager().callEvent(new PlayerJoinBattlefieldEvent((Player) event.getWhoClicked(),
							event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("¡ìrBattlefield: ¡ìb", "")));
					ItemStack battlefieldBook = new ItemStack(Material.BOOK);
					ItemMeta battlefieldBookMeta = battlefieldBook.getItemMeta();
					battlefieldBookMeta.setDisplayName("¡ìr¡ì6Battlefield Book");
					battlefieldBookMeta.setLore(Arrays.asList("¡ìrRight Click to Open Battlefield GUI"));
					battlefieldBook.setItemMeta(battlefieldBookMeta);
					event.getWhoClicked().getInventory().remove(battlefieldBook);
				}
			}
		}
	}
}
