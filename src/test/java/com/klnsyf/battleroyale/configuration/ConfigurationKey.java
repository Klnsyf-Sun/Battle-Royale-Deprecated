package com.klnsyf.battleroyale.configuration;

public enum ConfigurationKey {

	PLAYER_GENETIC_MAXHEALTH("player-genetic-option.max-health", "40");
	private String path;
	private Object defaultValue;

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
