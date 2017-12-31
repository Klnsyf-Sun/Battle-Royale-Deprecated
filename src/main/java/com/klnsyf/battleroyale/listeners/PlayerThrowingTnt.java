package com.klnsyf.battleroyale.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.PlayerThrowTntEvent;

public class PlayerThrowingTnt implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public PlayerThrowingTnt(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerThrowingTnt(PlayerInteractEvent event) {
		if (battleRoyale.getState() == 3) {
			if (event.getMaterial() == Material.TNT) {
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					event.setCancelled(true);
					plugin.getServer().getPluginManager().callEvent(new PlayerThrowTntEvent());
					event.getItem().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
					Entity e = event.getPlayer().getWorld().spawnEntity(event.getPlayer().getEyeLocation(),
							EntityType.PRIMED_TNT);
					e.setVelocity(event.getPlayer().getLocation().getDirection().normalize());
				}
			}
		}
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
