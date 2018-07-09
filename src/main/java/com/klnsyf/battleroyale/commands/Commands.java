package com.klnsyf.battleroyale.commands;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.ConfigurationChecker;
import com.klnsyf.battleroyale.events.BattleEndEvent;
import com.klnsyf.battleroyale.events.BattleLoadEvent;
import com.klnsyf.battleroyale.events.BattlefieldPresetEvent;
import com.klnsyf.battleroyale.events.PlayerJoinBattlefieldEvent;
import com.klnsyf.battleroyale.events.PlayerOpenAdminBoardEvent;
import com.klnsyf.battleroyale.events.PlayerQuitBattlefieldEvent;
import com.klnsyf.battleroyale.events.PlayerRequestBattlefieldBookEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;
import com.klnsyf.battleroyale.utils.ExistenceChecker;

public class Commands implements CommandExecutor {
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;

	public Commands() {
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("br") || label.equalsIgnoreCase("battleroyale")) {
			if (args.length == 0) {
				sender.sendMessage(
						Messages.getMessage(MessageKey.COMMANDS_HELP));
				sender.sendMessage(
						Messages.getMessage(MessageKey.COMMANDS_TIP));
				for (Method method : this.getClass().getDeclaredMethods()) {
					if (!method.isAnnotationPresent(SubCommand.class)) {
						continue;
					}
					SubCommand sub = method.getAnnotation(SubCommand.class);
					if (!sender.hasPermission(sub.premission())) {
						continue;
					}
					sender.sendMessage(
							"��6/battleroyale ��b" + sub.command() + " ��3" + sub.arg() + "��6-��a " + sub.des());
				}
				return true;
			}
			for (Method method : this.getClass().getDeclaredMethods()) {
				if (!method.isAnnotationPresent(SubCommand.class)) {
					continue;
				}
				SubCommand sub = method.getAnnotation(SubCommand.class);
				if (!sub.command().equalsIgnoreCase(args[0])) {
					continue;
				}
				if (!sender.hasPermission(sub.premission())) {
					sender.sendMessage("[��6Battle Royale��r] ��cYou do not have permission to use this command");
					continue;
				}
				try {
					method.invoke(this, sender, args);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				return true;
			}
			sender.sendMessage(prefix + Messages.getMessage(MessageKey.COMMANDS_UNDEFINDED, args[0]));
			return true;
		}
		return false;
	}

	@SubCommand(command = "preset", premission = "battleroyale.preset", arg = "[worldName] [ConfigName] [maxPlayer] [autoStart]", des = "Preset a battlefield")
	public void preset(CommandSender sender, String[] args) {
		if (args.length == 1) {
			if (sender instanceof Player) {
				server.getPluginManager()
						.callEvent(new BattlefieldPresetEvent(((Player) sender).getWorld()));
			} else {
				sender.sendMessage("[��6Battle Royale��r] ��cRequired argument: ��bworldName");
			}
		} else if (args.length == 2) {
			if (ExistenceChecker.worldExist(args[1])) {
				server.getPluginManager().callEvent(
						new BattlefieldPresetEvent(server.getWorld(args[1])));
			}
		} else if (args.length == 3) {
			if (ExistenceChecker.worldExist(args[1]) && ExistenceChecker.configExist(args[2])) {
				server.getPluginManager().callEvent(
						new BattlefieldPresetEvent(server.getWorld(args[1]), YamlConfiguration.loadConfiguration(
								new File(BattleRoyale.dataFolder.getPath() + "\\configuration\\" + args[2] + ".yml"))));
			}
		} else if (args.length == 4) {
			if (args[3].matches("\\d+")) {
				if (ExistenceChecker.worldExist(args[1]) && ExistenceChecker.configExist(args[2])) {
					server.getPluginManager().callEvent(
							new BattlefieldPresetEvent(server.getWorld(args[1]), YamlConfiguration.loadConfiguration(
									new File(BattleRoyale.dataFolder.getPath() + "\\configuration\\" + args[2] + ".yml")),
									Integer.valueOf(args[3])));
				}
			} else {
				sender.sendMessage("[��6Battle Royale��r] ��cInvaild argument: ��b" + args[3] + " ��cis not a number");
			}
		} else if (args.length == 5) {
			if (ExistenceChecker.worldExist(args[1]) && ExistenceChecker.configExist(args[2]) && args[3].matches("\\d+")) {
				server.getPluginManager().callEvent(
						new BattlefieldPresetEvent(server.getWorld(args[1]), YamlConfiguration.loadConfiguration(
								new File(BattleRoyale.dataFolder.getPath() + "\\configuration\\" + args[2] + ".yml")),
								Integer.valueOf(args[3]),
								Boolean.valueOf(args[4])));
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cInvaild argument: ��b" + args[5]);
		}
	}

	@SubCommand(command = "join", premission = "battleroyale.join", arg = "[worldName]", des = "Join into preseted battlefield")
	public void join(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			if (args.length == 1) {
				server.getPluginManager().callEvent(new PlayerRequestBattlefieldBookEvent((Player) sender));
			} else if (args.length == 2) {
				if (ExistenceChecker.worldExist(args[1]) && ExistenceChecker.worldPreset(server.getWorld(args[1]))) {
					server.getPluginManager()
							.callEvent(new PlayerJoinBattlefieldEvent((Player) sender, server.getWorld(args[1])));
				}
			} else {
				sender.sendMessage("[��6Battle Royale��r] ��cInvaild amount of arguments");
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cOnly Player can use this command");
		}
	}

	@SubCommand(command = "quit", premission = "battleroyale.quit")
	public void quit(CommandSender sender, String[] args) {
		if (sender instanceof Player) {
			server.getPluginManager().callEvent(new PlayerQuitBattlefieldEvent((Player) sender));
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cOnly Player can use this command");
		}
	}

	@SubCommand(command = "start", premission = "battleroyale.start", arg = "[worldName]", des = "Game Start!")
	public void start(CommandSender sender, String[] args) {
		if (args.length == 2) {
			if (ExistenceChecker.worldExist(args[1]) && ExistenceChecker.worldPreset(server.getWorld(args[1]))) {
				server.getPluginManager()
						.callEvent(new BattleLoadEvent(server.getWorld(args[1])));
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cInvaild amount of arguments");
		}
	}

	@SubCommand(command = "reset", premission = "battleroyale.reset", arg = "[worldName]", des = "Reset a battlefield")
	public void end(CommandSender sender, String[] args) {
		if (args.length == 2) {
			if (ExistenceChecker.worldExist(args[1]) && ExistenceChecker.worldPreset(server.getWorld(args[1]))) {
				server.getPluginManager()
						.callEvent(new BattleEndEvent(server.getWorld(args[1]), null));
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cInvaild amount of arguments");
		}
	}

	@SubCommand(command = "admin", premission = "battleroyale.admin", des = "Open Admin Board")
	public void admin(CommandSender sender, String[] args) {
		if (args.length == 1) {
			server.getPluginManager()
					.callEvent(new PlayerOpenAdminBoardEvent((Player) sender));
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cInvaild amount of arguments");
		}
	}

	@SubCommand(command = "check", premission = "battleroyale.check", arg = "[fileName]", des = "Check File Integrity")
	public void check(CommandSender sender, String[] args) {
		if (args.length == 2) {
			File file = new File(
					BattleRoyale.dataFolder.getPath() + "\\configuration\\" + args[1] + ".yml");
			if (!file.exists()) {
				sender.sendMessage(prefix + Messages
						.getMessage(MessageKey.BATTLEFIELD_PRESET_UNDEFINDED_CONFIGURATIONFILE, args[1]));
			} else {
				new YamlConfiguration();
				YamlConfiguration configurationFile = YamlConfiguration.loadConfiguration(file);
				int checker = new ConfigurationChecker().configuationChecker(configurationFile, false);
				sender.sendMessage(prefix + "��aFile integrity check completed with ��b" + checker + "��a ERROR(s)");
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cInvaild amount of arguments");
		}
	}
}
