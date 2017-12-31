package com.klnsyf.battleroyale.listeners;

import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.configuration.Config;

public class ListenersReload {

	public ListenersReload() {
		/*
		 * Add a new Listener: Complete the class generator; Add into Config; Add
		 * parameters into Config; Update the Config; Handler Update the config.yml; Add
		 * to this;
		 **/
	}

	public void listenersReload(BattleRoyale battleRoyale) {
		Config config = battleRoyale.getConfig();
		config.getAutoLapis().setBattleRoyale(battleRoyale);
		config.getAutoRespawn().setBattleRoyale(battleRoyale);
		config.getGameEnd().setBattleRoyale(battleRoyale);
		config.getGameLoad().setBattleRoyale(battleRoyale);
		config.getGamePreset().setBattleRoyale(battleRoyale);
		config.getGameProtection().setBattleRoyale(battleRoyale);
		config.getGameStart().setBattleRoyale(battleRoyale);
		config.getPlayerAccelerateShrink().setBattleRoyale(battleRoyale);
		config.getPlayerDeath().setBattleRoyale(battleRoyale);
		config.getPlayerQuit().setBattleRoyale(battleRoyale);
		config.getPlayerThrowingTnt().setBattleRoyale(battleRoyale);
		config.getPlayerUseCompass().setBattleRoyale(battleRoyale);
		config.getAnimalProtection().setBattleRoyale(battleRoyale);
		config.getOreAutoMelt().setBattleRoyale(battleRoyale);
		config.getWorldBorderStopShrink().setBattleRoyale(battleRoyale);
		config.getSummonBlaze().setBattleRoyale(battleRoyale);
	}

}
