package com.klnsyf.battleroyale.listeners;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.GameLoadEvent;
import com.klnsyf.battleroyale.events.GameStartEvent;
import com.klnsyf.battleroyale.utils.ActionbarMessage;
import com.klnsyf.battleroyale.utils.PlayerLocator;
import com.klnsyf.battleroyale.utils.PlayerReset;

public class GameLoad implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public GameLoad(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		battleRoyale.getPlugin().getServer().getPluginManager().registerEvents(this, battleRoyale.getPlugin());
	}

	@EventHandler(ignoreCancelled = true)
	public void onGameLoad(GameLoadEvent event) {
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		config.setAlivePlayers(config.getPlayers());
		gameruleReset();
		config.getWorldBorderHandler().initWorldBorder(config.getCenterX(), config.getCenterZ(),
				config.getDamageAmount(), config.getDamageBuffer(), config.getMaxRadius(), config.getWarningDistance());
		playersReset(config.getAlivePlayers());
		playersSpread(config.getAlivePlayers(), config.getAttempt(), config.getMinRange(), config.getMaxRange(),
				config.getSpreadDistance(), config.getSpreadSpace());
		locationBroadcast(config.getAlivePlayers(), config.getIsHideName());
		countDown(config.getAlivePlayers(), config.getPraticleAmount());
		battleRoyale.setState(2);
		new ListenersReload().listenersReload(battleRoyale);
	}

	private void gameruleReset() {
		battleRoyale.getWorld().setGameRuleValue("doWeatherCycle", "false");
		battleRoyale.getWorld().setGameRuleValue("keepInventory", "false");
		battleRoyale.getWorld().setGameRuleValue("showDeathMessages", "false");
		battleRoyale.getWorld().setGameRuleValue("naturalRegeneration", "true");
		battleRoyale.getWorld().setDifficulty(Difficulty.HARD);
		battleRoyale.getWorld().setTime(6000);
		battleRoyale.getWorld().setMonsterSpawnLimit(128);
	}

	private void playersReset(ArrayList<Player> players) {
		for (Player player : players) {
			new PlayerReset(plugin).effectClear(player);
			new PlayerReset(plugin).inventoryClear(player);
			new PlayerReset(plugin).stateClear(player);
			player.setGameMode(GameMode.SURVIVAL);
			player.setPlayerWeather(WeatherType.CLEAR);
		}
	}

	private void playersSpread(ArrayList<Player> players, int attempt, int minRange, int maxRange, int spreadDistance,
			int spreadSpace) {
		ArrayList<Double> locations = config.getSpreadLocation();
		for (Player player : config.getAlivePlayers()) {
			int index = (int) (Math.random() * locations.size());
			double radian = locations.get(index);
			locations.remove(index);
			Location location = new Location(battleRoyale.getWorld(), ((int) (Math.cos(radian) * spreadDistance)) + 0.5,
					battleRoyale.getWorld().getMaxHeight(), ((int) (Math.sin(radian) * spreadDistance)) + 0.5);
			while (location.getBlock().getType() == Material.AIR) {
				location.setY(location.getY() - 1);
			}
			location.getBlock().setType(Material.BEDROCK);
			location.setY(location.getY() + 1);
			player.teleport(location);
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1103700, 127, false, false));
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1103700, 128, false, false));
		}
	}

	private void countDown(final ArrayList<Player> players, final int particleAmount) {
		for (final Player player : players) {
			new BukkitRunnable() {
				final double totalTime = config.getGameLoadLatencyTime();
				final int block = 15;
				final double totalTick = 20 * totalTime;
				double tick = totalTick;

				public void run() {
					String s = "Game Start ";

					double i = block;
					for (; i > Math.round(block * tick / totalTick); i--) {
						s = s + "¡ì4¨€";
					}
					for (i = Math.round(block * tick / totalTick); i > 0; i--) {
						s = s + "¡ìa¨€";
					}
					s = s + " ¡ìr" + new DecimalFormat("0.0").format(tick * totalTime / totalTick) + " seconds";
					new ActionbarMessage(plugin).sendActionbarMessage(player, s);
					if (tick <= 10 * 20 && tick % 20 == 0) {
						player.playEffect(player.getLocation(), Effect.CLICK1, null);
						player.sendTitle("[¡ì6Battle Royale¡ìr]", "-= " + (int) (tick / 20) + " seconds left =-", 0, 200,
								0);
					}
					player.spawnParticle(Particle.SPELL_WITCH, player.getLocation(), particleAmount, 0,
							battleRoyale.getWorld().getMaxHeight() - player.getLocation().getY(), 0);
					player.setLevel((int) (tick * totalTime / totalTick));
					player.setExp((float) (tick / totalTick));
					tick--;
					if (tick <= 0) {
						plugin.getServer().getPluginManager().callEvent(new GameStartEvent());
						this.cancel();
					}

				}
			}.runTaskTimerAsynchronously(plugin, 0, 1);
		}
	}

	private void locationBroadcast(ArrayList<Player> players, boolean isHideName) {
		for (Player player : players) {
			player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaSurvivor Locator:");
			new PlayerLocator(plugin).playerLocate(player, players, isHideName, true);
		}
		plugin.getServer().getConsoleSender().sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaSurvivor Locator:");
		new PlayerLocator(plugin).playerLocate(plugin.getServer().getConsoleSender(), players);

	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

}
