package com.klnsyf.battleroyale.listeners;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.events.BattleLoadEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;

public class BattleLoad implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;

	public BattleLoad() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBattleLoad(BattleLoadEvent event) {
		World world = server.getWorld(event.getWorldName());
		if (world == null) {
			event.getSender()
					.sendMessage(prefix + Messages.getMessage(MessageKey.COMMANDS_UNDEFINDED_WORLD, event.getWorldName()));
			event.setCancelled(true);
		} else if (!BattlefieldHandler.battlefields.containsKey(world)) {
			event.getSender()
					.sendMessage(prefix + Messages.getMessage(MessageKey.COMMANDS_WORLD_UNPRESETTED, event.getWorldName()));
			event.setCancelled(true);
		}
	}
}
