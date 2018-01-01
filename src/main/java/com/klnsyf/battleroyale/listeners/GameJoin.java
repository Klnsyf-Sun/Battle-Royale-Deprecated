package com.klnsyf.battleroyale.listeners;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.klnsyf.battleroyale.BattleRoyaleSetup;
import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.GameJoinEvent;

public class GameJoin implements Listener {
	private BattleRoyale battleRoyale;
	private BattleRoyaleSetup plugin;
	private Config config;

	public GameJoin(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.plugin = battleRoyale.getPlugin();
		this.config = battleRoyale.getConfig();
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerJoin(GameJoinEvent event) {
		if (battleRoyale.getState() == 1) {
			ArrayList<Player> players = config.getPlayers();
			if (players.contains(event.getPlayer())) {
				event.getPlayer()
						.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcError: You have already joined into this battlefield");
			} else {
				if (players.size() == config.getSpreadLocation().size()) {
					event.getPlayer().sendMessage("[¡ì6Battle Royale¡ìr] ¡ìcThis battlefield is full");
				} else {
					players.add(event.getPlayer());
					config.setPlayers(players);
					battleRoyale.getPlugin().getServer().getConsoleSender()
							.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìd" + event.getPlayer().getName()
									+ " ¡ìajoin into battle field ¡ìb" + battleRoyale.getWorld().getName() + " ("
									+ players.size() + "/" + config.getSpreadLocation().size() + ")");
					for (Player player : battleRoyale.getPlugin().getServer().getOnlinePlayers()) {
						player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìd" + event.getPlayer().getName()
								+ " ¡ìajoin into battle field ¡ìb" + battleRoyale.getWorld().getName() + " ("
								+ players.size() + "/" + config.getSpreadLocation().size() + ")");
					}
					sendBattleFieldInfo(event.getPlayer());
				}
			}
		}
	}

	private void sendBattleFieldInfo(Player player) {
		player.sendMessage("[¡ì6Battle Royale¡ìr] ¡ìaBattlefield Information");
		player.sendMessage("¡ì7>> ¡ìaMax Health: ¡ìb" + config.getMaxHealth());
		player.sendMessage("¡ì7>> ¡ìaWorldborder: ¡ìb" + config.getMaxRadius() + "¡ìa downto ¡ìb" + config.getMinRadius()
				+ "¡ìa (¡ìb" + config.getShrinkSpeed() + " ¡ìam/s)");
		if (config.isProtectAnimal()) {
			player.sendMessage("¡ì7>> ¡ìaAnimals are ¡ìbinvulnerable¡ìa");
		}
		if (config.isAutoMelt()) {
			player.sendMessage("¡ì7>> ¡ìaOre will be ¡ìbautomatically¡ìa melted into ingots");
		}
		if (config.getCompassMode()) {
			player.sendMessage("¡ì7>> ¡ìa¡ìbRight click¡ìa to interact with compass");
		} else {
			player.sendMessage("¡ì7>> ¡ìa¡ìbDrop out¡ìa to interact with compass");
		}
		player.sendMessage("¡ì7>> ¡ìaSneak and drop out ¡ìb" + config.getAccelerateItem()
				+ "¡ìa to accelerafe the world border's shrinking as another ¡ìb" + config.getAccelerateSpeed()
				+ " ¡ìam/s for ¡ìb" + (int) (config.getAccelerateTicks() / 20) + "¡ìa seconds");
		player.sendMessage(
				"¡ì7>> ¡ìaSneak and right click a ¡ìbGOLD_BLOCK¡ìa with a stick to summon a blaze with brewing stand and nether wart");
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

}
