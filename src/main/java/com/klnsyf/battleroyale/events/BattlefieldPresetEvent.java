package com.klnsyf.battleroyale.events;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BattlefieldPresetEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private final CommandSender sender;
	private final String worldName;
	private final String configName;
	private final int maxPlayer;
	private final boolean autoStart;
	private final String DEFAULT_CONFIG_NAME = "default";

	public BattlefieldPresetEvent(CommandSender sender, String worldName, String configName, int maxPlayer, boolean autoStart) {
		super(false);
		this.sender = sender;
		this.worldName = worldName;
		this.configName = configName;
		this.maxPlayer = maxPlayer;
		this.autoStart = autoStart;
	}

	public BattlefieldPresetEvent(CommandSender sender, String worldName) {
		super(false);
		this.sender = sender;
		this.worldName = worldName;
		this.configName = DEFAULT_CONFIG_NAME;
		this.maxPlayer = Integer.MAX_VALUE;
		this.autoStart = false;
	}

	public BattlefieldPresetEvent(CommandSender sender, String worldName, String configName) {
		super(false);
		this.sender = sender;
		this.worldName = worldName;
		this.configName = configName;
		this.maxPlayer = Integer.MAX_VALUE;
		this.autoStart = false;
	}

	public BattlefieldPresetEvent(CommandSender sender, String worldName, String configName, int maxPlayer) {
		super(false);
		this.sender = sender;
		this.worldName = worldName;
		this.configName = configName;
		this.maxPlayer = maxPlayer;
		this.autoStart = false;
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

	public CommandSender getSender() {
		return sender;
	}

	public String getWorldName() {
		return worldName;
	}

	public String getConfigName() {
		return configName;
	}

	public int getMaxPlayer() {
		return maxPlayer;
	}

	public boolean isAutoStart() {
		return autoStart;
	}

}
