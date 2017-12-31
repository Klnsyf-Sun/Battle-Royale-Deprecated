package com.klnsyf.battleroyale.listeners;

import java.util.List;

import org.bukkit.Material;
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

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.events.SummonBlazeEvent;

public class SummonBlaze implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;

	public SummonBlaze(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onSummon(PlayerInteractEvent event) {
		if (battleRoyale.getState() == 3) {
			if (event.getPlayer().isSneaking()) {
				if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.STICK
						&& event.getClickedBlock() != null) {
					if (event.getClickedBlock().getType() == Material.GOLD_BLOCK) {
						event.getPlayer().getInventory().getItemInMainHand()
								.setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
						plugin.getServer().getPluginManager()
								.callEvent(new SummonBlazeEvent(event.getPlayer(), event.getClickedBlock()));
					}
				}
			}
		}
	}

	@EventHandler
	public void onBlazeSummoned(SummonBlazeEvent event) {
		if (battleRoyale.getState() == 3) {
			event.getPlayer().getWorld().strikeLightningEffect(event.getBlock().getLocation());
			event.getPlayer().sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaYou have successfully summon a blaze");
			event.getBlock().setType(Material.LAVA);
			Blaze blaze = (Blaze) event.getPlayer().getWorld().spawnEntity(event.getBlock().getLocation(),
					EntityType.BLAZE);
			blaze.setGlowing(true);
			event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 80, 0, false, false));
		}
	}

	@EventHandler
	public void onKillBlaze(EntityDeathEvent event) {
		if (battleRoyale.getState() == 3) {
			if (event.getEntity().getType() == EntityType.BLAZE) {
				if (event.getEntity().getKiller() instanceof Player) {
					event.setDroppedExp(16);
					List<ItemStack> drops = event.getDrops();
					drops.clear();
					drops.add(new ItemStack(Material.BREWING_STAND_ITEM));
					drops.add(new ItemStack(Material.NETHER_STALK));
				}
			}
		}
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

}
