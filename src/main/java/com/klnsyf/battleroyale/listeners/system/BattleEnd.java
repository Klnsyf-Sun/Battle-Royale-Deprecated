package com.klnsyf.battleroyale.listeners.system;

import org.bukkit.Difficulty;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.events.BattleEndEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;

public class BattleEnd implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;

	public BattleEnd() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBattleEnd(BattleEndEvent event) {
		if (event.getWinner() != null) {
			for (Player player : BattlefieldHandler.battlefields.get(event.getWorld()).players) {
				player.sendTitle("[¡ì6Battle Royale¡ì]",
						Messages.getMessage(MessageKey.BATTLE_END_WINNER, event.getWinner().getName()), 10, 80,
						10);
			}
		} else {
			server.getConsoleSender().sendMessage(prefix + Messages.getMessage(MessageKey.BATTLE_END_FORCED_END));
		}
		event.getWorld().setDifficulty(Difficulty.PEACEFUL);
		BattlefieldHandler.battlefields.remove(event.getWorld());
	}

}
