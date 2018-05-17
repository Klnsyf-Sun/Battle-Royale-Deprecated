package com.klnsyf.battleroyale.utils;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;
import com.klnsyf.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.events.WorldBorderStopShrinkingEvent;

public class WorldBorderHandler {
	private final BattleRoyale plugin = BattleRoyale.plugin;
	private final Server server = BattleRoyale.server;
	private final World world;
	private final WorldBorder worldBorder;

	public WorldBorderHandler(World world) {
		this.world = world;
		this.worldBorder = world.getWorldBorder();
	}

	public double getWorldBorderRadius() {
		return this.worldBorder.getSize() / 2;
	}

	private boolean isWorldBorderShrinking(int minRadius, double currentRadius) {
		if (minRadius == currentRadius) {
			if (BattlefieldHandler.battlefields.get(world).isWorldBorderShrinking()) {
				server.getPluginManager().callEvent(new WorldBorderStopShrinkingEvent(world));
				BattlefieldHandler.battlefields.get(world).setWorldBorderShrinking(false);
			}
			return false;
		} else {
			BattlefieldHandler.battlefields.get(world).setWorldBorderShrinking(true);
			return true;
		}
	}

	public void controlWorldBorder(int newRadius, int seconds) {
		isWorldBorderShrinking(newRadius, getWorldBorderRadius());
		BattlefieldHandler.battlefields.get(world).setShrinkSpeed((getWorldBorderRadius() - newRadius) / seconds);
		this.worldBorder.setSize(newRadius * 2, seconds);
	}

	public void controlWorldBorder(int minRadius, double shrinkSpeed) {
		isWorldBorderShrinking(minRadius, getWorldBorderRadius());
		BattlefieldHandler.battlefields.get(world).setShrinkSpeed(shrinkSpeed);
		worldBorder.setSize(minRadius * 2, (long) ((getWorldBorderRadius() - minRadius) / shrinkSpeed));
	}

	public void accerateShrink(final int minRadius, final double accelerateSpeed, int tick) {
		isWorldBorderShrinking(minRadius, getWorldBorderRadius());
		controlWorldBorder(minRadius, BattlefieldHandler.battlefields.get(world).getShrinkSpeed() + accelerateSpeed);
		new BukkitRunnable() {
			public void run() {
				if (BattlefieldHandler.battlefields.get(world) != null) {
					BattlefieldHandler.battlefields.get(world)
							.setShrinkSpeed(BattlefieldHandler.battlefields.get(world).getShrinkSpeed() - accelerateSpeed);
					controlWorldBorder(minRadius, BattlefieldHandler.battlefields.get(world).getShrinkSpeed());
				}
				this.cancel();
			}
		}.runTaskLaterAsynchronously(plugin, tick);
	}

	public void initWorldBorder(double centerX, double centerZ, double damageAmount, int damageBuffer,
			int initRadius, int warningDistance) {
		worldBorder.setCenter(centerX, centerZ);
		worldBorder.setDamageAmount(damageAmount);
		worldBorder.setDamageBuffer(damageBuffer);
		worldBorder.setSize(initRadius * 2);
		worldBorder.setWarningDistance(warningDistance);
		BattlefieldHandler.battlefields.get(world).setWorldBorderShrinking(false);
	}
}
