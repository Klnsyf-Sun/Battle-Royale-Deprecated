package com.klnsyf.battleroyale.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.events.BattleEndEvent;
import com.klnsyf.battleroyale.events.BattleLoadEvent;
import com.klnsyf.battleroyale.events.BattlefieldPresetEvent;
import com.klnsyf.battleroyale.events.PlayerJoinBattlefieldEvent;
import com.klnsyf.battleroyale.events.PlayerQuitBattlefieldEvent;
import com.klnsyf.battleroyale.events.PlayerRequestBattlefieldBookEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;

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
		if (sender.hasPermission("battleroyale.preset")) {
			if (args.length == 1) {
				if (sender instanceof Player) {
					server.getPluginManager()
							.callEvent(new BattlefieldPresetEvent(sender, ((Player) sender).getWorld().getName()));
				} else {
					sender.sendMessage("[��6Battle Royale��r] ��cRequired argument: ��bworldName");
				}
			} else if (args.length == 2) {
				server.getPluginManager().callEvent(
						new BattlefieldPresetEvent(sender, args[1]));
			} else if (args.length == 3) {
				server.getPluginManager().callEvent(
						new BattlefieldPresetEvent(sender, args[1], args[2]));
			} else if (args.length == 4) {
				if (args[3].matches("\\d+")) {
					server.getPluginManager().callEvent(
							new BattlefieldPresetEvent(sender, args[1], args[2], Integer.valueOf(args[3])));
				} else {
					sender.sendMessage("[��6Battle Royale��r] ��cInvaild argument: ��b" + args[3] + " ��cis not a number");
				}
			} else if (args.length == 5) {
				server.getPluginManager().callEvent(
						new BattlefieldPresetEvent(sender, args[1], args[2], Integer.valueOf(args[3]),
								Boolean.valueOf(args[4])));
			} else {
				sender.sendMessage("[��6Battle Royale��r] ��cInvaild argument: ��b" + args[5]);
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "join", premission = "battleroyale.join", arg = "[worldName]", des = "Join into preseted battlefield")
	public void join(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.join")) {
			if (sender instanceof Player) {
				if (args.length == 1) {
					server.getPluginManager().callEvent(new PlayerRequestBattlefieldBookEvent((Player) sender));
				} else if (args.length == 2) {
					server.getPluginManager()
							.callEvent(new PlayerJoinBattlefieldEvent((Player) sender, args[1]));
				} else {
					sender.sendMessage("[��6Battle Royale��r] ��cInvaild amount of arguments");
				}
			} else {
				sender.sendMessage("[��6Battle Royale��r] ��cOnly Player can use this command");
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "quit", premission = "battleroyale.quit")
	public void quit(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.quit")) {
			if (sender instanceof Player) {
				server.getPluginManager().callEvent(new PlayerQuitBattlefieldEvent((Player) sender));
			} else {
				sender.sendMessage("[��6Battle Royale��r] ��cOnly Player can use this command");
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "start", premission = "battleroyale.start", arg = "[worldName]", des = "Game Start!")
	public void start(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.start")) {
			if (args.length == 2) {
				server.getPluginManager()
						.callEvent(new BattleLoadEvent(sender, args[1]));
			} else {
				sender.sendMessage("[��6Battle Royale��r] ��cInvaild amount of arguments");
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "reset", premission = "battleroyale.reset", arg = "[worldName]", des = "Reset a battlefield")
	public void end(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.reset")) {
			if (args.length == 2) {
				server.getPluginManager()
						.callEvent(new BattleEndEvent(sender, server.getWorld(args[1]), null));
			} else {
				sender.sendMessage("[��6Battle Royale��r] ��cInvaild amount of arguments");
			}
		} else {
			sender.sendMessage("[��6Battle Royale��r] ��cYou do not have permission to use this command");
		}
	}

}
