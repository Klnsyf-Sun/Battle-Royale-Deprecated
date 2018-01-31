package com.klnsyf.battleroyale.listeners;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;
import com.klnsyf.battleroyale.events.PlayerAccelerateWorldBorderShrinkingEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;
import com.klnsyf.battleroyale.utils.WorldBorderHandler;

public class PlayerAccelerateWorldBorderShrinking implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;
	private final Configuration configuation = new Configuration();

	public PlayerAccelerateWorldBorderShrinking() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerDropActivateItem(PlayerDropItemEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if (event.getItemDrop().getItemStack().getType() == Material.getMaterial(((String) configuation
						.getValue(event.getPlayer().getWorld(), ConfigurationKey.BATTLE_MISC_SHRINK_ACCELERATING_ITEM)))
						&& event.getPlayer().isSneaking()) {
					server.getPluginManager().callEvent(new PlayerAccelerateWorldBorderShrinkingEvent(event.getPlayer()));
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerAccelerateWorldBorderShrinking(PlayerAccelerateWorldBorderShrinkingEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if (event.getPlayer().getCooldown(Material.getMaterial(((String) configuation
						.getValue(event.getPlayer().getWorld(), ConfigurationKey.BATTLE_MISC_SHRINK_ACCELERATING_ITEM)))) > 0) {
					event.setCancelled(true);
				} else {
					if (BattlefieldHandler.battlefields.get(event.getWorld()).isWorldBorderShrinking()) {
						event.getPlayer().getInventory().getItemInMainHand()
								.setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
						new WorldBorderHandler(event.getWorld()).accerateShrink(
								configuation.getValue(event.getWorld(), ConfigurationKey.WORLD_BORDER_MIN_RADIUS),
								configuation.getValue(event.getWorld(), ConfigurationKey.BATTLE_MISC_SHRINK_ACCELERATING_SPEED),
								configuation.getValue(event.getWorld(),
										ConfigurationKey.BATTLE_MISC_SHRINK_ACCELERATING_DURATION));
						event.getPlayer().setCooldown(Material.getMaterial(((String) configuation
								.getValue(event.getPlayer().getWorld(),
										ConfigurationKey.BATTLE_MISC_SHRINK_ACCELERATING_ITEM))),
								configuation.getValue(event.getWorld(),
										ConfigurationKey.BATTLE_MISC_SHRINK_ACCELERATING_COOLDOWN));
						event.getWorld().strikeLightningEffect(event.getPlayer().getLocation());
						server.getConsoleSender().sendMessage(prefix + Messages.getMessage(
								MessageKey.PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING_SUCCESS, event.getPlayer().getName()));
						for (Player player : BattlefieldHandler.battlefields.get(event.getWorld()).players) {
							if ((boolean) configuation.getValue(event.getWorld(), ConfigurationKey.BATTLE_MISC_HIDE_NAME)) {
								player.sendMessage(prefix + Messages.getMessage(
										MessageKey.PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING_SUCCESS_ANONYMOUS));
							} else {
								player.sendMessage(prefix + Messages.getMessage(
										MessageKey.PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING_SUCCESS,
										event.getPlayer().getName()));
							}
						}
					} else {
						event.getPlayer().sendMessage(prefix + Messages.getMessage(MessageKey.EVENTS_SHRINK_STOPPED));
						event.setCancelled(true);
					}
				}
			}
		}
	}
}
