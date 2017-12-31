package com.klnsyf.battleroyale.listeners;

import java.text.DecimalFormat;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.PlayerUseCompassEvent;
import com.klnsyf.battleroyale.events.WorldBorderStopShrinkEvent;
import com.klnsyf.battleroyale.utils.PlayerLocator;

public class PlayerUseCompass implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public PlayerUseCompass(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerUseCompass(PlayerDropItemEvent event) {
		if (battleRoyale.getState() == 3) {
			if (!config.getCompassMode()) {
				if (event.getItemDrop().getItemStack().getType() == Material.COMPASS) {
					plugin.getServer().getPluginManager().callEvent(new PlayerUseCompassEvent());
					Item i = event.getItemDrop();
					i.setGlowing(true);
					i.setInvulnerable(true);
					i.setPickupDelay(config.getCompassColdDown());
					sendPlayerLocation(event.getPlayer());
				}
			}
		}
	}

	@EventHandler
	public void onPlayerUseCompass(PlayerInteractEvent event) {
		if (battleRoyale.getState() == 3) {
			if (config.getCompassMode()) {
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (event.getMaterial() == Material.COMPASS) {
						if (event.getPlayer().hasCooldown((Material.COMPASS))) {
							event.setCancelled(true);
						} else {
							plugin.getServer().getPluginManager().callEvent(new PlayerUseCompassEvent());
							event.getPlayer().setCooldown(Material.COMPASS, config.getCompassColdDown());
							sendPlayerLocation(event.getPlayer());
						}
					}
				}
			}
		}
	}

	private boolean isWorldBorderShrinking(double minRadius, double currentRadius) {
		if (minRadius == currentRadius) {
			if (config.isWorldBorderShrinking()) {
				battleRoyale.getPlugin().getServer().getPluginManager().callEvent(new WorldBorderStopShrinkEvent());
				config.setWorldBorderShrinking(false);
			}
			return false;
		} else {
			config.setWorldBorderShrinking(true);
			return true;
		}
	}

	private void sendPlayerLocation(Player player) {
		isWorldBorderShrinking(config.getMinRadius(), config.getWorldBorderHandler().getWorldBorderRadius());
		Location l = player.getLocation();
		l.setY(l.getY() + 1);
		player.spawnParticle(Particle.SPELL_WITCH, l, 256);
		player.getWorld().strikeLightningEffect(player.getLocation());
		player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaWorldBorder Information:");
		int buffer = ((int) config.getWorldBorderHandler().getWorldBorderRadius())
				- ((int) Math.max(Math.abs(l.getX()), Math.abs(l.getZ())));
		String s;
		if (buffer >= 800) {
			s = "¡ìa";
		} else if (buffer >= 500) {
			s = "¡ìe";
		} else if (buffer >= 200) {
			s = "¡ì6";
		} else {
			s = "¡ì4";
		}
		if (config.isWorldBorderShrinking()) {
			player.sendMessage("¡ì7>> ¡ìaRadius: " + (int) config.getWorldBorderHandler().getWorldBorderRadius() + "m ("
					+ s + buffer + "¡ìam)" + "    Speed: "
					+ new DecimalFormat("0.00").format(config.getWorldBorderHandler().getShrinkSpeed()) + " ¡ìam/s");
		} else {
			player.sendMessage("¡ì7>> ¡ìaRadius: " + (int) config.getWorldBorderHandler().getWorldBorderRadius() + "m");
		}
		player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaSurvivor Locator:");
		new PlayerLocator(plugin).playerLocate(player, config.getAlivePlayers(), config.getIsHideName(), true);
	}

	public BattleRoyaleSetup getPlugin() {
		return plugin;
	}

	public void setPlugin(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

}
