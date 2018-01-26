package com.klnsyf.battleroyale.utils.autorespawn;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand.EnumClientCommand;

public class AutoRespawn_v1_12_R1 implements AutoRespawn {

	public void autoRespawn(Player player, Location location) {
		((CraftPlayer) player).getHandle().playerConnection
				.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
		player.teleport(location);
	}

}
