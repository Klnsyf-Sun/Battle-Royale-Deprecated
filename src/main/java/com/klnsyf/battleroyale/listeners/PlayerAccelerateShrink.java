package com.klnsyf.battleroyale.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.PlayerAccelerateShrinkEvent;

public class PlayerAccelerateShrink implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public PlayerAccelerateShrink(BattleRoyale battleRoyale) {
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerAccelerateShrink(PlayerDropItemEvent event) {
		if (battleRoyale.getState() == 3) {
			if (event.getItemDrop().getItemStack().getType() == config.getAccelerateItem()
					&& event.getPlayer().isSneaking()) {
				if (event.getPlayer().getCooldown(config.getAccelerateItem()) > 0) {
					event.setCancelled(true);
				} else {
					if (config.isWorldBorderShrinking()) {
						plugin.getServer().getPluginManager().callEvent(new PlayerAccelerateShrinkEvent());
						event.getItemDrop().getItemStack()
								.setAmount(event.getItemDrop().getItemStack().getAmount() - 1);
						event.setCancelled(true);
						config.getWorldBorderHandler().accerateShrink(config.getMinRadius(),
								config.getAccelerateSpeed(), config.getAccelerateTicks());
						String s = event.getPlayer().getName();
						plugin.getServer().getConsoleSender().sendMessage(
								"[¡ì6Battle Royale¡ìr] ¡ìd" + s + " ¡ìcaccelerates the worldborder's shrinking");
						if (config.getIsHideName()) {
							s = "Someone";
						}
						for (Player player : config.getPlayers()) {
							player.sendMessage(
									"[¡ì6Battle Royale¡ìr] ¡ìd" + s + " ¡ìcaccelerates the worldborder's shrinking");
						}
						event.getPlayer().setCooldown(Material.IRON_BLOCK, config.getAccelerateColdDown());
						event.getPlayer().getWorld().strikeLightningEffect(event.getPlayer().getLocation());
					} else {
						event.getPlayer().sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaWorld Border has stopped shrinking");
					}
				}
			}
		}
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
