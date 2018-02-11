package com.klnsyf.battleroyale.utils.actionbar;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.klnsyf.battleroyale.BattleRoyale;

public class ActionbarMessage_Old implements ActionbarMessage {

	public void sendActionbarMessage(Player player, String message) {
		String server = BattleRoyale.server.getClass().getPackage().getName();
		server.substring(server.lastIndexOf(".") + 1);
		try {
			Class<?> class1 = Class.forName("org.bukkit.craftbukkit." + server + ".entity.CraftPlayer");
			Object craftPlayer = class1.cast(player);
			Object object2;
			Class<?> class2 = Class.forName("net.minecraft.server." + server + ".PacketPlayOutChat");
			Class<?> class3 = Class.forName("net.minecraft.server." + server + ".Packet");
			Class<?> class4 = Class.forName("net.minecraft.server." + server + ".ChatComponentText");
			Class<?> class5 = Class.forName("net.minecraft.server." + server + ".IChatBaseComponent");
			Object object4 = class4.getConstructor(new Class<?>[] { String.class }).newInstance(message);
			object2 = class2.getConstructor(new Class<?>[] { class5, byte.class }).newInstance(object4, (byte) 2);
			Method method1 = class1.getDeclaredMethod("getHandle");
			Object handle1 = method1.invoke(craftPlayer);
			Field field1 = handle1.getClass().getDeclaredField("playerConnection");
			Object playerConnection = field1.get(handle1);
			Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket", class3);
			sendPacket.invoke(playerConnection, object2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
