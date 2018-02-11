package com.klnsyf.battleroyale.initialization;

import com.klnsyf.battleroyale.listeners.AnimalProtection;
import com.klnsyf.battleroyale.listeners.AutoLapis;
import com.klnsyf.battleroyale.listeners.BattleEnd;
import com.klnsyf.battleroyale.listeners.BattleLoad;
import com.klnsyf.battleroyale.listeners.BattleStart;
import com.klnsyf.battleroyale.listeners.BattlefieldPreset;
import com.klnsyf.battleroyale.listeners.BattlefieldProtect;
import com.klnsyf.battleroyale.listeners.DeathMatch;
import com.klnsyf.battleroyale.listeners.OreAutoMelt;
import com.klnsyf.battleroyale.listeners.PlayerAccelerateWorldBorderShrinking;
import com.klnsyf.battleroyale.listeners.PlayerDeath;
import com.klnsyf.battleroyale.listeners.PlayerJoinBattlefield;
import com.klnsyf.battleroyale.listeners.PlayerQuit;
import com.klnsyf.battleroyale.listeners.PlayerQuitBattlefield;
import com.klnsyf.battleroyale.listeners.PlayerSummonBlaze;
import com.klnsyf.battleroyale.listeners.PlayerThrowPrimedTnt;
import com.klnsyf.battleroyale.listeners.PlayerUseCompass;

public class Listeners {

	public void initListeners() {
		new AnimalProtection();
		new AutoLapis();
		new BattleEnd();
		new BattlefieldPreset();
		new BattlefieldProtect();
		new BattleLoad();
		new BattleStart();
		new DeathMatch();
		new OreAutoMelt();
		new PlayerAccelerateWorldBorderShrinking();
		new PlayerDeath();
		new PlayerJoinBattlefield();
		new PlayerQuit();
		new PlayerQuitBattlefield();
		new PlayerSummonBlaze();
		new PlayerThrowPrimedTnt();
		new PlayerUseCompass();
	}
}
