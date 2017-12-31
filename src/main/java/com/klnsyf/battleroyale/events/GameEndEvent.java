package com.klnsyf.battleroyale.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GameEndEvent extends Event {
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	private boolean isNormal;
	private Player winner;

	public GameEndEvent(Player... winner) {
		if (winner.length == 0) {
			this.isNormal = false;
			this.winner = null;
		} else {
			this.isNormal = true;
			this.winner = winner[0];
		}
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

	public boolean isNormal() {
		return isNormal;
	}

	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

}
