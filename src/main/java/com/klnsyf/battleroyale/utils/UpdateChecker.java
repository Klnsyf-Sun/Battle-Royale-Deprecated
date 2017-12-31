package com.klnsyf.battleroyale.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.command.CommandSender;

import com.klnsyf.battleroyale.BattleRoyaleSetup;

public class UpdateChecker {
	private String latestVer;
	private BattleRoyaleSetup plugin;

	public UpdateChecker(BattleRoyaleSetup plugin) {
		this.plugin = plugin;
	}

	public String getLatestVersion() throws IOException {
		String version = null;
		try {
			URL url = new URL("https://klnsyf.github.io/version.txt");
			URLConnection urlc = url.openConnection();
			urlc.setConnectTimeout(5000);
			urlc.setDoOutput(true);
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			version = br.readLine();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		setLatestVer(version);
		return version;
	}

	public void updateCheck(CommandSender sender) {
		String currentVersion = plugin.getDescription().getVersion();
		String latestVersion = null;
		try {
			latestVersion = getLatestVersion();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!currentVersion.equalsIgnoreCase(latestVersion)) {
			sender.sendMessage("[§6Battle Royale§r] §a发现新版本: " + latestVersion);
		}
	}

	public String getLatestVer() {
		return latestVer;
	}

	public void setLatestVer(String latestVer) {
		this.latestVer = latestVer;
	}

}
