package com.klnsyf.battleroyale.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.events.GameEndEvent;
import com.klnsyf.battleroyale.events.GameJoinEvent;
import com.klnsyf.battleroyale.events.GameLoadEvent;
import com.klnsyf.battleroyale.events.GamePresetEvent;

public class BattleRoyaleCommand implements CommandExecutor {
	private BattleRoyale battleRoyale;

	public BattleRoyaleCommand(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("br") || label.equalsIgnoreCase("battleroyale")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&6=================[&b HELP&6 ]================="));
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&aTips: You can use ¡ì6/br ¡ìaas aliases of ¡ì6/battleroyale"));
				for (Method method : this.getClass().getDeclaredMethods()) {
					if (!method.isAnnotationPresent(SubCommand.class)) {
						continue;
					}
					SubCommand sub = method.getAnnotation(SubCommand.class);
					if (!sender.hasPermission(sub.premission())) {
						continue;
					}
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
							"&6/battleroyale &b" + sub.command() + " &3" + sub.arg() + "&6-&a " + sub.des()));
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
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
					"[¡ì6Battle Royale¡ìr] &aUndefinded SubCommand: &c" + args[0]));
			return true;
		}
		return false;
	}

	@SubCommand(command = "preset", premission = "battleroyale.main", arg = "[worldName] [ConfigName]", des = "Preset the battlefield with chosen config")
	public void preset(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.main")) {
			if (battleRoyale.getState() == 0) {
				if (args.length == 1) {
					if (sender instanceof Player) {
						battleRoyale.getPlugin().getServer().getPluginManager().callEvent(new GamePresetEvent(
								((Player) sender).getWorld(), battleRoyale.getConfig(), battleRoyale));
					} else {
						sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcMissing required argument: ¡ìbworldName");
					}
				} else if (args.length == 2) {
					if (battleRoyale.getPlugin().getServer().getWorld(args[1]) != null) {
						battleRoyale.getPlugin().getServer().getPluginManager()
								.callEvent(new GamePresetEvent(battleRoyale.getPlugin().getServer().getWorld(args[1]),
										battleRoyale.getConfig(), battleRoyale));
					} else {
						sender.sendMessage(
								"[¡ì6Battle Royale¡ìr] ¡ìcError argument: There is no such world with name ¡ìb" + args[1]);
					}
				} else if (args.length == 3) {
					if (battleRoyale.getPlugin().getConfig().getList(args[2]) != null) {
						battleRoyale.getConfig().setConfigName(args[2]);
						battleRoyale.getPlugin().getServer().getPluginManager()
								.callEvent(new GamePresetEvent(battleRoyale.getPlugin().getServer().getWorld(args[1]),
										battleRoyale.getConfig(), battleRoyale));
					} else {
						sender.sendMessage(
								"[¡ì6Battle Royale¡ìr] ¡ìcError argument: There is no such configuration file with name ¡ìb"
										+ args[2]);
					}
				} else {
					sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcIllegal argument: ¡ìb" + args[3]);
				}
			} else {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcWrong state: ¡ìb" + battleRoyale.getState());
			}
		} else {
			sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "join", premission = "battleroyale.join", des = "Join into preseted battlefield")
	public void join(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.join")) {
			if (battleRoyale.getState() == 0) {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcError: There is no battlefield preseted");
			} else if (battleRoyale.getState() == 1) {
				if (sender instanceof Player) {
					battleRoyale.getPlugin().getServer().getPluginManager()
							.callEvent(new GameJoinEvent((Player) sender));
				} else {
					sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcError: Only players can use this command");
				}
			} else {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcWrong state: ¡ìb" + battleRoyale.getState());
			}
		} else {
			sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "start", premission = "battleroyale.main", des = "Game Start!")
	public void start(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.main")) {
			if (battleRoyale.getState() == 0) {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcError: There is no battlefield preseted");
			} else if (battleRoyale.getState() == 1) {
				if (args.length == 1) {
					if (battleRoyale.getConfig().getPlayers().size() >= 2) {
						battleRoyale.getPlugin().getServer().getPluginManager().callEvent(new GameLoadEvent());
					} else {
						sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcError: Not enough player (¡ìb"
								+ battleRoyale.getConfig().getPlayers().size() + "¡ìc)");
					}
				} else {
					sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcIllegal argument: ¡ìb" + args[1]);
				}
			} else {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcWrong state: ¡ìb" + battleRoyale.getState());
			}
		} else {
			sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcYou do not have permission to use this command");
		}
	}

	@SubCommand(command = "end", premission = "battleroyale.main", des = "Force end the battle")
	public void end(CommandSender sender, String[] args) {
		if (sender.hasPermission("battleroyale.main")) {
			if (battleRoyale.getState() != 0) {
				battleRoyale.getPlugin().getServer().getPluginManager().callEvent(new GameEndEvent());
			} else {
				sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcError: There is no battlefield preseted");
			}
		} else {
			sender.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcYou do not have permission to use this command");
		}
	}

	@SuppressWarnings("unused")
	@SubCommand(command = "test", premission = "battleroyale.test")
	public void test(CommandSender sender, String[] args) {
		if (false && sender instanceof Player && sender.hasPermission("battleroyale.test")) {
			battleRoyale.getPlugin().getServer().getPluginManager().callEvent(new GameEndEvent());
			battleRoyale.getPlugin().getServer().getPluginManager().callEvent(
					new GamePresetEvent(((Player) sender).getWorld(), battleRoyale.getConfig(), battleRoyale));
			ArrayList<Player> players = battleRoyale.getConfig().getPlayers();
			for (Player player : battleRoyale.getPlugin().getServer().getOnlinePlayers()) {
				battleRoyale.getPlugin().getServer().getPluginManager().callEvent(new GameJoinEvent(player));
			}
			battleRoyale.getConfig().setPlayers(players);
			battleRoyale.getPlugin().getServer().getPluginManager().callEvent(new GameLoadEvent());
		}
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

}
