package dev.darcosse.silowmons_integrations.fabric.config;

import net.minecraft.util.math.Vec3d;

/**
 * Représente un objectif de scoreboard avec sa position et son nom d'affichage
 */
public class ScoreboardObjective {
    public String name;
    public String displayName;
    public Vec3d position;

    // Constructeur par défaut pour GSON
    public ScoreboardObjective() {}

    public ScoreboardObjective(String name, String displayName, Vec3d position) {
        this.name = name;
        this.displayName = displayName;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Vec3d getPosition() {
        return position;
    }
}