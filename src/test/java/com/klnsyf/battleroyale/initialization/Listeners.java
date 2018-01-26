package com.klnsyf.battleroyale.initialization;

import com.klnsyf.battleroyale.listeners.BattleLoad;
import com.klnsyf.battleroyale.listeners.BattlefieldPreset;
import com.klnsyf.battleroyale.listeners.PlayerJoinBattlefield;

public class Listeners {

	public void initListeners() {
		new BattlefieldPreset();
		new PlayerJoinBattlefield();
		new BattleLoad();
	}
}
