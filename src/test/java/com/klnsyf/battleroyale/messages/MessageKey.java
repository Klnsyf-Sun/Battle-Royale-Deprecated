package com.klnsyf.battleroyale.messages;

public enum MessageKey {

	// Hello, %PLAYER
	TEST_MESSAGE("TEST_MESSAGE", "%PLAYER"),

	// ¡ì6=================[¡ìb HELP¡ì6 ]=================
	COMMANDS_HELP("COMMANDS.HELP"),

	// ¡ìaTips: You can use ¡ì6/br ¡ìaas aliases of ¡ì6/battleroyale
	COMMANDS_TIP("COMMANDS.TIP"),

	// ¡ìaUndefinded SubCommand: ¡ìc%COMMAND
	COMMANDS_UNDEFINDED("COMMANDS.UNDEFINDED", "%COMMAND"),

	// ¡ìcCan't find world with name: ¡ìb%WORLDNAME
	COMMANDS_UNDEFINDED_WORLD("COMMANDS.UNDEFINDED_WORLD", "%WORLDNAME"),

	// ¡ìcWorld ¡ìb%WORLDNAME ¡ìchas been preseted
	COMMANDS_WORLD_PRESETTED("COMMANDS.WORLD_PRESETTED", "%WORLDNAME"),

	// ¡ìcWorld ¡ìb%WORLDNAME ¡ìchasn't been preseted
	COMMANDS_WORLD_UNPRESETTED("COMMANDS.WORLD_UNPRESETTED", "%WORLDNAME"),

	// ¡ìcCan't find configuration file with name: ¡ìb%CONFIGNAME
	BATTLEFIELD_PRESET_UNDEFINDED_CONFIGURATIONFILE("BATTLEFIELD_PRESET.UNDEFINDED_CONFIGURATIONFILE", "%CONFIGNAME"),

	// ¡ìaBattlefield ¡ìb%WORLDNAME ¡ìahas successfully presetted with configuration
	// ¡ìb%CONFIGNAME
	BATTLEFIELD_PRESET_SUCCESS_CONSOLE("BATTLEFIELD_PRESET.SUCCESS_CONSOLE", "%WORLDNAME", "%CONFIGNAME"),

	// ¡ìaBattlefield ¡ìb%WORLDNAME ¡ìahas been presetted\n ¡ì7>> ¡ìaYou can join this
	// battlefield by command: ¡ì6/battleroyale join ¡ìb%WORLDNAME
	BATTLEFIELD_PRESET_SUCCESS_PLAYER("BATTLEFIELD_PRESET.SUCCESS_PLAYER", "%WORLDNAME"),

	// ¡ìcYou have already join battlefield ¡ìb%WORLDNAME
	PLAYER_JOIN_BATTLEFIELD_JOINED("PLAYER_JOIN_BATTLEFIELD.JOINED", "%WORLDNAME"),

	// ¡ìaPlayer ¡ìd%PLAYERNAME has joined battlefield ¡ìb%WORLDNAME
	PLAYER_JOIN_BATTLEFIELD_SUCCESS("PLAYER_JOIN_BATTLEFIELD.SUCCESS", "%PLAYERNAME", "%WORLDNAME"),
	;
	private String key;
	private String[] tags;

	private MessageKey(String key, String... tags) {
		this.key = key;
		this.tags = tags;
	}

	public String getKey() {
		return key;
	}

	public String[] getTags() {
		return tags;
	}

	@Override
	public String toString() {
		return key;
	}

}
