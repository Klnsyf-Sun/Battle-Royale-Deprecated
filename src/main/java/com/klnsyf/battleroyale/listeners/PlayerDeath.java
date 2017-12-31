package com.klnsyf.battleroyale.listeners;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.GameEndEvent;

public class PlayerDeath implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public PlayerDeath(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (battleRoyale.getState() == 3) {
			plugin.getServer().getConsoleSender().sendMessage("[¡ì6Battle Royale¡ìr] ¡ìc" + event.getDeathMessage());
			event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
			event.getEntity().setGameMode(GameMode.SPECTATOR);
			ArrayList<Player> alivePlayers = config.getAlivePlayers();
			alivePlayers.remove(event.getEntity());
			config.setAlivePlayers(alivePlayers);
			if (event.getEntity().getKiller() != null) {
				for (Player player : plugin.getServer().getOnlinePlayers()) {
					player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìd" + event.getEntity().getName() + " ¡ìcwas killed by ¡ìd"
							+ event.getEntity().getKiller().getName());
				}
				event.getEntity().getKiller()
						.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5, 7, false, false));
				event.getEntity().getKiller()
						.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 5, 0, false, false));
			} else {
				for (Player player : plugin.getServer().getOnlinePlayers()) {
					player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìd" + event.getEntity().getName() + " ¡ìcdied");
				}
			}
		}
		if (config.getAlivePlayers().size() == 1) {
			plugin.getServer().getPluginManager().callEvent(new GameEndEvent(config.getAlivePlayers().get(0)));
		} else {
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìc" + config.getAlivePlayers().size() + " survivors left");
			}
		}
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
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

}
