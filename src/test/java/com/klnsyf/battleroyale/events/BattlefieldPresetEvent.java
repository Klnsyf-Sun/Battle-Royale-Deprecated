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

	public BattlefieldPresetEvent(CommandSender sender, String worldName, String configName) {
		super(false);
		this.sender = sender;
		this.worldName = worldName;
		this.configName = configName;
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

}
