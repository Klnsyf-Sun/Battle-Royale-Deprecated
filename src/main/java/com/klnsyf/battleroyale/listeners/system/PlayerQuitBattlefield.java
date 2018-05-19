package com.klnsyf.battleroyale.listeners.system;

import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.events.PlayerQuitBattlefieldEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;

public class PlayerQuitBattlefield implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;

	public PlayerQuitBattlefield() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerQuitBattlefield(PlayerQuitBattlefieldEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			server.getConsoleSender().sendMessage(prefix
					+ Messages.getMessage(MessageKey.PLAYER_QUIT_BATTLEFIELD_SUCCESS, event.getPlayer().getName(),
							event.getPlayer().getWorld().getName()));
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).alivePlayers.contains(event.getPlayer())) {
				event.getPlayer().damage(11037);
			} else {
				BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).players.remove(event.getPlayer());
			}
		}
	}
}
