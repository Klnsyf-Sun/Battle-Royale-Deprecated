package com.klnsyf.battleroyale;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.lang.SystemUtils;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import com.klnsyf.battleroyale.battlefield.BattlefieldHandler;
import com.klnsyf.battleroyale.commands.Commands;
import com.klnsyf.battleroyale.initialization.Listeners;
import com.klnsyf.battleroyale.messages.Messages;
import com.klnsyf.battleroyale.settings.Settings;
import com.klnsyf.battleroyale.utils.UpdateChecker;

public class BattleRoyale extends JavaPlugin {
	public static BattleRoyale plugin;
	public static Server server;
	public static File dataFolder;
	public static Settings settings;
	public final static String prefix = "[¡ì6Battle Royale¡ìr] ";

	@Override
	public void onEnable() {
		plugin = this;

		saveDefaultConfig();

		BattleRoyale.server = getServer();
		BattleRoyale.dataFolder = getDataFolder();
		BattleRoyale.settings = new Settings();

		if (!SystemUtils.isJavaVersionAtLeast(1.8f)) {
			throw new IllegalStateException("[Battle Royale] Required Java Version is at least 1.8!");
		}

		if (Settings.isCheckUpdate) {
			new UpdateChecker().checkUpdate();
		}

		if (!Messages.language.exists()) {
			try {
				throw new FileNotFoundException("\n		 [Battle Royale] Can't find language file with name "
						+ Settings.language
						+ "\n		 [Battle Royale] Please make sure the language file has completely installed under the right path");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				server.getPluginManager().disablePlugin(this);
			}
		}

		for (String str : this.getDescription().getCommands().keySet()) {
			getCommand(str).setExecutor(new Commands());
		}

		new Listeners().initListeners();
		new BattlefieldHandler();
	}

}
