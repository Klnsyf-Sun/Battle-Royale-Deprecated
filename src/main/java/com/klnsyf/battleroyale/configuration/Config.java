package com.klnsyf.battleroyale.configuration;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.klnsyf.battleroyale.battleroyale.BattleRoyale;
import com.klnsyf.battleroyale.listeners.AnimalProtection;
import com.klnsyf.battleroyale.listeners.AutoLapis;
import com.klnsyf.battleroyale.listeners.AutoRespawn;
import com.klnsyf.battleroyale.listeners.GameEnd;
import com.klnsyf.battleroyale.listeners.GameLoad;
import com.klnsyf.battleroyale.listeners.GamePreset;
import com.klnsyf.battleroyale.listeners.GameProtection;
import com.klnsyf.battleroyale.listeners.GameStart;
import com.klnsyf.battleroyale.listeners.OreAutoMelt;
import com.klnsyf.battleroyale.listeners.PlayerAccelerateShrink;
import com.klnsyf.battleroyale.listeners.PlayerDeath;
import com.klnsyf.battleroyale.listeners.PlayerQuit;
import com.klnsyf.battleroyale.listeners.PlayerThrowingTnt;
import com.klnsyf.battleroyale.listeners.PlayerUseCompass;
import com.klnsyf.battleroyale.listeners.SummonBlaze;
import com.klnsyf.battleroyale.listeners.WorldBorderStopShrink;
import com.klnsyf.battleroyale.worldborder.WorldBorderHandler;

public class Config {
	private BattleRoyale battleRoyale;
	private String configName;
	// player-genetic-option
	private int maxHealth;
	// player-spread-option
	private int minRange;
	private int maxRange;
	private int spreadDistance;
	private int spreadSpace;
	private int attempt;
	// world-border-option
	private double centerX;
	private double centerZ;
	private double damageAmount;
	private double damageBuffer;
	private int warningDistance;
	// game-option
	private boolean isHideName;
	private boolean isProtectAnimal;
	private boolean isAutoMelt;
	private int gameLoadLatencyTime;
	private int praticleAmount;
	private double minRadius;
	private double maxRadius;
	private double shrinkSpeed;
	private boolean compassMode;
	private int compassColdDown;
	private Material accelerateItem;
	private double accelerateSpeed;
	private int accelerateTicks;
	private int accelerateColdDown;
	private int accelerateProtectTime;
	// others
	private ArrayList<Player> players;
	private ArrayList<Player> alivePlayers;
	private WorldBorderHandler worldBorderHandler;
	private boolean isWorldBorderShrinking;
	// listeners
	private AutoLapis autoLapis;
	private AutoRespawn autoRespawn;
	private GameEnd gameEnd;
	private GameLoad gameLoad;
	private GamePreset gamePreset;
	private GameProtection gameProtection;
	private GameStart gameStart;
	private PlayerAccelerateShrink playerAccelerateShrink;
	private PlayerDeath playerDeath;
	private PlayerQuit playerQuit;
	private PlayerThrowingTnt playerThrowingTnt;
	private PlayerUseCompass playerUseCompass;
	private AnimalProtection animalProtection;
	private OreAutoMelt oreAutoMelt;
	private WorldBorderStopShrink worldBorderStopShrink;
	private SummonBlaze summonBlaze;

	public Config(BattleRoyale battleRoyale, String configName) {
		this.battleRoyale = battleRoyale;
		this.configName = configName;
		battleRoyale.setConfig(this);
		new ConfigHandler(battleRoyale).loadConfig();
		this.players = new ArrayList<Player>();
		this.autoLapis = new AutoLapis(battleRoyale);
		this.autoRespawn = new AutoRespawn(battleRoyale);
		this.gameEnd = new GameEnd(battleRoyale);
		this.gameLoad = new GameLoad(battleRoyale);
		this.gamePreset = new GamePreset(battleRoyale);
		this.gameProtection = new GameProtection(battleRoyale);
		this.gameStart = new GameStart(battleRoyale);
		this.playerAccelerateShrink = new PlayerAccelerateShrink(battleRoyale);
		this.playerDeath = new PlayerDeath(battleRoyale);
		this.playerQuit = new PlayerQuit(battleRoyale);
		this.playerThrowingTnt = new PlayerThrowingTnt(battleRoyale);
		this.playerUseCompass = new PlayerUseCompass(battleRoyale);
		this.animalProtection = new AnimalProtection(battleRoyale);
		this.oreAutoMelt = new OreAutoMelt(battleRoyale);
		this.worldBorderStopShrink = new WorldBorderStopShrink(battleRoyale);
		this.summonBlaze = new SummonBlaze(battleRoyale);
	}

	public void roundReset() {
		this.players = new ArrayList<Player>();
		this.isWorldBorderShrinking = false;
	}

	public int getMinRange() {
		return minRange;
	}

	public void setMinRange(int minRange) {
		this.minRange = minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(int maxRange) {
		this.maxRange = maxRange;
	}

	public int getSpreadDistance() {
		return spreadDistance;
	}

	public void setSpreadDistance(int spreadDistance) {
		this.spreadDistance = spreadDistance;
	}

	public int getSpreadSpace() {
		return spreadSpace;
	}

	public void setSpreadSpace(int spreadSpace) {
		this.spreadSpace = spreadSpace;
	}

	public int getAttempt() {
		return attempt;
	}

	public void setAttempt(int attempt) {
		this.attempt = attempt;
	}

	public boolean getIsHideName() {
		return isHideName;
	}

	public void setIsHideName(boolean isHideName) {
		this.isHideName = isHideName;
	}

	public int getPraticleAmount() {
		return praticleAmount;
	}

	public void setPraticleAmount(int praticleAmount) {
		this.praticleAmount = praticleAmount;
	}

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterZ() {
		return centerZ;
	}

	public void setCenterZ(double centerZ) {
		this.centerZ = centerZ;
	}

	public double getDamageAmount() {
		return damageAmount;
	}

	public void setDamageAmount(double damageAmount) {
		this.damageAmount = damageAmount;
	}

	public double getDamageBuffer() {
		return damageBuffer;
	}

	public void setDamageBuffer(double damageBuffer) {
		this.damageBuffer = damageBuffer;
	}

	public int getWarningDistance() {
		return warningDistance;
	}

	public void setWarningDistance(int warningDistance) {
		this.warningDistance = warningDistance;
	}

	public double getMaxRadius() {
		return maxRadius;
	}

	public void setMaxRadius(double maxRadius) {
		this.maxRadius = maxRadius;
	}

	public double getMinRadius() {
		return minRadius;
	}

	public void setMinRadius(double minRadius) {
		this.minRadius = minRadius;
	}

	public double getShrinkSpeed() {
		return shrinkSpeed;
	}

	public void setShrinkSpeed(double shrinkSpeed) {
		this.shrinkSpeed = shrinkSpeed;
	}

	public void setHideName(boolean isHideName) {
		this.isHideName = isHideName;
	}

	public boolean getCompassMode() {
		return compassMode;
	}

	public void setCompassMode(boolean compassMode) {
		this.compassMode = compassMode;
	}

	public int getCompassColdDown() {
		return compassColdDown;
	}

	public void setCompassColdDown(int compassColdDown) {
		this.compassColdDown = compassColdDown;
	}

	public ArrayList<Player> getAlivePlayers() {
		return alivePlayers;
	}

	public void setAlivePlayers(ArrayList<Player> alivePlayers) {
		this.alivePlayers = alivePlayers;
	}

	public int getAccelerateTicks() {
		return accelerateTicks;
	}

	public void setAccelerateTicks(int accelerateTicks) {
		this.accelerateTicks = accelerateTicks;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public Material getAccelerateItem() {
		return accelerateItem;
	}

	public void setAccelerateItem(Material accelerateItem) {
		this.accelerateItem = accelerateItem;
	}

	public int getGameLoadLatencyTime() {
		return gameLoadLatencyTime;
	}

	public void setGameLoadLatencyTime(int gameLoadLatencyTime) {
		this.gameLoadLatencyTime = gameLoadLatencyTime;
	}

	public WorldBorderHandler getWorldBorderHandler() {
		return worldBorderHandler;
	}

	public void setWorldBorderHandler(WorldBorderHandler worldBorderHandler) {
		this.worldBorderHandler = worldBorderHandler;
	}

	public BattleRoyale getBattleRoyale() {
		return battleRoyale;
	}

	public void setBattleRoyale(BattleRoyale battleRoyale) {
		this.battleRoyale = battleRoyale;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public AutoLapis getAutoLapis() {
		return autoLapis;
	}

	public void setAutoLapis(AutoLapis autoLapis) {
		this.autoLapis = autoLapis;
	}

	public AutoRespawn getAutoRespawn() {
		return autoRespawn;
	}

	public void setAutoRespawn(AutoRespawn autoRespawn) {
		this.autoRespawn = autoRespawn;
	}

	public GameEnd getGameEnd() {
		return gameEnd;
	}

	public void setGameEnd(GameEnd gameEnd) {
		this.gameEnd = gameEnd;
	}

	public GameLoad getGameLoad() {
		return gameLoad;
	}

	public void setGameLoad(GameLoad gameLoad) {
		this.gameLoad = gameLoad;
	}

	public GamePreset getGamePreset() {
		return gamePreset;
	}

	public void setGamePreset(GamePreset gamePreset) {
		this.gamePreset = gamePreset;
	}

	public GameProtection getGameProtection() {
		return gameProtection;
	}

	public void setGameProtection(GameProtection gameProtection) {
		this.gameProtection = gameProtection;
	}

	public GameStart getGameStart() {
		return gameStart;
	}

	public void setGameStart(GameStart gameStart) {
		this.gameStart = gameStart;
	}

	public PlayerAccelerateShrink getPlayerAccelerateShrink() {
		return playerAccelerateShrink;
	}

	public void setPlayerAccelerateShrink(PlayerAccelerateShrink playerAccelerateShrink) {
		this.playerAccelerateShrink = playerAccelerateShrink;
	}

	public PlayerDeath getPlayerDeath() {
		return playerDeath;
	}

	public void setPlayerDeath(PlayerDeath playerDeath) {
		this.playerDeath = playerDeath;
	}

	public PlayerQuit getPlayerQuit() {
		return playerQuit;
	}

	public void setPlayerQuit(PlayerQuit playerQuit) {
		this.playerQuit = playerQuit;
	}

	public PlayerThrowingTnt getPlayerThrowingTnt() {
		return playerThrowingTnt;
	}

	public void setPlayerThrowingTnt(PlayerThrowingTnt playerThrowingTnt) {
		this.playerThrowingTnt = playerThrowingTnt;
	}

	public PlayerUseCompass getPlayerUseCompass() {
		return playerUseCompass;
	}

	public void setPlayerUseCompass(PlayerUseCompass playerUseCompass) {
		this.playerUseCompass = playerUseCompass;
	}

	public AnimalProtection getAnimalProtection() {
		return animalProtection;
	}

	public void setAnimalProtection(AnimalProtection animalProtection) {
		this.animalProtection = animalProtection;
	}

	public boolean isProtectAnimal() {
		return isProtectAnimal;
	}

	public void setProtectAnimal(boolean isProtectAnimal) {
		this.isProtectAnimal = isProtectAnimal;
	}

	public boolean isAutoMelt() {
		return isAutoMelt;
	}

	public void setAutoMelt(boolean isAutoMelt) {
		this.isAutoMelt = isAutoMelt;
	}

	public OreAutoMelt getOreAutoMelt() {
		return oreAutoMelt;
	}

	public void setOreAutoMelt(OreAutoMelt oreAutoMelt) {
		this.oreAutoMelt = oreAutoMelt;
	}

	public boolean isWorldBorderShrinking() {
		return isWorldBorderShrinking;
	}

	public void setWorldBorderShrinking(boolean isWorldBorderShrinking) {
		this.isWorldBorderShrinking = isWorldBorderShrinking;
	}

	public WorldBorderStopShrink getWorldBorderStopShrink() {
		return worldBorderStopShrink;
	}

	public void setWorldBorderStopShrink(WorldBorderStopShrink worldBorderStopShrink) {
		this.worldBorderStopShrink = worldBorderStopShrink;
	}

	public SummonBlaze getSummonBlaze() {
		return summonBlaze;
	}

	public void setSummonBlaze(SummonBlaze summonBlaze) {
		this.summonBlaze = summonBlaze;
	}

	public double getAccelerateSpeed() {
		return accelerateSpeed;
	}

	public void setAccelerateSpeed(double accelerateSpeed) {
		this.accelerateSpeed = accelerateSpeed;
	}

	public int getAccelerateColdDown() {
		return accelerateColdDown;
	}

	public void setAccelerateColdDown(int accelerateColdDown) {
		this.accelerateColdDown = accelerateColdDown;
	}

	public int getAccelerateProtectTime() {
		return accelerateProtectTime;
	}

	public void setAccelerateProtectTime(int accelerateProtectTime) {
		this.accelerateProtectTime = accelerateProtectTime;
	}

}
