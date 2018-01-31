package com.klnsyf.battleroyale.battlefield;

import java.util.HashMap;
import org.bukkit.World;

public class BattlefieldHandler {

	public static HashMap<World, BattlefieldConfiguration> battlefields;

	public BattlefieldHandler() {
		BattlefieldHandler.battlefields = new HashMap<World, BattlefieldConfiguration>();
	}

}
