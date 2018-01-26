package com.klnsyf.battleroyale.events;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BattleEndEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private final CommandSender sender;
	private final World world;

	public BattleEndEvent(CommandSender sender, World world) {
		super(true);
		this.sender = sender;
		this.world = world;
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

	public World getWorld() {
		return world;
	}
}
