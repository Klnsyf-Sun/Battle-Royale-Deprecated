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
	// ¡ìb%CONFIGNAME\n¡ì7>> ¡ìaMax Player: ¡ìb%MAXPLAYER
	BATTLEFIELD_PRESET_SUCCESS_CONSOLE("BATTLEFIELD_PRESET.SUCCESS_CONSOLE", "%WORLDNAME", "%CONFIGNAME", "%MAXPLAYER"),

	// ¡ìaBattlefield ¡ìb%WORLDNAME ¡ìahas been presetted\n¡ì7>> ¡ìaMax Player:
	// ¡ìb%MAXPLAYER\n¡ì7>> ¡ìaYou can join this battlefield by command:
	// ¡ì6/battleroyale join ¡ìb%WORLDNAME
	BATTLEFIELD_PRESET_SUCCESS_PLAYER("BATTLEFIELD_PRESET.SUCCESS_PLAYER", "%WORLDNAME", "%MAXPLAYER"),

	// ¡ìcYou have already join battlefield ¡ìb%WORLDNAME
	PLAYER_JOIN_BATTLEFIELD_JOINED("PLAYER_JOIN_BATTLEFIELD.JOINED", "%WORLDNAME"),

	// Battlefield %WORLDNAME is full
	PLAYER_JOIN_BATTLEFIELD_FULL("PLAYER_JOIN_BATTLEFIELD.FULL", "%WORLDNAME"),

	// ¡ìd%PLAYERNAME has joined battlefield ¡ìb%WORLDNAME
	// ¡ìa(¡ìb%CURRENTPLAYER¡ìa/%MAXPLAYER)
	PLAYER_JOIN_BATTLEFIELD_SUCCESS("PLAYER_JOIN_BATTLEFIELD.SUCCESS", "%PLAYERNAME", "%WORLDNAME", "%CURRENTPLAYER",
			"%MAXPLAYER"),

	// ¡ìdSomeone ¡ìcaccelerated the world border's shrinking
	PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING_SUCCESS_ANONYMOUS("PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING.SUCCESS_ANONYMOUS"),

	// ¡ìd%PLAYERNAME ¡ìcaccelerated the world border's shrinking
	PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING_SUCCESS("PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING.SUCCESS", "%PLAYERNAME"),

	// ¡ìcWorld border has stopped shrinking
	EVENTS_SHRINK_STOPPED("EVENTS.SHRINK_STOPPED"),

	// -= Winner: ¡ìd%PLAYERNAME ¡ìr=-
	BATTLE_END_WINNER("BATTLE_END.WINNER", "%PLAYERNAME"),

	// ¡ìcGame has been forced to terminate
	BATTLE_END_FORCED_END("BATTLE_END.FORCED_END"),

	// second(s)
	EVENTS_SECOND("EVENTS.SECOND"),

	// Battle Start
	EVENTS_BATTLE_START("EVENTS.BATTLE_START"),

	// World Border Infomation
	PLAYER_USE_COMPASS_WORLD_BORDER("PLAYER_USE_COMPASS.WORLD_BORDER"),

	// Radius
	PLAYER_USE_COMPASS_RADIUS("PLAYER_USE_COMPASS.RADIUS"),

	// Speed
	PLAYER_USE_COMPASS_SPEED("PLAYER_USE_COMPASS.SPEED"),

	// Survivor Locator
	PLAYER_USE_COMPASS_SURVIVOR("PLAYER_USE_COMPASS.SURVIVOR"),

	// Player
	PLAYER_USE_COMPASS_ANONYMOUS("PLAYER_USE_COMPASS.ANONYMOUS"),

	// You have successfully summon a blaze
	PLAYER_SUMMON_BLAZE_SUCCESS("PLAYER_SUMMON_BLAZE.SUCCESS"),

	// ¡ìd%PLAYERNAME ¡ìckilled by ¡ìd%KILLERNAME
	PLAYER_DEATH_PLAYER_KILLED("PLAYER_DEATH.PLAYER_KILLED", "%PLAYERNAME", "%KILLERNAME"),

	// ¡ìd%PLAYERNAME ¡ìcdied
	PLAYER_DEATH_NATURE_DEATH("PLAYER_DEATH.NATURE_DEATH", "%PLAYERNAME"),

	// Death Match
	DEATH_MATCH("DEATH_MATCH.DEATH_MATCH"),

	// World Border Has Stopped Shrinking
	DEATH_MATCH_ACTION_BAR("DEATH_MATCH.ACTION_BAR_INFO"),

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
