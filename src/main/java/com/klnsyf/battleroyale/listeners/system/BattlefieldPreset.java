package com.klnsyf.battleroyale.listeners.system;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldConfiguration;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationChecker;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;
import com.klnsyf.battleroyale.events.BattlefieldPresetEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;

public class BattlefieldPreset implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;
	private final Configuration configuation = new Configuration();

	public BattlefieldPreset() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBattlefieldPreset(BattlefieldPresetEvent event) {
		YamlConfiguration configFile = event.getConfigFile();
		int checker = new ConfigurationChecker().configuationChecker(configFile, true);
		server.getConsoleSender().sendMessage(prefix + "¡ìaFile integrity check completed with ¡ìb" + checker + "¡ìa ERROR(s)");
		BattlefieldHandler.battlefields.put(event.getWorld(), new BattlefieldConfiguration(configFile));
		BattlefieldHandler.battlefields.get(event.getWorld()).setSpreadLocations(spreadAttempt(
				configuation.getValue(event.getWorld(), ConfigurationKey.PLAYER_SPREAD_ATTEMPT_TIMES),
				configuation.getValue(event.getWorld(), ConfigurationKey.PLAYER_SPREAD_MIN_RANGE),
				configuation.getValue(event.getWorld(), ConfigurationKey.PLAYER_SPREAD_MAX_RANGE),
				configuation.getValue(event.getWorld(), ConfigurationKey.PLAYER_SPREAD_RADIUS),
				configuation.getValue(event.getWorld(), ConfigurationKey.PLAYER_SPREAD_MIN_RELATIVE_SPACE)));
		BattlefieldHandler.battlefields.get(event.getWorld())
				.setMaxPlayer(Math.min(BattlefieldHandler.battlefields.get(event.getWorld()).getSpreadLocations().size(),
						event.getMaxPlayer()));
		BattlefieldHandler.battlefields.get(event.getWorld()).setAutoStart(event.isAutoStart());
		server.getConsoleSender()
				.sendMessage(prefix + Messages.getMessage(MessageKey.BATTLEFIELD_PRESET_SUCCESS_CONSOLE,
						event.getWorld().getName(), event.getConfigFile().getName(),
						BattlefieldHandler.battlefields.get(event.getWorld()).getMaxPlayer() + ""));
		event.getWorld().setSpawnLocation(event.getWorld().getHighestBlockAt(0, 0).getLocation().add(0, 1, 0));
		for (Player player : server.getOnlinePlayers()) {
			if (BattlefieldHandler.battlefields.containsKey(player.getWorld())) {
				if (BattlefieldHandler.battlefields.get(player.getWorld()).players.contains(player)) {
				} else {
					player.sendMessage(
							prefix + Messages.getMessage(MessageKey.BATTLEFIELD_PRESET_SUCCESS_PLAYER,
									event.getWorld().getName(),
									BattlefieldHandler.battlefields.get(event.getWorld()).getMaxPlayer() + ""));
				}
			} else {
				player.sendMessage(
						prefix + Messages.getMessage(MessageKey.BATTLEFIELD_PRESET_SUCCESS_PLAYER,
								event.getWorld().getName(),
								BattlefieldHandler.battlefields.get(event.getWorld()).getMaxPlayer() + ""));
			}
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
}
