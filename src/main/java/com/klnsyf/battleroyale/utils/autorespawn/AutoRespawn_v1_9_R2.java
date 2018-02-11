package com.klnsyf.battleroyale.utils.autorespawn;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R2.PacketPlayInClientCommand;
import net.minecraft.server.v1_9_R2.PacketPlayInClientCommand.EnumClientCommand;

public class AutoRespawn_v1_9_R2 implements AutoRespawn {

	public void autoRespawn(Player player, Location location) {
		((CraftPlayer) player).getHandle().playerConnection
				.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
		player.teleport(location);
	}

}
