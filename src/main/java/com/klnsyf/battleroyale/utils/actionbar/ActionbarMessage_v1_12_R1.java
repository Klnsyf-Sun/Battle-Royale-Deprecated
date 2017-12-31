package com.klnsyf.battleroyale.utils.actionbar;

import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class ActionbarMessage_v1_12_R1 implements ActionbarMessage {

	public void sendActionbarMessage(Player player, String message) {
		IChatBaseComponent icbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");
		ChatMessageType cmt = ChatMessageType.GAME_INFO;
		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, cmt);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);
	}

}
