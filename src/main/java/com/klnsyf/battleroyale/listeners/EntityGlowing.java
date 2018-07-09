package com.klnsyf.battleroyale.listeners;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
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
import com.klnsyf.battleroyale.events.EntityGlowingEvent;

public class EntityGlowing implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final Configuration configuration = new Configuration();

	public EntityGlowing() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerGlowing(PlayerToggleSneakEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if ((boolean) configuration.getValue(event.getPlayer().getWorld(),
						ConfigurationKey.BATTLE_MISC_PLAYER_GLOWING_ENABLED)) {
					if (event.getPlayer().getInventory().getItemInOffHand().getType() == Material
							.getMaterial((String) configuration.getValue(event.getPlayer().getWorld(),
									ConfigurationKey.BATTLE_MISC_PLAYER_GLOWING_ITEM))) {
						if (!event.getPlayer().isSneaking()) {
							new BukkitRunnable() {
								@Override
								public void run() {
									if (!(event.getPlayer().getInventory().getItemInOffHand().getType() == Material
											.getMaterial((String) configuration.getValue(event.getPlayer().getWorld(),
													ConfigurationKey.BATTLE_MISC_PLAYER_GLOWING_ITEM))
											&&
											event.getPlayer().isSneaking())) {
										this.cancel();
									} else {
										event.getPlayer().getInventory().getItemInOffHand()
												.setAmount(event.getPlayer().getInventory().getItemInOffHand().getAmount() - 1);
										server.getPluginManager().callEvent(
												new EntityGlowingEvent(event.getPlayer()));
									}
								}
							}.runTaskTimer(plugin, 0,
									(int) configuration.getValue(event.getPlayer().getWorld(),
											ConfigurationKey.BATTLE_MISC_PLAYER_GLOWING_DURATION));
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityGlowing(EntityGlowingEvent event) {
		event.getPlayer().getWorld().spawnParticle(Particle.SPELL_WITCH,
				event.getPlayer().getLocation(), 255, 0,
				event.getPlayer().getWorld().getMaxHeight()
						- event.getPlayer().getLocation().getY(),
				0);
		for (Entity entity : event.getPlayer().getNearbyEntities(
				configuration.getValue(event.getPlayer().getWorld(),
						ConfigurationKey.BATTLE_MISC_PLAYER_GLOWING_RADIUS),
				configuration.getValue(event.getPlayer().getWorld(),
						ConfigurationKey.BATTLE_MISC_PLAYER_GLOWING_RADIUS),
				configuration.getValue(event.getPlayer().getWorld(),
						ConfigurationKey.BATTLE_MISC_PLAYER_GLOWING_RADIUS))) {
			if (entity instanceof LivingEntity) {
				((LivingEntity) entity).removePotionEffect(PotionEffectType.GLOWING);
				((LivingEntity) entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
						(int) configuration.getValue(event.getPlayer().getWorld(),
								ConfigurationKey.BATTLE_MISC_PLAYER_GLOWING_DURATION) + 1,
						0, false, false));
			}
		}
	}

}
