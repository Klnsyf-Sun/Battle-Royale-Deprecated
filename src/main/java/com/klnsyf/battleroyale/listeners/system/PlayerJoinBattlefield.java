package com.klnsyf.battleroyale.listeners.system;

import org.bukkit.Server;
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
		event.getWorld().loadChunk(0, 0);
		event.getPlayer().teleport(event.getWorld().getSpawnLocation());
		BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).players.add(event.getPlayer());
		server.getConsoleSender()
				.sendMessage(prefix + Messages.getMessage(MessageKey.PLAYER_JOIN_BATTLEFIELD_SUCCESS,
						event.getPlayer().getName(), event.getWorld().getName(),
						BattlefieldHandler.battlefields.get(event.getWorld()).players.size() + "",
						BattlefieldHandler.battlefields.get(event.getWorld()).getMaxPlayer() + ""));
		for (Player player : BattlefieldHandler.battlefields.get(event.getWorld()).players) {
			player.sendMessage(prefix + Messages.getMessage(MessageKey.PLAYER_JOIN_BATTLEFIELD_SUCCESS,
					event.getPlayer().getName(), event.getWorld().getName(),
					BattlefieldHandler.battlefields.get(event.getWorld()).players.size() + "",
					BattlefieldHandler.battlefields.get(event.getWorld()).getMaxPlayer() + ""));
		}
		if (BattlefieldHandler.battlefields.get(event.getWorld()).isAutoStart()
				&& BattlefieldHandler.battlefields.get(event.getWorld()).players
						.size() == BattlefieldHandler.battlefields.get(event.getWorld()).getMaxPlayer()) {
			server.getPluginManager().callEvent(new BattleLoadEvent(event.getWorld()));
		}
	}
}
