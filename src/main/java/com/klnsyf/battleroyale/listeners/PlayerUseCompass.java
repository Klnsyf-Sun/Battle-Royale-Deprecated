package com.klnsyf.battleroyale.listeners;

import java.text.DecimalFormat;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;
import com.klnsyf.battleroyale.events.PlayerUseCompassEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;
import com.klnsyf.battleroyale.utils.WorldBorderHandler;

public class PlayerUseCompass implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;
	private final Configuration configuation = new Configuration();

	public PlayerUseCompass() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerInteractCompass(PlayerDropItemEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if (!(boolean) configuation.getValue(event.getPlayer().getWorld(), ConfigurationKey.BATTLE_MISC_COMPASS_MODE)) {
					if (event.getItemDrop().getItemStack().getType() == Material.COMPASS) {
						server.getPluginManager().callEvent(new PlayerUseCompassEvent(event.getPlayer()));
						Item item = event.getItemDrop();
						item.setGlowing(true);
						item.setInvulnerable(true);
						item.setPickupDelay(configuation.getValue(event.getPlayer().getWorld(),
								ConfigurationKey.BATTLE_MISC_COMPASS_COOLDOWN));
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteractCompass(PlayerInteractEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if ((boolean) configuation.getValue(event.getPlayer().getWorld(), ConfigurationKey.BATTLE_MISC_COMPASS_MODE)) {
					if (event.getMaterial() == Material.COMPASS) {
						if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
							server.getPluginManager().callEvent(new PlayerUseCompassEvent(event.getPlayer()));
							event.getPlayer().setCooldown(Material.COMPASS, configuation.getValue(event.getPlayer().getWorld(),
									ConfigurationKey.BATTLE_MISC_COMPASS_COOLDOWN));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerUseCompass(PlayerUseCompassEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				sendPlayerLocation(event.getPlayer());
			}
		}
	}

	private void sendPlayerLocation(Player player) {
		new WorldBorderHandler(player.getWorld()).controlWorldBorder(
				configuation.getValue(player.getWorld(), ConfigurationKey.WORLD_BORDER_MIN_RADIUS),
				BattlefieldHandler.battlefields.get(player.getWorld()).getShrinkSpeed());
		Location loc = player.getEyeLocation();
		player.spawnParticle(Particle.SPELL_WITCH, loc, 256);
		player.getWorld().strikeLightningEffect(player.getLocation());
		player.sendMessage(prefix + "§a" + Messages.getMessage(MessageKey.PLAYER_USE_COMPASS_WORLD_BORDER) + ":");
		int buffer = ((int) new WorldBorderHandler(player.getWorld()).getWorldBorderRadius())
				- ((int) Math.max(Math.abs(loc.getX()), Math.abs(loc.getZ())));
		String s;
		if (buffer >= 800) {
			s = "§a";
		} else if (buffer >= 500) {
			s = "§e";
		} else if (buffer >= 200) {
			s = "§6";
		} else {
			s = "§4";
		}
		if (BattlefieldHandler.battlefields.get(player.getWorld()).isWorldBorderShrinking()) {
			player.sendMessage("§7>> §a" + Messages.getMessage(MessageKey.PLAYER_USE_COMPASS_RADIUS) + ": "
					+ (int) new WorldBorderHandler(player.getWorld()).getWorldBorderRadius()
					+ "m ("
					+ s + buffer + "§am)" + "   " + Messages.getMessage(MessageKey.PLAYER_USE_COMPASS_SPEED) + ": "
					+ new DecimalFormat("0.00").format(BattlefieldHandler.battlefields.get(player.getWorld()).getShrinkSpeed())
					+ " §am/s");
		} else {
			player.sendMessage(
					"§7>> §a" + Messages.getMessage(MessageKey.PLAYER_USE_COMPASS_RADIUS) + ": "
							+ (int) new WorldBorderHandler(player.getWorld()).getWorldBorderRadius() + "m");
		}
		player.sendMessage(prefix + "§a" + Messages.getMessage(MessageKey.PLAYER_USE_COMPASS_SURVIVOR) + ":");
		playerLocate(player);

	}

	public void playerLocate(Player sender) {
		for (Player player : BattlefieldHandler.battlefields.get(sender.getWorld()).players) {
			if (sender == player) {
				continue;
			}
			String s = Messages.getMessage(MessageKey.PLAYER_USE_COMPASS_ANONYMOUS);
			if (!(boolean) configuation.getValue(sender.getWorld(), ConfigurationKey.BATTLE_MISC_HIDE_NAME)) {
				s = player.getName();
			}
			int dis = (int) sender.getLocation().distance(player.getLocation());
			String c;
			if (dis >= 800) {
				c = "§a";
			} else if (dis >= 500) {
				c = "§e";
			} else if (dis >= 200) {
				c = "§6";
			} else {
				c = "§4";
			}
			sender.sendMessage("§7>> §d " + s + " §b>§f " + (int) player.getLocation().getX() + ", "
					+ (int) player.getLocation().getZ() + " (" + c + dis + "§am)");
		}
	}
}
