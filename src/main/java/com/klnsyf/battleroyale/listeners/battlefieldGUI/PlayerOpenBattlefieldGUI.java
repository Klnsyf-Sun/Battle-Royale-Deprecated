package com.klnsyf.battleroyale.listeners.battlefieldGUI;

import java.util.Arrays;
import java.util.HashMap;

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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldConfiguration;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.events.PlayerJoinBattlefieldEvent;
import com.klnsyf.battleroyale.events.PlayerOpenBattlefieldGUIEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;

public class PlayerOpenBattlefieldGUI implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;

	public PlayerOpenBattlefieldGUI() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerUseBattlefieldBook(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemStack itemStack = event.getItem();
			if (itemStack != null) {
				if (itemStack.getType() == Material.BOOK) {
					if (itemStack.hasItemMeta()) {
						ItemMeta itemMeta = itemStack.getItemMeta();
						if (itemMeta.getDisplayName()
								.equals(Messages.getMessage(MessageKey.BATTLEFIELD_BOOK_DISPLAY_NAME))
								&& itemMeta.getLore()
										.equals(Arrays.asList(Messages.getMessage(MessageKey.BATTLEFIELD_BOOK_LORE)))) {
							server.getPluginManager().callEvent(new PlayerOpenBattlefieldGUIEvent(event.getPlayer()));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerOpenBattlefieldGUI(PlayerOpenBattlefieldGUIEvent event) {
		Inventory inventory = Bukkit.createInventory(null, InventoryType.CHEST,
				Messages.getMessage(MessageKey.BATTLEFIELD_GUI_TITLE));
		HashMap<World, BattlefieldConfiguration> currentBattlefield = BattlefieldHandler.battlefields;
		for (int index = 0; index < Math.min(currentBattlefield.size(), 27); index++) {
			if (index > currentBattlefield.size() - 1) {
				break;
			} else {
				ItemStack battlefieldItem;
				String statue;
				switch (currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getStatue()) {
					case 0:
						battlefieldItem = new Wool(DyeColor.GREEN).toItemStack(1);
						statue = Messages.getMessage(MessageKey.BATTLEFIELD_GUI_STATUE_PRESETED);
						break;
					case 1:
						battlefieldItem = new Wool(DyeColor.YELLOW).toItemStack(1);
						statue = Messages.getMessage(MessageKey.BATTLEFIELD_GUI_STATUE_LOADING);
						break;
					case 2:
						battlefieldItem = new Wool(DyeColor.RED).toItemStack(1);
						statue = Messages.getMessage(MessageKey.BATTLEFIELD_GUI_STATUE_RUNNING);
						break;
					default:
						battlefieldItem = new Wool(DyeColor.BLACK).toItemStack(1);
						statue = Messages.getMessage(MessageKey.BATTLEFIELD_GUI_STATUE_ERROR);
						break;
				}
				ItemMeta battlefieldItemMeta = battlefieldItem.getItemMeta();
				battlefieldItemMeta.setDisplayName(Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_DISPLAY_NAME,
						((World) (currentBattlefield.keySet().toArray()[index])).getName()));
				if (currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getStatue() == 0) {
					battlefieldItemMeta.setLore(Arrays.asList(
							Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_PLAYER,
									currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).players.size() + "",
									currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getMaxPlayer() + ""),
							Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_STATUE, statue),
							Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_AVAILABLE)));
				} else {
					battlefieldItemMeta.setLore(Arrays.asList(
							Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_PLAYER,
									currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).alivePlayers.size()
											+ "",
									currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getMaxPlayer() + ""),
							Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_STATUE, statue)));
				}
				battlefieldItem.setItemMeta(battlefieldItemMeta);
				inventory.setItem(index, battlefieldItem);
			}
		}
		event.getPlayer().openInventory(inventory);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory().getName().equals(Messages.getMessage(MessageKey.BATTLEFIELD_GUI_TITLE))) {
			event.getInventory().clear();
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getName().equals(Messages.getMessage(MessageKey.BATTLEFIELD_GUI_TITLE))) {
			event.setCancelled(true);
			if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() &&
					event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
				if (event.getCurrentItem().getItemMeta().getLore()
						.contains((String) Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_AVAILABLE))) {
					server.getPluginManager().callEvent(new PlayerJoinBattlefieldEvent((Player) event.getWhoClicked(),
							event.getCurrentItem().getItemMeta().getDisplayName()
									.replaceAll(Messages
											.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_DISPLAY_NAME, "%WORLDNAME")
											.replaceAll("%WORLDNAME", ""), "")));
					ItemStack battlefieldBook = new ItemStack(Material.BOOK);
					ItemMeta battlefieldBookMeta = battlefieldBook.getItemMeta();
					battlefieldBookMeta.setDisplayName(Messages.getMessage(MessageKey.BATTLEFIELD_BOOK_DISPLAY_NAME));
					battlefieldBookMeta.setLore(Arrays.asList(Messages.getMessage(MessageKey.BATTLEFIELD_BOOK_LORE)));
					battlefieldBook.setItemMeta(battlefieldBookMeta);
					event.getWhoClicked().getInventory().remove(battlefieldBook);
				}
			}
		}
	}
}
