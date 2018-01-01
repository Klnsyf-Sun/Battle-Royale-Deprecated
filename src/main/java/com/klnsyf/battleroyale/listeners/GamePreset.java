package com.klnsyf.battleroyale.listeners;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.GamePresetEvent;
import com.klnsyf.battleroyale.worldborder.WorldBorderHandler;

public class GamePreset implements Listener {
	private BattleRoyaleSetup plugin;
	private BattleRoyale battleRoyale;
	private Config config;

	public GamePreset(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onGamePreset(GamePresetEvent event) {
		if (battleRoyale.getState() == 0) {
			battleRoyale = event.getBattleRoyale();
			battleRoyale.setWorld(event.getWorld());
			battleRoyale.setState(1);
			config = battleRoyale.getConfig();
			config.setWorldBorderHandler(new WorldBorderHandler(battleRoyale));
			config.setSpreadLocation(spreadAttempt(config.getAttempt(), config.getMinRange(), config.getMaxRange(),
					config.getSpreadDistance(), config.getSpreadSpace()));
			plugin.getServer().getConsoleSender()
					.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaBattlefield ¡ìb" + battleRoyale.getWorld().getName()
							+ "¡ìa has been preseted with configuration file with name "
							+ battleRoyale.getConfig().getConfigName());
			plugin.getServer().getConsoleSender()
					.sendMessage("¡ì7>> ¡ìaMax Player: " + config.getSpreadLocation().size());
			for (Player player : battleRoyale.getWorld().getPlayers()) {
				player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaBattlefield ¡ìb" + battleRoyale.getWorld().getName()
						+ "¡ìa has been preseted");
				player.sendMessage("¡ì7>> ¡ìaMax Player: " + config.getSpreadLocation().size());
				player.sendMessage("¡ì7>> ¡ìaYou can use command ¡ì6/battleroyale¡ìa join to join into the battlefield");
			}
			new ListenersReload().listenersReload(battleRoyale);
		}
	}

	private ArrayList<Double> spreadAttempt(int attempt, int minRange, int maxRange, int spreadDistance,
			int spreadSpace) {
		double[] randomRadian = new double[attempt];
		for (int index = 0; index < attempt; index++) {
			randomRadian[index] = (new Random()).nextDouble() * 2 * Math.PI;
		}
		ArrayList<Double> legalRadian = new ArrayList<Double>();
		for (double radian : randomRadian) {
			if (Math.abs(spreadDistance * Math.cos(radian)) < minRange
					|| Math.abs(spreadDistance * Math.cos(radian)) > maxRange
					|| Math.abs(spreadDistance * Math.sin(radian)) < minRange
					|| Math.abs(spreadDistance * Math.sin(radian)) > maxRange
					|| spreadSpaceTest(radian, legalRadian, spreadDistance, spreadSpace)) {
			} else {
				legalRadian.add(radian);
			}
		}
		return legalRadian;
	}

	private boolean spreadSpaceTest(double testRadian, ArrayList<Double> radianArray, int spreadDistance,
			int spreadSpace) {
		double minRadianSpace = 2 * Math.asin((double) spreadSpace / 2 / (double) spreadDistance);
		boolean test = false;
		for (double radian : radianArray) {
			if (Math.abs(testRadian - radian) < minRadianSpace
					|| 2 * Math.PI - Math.abs(testRadian - radian) < minRadianSpace) {
				test = true;
				break;
			}
		}
		return test;
	}

	public BattleRoyaleSetup getPlugin() {
		return plugin;
	}

	public void setPlugin(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

}
