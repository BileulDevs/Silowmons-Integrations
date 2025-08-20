package dev.darcosse.silowmons_integrations.fabric.config;

/**
 * Configuration principale du mod Silowmons Integrations
 */
public class SilowmonsIntegrationsConfig {

    // Configuration des scoreboards
    public ScoreboardConfig scoreboardConfig = new ScoreboardConfig();

    public int scoreboardRefreshInterval = 1200; // En ticks
    public boolean enableScoreboardHolograms = true;
}