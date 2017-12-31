package com.klnsyf.battleroyale.worldborder;

import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;
import com.klnsyf.battleroyale.events.WorldBorderStopShrinkEvent;

public class WorldBorderHandler {
	private BattleRoyale battleRoyale;
	private Config config;
	private WorldBorder worldBorder;
	private boolean isAccelerating;
	private double shrinkSpeed;

	public WorldBorderHandler(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
		this.config = battleRoyale.getConfig();
		this.worldBorder = battleRoyale.getWorld().getWorldBorder();
	}

	public double getWorldBorderRadius() {
		return worldBorder.getSize() / 2;
	}

	private boolean isWorldBorderShrinking(double minRadius, double currentRadius) {
		if (minRadius == currentRadius) {
			if (config.isWorldBorderShrinking()) {
				battleRoyale.getPlugin().getServer().getPluginManager().callEvent(new WorldBorderStopShrinkEvent());
				config.setWorldBorderShrinking(false);
			}
			return false;
		} else {
			config.setWorldBorderShrinking(true);
			return true;
		}
	}

	public void controlWorldBorder(double newRadius, long seconds) {
		isWorldBorderShrinking(newRadius, getWorldBorderRadius());
		this.shrinkSpeed = (getWorldBorderRadius() - newRadius) / seconds;
		worldBorder.setSize(newRadius * 2, seconds);
	}

	public void controlWorldBorder(double minRadius, double shrinkSpeed) {
		isWorldBorderShrinking(minRadius, getWorldBorderRadius());
		this.shrinkSpeed = shrinkSpeed;
		worldBorder.setSize(minRadius * 2, (long) ((getWorldBorderRadius() - minRadius) / shrinkSpeed));
	}

	public void accerateShrink(final double minRadius, final double accelerateSpeed, long tick) {
		isWorldBorderShrinking(minRadius, getWorldBorderRadius());
		controlWorldBorder(minRadius, shrinkSpeed + accelerateSpeed);
		this.isAccelerating = true;
		new BukkitRunnable() {

			public void run() {
				setShrinkSpeed(shrinkSpeed - accelerateSpeed);
				if (shrinkSpeed == config.getShrinkSpeed()) {
					isAccelerating = false;
				}
				controlWorldBorder(minRadius, shrinkSpeed);
				this.cancel();

			}
		}.runTaskLaterAsynchronously(battleRoyale.getPlugin(), tick);
	}

	public void initWorldBorder(double centerX, double centerZ, double damageAmount, double damageBuffer,
			double initRadius, int warningDistance) {
		worldBorder.setCenter(centerX, centerZ);
		worldBorder.setDamageAmount(damageAmount);
		worldBorder.setDamageBuffer(damageBuffer);
		worldBorder.setSize(initRadius * 2);
		worldBorder.setWarningDistance(warningDistance);
		config.setWorldBorderShrinking(false);
	}

	public boolean isAccelerating() {
		return isAccelerating;
	}

	public void setAccelerating(boolean isAccelerating) {
		this.isAccelerating = isAccelerating;
	}

	public double getShrinkSpeed() {
		return shrinkSpeed;
	}

	public void setShrinkSpeed(double shrinkSpeed) {
		this.shrinkSpeed = shrinkSpeed;
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

	public WorldBorder getWorldBorder() {
		return worldBorder;
	}

	public void setWorldBorder(WorldBorder worldBorder) {
		this.worldBorder = worldBorder;
	}
}