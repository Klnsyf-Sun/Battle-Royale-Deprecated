package com.klnsyf.battleroyale.events;

import java.io.File;

import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.klnsyf.battleroyale.BattleRoyale;

public class BattlefieldPresetEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private final World world;
	private final YamlConfiguration configFile;
	private final int maxPlayer;
	private final boolean isAutoStart;

	public BattlefieldPresetEvent(World world, YamlConfiguration configFile, int maxPlayer, boolean isAutoStart) {
		this.world = world;
		this.configFile = configFile;
		this.maxPlayer = maxPlayer;
		this.isAutoStart = isAutoStart;
	}

	public BattlefieldPresetEvent(World world, YamlConfiguration configFile, int maxPlayer) {
		this.world = world;
		this.configFile = configFile;
		this.maxPlayer = maxPlayer;
		this.isAutoStart = false;
	}

	public BattlefieldPresetEvent(World world, YamlConfiguration configFile) {
		this.world = world;
		this.configFile = configFile;
		this.maxPlayer = Integer.MAX_VALUE;
		this.isAutoStart = false;
	}

	public BattlefieldPresetEvent(World world) {
		this.world = world;
		new YamlConfiguration();
		this.configFile = YamlConfiguration
				.loadConfiguration(new File(BattleRoyale.dataFolder.getPath() + "\\configuration\\default.yml"));
		this.maxPlayer = Integer.MAX_VALUE;
		this.isAutoStart = false;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public World getWorld() {
		return world;
	}

	public YamlConfiguration getConfigFile() {
		return configFile;
	}

	public int getMaxPlayer() {
		return maxPlayer;
	}

	public boolean isAutoStart() {
		return isAutoStart;
	}

}
