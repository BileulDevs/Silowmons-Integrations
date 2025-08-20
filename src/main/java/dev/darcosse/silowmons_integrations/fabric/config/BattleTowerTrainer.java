package dev.darcosse.silowmons_integrations.fabric.config;

/**
 * Configuration des Trainers de la BattleTower
 */
public class BattleTowerTrainer {
    public String id;

    public BattleTowerTrainer() {}

    public BattleTowerTrainer(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}