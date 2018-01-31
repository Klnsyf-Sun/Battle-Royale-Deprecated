package com.klnsyf.battleroyale.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum ConfigurationKey {

	PLAYER_GENERIC_MAX_HEALTH("player-generic-option.max-health", 40.0),
	PLAYER_SPREAD_MIN_RANGE("player-spread-option.range.min", 500),
	PLAYER_SPREAD_MAX_RANGE("player-spread-option.range.max", 1750),
	PLAYER_SPREAD_RADIUS("player-spread-option.radius", 1500),
	PLAYER_SPREAD_MIN_RELATIVE_SPACE("player-spread-option.min-relative-space", 500),
	PLAYER_SPREAD_ATTEMPT_TIMES("player-spread-option.attempt-times", 1024),
	WOLRD_BORDER_DAMAGE_AMOUNT("world-border-option.damage.amount", 1.0),
	WORLD_BORDER_DAMAGE_BUFFER("world-border-option.damage.buffer-distance", 0),
	WORLD_BORDER_OPTION_WARNING_DISTANCE("world-border-option.warning-distance", 8),
	WORLD_BORDER_MIN_RADIUS("world-border-option.radius.min", 64),
	WORLD_BORDER_MAX_RADIUS("world-border-option.radius.max", 2048),
	WORLD_BORDER_SHRINK_SPEED("world-border-option.shrink-speed", 1.0),
	BATTLE_MISC_PROTECT_ANIMAL("battle-misc-option.protect-animal", true),
	BATTLE_MISC_ORE_AUTO_MELT("battle-misc-option.ore-auto-melt", false),
	BATTLE_MISC_HIDE_NAME("battle-misc-option.hide-player-name", true),
	BATTLE_MISC_INIT_SUPPLY("battle-misc-option.init-supply", ConfigurationKey.DEFAULT_INIT_SUPPLY),
	BATTLE_MISC_LOADING_TIME("battle-misc-option.loading-time", 30),
	BATTLE_MISC_COMPASS_ENABLED("battle-misc-option.compass-option.enabled", true),
	BATTLE_MISC_COMPASS_MODE("battle-misc-option.compass-option.mode", true),
	BATTLE_MISC_COMPASS_COOLDOWN("battle-misc-option.compass-option.cooldown", 100),
	BATTLE_MISC_SHRINK_ACCELERATING_ENABLED("battle-misc-option.shrink-accelerating-option.enabled", true),
	BATTLE_MISC_SHRINK_ACCELERATING_ITEM("battle-misc-option.shrink-accelerating-option.active-item", "IRON_BLOCK"),
	BATTLE_MISC_SHRINK_ACCELERATING_SPEED("battle-misc-option.shrink-accelerating-option.accelerate-speed", 1.0),
	BATTLE_MISC_SHRINK_ACCELERATING_DURATION("battle-misc-option.shrink-accelerating-option.duration", 1200),
	BATTLE_MISC_SHRINK_ACCELERATING_COOLDOWN("battle-misc-option.shrink-accelerating-option.cooldown", 300),
	BATTLE_MISC_SHRINK_ACCELERATING_PROTECT_TIME("battle-misc-option.shrink-accelerating-option.protect-time", 6000),
	BATTLE_MISC_SUMMON_BLAZE_ENABLED("battle-misc-option.summon-blaze.enabled", true),
	BATTLE_MISC_DEATHMATCH_ENABLED("battle-misc-option.deathmatch.enabled", true),
	BATTLE_MISC_KILLER_BONUS_HEALTH("battle-misc-option.killer-bonus.health", 5),
	BATTLE_MISC_KILLER_BONUS_SATURATION("battle-misc-option.killer-bonus.saturation", 5);

	private String path;
	private Object defaultValue;
	private final static ArrayList<Map<String, Integer>> DEFAULT_INIT_SUPPLY = new ArrayList<Map<String, Integer>>() {
		private static final long serialVersionUID = 1L;
		{
			add(new HashMap<String, Integer>() {
				private static final long serialVersionUID = 1L;
				{
					put("REDSTONE", 1);
				}
			});
			add(new HashMap<String, Integer>() {
				private static final long serialVersionUID = 1L;
				{
					put("BREAD", 4);
				}
			});
			add(new HashMap<String, Integer>() {
				private static final long serialVersionUID = 1L;
				{
					put("BOAT", 1);
				}
			});
		}
	};

	private <T> ConfigurationKey(String path, T defaultValue) {
		this.path = path;
		this.defaultValue = defaultValue;
	}

	public String getPath() {
		return path;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}
}
