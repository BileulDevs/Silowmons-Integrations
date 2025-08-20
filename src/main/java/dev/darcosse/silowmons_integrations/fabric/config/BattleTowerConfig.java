package dev.darcosse.silowmons_integrations.fabric.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration des scoreboards du mod
 */
public class BattleTowerConfig {
    public List<BattleTowerTrainer> trainers = new ArrayList<>();

    public BattleTowerConfig() {
        trainers = Arrays.asList(
                new BattleTowerTrainer(
                        "catch_count"
                ),
                new BattleTowerTrainer(
                        "registered"
                )
        );
    }
}