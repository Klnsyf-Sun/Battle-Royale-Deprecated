package com.klnsyf.battleroyale.listeners;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;
import com.klnsyf.battleroyale.events.WorldBorderStopShrinkingEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;
import com.klnsyf.battleroyale.utils.actionbar.ActionbarMessageSetup;

public class DeathMatch implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;
	private final Configuration configuation = new Configuration();

	public DeathMatch() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onWorldBorderStopShrinking(WorldBorderStopShrinkingEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getWorld()).getStatue() == 2) {
				if ((boolean) configuation.getValue(event.getWorld(), ConfigurationKey.BATTLE_MISC_DEATHMATCH_ENABLED)) {
					event.getWorld().setGameRuleValue("naturalRegeneration", "false");
					server.getConsoleSender().sendMessage(prefix + Messages.getMessage(MessageKey.EVENTS_SHRINK_STOPPED));
					for (Player player : BattlefieldHandler.battlefields.get(event.getWorld()).players) {
						player.sendTitle("[¡ì6Battle Royale¡ìr]", "-= ¡ìc" + Messages.getMessage(MessageKey.DEATH_MATCH) + "¡ìr =-",
								10, 20, 10);
						new ActionbarMessageSetup().getActionBarMessage().sendActionbarMessage(player,
								"¡ìc¡ìl" + Messages.getMessage(MessageKey.DEATH_MATCH_ACTION_BAR));
					}
				}
			}
		}
	}

}
