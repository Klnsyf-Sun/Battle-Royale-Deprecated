package com.klnsyf.battleroyale.configuration;

import org.bukkit.Material;

public enum InitSupply {
	Item0(0, Material.REDSTONE, 1), Item1(1, Material.BOAT, 1), Item2(2, Material.BREAD, 4);

	private int index;
	private Material type;
	private int amount;

	private InitSupply(int index, Material item, int amount) {
		this.index = index;
		this.type = item;
		this.amount = amount;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Material getItem() {
		return type;
	}

	public void setItem(Material item) {
		this.type = item;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
