package com.klnsyf.battleroyale.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.klnsyf.battleroyale.BattleRoyale;

public class UpdateChecker {
	private final String prefix = BattleRoyale.prefix;

	public UpdateChecker() {
	}

	public String getLatestVersion() throws IOException {
		String version = null;
		try {
			URL url = new URL("https://raw.githubusercontent.com/Klnsyf-Sun/Battle-Royale/master/version.txt");
			URLConnection urlc = url.openConnection();
			urlc.setConnectTimeout(5000);
			urlc.setDoOutput(true);
			InputStream is = url.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			version = br.readLine();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return version;
	}

	public void checkUpdate() {
		String currentVersion = BattleRoyale.plugin.getDescription().getVersion();
		String latestVersion = null;
		try {
			latestVersion = getLatestVersion();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!currentVersion.equalsIgnoreCase(latestVersion)) {
			BattleRoyale.plugin.getServer().getConsoleSender()
					.sendMessage(prefix + "¡ìaNew Version Available: ¡ìb" + latestVersion);
		}
	}
}
