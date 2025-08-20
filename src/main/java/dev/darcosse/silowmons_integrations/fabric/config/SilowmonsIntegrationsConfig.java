package dev.darcosse.silowmons_integrations.fabric.config;

/**
 * Configuration principale du mod Silowmons Integrations
 */
public class SilowmonsIntegrationsConfig {
    public ScoreboardConfig scoreboardConfig = new ScoreboardConfig();

    public BattleTowerConfig battleTowerConfig = new BattleTowerConfig();

    public int scoreboardRefreshInterval = 1200; // En ticks
    public boolean enableScoreboardHolograms = true;
}