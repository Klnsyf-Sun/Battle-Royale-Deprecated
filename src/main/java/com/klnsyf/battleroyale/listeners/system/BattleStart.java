package com.klnsyf.battleroyale.listeners.system;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;
import com.klnsyf.battleroyale.events.BattleStartEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;
import com.klnsyf.battleroyale.utils.ActionbarMessage;
import com.klnsyf.battleroyale.utils.PlayerReset;
import com.klnsyf.battleroyale.utils.WorldBorderHandler;

public class BattleStart implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final Configuration configuation = new Configuration();

	public BattleStart() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBattleStart(BattleStartEvent event) {
		World world = event.getWorld();
		gameNotice(world);
		playersReset(world);
		initSupply(world);
		new WorldBorderHandler(world).controlWorldBorder(
				configuation.getValue(world, ConfigurationKey.WORLD_BORDER_MIN_RADIUS),
				(double) configuation.getValue(world, ConfigurationKey.WORLD_BORDER_SHRINK_SPEED));
		BattlefieldHandler.battlefields.get(world).setStatue(2);
	}

	@SuppressWarnings("deprecation")
	private void playersReset(World world) {
		for (Player player : BattlefieldHandler.battlefields.get(world).players) {
			new PlayerReset().effectClear(player);
			new PlayerReset().inventoryClear(player);
			player.setMaxHealth(configuation.getValue(world, ConfigurationKey.PLAYER_GENERIC_MAX_HEALTH));
			new PlayerReset().stateClear(player);
			if ((boolean) configuation.getValue(world, ConfigurationKey.BATTLE_MISC_SHRINK_ACCELERATING_ENABLED)) {
				player.setCooldown(
						Material.getMaterial(
								configuation.getValue(world, ConfigurationKey.BATTLE_MISC_SHRINK_ACCELERATING_ITEM)),
						configuation.getValue(world, ConfigurationKey.BATTLE_MISC_SHRINK_ACCELERATING_PROTECT_TIME));
			}
		}
	}

	private void gameNotice(World world) {
		for (Player player : BattlefieldHandler.battlefields.get(world).players) {
			new ActionbarMessage().sendActionbarMessage(player,
					"-= " + Messages.getMessage(MessageKey.EVENTS_BATTLE_START) + " =-");
			player.sendTitle("[¡ì6Battle Royale¡ìr]", "-= " + Messages.getMessage(MessageKey.EVENTS_BATTLE_START) + " =-", 0,
					40, 40);
			player.playEffect(player.getLocation(), Effect.CLICK1, null);
			player.spawnParticle(Particle.SPELL_WITCH, player.getEyeLocation(), 255, null);
		}
	}

	private void initSupply(World world) {
		for (Player player : BattlefieldHandler.battlefields.get(world).players) {
			ArrayList<Map<String, Integer>> itemList = configuation.getValue(world, ConfigurationKey.BATTLE_MISC_INIT_SUPPLY);
			for (Map<String, Integer> item : itemList) {
				player.getInventory()
						.addItem(new ItemStack(Material.getMaterial((String) item.keySet().toArray()[0]),
								(int) item.values().toArray()[0]));
			}
		}
	}
}
