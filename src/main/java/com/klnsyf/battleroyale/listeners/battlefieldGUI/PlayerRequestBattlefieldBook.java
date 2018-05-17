package com.klnsyf.battleroyale.listeners.battlefieldGUI;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.events.PlayerRequestBattlefieldBookEvent;

public class PlayerRequestBattlefieldBook implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;
	private final Configuration configuation = new Configuration();

	public PlayerRequestBattlefieldBook() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerRequestBattlefield(PlayerRequestBattlefieldBookEvent event) {
		if (BattlefieldHandler.battlefields.containsKey(event.getPlayer().getWorld())) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).players.contains(event.getPlayer())) {
				if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() != 0) {
					// TODO: sendMessage
					event.setCancelled(true);
					return;
				}
			}
		}
		ItemStack battlefieldBook = new ItemStack(Material.BOOK);
		ItemMeta battlefieldBookMeta = battlefieldBook.getItemMeta();
		battlefieldBookMeta.setDisplayName("¡ìr¡ì6Battlefield Book");
		battlefieldBookMeta.setLore(Arrays.asList("¡ìrRight Click to Open Battlefield GUI"));
		battlefieldBook.setItemMeta(battlefieldBookMeta);
		if (event.getPlayer().getInventory().contains(battlefieldBook)) {
			// TODO
		} else {
			event.getPlayer().getInventory().addItem(battlefieldBook);
		}
	}
}
