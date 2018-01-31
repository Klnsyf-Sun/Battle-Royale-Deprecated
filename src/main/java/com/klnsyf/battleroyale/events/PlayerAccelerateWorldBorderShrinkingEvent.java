package com.klnsyf.battleroyale.events;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerAccelerateWorldBorderShrinkingEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private final Player player;
	private final World world;

	public PlayerAccelerateWorldBorderShrinkingEvent(Player player) {
		super(false);
		this.player = player;
		this.world = player.getWorld();
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

	public Player getPlayer() {
		return player;
	}
}
