package com.klnsyf.battleroyale.listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;
import com.klnsyf.battleroyale.events.PlayerSummonBlazeEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;

public class PlayerSummonBlaze implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;
	private final Configuration configuation = new Configuration();

	public PlayerSummonBlaze() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onSummonBlaze(PlayerInteractEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if ((boolean) configuation.getValue(event.getPlayer().getWorld(),
						ConfigurationKey.BATTLE_MISC_SUMMON_BLAZE_ENABLED)) {
					if (event.getPlayer().isSneaking()) {
						if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK
								&& event.getClickedBlock() != null) {
							if (event.getClickedBlock().getType() == Material.GOLD_BLOCK) {
								event.getPlayer().getInventory().getItemInMainHand()
										.setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
								plugin.getServer().getPluginManager()
										.callEvent(new PlayerSummonBlazeEvent(event.getPlayer(), event.getClickedBlock()));
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onBlazeSummoned(PlayerSummonBlazeEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getPlayer().getWorld()).getStatue() == 2) {
				if ((boolean) configuation.getValue(event.getPlayer().getWorld(),
						ConfigurationKey.BATTLE_MISC_SUMMON_BLAZE_ENABLED)) {
					event.getPlayer().getWorld().strikeLightningEffect(event.getBlock().getLocation());
					event.getPlayer().sendMessage(prefix + "¡ìa" + Messages.getMessage(MessageKey.PLAYER_SUMMON_BLAZE_SUCCESS));
					event.getBlock().setType(Material.LAVA);
					Blaze blaze = (Blaze) event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation(),
							EntityType.BLAZE);
					blaze.setGlowing(true);
					blaze.setTarget(event.getPlayer());
					blaze.setMaximumAir(11037);
					blaze.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 110370, 1, false, false));
					blaze.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 110370, 0, false, false));
					blaze.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 110370, 4, false, false));
				}
			}
		}
	}

	@EventHandler
	public void onKillBlaze(EntityDeathEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).getStatue() == 2) {
				if ((boolean) configuation.getValue(event.getEntity().getWorld(),
						ConfigurationKey.BATTLE_MISC_SUMMON_BLAZE_ENABLED)) {
					if (event.getEntity().getType() == EntityType.BLAZE && event.getEntity().getMaximumAir() == 11037) {
						if (event.getEntity().getKiller() instanceof Player) {
							event.setDroppedExp(16);
							List<ItemStack> drops = event.getDrops();
							drops.clear();
							drops.add(new ItemStack(Material.BLAZE_ROD));
							drops.add(new ItemStack(Material.NETHER_STALK));
							drops.add(new ItemStack(Material.GLOWSTONE_DUST, new Random().nextInt(4) + 1));
						}
					}
				}
			}
		}
	}

}
