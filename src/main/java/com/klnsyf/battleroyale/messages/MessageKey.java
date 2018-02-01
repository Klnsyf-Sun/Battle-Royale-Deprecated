package com.klnsyf.battleroyale.messages;

public enum MessageKey {

	// Hello, %PLAYER
	TEST_MESSAGE("TEST_MESSAGE", "%PLAYER"),

	// ��6=================[��b HELP��6 ]=================
	COMMANDS_HELP("COMMANDS.HELP"),

	// ��aTips: You can use ��6/br ��aas aliases of ��6/battleroyale
	COMMANDS_TIP("COMMANDS.TIP"),

	// ��aUndefinded SubCommand: ��c%COMMAND
	COMMANDS_UNDEFINDED("COMMANDS.UNDEFINDED", "%COMMAND"),

	// ��cCan't find world with name: ��b%WORLDNAME
	COMMANDS_UNDEFINDED_WORLD("COMMANDS.UNDEFINDED_WORLD", "%WORLDNAME"),

	// ��cWorld ��b%WORLDNAME ��chas been preseted
	COMMANDS_WORLD_PRESETTED("COMMANDS.WORLD_PRESETTED", "%WORLDNAME"),

	// ��cWorld ��b%WORLDNAME ��chasn't been preseted
	COMMANDS_WORLD_UNPRESETTED("COMMANDS.WORLD_UNPRESETTED", "%WORLDNAME"),

	// ��cCan't find configuration file with name: ��b%CONFIGNAME
	BATTLEFIELD_PRESET_UNDEFINDED_CONFIGURATIONFILE("BATTLEFIELD_PRESET.UNDEFINDED_CONFIGURATIONFILE", "%CONFIGNAME"),

	// ��aBattlefield ��b%WORLDNAME ��ahas successfully presetted with configuration
	// ��b%CONFIGNAME\n��7>> ��aMax Player: ��b%MAXPLAYER
	BATTLEFIELD_PRESET_SUCCESS_CONSOLE("BATTLEFIELD_PRESET.SUCCESS_CONSOLE", "%WORLDNAME", "%CONFIGNAME", "%MAXPLAYER"),

	// ��aBattlefield ��b%WORLDNAME ��ahas been presetted\n��7>> ��aMax Player:
	// ��b%MAXPLAYER\n��7>> ��aYou can join this battlefield by command:
	// ��6/battleroyale join ��b%WORLDNAME
	BATTLEFIELD_PRESET_SUCCESS_PLAYER("BATTLEFIELD_PRESET.SUCCESS_PLAYER", "%WORLDNAME", "%MAXPLAYER"),

	// ��cYou have already join battlefield ��b%WORLDNAME
	PLAYER_JOIN_BATTLEFIELD_JOINED("PLAYER_JOIN_BATTLEFIELD.JOINED", "%WORLDNAME"),

	// Battlefield %WORLDNAME is full
	PLAYER_JOIN_BATTLEFIELD_FULL("PLAYER_JOIN_BATTLEFIELD.FULL", "%WORLDNAME"),

	// ��d%PLAYERNAME has joined battlefield ��b%WORLDNAME
	// ��a(��b%CURRENTPLAYER��a/%MAXPLAYER)
	PLAYER_JOIN_BATTLEFIELD_SUCCESS("PLAYER_JOIN_BATTLEFIELD.SUCCESS", "%PLAYERNAME", "%WORLDNAME", "%CURRENTPLAYER",
			"%MAXPLAYER"),

	// ��dSomeone ��caccelerated the world border's shrinking
	PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING_SUCCESS_ANONYMOUS("PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING.SUCCESS_ANONYMOUS"),

	// ��d%PLAYERNAME ��caccelerated the world border's shrinking
	PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING_SUCCESS("PLAYER_ACCELERATE_WORLD_BORDER_SHRINKING.SUCCESS", "%PLAYERNAME"),

	// ��cWorld border has stopped shrinking
	EVENTS_SHRINK_STOPPED("EVENTS.SHRINK_STOPPED"),

	// -= Winner: ��d%PLAYERNAME ��r=-
	BATTLE_END_WINNER("BATTLE_END.WINNER", "%PLAYERNAME"),

	// ��cGame has been forced to terminate
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

	// ��d%PLAYERNAME ��ckilled by ��d%KILLERNAME
	PLAYER_DEATH_PLAYER_KILLED("PLAYER_DEATH.PLAYER_KILLED", "%PLAYERNAME", "%KILLERNAME"),

	// ��d%PLAYERNAME ��cdied
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