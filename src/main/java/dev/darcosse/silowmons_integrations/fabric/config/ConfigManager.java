package dev.darcosse.silowmons_integrations.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.darcosse.silowmons_integrations.fabric.SilowmonsIntegrations;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestionnaire de configuration pour le mod Silowmons Integrations
 */
public class ConfigManager {
    private static final String CONFIG_FILE = "silowmons_integrations.json";
    private static SilowmonsIntegrationsConfig config;
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void loadConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), CONFIG_FILE);

        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                config = GSON.fromJson(reader, SilowmonsIntegrationsConfig.class);

                if (config.scoreboardConfig == null) {
                    config.scoreboardConfig = new ScoreboardConfig();
                }
                if (config.scoreboardConfig.objectives == null || config.scoreboardConfig.objectives.isEmpty()) {
                    config.scoreboardConfig = new ScoreboardConfig();
                }

            } catch (IOException e) {
                SilowmonsIntegrations.LOGGER.error("Failed to load config, creating default: {}", e.getMessage());
                config = new SilowmonsIntegrationsConfig();
                saveConfig();
            }
        } else {
            config = new SilowmonsIntegrationsConfig();
            saveConfig();
        }

        SilowmonsIntegrations.LOGGER.info("[ConfigManager] Successfully loaded config with {} scoreboard(s)",
                getObjectives().size());
    }

    public static void saveConfig() {
        File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), CONFIG_FILE);
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(config, writer);
            SilowmonsIntegrations.LOGGER.info("[ConfigManager] Config saved successfully");
        } catch (IOException e) {
            SilowmonsIntegrations.LOGGER.error("Failed to save config: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static SilowmonsIntegrationsConfig getConfig() {
        if (config == null) loadConfig();
        return config;
    }

    public static void reloadConfig() {
        config = null;
        loadConfig();
    }

    /**
     * Obtient tous les objectifs de scoreboard configurés
     */
    public static List<ScoreboardObjective> getObjectives() {
        if (config == null) loadConfig();
        if (config.scoreboardConfig == null || config.scoreboardConfig.objectives == null) {
            return new ArrayList<>();
        }
        return config.scoreboardConfig.objectives;
    }

    /**
     * Obtient l'intervalle de rafraîchissement des scoreboards
     */
    public static int getScoreboardRefreshInterval() {
        if (config == null) loadConfig();
        return config.scoreboardRefreshInterval;
    }

    /**
     * Vérifie si les hologrammes de scoreboard sont activés
     */
    public static boolean isScoreboardHologramsEnabled() {
        if (config == null) loadConfig();
        return config.enableScoreboardHolograms;
    }

    /**
     * Obtient tous les Trainers de la BattleTower configurés
     */
    public static List<BattleTowerTrainer> getBattleTowerTrainers() {
        if (config == null) loadConfig();
        if (config.battleTowerConfig == null || config.battleTowerConfig.trainers == null) {
            return new ArrayList<>();
        }
        return config.battleTowerConfig.trainers;
    }

    /**
     * Obtient tous les Trainers de la BattleTower configurés
     */
    public static List<BattleTowerArena> getBattleTowerArenas() {
        if (config == null) loadConfig();
        if (config.battleTowerConfig == null || config.battleTowerConfig.arenas == null) {
            return new ArrayList<>();
        }
        return config.battleTowerConfig.arenas;
    }
}