package com.klnsyf.battleroyale.configuration;

import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;

import com.klnsyf.battleroyale.BattleRoyale;

public class ConfigurationChecker {
	private final Server server = BattleRoyale.server;
	private final String prefix = BattleRoyale.prefix;

	public ConfigurationChecker() {
	}

	public int configuationChecker(YamlConfiguration configuration, boolean isReset) {
		int checker = 0;
		for (ConfigurationKey key : ConfigurationKey.values()) {
			if ((configuration.get(key.getPath(), key.getDefaultValue())).getClass() != key.getClazz()) {
				server.getConsoleSender()
						.sendMessage(prefix + "��cValue of ��d" + key.getPath() + " ��cis invaild (It's ��d"
								+ (configuration.get(key.getPath(), key.getDefaultValue())).getClass().getName()
								+ " ��cbut needs ��d"
								+ key.getClazz().getName() + "��c)");
				if (isReset) {
					configuration.set(key.getPath(), key.getDefaultValue());
					server.getConsoleSender()
							.sendMessage(
									prefix + "��aIt has been resetted to default value (��b" + key.getDefaultValue() + "��a)");
				}
				checker++;
			}
		}
		return checker;
	}
}
