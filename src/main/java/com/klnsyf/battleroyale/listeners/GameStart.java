package com.klnsyf.battleroyale.listeners;

import java.util.ArrayList;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.configuration.InitEffect;
import com.klnsyf.battleroyale.configuration.InitSupply;
import com.klnsyf.battleroyale.events.GameStartEvent;
import com.klnsyf.battleroyale.utils.ActionbarMessage;
import com.klnsyf.battleroyale.utils.PlayerReset;

public class GameStart implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public GameStart(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onGameStart(GameStartEvent event) {
		if (battleRoyale.getState() == 2) {
			gameNotice(config.getAlivePlayers());
			playersReset(config.getAlivePlayers());
			initPotionEffect(config.getAlivePlayers());
			initSupply(config.getAlivePlayers());
			config.getWorldBorderHandler().controlWorldBorder(config.getMinRadius(), config.getShrinkSpeed());
			battleRoyale.setState(3);
			new ListenersReload().listenersReload(battleRoyale);
		}
	}

	@SuppressWarnings("deprecation")
	private void playersReset(ArrayList<Player> players) {
		for (Player player : players) {
			new PlayerReset(plugin).effectClear(player);
			new PlayerReset(plugin).inventoryClear(player);
			player.setMaxHealth(config.getMaxHealth());
			new PlayerReset(plugin).stateClear(player);
			player.setCooldown(Material.IRON_BLOCK, config.getAccelerateProtectTime());
		}
	}

	private void gameNotice(ArrayList<Player> players) {
		for (Player player : players) {
			new ActionbarMessage(plugin).sendActionbarMessage(player, "-= Game Start =-");
			player.sendTitle("[¡ì6Battle Royale¡ìr]", "-= Game Start =-", 0, 40, 40);
			player.playEffect(player.getLocation(), Effect.CLICK1, null);
			player.spawnParticle(Particle.SPELL_WITCH, player.getEyeLocation(), config.getPraticleAmount(), null);
		}
	}

	private void initSupply(ArrayList<Player> players) {
		for (Player player : players) {
			for (InitSupply item : InitSupply.values()) {
				player.getInventory().addItem(new ItemStack(item.getItem(), item.getAmount()));
			}
		}
	}

	private void initPotionEffect(ArrayList<Player> players) {
		for (Player player : players) {
			for (InitEffect effect : InitEffect.values()) {
				player.addPotionEffect(new PotionEffect(effect.getType(), 11037, effect.getAmplifier(), false, false));
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
