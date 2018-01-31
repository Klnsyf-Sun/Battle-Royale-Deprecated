package com.klnsyf.battleroyale.listeners;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.events.BattleLoadEvent;
import com.klnsyf.battleroyale.events.PlayerJoinBattlefieldEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;

public class PlayerJoinBattlefield implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;

	public PlayerJoinBattlefield() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoinBattlefield(PlayerJoinBattlefieldEvent event) {
		World world = server.getWorld(event.getWorldName());
		if (world == null) {
			event.getPlayer()
					.sendMessage(prefix + Messages.getMessage(MessageKey.COMMANDS_UNDEFINDED_WORLD, event.getWorldName()));
			event.setCancelled(true);
		} else if (!BattlefieldHandler.battlefields.containsKey(world)) {
			event.getPlayer()
					.sendMessage(prefix + Messages.getMessage(MessageKey.COMMANDS_WORLD_UNPRESETTED, event.getWorldName()));
			event.setCancelled(true);
		} else if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).players.contains(event.getPlayer())) {
			event.getPlayer().sendMessage(prefix +
					Messages.getMessage(MessageKey.PLAYER_JOIN_BATTLEFIELD_JOINED, event.getPlayer().getWorld().getName()));
			event.setCancelled(true);
		} else if (BattlefieldHandler.battlefields.get(world).players.size() == BattlefieldHandler.battlefields.get(world)
				.getMaxPlayer()) {
			event.getPlayer()
					.sendMessage(prefix + Messages.getMessage(MessageKey.PLAYER_JOIN_BATTLEFIELD_FULL, event.getWorldName()));
		} else {
			event.getPlayer().teleport(world.getSpawnLocation());
			BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).players.add(event.getPlayer());
			server.getConsoleSender()
					.sendMessage(prefix + Messages.getMessage(MessageKey.PLAYER_JOIN_BATTLEFIELD_SUCCESS,
							event.getPlayer().getName(), event.getWorldName(),
							BattlefieldHandler.battlefields.get(world).players.size() + "",
							BattlefieldHandler.battlefields.get(world).getMaxPlayer() + ""));
			for (Player player : world.getPlayers()) {
				player.sendMessage(prefix + Messages.getMessage(MessageKey.PLAYER_JOIN_BATTLEFIELD_SUCCESS,
						event.getPlayer().getName(), event.getWorldName(),
						BattlefieldHandler.battlefields.get(world).players.size() + "",
						BattlefieldHandler.battlefields.get(world).getMaxPlayer() + ""));
			}
			if (BattlefieldHandler.battlefields.get(world).isAutoStart() && BattlefieldHandler.battlefields.get(world).players
					.size() == BattlefieldHandler.battlefields.get(world).getMaxPlayer()) {
				server.getPluginManager().callEvent(new BattleLoadEvent(server.getConsoleSender(), event.getWorldName()));
			}
		}
	}
}
