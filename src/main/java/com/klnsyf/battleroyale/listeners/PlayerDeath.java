package com.klnsyf.battleroyale.listeners;

import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.configuration.Configuration;
import com.klnsyf.battleroyale.configuration.ConfigurationKey;
import com.klnsyf.battleroyale.events.BattleEndEvent;
import com.klnsyf.battleroyale.messages.MessageKey;
import com.klnsyf.battleroyale.messages.Messages;
import com.klnsyf.battleroyale.utils.autorespawn.AutoRespawnSetup;

public class PlayerDeath implements Listener {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;
	private final Configuration configuation = new Configuration();

	public PlayerDeath() {
		server.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()) != null) {
			if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).getStatue() == 2) {
				server.getConsoleSender().sendMessage(prefix + "¡ìc" + event.getDeathMessage());
				event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
				event.getEntity().setGameMode(GameMode.SPECTATOR);
				if (event.getEntity().getKiller() != null) {
					for (Player player : BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).players) {
						player.sendMessage(prefix + Messages.getMessage(MessageKey.PLAYER_DEATH_PLAYER_KILLED,
								event.getEntity().getName(), event.getEntity().getKiller().getName()));
					}
					event.getEntity().getKiller()
							.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,
									configuation.getValue(event.getEntity().getWorld(),
											ConfigurationKey.BATTLE_MISC_KILLER_BONUS_HEALTH),
									7, false, false));
					event.getEntity().getKiller()
							.addPotionEffect(
									new PotionEffect(PotionEffectType.SATURATION,
											configuation.getValue(event.getEntity().getWorld(),
													ConfigurationKey.BATTLE_MISC_KILLER_BONUS_SATURATION),
											0, false, false));
				} else {
					for (Player player : BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).players) {
						player.sendMessage(prefix
								+ Messages.getMessage(MessageKey.PLAYER_DEATH_NATURE_DEATH, event.getEntity().getName()));
					}
				}
				new AutoRespawnSetup().getAutoRespawn().autoRespawn(event.getEntity(), event.getEntity().getLocation());
				BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).alivePlayers.remove(event.getEntity());
				if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).alivePlayers.size() == 1) {
					server.getPluginManager()
							.callEvent(new BattleEndEvent(event.getEntity().getWorld(),
									BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).alivePlayers.get(0)));
				} else if (BattlefieldHandler.battlefields.get(event.getEntity().getWorld()).alivePlayers.size() < 1) {
					server.getPluginManager()
							.callEvent(new BattleEndEvent(event.getEntity().getWorld(),
									null));

				}
			}
		}
	}

}
