package com.klnsyf.battleroyale.initialization;

import com.klnsyf.battleroyale.listeners.AnimalProtection;
import com.klnsyf.battleroyale.listeners.AutoLapis;
import com.klnsyf.battleroyale.listeners.DeathMatch;
import com.klnsyf.battleroyale.listeners.OreAutoMelt;
import com.klnsyf.battleroyale.listeners.PlayerAccelerateWorldBorderShrinking;
import com.klnsyf.battleroyale.listeners.PlayerBreakBlock;
import com.klnsyf.battleroyale.listeners.PlayerDeath;
import com.klnsyf.battleroyale.listeners.PlayerInvisible;
import com.klnsyf.battleroyale.listeners.EntityGlowing;
import com.klnsyf.battleroyale.listeners.PlayerSummonBlaze;
import com.klnsyf.battleroyale.listeners.PlayerThrowPrimedTnt;
import com.klnsyf.battleroyale.listeners.PlayerUseCompass;
import com.klnsyf.battleroyale.listeners.battlefieldGUI.PlayerOpenAdminBoard;
import com.klnsyf.battleroyale.listeners.battlefieldGUI.PlayerOpenBattlefieldGUI;
import com.klnsyf.battleroyale.listeners.battlefieldGUI.PlayerRequestBattlefieldBook;
import com.klnsyf.battleroyale.listeners.system.BattleEnd;
import com.klnsyf.battleroyale.listeners.system.BattleLoad;
import com.klnsyf.battleroyale.listeners.system.BattleStart;
import com.klnsyf.battleroyale.listeners.system.BattlefieldPreset;
import com.klnsyf.battleroyale.listeners.system.BattlefieldProtect;
import com.klnsyf.battleroyale.listeners.system.PlayerJoinBattlefield;
import com.klnsyf.battleroyale.listeners.system.PlayerQuit;
import com.klnsyf.battleroyale.listeners.system.PlayerQuitBattlefield;

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
		new PlayerBreakBlock();
		new PlayerDeath();
		new EntityGlowing();
		new PlayerJoinBattlefield();
		new PlayerOpenBattlefieldGUI();
		new PlayerQuit();
		new PlayerQuitBattlefield();
		new PlayerRequestBattlefieldBook();
		new PlayerSummonBlaze();
		new PlayerThrowPrimedTnt();
		new PlayerUseCompass();
		new PlayerOpenAdminBoard();
		new PlayerInvisible();
	}
}
