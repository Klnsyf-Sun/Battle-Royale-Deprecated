package com.klnsyf.battleroyale.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public enum ConfigurationKey {

	PLAYER_GENERIC_MAX_HEALTH("player-generic-option.max-health", Double.class, 40.0),
	PLAYER_SPREAD_MIN_RANGE("player-spread-option.range.min", Integer.class, 500),
	PLAYER_SPREAD_MAX_RANGE("player-spread-option.range.max", Integer.class, 1750),
	PLAYER_SPREAD_RADIUS("player-spread-option.radius", Integer.class, 1500),
	PLAYER_SPREAD_MIN_RELATIVE_SPACE("player-spread-option.min-relative-space", Integer.class, 500),
	PLAYER_SPREAD_ATTEMPT_TIMES("player-spread-option.attempt-times", Integer.class, 1024),
	WOLRD_BORDER_DAMAGE_AMOUNT("world-border-option.damage.amount", Double.class, 1.0),
	WORLD_BORDER_DAMAGE_BUFFER("world-border-option.damage.buffer-distance", Integer.class, 0),
	WORLD_BORDER_OPTION_WARNING_DISTANCE("world-border-option.warning-distance", Integer.class, 8),
	WORLD_BORDER_MIN_RADIUS("world-border-option.radius.min", Integer.class, 64),
	WORLD_BORDER_MAX_RADIUS("world-border-option.radius.max", Integer.class, 2048),
	WORLD_BORDER_SHRINK_SPEED("world-border-option.shrink-speed", Double.class, 1.0),
	BATTLE_MISC_PROTECT_ANIMAL("battle-misc-option.protect-animal", Boolean.class, true),
	BATTLE_MISC_ORE_AUTO_MELT("battle-misc-option.ore-auto-melt", Boolean.class, false),
	BATTLE_MISC_HIDE_NAME("battle-misc-option.hide-player-name", Boolean.class, true),
	BATTLE_MISC_INIT_SUPPLY("battle-misc-option.init-supply", ArrayList.class, ConfigurationKey.DEFAULT_INIT_SUPPLY),
	BATTLE_MISC_LOADING_TIME("battle-misc-option.loading-time", Integer.class, 30),
	BATTLE_MISC_COMPASS_ENABLED("battle-misc-option.compass-option.enabled", Boolean.class, true),
	BATTLE_MISC_COMPASS_MODE("battle-misc-option.compass-option.mode", Boolean.class, true),
	BATTLE_MISC_COMPASS_COOLDOWN("battle-misc-option.compass-option.cooldown", Integer.class, 100),
	BATTLE_MISC_COMPASS_IGNORE_INVISIBLE("battle-misc-option.compass-option.ignore-invisible", Boolean.class, true),
	BATTLE_MISC_COMPASS_MAX_SHOWN_PLAYER("battle-misc-option.compass-option.max-shown-player", Integer.class, 4),
	BATTLE_MISC_SHRINK_ACCELERATING_ENABLED("battle-misc-option.shrink-accelerating-option.enabled", Boolean.class, true),
	BATTLE_MISC_SHRINK_ACCELERATING_ITEM("battle-misc-option.shrink-accelerating-option.active-item", String.class,
			"IRON_BLOCK"),
	BATTLE_MISC_SHRINK_ACCELERATING_SPEED("battle-misc-option.shrink-accelerating-option.accelerate-speed", Double.class, 1.0),
	BATTLE_MISC_SHRINK_ACCELERATING_DURATION("battle-misc-option.shrink-accelerating-option.duration", Integer.class, 1200),
	BATTLE_MISC_SHRINK_ACCELERATING_COOLDOWN("battle-misc-option.shrink-accelerating-option.cooldown", Integer.class, 300),
	BATTLE_MISC_SHRINK_ACCELERATING_PROTECT_TIME("battle-misc-option.shrink-accelerating-option.protect-time", Integer.class, 6000),
	BATTLE_MISC_SUMMON_BLAZE_ENABLED("battle-misc-option.summon-blaze.enabled", Boolean.class, true),
	BATTLE_MISC_DEATHMATCH_ENABLED("battle-misc-option.deathmatch.enabled", Boolean.class, true),
	BATTLE_MISC_KILLER_BONUS_HEALTH("battle-misc-option.killer-bonus.health", Integer.class, 5),
	BATTLE_MISC_KILLER_BONUS_SATURATION("battle-misc-option.killer-bonus.saturation", Integer.class, 5),
	BATTLE_MISC_DROP_LIST("battle-misc-option.drop-list", ArrayList.class, ConfigurationKey.DEFAULT_DROP_LIST),
	BATTLE_MISC_DROP_LIST_TOTAL_CHANCE("battle-misc-option.drop-list-total-chance", Integer.class, 10000),
	BATTLE_MISC_PLAYER_GLOWING_ENABLED("battle-misc-option.player-glowing-option.enabled", Boolean.class, true),
	BATTLE_MISC_PLAYER_GLOWING_ITEM("battle-misc-option.player-glowing-option.activate-item", String.class, "GLOWSTONE"),
	BATTLE_MISC_PLAYER_GLOWING_DURATION("battle-misc-option.player-glowing-option.duration", Integer.class, 20),
	BATTLE_MISC_PLAYER_GLOWING_RADIUS("battle-misc-option.player-glowing-option.radius", Double.class, 32.0),
	BATTLE_MISC_PLAYER_GLOWING_IGNORE_INVISIBLE("battle-misc-option.player-glowing-option.ignore-invisible", Boolean.class,
			true),
	BATTLE_MISC_PLAYER_INVISIBLE_OPTION_ENABLED("battle-misc-option.player-invisible-option.enabled", Boolean.class, true),
	BATTLE_MISC_PLAYER_INVISIBLE_OPTION_ACTIVATE_ITEM("battle-misc-option.player-invisible-option.activate-item", String.class,
			"COMPASS"),
	BATTLE_MISC_PLAYER_INVISIBLE_OPTION_DURATION("battle-misc-option.player-invisible-option.duration", Integer.class, 100),
	BATTLE_MISC_PLAYER_INVISIBLE_COST_HEALTH("battle-misc-option.player-invisible-option.cost.health", Integer.class, 0),
	BATTLE_MISC_PLAYER_INVISIBLE_COST_SATURATION("battle-misc-option.player-invisible-option.cost.saturation", Integer.class, 1),
	BATTLE_MISC_PLAYER_INVISIBLE_COST_ITEM("battle-misc-option.player-invisible-option.cost.item", Integer.class, 0),
	;

	private String path;
	private Class<?> clazz;
	private Object defaultValue;
	private final static ArrayList<Map<String, Integer>> DEFAULT_INIT_SUPPLY = new ArrayList<Map<String, Integer>>() {
		private static final long serialVersionUID = 1L;
		{
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

	private final static ArrayList<Map<String, ArrayList<Map<String, Integer>>>> DEFAULT_DROP_LIST = new ArrayList<Map<String, ArrayList<Map<String, Integer>>>>() {
		private static final long serialVersionUID = 1L;
		{
			add(new HashMap<String, ArrayList<Map<String, Integer>>>() {
				private static final long serialVersionUID = 1L;
				{
					put("STONE",
							new ArrayList<Map<String, Integer>>() {
								private static final long serialVersionUID = 1L;
								{
									add(new HashMap<String, Integer>() {
										private static final long serialVersionUID = 1L;
										{
											put("REDSTONE", 1000);
										}
									});
									add(new HashMap<String, Integer>() {
										private static final long serialVersionUID = 1L;
										{
											put("GLOWSTONE_DUST", 1000);
										}
									});
									add(new HashMap<String, Integer>() {
										private static final long serialVersionUID = 1L;
										{
											put("GLOWSTONE_DUST", 1000);
										}
									});
									add(new HashMap<String, Integer>() {
										private static final long serialVersionUID = 1L;
										{
											put("GLOWSTONE_DUST", 1000);
										}
									});
									add(new HashMap<String, Integer>() {
										private static final long serialVersionUID = 1L;
										{
											put("GLOWSTONE_DUST", 1000);
										}
									});
								}
							});
				}
			});
		}
	};

	private <T> ConfigurationKey(String path, Class<T> clazz, T defaultValue) {
		this.path = path;
		this.clazz = clazz;
		this.defaultValue = defaultValue;
	}

	public String getPath() {
		return path;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public Class<?> getClazz() {
		return clazz;
	}

}
