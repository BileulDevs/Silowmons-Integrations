package dev.darcosse.silowmons_integrations.fabric.config;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Configuration des scoreboards du mod
 */
public class BattleTowerConfig {
    public List<BattleTowerTrainer> trainers = new ArrayList<>();
    public List<BattleTowerArena> arenas = new ArrayList<>();

    public BattleTowerConfig() {
        trainers = Arrays.asList(
                new BattleTowerTrainer(
                        "test"
                ),
                new BattleTowerTrainer(
                        "test1"
                )
        );

        arenas = Arrays.asList(
                new BattleTowerArena(
                        1,
                        new BlockPos(175, -61, 285),
                        new BlockPos(180, -61, 285),
                        false
                )
        );
    }
}