package com.klnsyf.battleroyale.listeners;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;
import com.klnsyf.battleroyale.events.PlayerInvisibleEvent;

public class PlayerInvisible implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final Configuration configuration = new Configuration();

	public PlayerInvisible() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerInvisible(PlayerToggleSneakEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if ((boolean) configuration.getValue(event.getPlayer().getWorld(),
						ConfigurationKey.BATTLE_MISC_PLAYER_INVISIBLE_OPTION_ENABLED)) {
					if (event.getPlayer().getInventory().getItemInOffHand().getType() == Material
							.getMaterial((String) configuration.getValue(event.getPlayer().getWorld(),
									ConfigurationKey.BATTLE_MISC_PLAYER_INVISIBLE_OPTION_ACTIVATE_ITEM))) {
						if (!event.getPlayer().isSneaking()) {
							new BukkitRunnable() {
								@Override
								public void run() {
									if (!(event.getPlayer().getInventory().getItemInOffHand().getType() == Material
											.getMaterial((String) configuration.getValue(event.getPlayer().getWorld(),
													ConfigurationKey.BATTLE_MISC_PLAYER_INVISIBLE_OPTION_ACTIVATE_ITEM))
											&& event.getPlayer().getHealth() > 1 && event.getPlayer().getFoodLevel() > 0 &&
											event.getPlayer().isSneaking())) {
										this.cancel();
									} else {
										event.getPlayer().getInventory().getItemInOffHand()
												.setAmount(event.getPlayer().getInventory().getItemInOffHand().getAmount()
														- (int) configuration.getValue(event.getPlayer().getWorld(),
																ConfigurationKey.BATTLE_MISC_PLAYER_INVISIBLE_COST_ITEM));

										event.getPlayer()
												.setHealth(Math.max(event.getPlayer().getHealth()
														- (int) configuration.getValue(event.getPlayer().getWorld(),
																ConfigurationKey.BATTLE_MISC_PLAYER_INVISIBLE_COST_HEALTH),
														1));
										event.getPlayer()
												.setFoodLevel(Math.max(event.getPlayer().getFoodLevel()
														- (int) configuration.getValue(event.getPlayer().getWorld(),
																ConfigurationKey.BATTLE_MISC_PLAYER_INVISIBLE_COST_HEALTH),
														0));
										server.getPluginManager().callEvent(
												new PlayerInvisibleEvent(event.getPlayer()));
									}
								}
							}.runTaskTimer(plugin, 0,
									(int) configuration.getValue(event.getPlayer().getWorld(),
											ConfigurationKey.BATTLE_MISC_PLAYER_INVISIBLE_OPTION_DURATION));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInvisible(PlayerInvisibleEvent event) {
		event.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
		event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,
				(int) configuration.getValue(event.getWorld(), ConfigurationKey.BATTLE_MISC_PLAYER_INVISIBLE_OPTION_DURATION)
						+ 1,
				0, false, false));
	}
}
