package com.klnsyf.battleroyale.listeners;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;
import com.klnsyf.battleroyale.events.BattleLoadEvent;
import com.klnsyf.battleroyale.events.BattleStartEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;
import com.klnsyf.battleroyale.utils.ActionbarMessage;
import com.klnsyf.battleroyale.utils.PlayerReset;
import com.klnsyf.battleroyale.utils.WorldBorderHandler;

public class BattleLoad implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;
	private final Configuration configuation = new Configuration();

	public BattleLoad() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBattleLoad(BattleLoadEvent event) {
		World world = server.getWorld(event.getWorldName());
		if (world == null) {
			event.getSender()
					.sendMessage(prefix + Messages.getMessage(MessageKey.COMMANDS_UNDEFINDED_WORLD, event.getWorldName()));
			event.setCancelled(true);
		} else if (!BattlefieldHandler.battlefields.containsKey(world)) {
			event.getSender()
					.sendMessage(prefix + Messages.getMessage(MessageKey.COMMANDS_WORLD_UNPRESETTED, event.getWorldName()));
			event.setCancelled(true);
		} else {
			BattlefieldHandler.battlefields.get(world).alivePlayers = BattlefieldHandler.battlefields.get(world).players;
			gameruleReset(world);
			new WorldBorderHandler(world).initWorldBorder(0,
					0,
					configuation.getValue(world, ConfigurationKey.WOLRD_BORDER_DAMAGE_AMOUNT),
					configuation.getValue(world, ConfigurationKey.WORLD_BORDER_DAMAGE_BUFFER),
					configuation.getValue(world, ConfigurationKey.WORLD_BORDER_MAX_RADIUS),
					configuation.getValue(world, ConfigurationKey.WORLD_BORDER_OPTION_WARNING_DISTANCE));
			playersReset(world);
			playersSpread(world);
			countDown(world);
			BattlefieldHandler.battlefields.get(world).setStatue(1);
		}
	}

	private void gameruleReset(World world) {
		world.setGameRuleValue("doWeatherCycle", "false");
		world.setGameRuleValue("keepInventory", "false");
		world.setGameRuleValue("showDeathMessages", "false");
		world.setGameRuleValue("naturalRegeneration", "true");
		world.setDifficulty(Difficulty.HARD);
		world.setTime(6000);
		world.setMonsterSpawnLimit(128);
	}

	private void playersReset(World world) {
		for (Player player : BattlefieldHandler.battlefields.get(world).players) {
			new PlayerReset().effectClear(player);
			new PlayerReset().inventoryClear(player);
			new PlayerReset().stateClear(player);
			player.setGameMode(GameMode.SURVIVAL);
			player.setPlayerWeather(WeatherType.CLEAR);
		}
	}

	private void playersSpread(World world) {
		ArrayList<Double> locations = BattlefieldHandler.battlefields.get(world).getSpreadLocations();
		for (Player player : BattlefieldHandler.battlefields.get(world).players) {
			int index = (int) (Math.random() * locations.size());
			double radian = locations.get(index);
			locations.remove(index);
			Location location = world.getHighestBlockAt(new Location(world,
					((int) (Math.cos(radian) * (int) configuation.getValue(world, ConfigurationKey.PLAYER_SPREAD_RADIUS))),
					world.getMaxHeight(),
					((int) (Math.sin(radian) * (int) configuation.getValue(world, ConfigurationKey.PLAYER_SPREAD_RADIUS)))))
					.getLocation();
			location.setY(location.getY() - 1);
			location.getBlock().setType(Material.BEDROCK);
			location.setY(location.getY() + 1);
			player.teleport(location);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1103700, 127, false, false));
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1103700, 128, false, false));
		}
	}

	private void countDown(World world) {
		for (final Player player : BattlefieldHandler.battlefields.get(world).players) {
			new BukkitRunnable() {

				final int totalTime = configuation.getValue(world, ConfigurationKey.BATTLE_MISC_LOADING_TIME);
				final int block = 15;
				final double totalTick = 20 * totalTime;
				double tick = totalTick;

				public void run() {
					if (BattlefieldHandler.battlefields.get(world) == null) {
						this.cancel();
					}
					String s = Messages.getMessage(MessageKey.EVENTS_BATTLE_START);

					double i = block;
					for (; i > Math.round(block * tick / totalTick); i--) {
						s = s + "¡ì4¨€";
					}
					for (i = Math.round(block * tick / totalTick); i > 0; i--) {
						s = s + "¡ìa¨€";
					}
					s = s + " ¡ìr" + new DecimalFormat("0.0").format(tick * totalTime / totalTick) + " "
							+ Messages.getMessage(MessageKey.EVENTS_SECOND);
					new ActionbarMessage().sendActionbarMessage(player, s);
					if (tick <= 10 * 20 && tick % 20 == 0) {
						player.playEffect(player.getLocation(), Effect.CLICK1, null);
						player.sendTitle("[¡ì6Battle Royale¡ìr]", "-= " + (int) (tick / 20) + " =-", 0, 200,
								0);
					}
					player.spawnParticle(Particle.SPELL_WITCH, player.getLocation(), 255, 0,
							world.getMaxHeight() - player.getLocation().getY(), 0);
					player.setLevel((int) (tick * totalTime / totalTick));
					player.setExp((float) (tick / totalTick));
					tick--;
					if (tick <= 0) {
						plugin.getServer().getPluginManager().callEvent(new BattleStartEvent(world));
						this.cancel();
					}

				}
			}.runTaskTimerAsynchronously(plugin, 0, 1);
		}
	}
}
