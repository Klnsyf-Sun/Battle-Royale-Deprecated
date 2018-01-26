package com.klnsyf.battleroyale.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.events.BattleEndEvent;
import com.klnsyf.battleroyale.events.BattleLoadEvent;
import com.klnsyf.battleroyale.events.BattlefieldPresetEvent;
import com.klnsyf.battleroyale.events.PlayerJoinBattlefieldEvent;
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
							"¡ì6/battleroyale ¡ìb" + sub.command() + " ¡ì3" + sub.arg() + "¡ì6-¡ìa " + sub.des());
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

	@SubCommand(command = "preset", premission = "battleroyale.main", arg = "[worldName] [ConfigName]", des = "Preset the battlefield with chosen config")
	public void preset(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.main")) {
			if (args.length == 1) {
				if (sender instanceof Player) {
					server.getPluginManager()
							.callEvent(new BattlefieldPresetEvent(sender, ((Player) sender).getWorld().getName(),
									"default"));
				} else {
					sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcRequired argument: ¡ìbworldName");
				}
			} else if (args.length == 2) {
				server.getPluginManager().callEvent(
						new BattlefieldPresetEvent(sender, args[1], "default"));
			} else if (args.length == 3) {
				server.getPluginManager().callEvent(
						new BattlefieldPresetEvent(sender, args[1], args[2]));
			} else {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcInvaild argument: ¡ìb" + args[3]);
			}
		} else {
			sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "join", premission = "battleroyale.join", arg = "[worldName]", des = "Join into preseted battlefield")
	public void join(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.join")) {
			if (args.length == 2) {
				if (sender instanceof Player) {
					server.getPluginManager()
							.callEvent(new PlayerJoinBattlefieldEvent((Player) sender, args[1]));
				} else {
					sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcOnly Player can use this command");
				}
			} else {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcInvaild amount of arguments");
			}
		} else
			sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcYou do not have permission to use this command");
	}

	@SubCommand(command = "start", premission = "battleroyale.main", arg = "[worldName]", des = "Game Start!")
	public void start(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.main")) {
			if (args.length == 2) {
				server.getPluginManager()
						.callEvent(new BattleLoadEvent(sender, args[1]));
			} else {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcInvaild amount of arguments");
			}
		} else {
			sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "reset", premission = "battleroyale.main", arg = "[worldName]", des = "Reset a battlefield")
	public void end(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.main")) {
			if (args.length == 2) {
				server.getPluginManager()
						.callEvent(new BattleEndEvent(sender, server.getWorld(args[1])));
			} else {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcInvaild amount of arguments");
			}
		} else {
			sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "test", premission = "battleroyale.main", arg = "???", des = "Just a test")
	public void test(CommandSender sender, String[] args) {
		sender.sendMessage(prefix + "¡ìaBattlefields:");
		for (World world : BattlefieldHandler.battlefields.keySet()) {
			String statue = "¡ì7>> ¡ìb" + world.getName() + " ¡ì3> ¡ìd";
			for (Player player : BattlefieldHandler.battlefields.get(world).players) {
				statue = statue + "[" + player.getName() + "] ";
			}
			sender.sendMessage(statue);
		}
	}

}
