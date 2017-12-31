package com.klnsyf.battleroyale.configuration;

import org.bukkit.potion.PotionEffectType;

public enum InitEffect {
	;

	private int index;
	private PotionEffectType type;
	private int amplifier;
	private int duration;

	private InitEffect(int index, PotionEffectType type, int amplifier, int duration) {
		this.index = index;
		this.type = type;
		this.amplifier = amplifier;
		this.duration = duration;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public PotionEffectType getType() {
		return type;
	}

	public void setType(PotionEffectType type) {
		this.type = type;
	}

	public int getAmplifier() {
		return amplifier;
	}

	public void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
