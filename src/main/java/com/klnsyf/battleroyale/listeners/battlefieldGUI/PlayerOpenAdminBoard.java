package com.klnsyf.battleroyale.listeners.battlefieldGUI;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldConfiguration;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.events.BattleEndEvent;
import com.klnsyf.battleroyale.events.BattleLoadEvent;
import com.klnsyf.battleroyale.events.PlayerOpenAdminBoardEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;

public class PlayerOpenAdminBoard implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;

	public PlayerOpenAdminBoard() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerOpenAdminBoard(PlayerOpenAdminBoardEvent event) {
		if (!event.getPlayer().hasPermission("battleroyale.admin")) {
			event.setCancelled(true);
			// TODO: Alert.
		} else {
			event.getPlayer().openInventory(updateAdminBoard());
		}
	}

	Inventory updateAdminBoard() {
		Inventory adminBoard = Bukkit.createInventory(null, InventoryType.CHEST,
				Messages.getMessage(MessageKey.ADMIN_BOARD_TITLE));
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
					if (currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).players.size() < 2) {
						battlefieldItemMeta.setLore(Arrays.asList(
								Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_PLAYER,
										currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).players.size()
												+ "",
										currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getMaxPlayer()
												+ ""),
								Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_STATUE, statue),
								Messages.getMessage(MessageKey.ADMIN_BOARD_LORE_RESET)));
					} else {
						battlefieldItemMeta.setLore(Arrays.asList(
								Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_PLAYER,
										currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).players.size()
												+ "",
										currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getMaxPlayer()
												+ ""),
								Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_STATUE, statue),
								Messages.getMessage(MessageKey.ADMIN_BOARD_LORE_START),
								Messages.getMessage(MessageKey.ADMIN_BOARD_LORE_RESET)));
					}
				} else {
					battlefieldItemMeta.setLore(Arrays.asList(
							Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_PLAYER,
									currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).alivePlayers.size()
											+ "",
									currentBattlefield.get(currentBattlefield.keySet().toArray()[index]).getMaxPlayer() + ""),
							Messages.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_LORE_STATUE, statue),
							Messages.getMessage(MessageKey.ADMIN_BOARD_LORE_RESET)));
				}
				battlefieldItem.setItemMeta(battlefieldItemMeta);
				adminBoard.setItem(index, battlefieldItem);
			}
		}
		return adminBoard;
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getInventory().getName().equals(Messages.getMessage(MessageKey.ADMIN_BOARD_TITLE))) {
			event.getInventory().clear();
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getInventory().getName().equals(Messages.getMessage(MessageKey.ADMIN_BOARD_TITLE))) {
			event.setCancelled(true);
			if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta() &&
					event.getCurrentItem().getItemMeta().hasDisplayName() && event.getCurrentItem().getItemMeta().hasLore()) {
				if (event.getCurrentItem().getItemMeta().getLore()
						.contains(Messages.getMessage(MessageKey.ADMIN_BOARD_LORE_START))
						&& event.getClick() == ClickType.LEFT) {
					server.getPluginManager().callEvent(new BattleLoadEvent(server.getWorld(
							event.getCurrentItem().getItemMeta().getDisplayName()
									.replaceAll(Messages
											.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_DISPLAY_NAME, "%WORLDNAME")
											.replaceAll("%WORLDNAME", ""), ""))));
				} else if (event.getCurrentItem().getItemMeta().getLore()
						.contains(Messages.getMessage(MessageKey.ADMIN_BOARD_LORE_RESET))
						&& event.getClick() == ClickType.RIGHT) {
					server.getPluginManager().callEvent(new BattleEndEvent(
							server.getWorld(event.getCurrentItem().getItemMeta().getDisplayName()
									.replaceAll(Messages
											.getMessage(MessageKey.BATTLEFIELD_GUI_BATTLEFIELD_ITEM_DISPLAY_NAME, "%WORLDNAME")
											.replaceAll("%WORLDNAME", ""), "")),
							null));
				}
				event.getWhoClicked().closeInventory();
				event.getWhoClicked().openInventory(updateAdminBoard());

			}
		}
	}
}
