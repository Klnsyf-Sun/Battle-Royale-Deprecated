package com.klnsyf.battleroyale.events;

import org.bukkit.event.HandlerList;

public class PlayerSummonBlazeEvent {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;

	public PlayerSummonBlazeEvent() {
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
}
