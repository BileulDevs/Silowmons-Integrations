package dev.darcosse.silowmons_integrations.fabric.registry;

import dev.darcosse.silowmons_integrations.fabric.config.BattleTowerArena;

import java.util.*;

public class BattleTowerRegistry {
    private static final Map<UUID, BattleTowerArena> trainers = new HashMap<>();

    public static void addTrainer(UUID uuid, BattleTowerArena arena) {
        trainers.put(uuid, arena);
    }

    public static void removeTrainer(UUID uuid) {
        trainers.remove(uuid);
    }

    public static Set<UUID> getTrainers() {
        return trainers.keySet();
    }

    public static boolean isTrainer(UUID uuid) {
        return trainers.containsKey(uuid);
    }

    public static BattleTowerArena getArena(UUID uuid) {
        return trainers.get(uuid);
    }
}
